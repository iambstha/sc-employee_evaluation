package org.base.service.evaluation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.EvaluationMapper;
import org.base.model.Competency;
import org.base.model.CompetencyEvaluation;
import org.base.model.Evaluation;
import org.base.repository.CompetencyRepository;
import org.base.repository.EvaluationRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    @Inject
    EvaluationRepository evaluationRepository;

    @Inject
    CompetencyRepository competencyRepository;

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

            for (CompetencyEvaluation competencyEvaluation : competencyEvaluations) {
                Competency competency = competencyEvaluation.getCompetency();
                if (competency.getCompetencyId() != null) {
                    Competency existingCompetency = competencyRepository.findById(competency.getCompetencyId());
                    if (existingCompetency != null) {
                        competencyEvaluation.setCompetency(existingCompetency);
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
    public List<EvaluationResDto> getAll() {
        try {
            return  evaluationRepository.listAll()
                    .stream()
                    .map(evaluationMapper::toResDto)
                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency evaluation: " + e.getMessage(), e);
        }
    }

    @Override
    public EvaluationResDto getById(Long id) {
        Evaluation evaluation = evaluationRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competency evaluation with ID " + id + " not found."));

        return evaluationMapper.toResDto(evaluation);
    }

    @Override
    public EvaluationResDto updateById(Long id, EvaluationReqDto evaluationReqDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            evaluationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Competency evaluation with ID " + id + " could not be deleted: " + e.getMessage());
        }
    }
}
