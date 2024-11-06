package org.base.service.competencyEvaluation;

import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.base.dto.CompetencyEvaluationReqDto;
import org.base.dto.CompetencyEvaluationResDto;
import org.base.exception.BadRequestException;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyEvaluationMapper;
import org.base.mapper.ScoreMapper;
import org.base.model.Competency;
import org.base.model.CompetencyEvaluation;
import org.base.model.Evaluation;
import org.base.model.Score;
import org.base.repository.CompetencyEvaluationRepository;
import org.base.repository.CompetencyRepository;
import org.base.repository.EvaluationRepository;
import org.base.repository.ScoreRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class CompetencyEvaluationServiceImpl implements CompetencyEvaluationService {

    @Inject
    CompetencyEvaluationRepository competencyEvaluationRepository;

    @Inject
    CompetencyRepository competencyRepository;

    @Inject
    ScoreMapper scoreMapper;

    @Inject
    ScoreRepository scoreRepository;

    @Inject
    EvaluationRepository evaluationRepository;

    @Inject
    CompetencyEvaluationMapper competencyEvaluationMapper;

    private static final List<String> ALLOWED_SORT_COLUMNS = List.of("competencyEvaluationId");

    @Override
    public CompetencyEvaluationResDto save(CompetencyEvaluationReqDto competencyEvaluationReqDto) {
            try{
                if (competencyEvaluationReqDto.getCompetencyEvaluationId() != null) {
                    Optional<CompetencyEvaluation> existingCompetencyEvaluation = competencyEvaluationRepository.findByIdOptional(competencyEvaluationReqDto.getCompetencyEvaluationId());

                    if (existingCompetencyEvaluation.isPresent()) {
                        throw new ResourceAlreadyExistsException("Competency evaluation with id " + competencyEvaluationReqDto.getCompetencyEvaluationId() + " already exists.");
                    }
                }

            CompetencyEvaluation competencyEvaluation = competencyEvaluationMapper.toEntity(competencyEvaluationReqDto);

            Competency existingCompetency = competencyRepository.findById(competencyEvaluationReqDto.getCompetencyId());
            if (existingCompetency != null) {
                competencyEvaluation.setCompetency(existingCompetency);
            } else {
                throw new BadRequestException("Competency with id " + competencyEvaluationReqDto.getCompetencyId() + " not found.");
            }

            Evaluation existingEvaluation = evaluationRepository.findById(competencyEvaluationReqDto.getEvaluationId());
            if(existingEvaluation != null) {
                competencyEvaluation.setEvaluation(existingEvaluation);
            }else{
                throw new ResourceAlreadyExistsException("Evaluation with id " + competencyEvaluationReqDto.getEvaluationId() + " not found.");
            }

            if (competencyEvaluationReqDto.getScore() != null) {
                Score score = scoreMapper.toEntity(competencyEvaluationReqDto.getScore());
                scoreRepository.persist(score);
            }
            competencyEvaluation.setCreatedBy(null);
            competencyEvaluationRepository.persist(competencyEvaluation);
            return competencyEvaluationMapper.toResDto(competencyEvaluation);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CompetencyEvaluationResDto> getPaginated(int page, int size, String sortDirection, String sortColumn) {
        try {
            page = Math.max(page, 0);
            size = Math.max(size, 0);

            String direction = (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) ? "Descending" : "Ascending";
            String sortBy = (sortColumn != null && !sortColumn.isEmpty()) ? sortColumn : ALLOWED_SORT_COLUMNS.getFirst();

            if (!ALLOWED_SORT_COLUMNS.contains(sortBy)) {
                throw new BadRequestException("Invalid sort column: " + sortBy);
            }

            return  competencyEvaluationRepository
                    .findAll(Sort.by(sortBy, Sort.Direction.valueOf(direction)))
                    .page(Page.of(page, size))
                    .list()
                    .stream()
                    .map(competencyEvaluationMapper::toResDto)
                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency evaluation: " + e.getMessage());
        }
    }

    @Override
    public CompetencyEvaluationResDto getById(Long id) {
        CompetencyEvaluation competencyEvaluation = competencyEvaluationRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competency evaluation with ID " + id + " not found."));

        return competencyEvaluationMapper.toResDto(competencyEvaluation);
    }

    @Override
    public CompetencyEvaluationResDto updateById(Long id, CompetencyEvaluationReqDto competencyEvaluationReqDto) {
        try{
            CompetencyEvaluation existingCompetencyEvaluation = competencyEvaluationRepository.findByIdOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Competency evaluation with ID " + id + " not found."));

            competencyEvaluationMapper.updateEntityFromDto(competencyEvaluationReqDto, existingCompetencyEvaluation);

            Competency existingCompetency = competencyRepository.findById(competencyEvaluationReqDto.getCompetencyId());
            if (existingCompetency != null) {
                existingCompetencyEvaluation.setCompetency(existingCompetency);
            } else {
                throw new ResourceNotFoundException("Competency with ID " + competencyEvaluationReqDto.getCompetencyId() + " not found.");
            }

            Evaluation existingEvaluation = evaluationRepository.findById(competencyEvaluationReqDto.getEvaluationId());
            if (existingEvaluation != null) {
                existingCompetencyEvaluation.setEvaluation(existingEvaluation);
            } else {
                throw new ResourceNotFoundException("Evaluation with ID " + competencyEvaluationReqDto.getEvaluationId() + " not found.");
            }

            Score score = scoreMapper.toEntity(competencyEvaluationReqDto.getScore());
            if (score != null) {
                if (score.getScoreId() == null) {
                    scoreRepository.persist(score);
                } else {
                    scoreRepository.getEntityManager().merge(score);
                }
            }

            competencyEvaluationRepository.getEntityManager().merge(existingCompetencyEvaluation);

            return competencyEvaluationMapper.toResDto(existingCompetencyEvaluation);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            competencyEvaluationRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public long countTotal() {
        return competencyEvaluationRepository.count();
    }

}
