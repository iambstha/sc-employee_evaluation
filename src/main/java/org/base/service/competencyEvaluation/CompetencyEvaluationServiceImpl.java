package org.base.service.competencyEvaluation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.base.dto.CompetencyEvaluationReqDto;
import org.base.dto.CompetencyEvaluationResDto;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyEvaluationMapper;
import org.base.model.CompetencyEvaluation;
import org.base.repository.CompetencyEvaluationRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class CompetencyEvaluationServiceImpl implements CompetencyEvaluationService {

    @Inject
    CompetencyEvaluationRepository competencyEvaluationRepository;

    @Inject
    CompetencyEvaluationMapper competencyEvaluationMapper;


    @Override
    public CompetencyEvaluationResDto save(CompetencyEvaluationReqDto competencyEvaluationReqDto) {
        return null;
    }

    @Override
    public List<CompetencyEvaluationResDto> getAll() {
        try {
            return  competencyEvaluationRepository.listAll()
                    .stream()
                    .map(competencyEvaluationMapper::toResDto)
                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency evaluation: " + e.getMessage(), e);
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
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            competencyEvaluationRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Competency evaluation with ID " + id + " could not be deleted: " + e.getMessage());
        }
    }
}
