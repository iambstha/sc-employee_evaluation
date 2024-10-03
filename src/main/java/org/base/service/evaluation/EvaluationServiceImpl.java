package org.base.service.evaluation;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.base.dto.EvaluationReqDto;
import org.base.dto.EvaluationResDto;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.EvaluationMapper;
import org.base.model.CompetencyEvaluation;
import org.base.model.Evaluation;
import org.base.repository.EvaluationRepository;

import java.util.List;

@ApplicationScoped
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    @Inject
    EvaluationRepository evaluationRepository;

    @Inject
    EvaluationMapper evaluationMapper;


    @Override
    public EvaluationResDto save(EvaluationReqDto evaluationReqDto) {
        return null;
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
