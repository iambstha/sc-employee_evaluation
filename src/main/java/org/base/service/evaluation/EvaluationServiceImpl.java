package org.base.service.evaluation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;
import org.base.exception.BadRequestException;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.EvaluationMapper;
import org.base.model.*;
import org.base.model.enums.ApprovalStage;
import org.base.model.enums.EvaluationByType;
import org.base.model.enums.ReviewStage;
import org.base.repository.CompetencyEvaluationRepository;
import org.base.repository.CompetencyRepository;
import org.base.repository.EvaluationRepository;
import org.base.repository.ScoreRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    @Inject
    EvaluationRepository evaluationRepository;

    @Inject
    CompetencyRepository competencyRepository;

    @Inject
    ScoreRepository scoreRepository;

    @Inject
    CompetencyEvaluationRepository competencyEvaluationRepository;

    @Inject
    EvaluationMapper evaluationMapper;


    @Override
    public EvaluationResDto save(EvaluationReqDto evaluationReqDto) {
        try {
            if (evaluationReqDto.getEvaluationId() != null) {
                Optional<Evaluation> existingEvaluation = evaluationRepository.findByIdOptional(evaluationReqDto.getEvaluationId());

                if (existingEvaluation.isPresent()) {
                    throw new ResourceAlreadyExistsException("Evaluation with id " + evaluationReqDto.getEvaluationId() + " already exists.");
                }
            }

            Evaluation evaluation = evaluationMapper.toEntity(evaluationReqDto);
            List<CompetencyEvaluation> competencyEvaluations = evaluationMapper.mapCompetencyEvaluationFromReqDtos(evaluationReqDto.getCompetencyEvaluations(), evaluation);

            if (evaluationReqDto.getCompetencyEvaluations() != null) {
                for (CompetencyEvaluation competencyEvaluation : competencyEvaluations) {
                    Competency competency = competencyEvaluation.getCompetency();
                    if (competency.getCompetencyId() != null) {
                        Competency existingCompetency = competencyRepository.findById(competency.getCompetencyId());
                        if (existingCompetency != null) {
                            competencyEvaluation.setCompetency(existingCompetency);
                        }
                    }
                    if (competencyEvaluation.getScore() != null) {
                        Score score = competencyEvaluation.getScore();
                        if (score.getScoreId() != null) {
                            Score existingScore = scoreRepository.findById(score.getScoreId());
                            if (existingScore != null) {
                                competencyEvaluation.setScore(existingScore);
                            } else {
                                scoreRepository.persist(score);
                            }
                        } else {
                            scoreRepository.persist(score);
                        }
                    }
                }
            }

            evaluation.setCompetencyEvaluations(competencyEvaluations);
            evaluation.setCreatedBy(null);

            evaluationRepository.persist(evaluation);

            return evaluationMapper.toResDto(evaluation);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }


    @Override
    public List<EvaluationResDto> getPaginated(int page, int size) {
        try {
            return evaluationRepository.findAll()
                    .page(page, size)
                    .list()
                    .stream()
                    .map(evaluationMapper::toResDto)
                    .toList();
        } catch (Exception e) {
            throw new BadRequestException("Error occurred while fetching competency evaluation: " + e.getMessage());
        }
    }

    @Override
    public EvaluationResDto getById(Long id) {
        Evaluation evaluation = evaluationRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competency evaluation with ID " + id + " not found."));

        return evaluationMapper.toResDto(evaluation);
    }

    @Override
    public List<EvaluationResDto> getByFilters(EvaluationByType evaluationByType, ReviewStage reviewStage, Long employeeId) {
        try {
            List<Evaluation> evaluations = evaluationRepository.findByOptionalFilters(evaluationByType, reviewStage, employeeId);

            return evaluations.stream().map(evaluationMapper::toResDto).toList();
        } catch (Exception e) {
            throw new BadRequestException("Error occurred while fetching competency evaluation: " + e.getMessage());
        }
    }

    @Override
    public EvaluationResDto updateById(Long id, EvaluationReqDto evaluationReqDto) {
        try {
            Evaluation existingEvaluation = evaluationRepository.findByIdOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluation with ID " + id + " not found."));

            evaluationMapper.updateEntityFromDto(evaluationReqDto, existingEvaluation);

            if (evaluationReqDto.getCompetencyEvaluations() != null && !evaluationReqDto.getCompetencyEvaluations().isEmpty()) {
                existingEvaluation.getCompetencyEvaluations().clear();

                List<CompetencyEvaluation> competencyEvaluations = evaluationMapper.mapCompetencyEvaluationFromReqDtos(
                        evaluationReqDto.getCompetencyEvaluations(), existingEvaluation
                );

                for (CompetencyEvaluation competencyEvaluation : competencyEvaluations) {
                    Score score = competencyEvaluation.getScore();
                    if (score != null) {
                        if (score.getScoreId() == null) {
                            scoreRepository.persist(score);
                        } else {
                            scoreRepository.getEntityManager().merge(score);
                        }
                    }
                }
                existingEvaluation.getCompetencyEvaluations().addAll(competencyEvaluations);
            }

            evaluationRepository.getEntityManager().merge(existingEvaluation);

            return evaluationMapper.toResDto(existingEvaluation);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public EvaluationResDto updateReviewStage(Long id, ReviewStage reviewStage, ApprovalStage approvalStage) {
        try {
            Evaluation existingEvaluation = evaluationRepository.findByIdOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Evaluation with ID " + id + " not found."));

            if(reviewStage != null || approvalStage != null){
                if(reviewStage != null) {
                    existingEvaluation.setReviewStage(reviewStage);
                }
                if(approvalStage != null){
                    existingEvaluation.setApprovalStage(approvalStage);
                }
            }else{
                throw new BadRequestException("Both review stage and approval stage cannot be null.");
            }
            evaluationRepository.getEntityManager().merge(existingEvaluation);
            return evaluationMapper.toResDto(existingEvaluation);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            Evaluation evaluation = evaluationRepository.findById(id);
            for (CompetencyEvaluation competencyEvaluation : evaluation.getCompetencyEvaluations()) {
                competencyEvaluationRepository.delete(competencyEvaluation);
            }
            evaluationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Competency evaluation with ID " + id + " could not be deleted: " + e.getMessage());
        }
    }

    public long countTotal() {
        return evaluationRepository.count();
    }

}
