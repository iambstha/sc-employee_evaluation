package org.base.service.competencyGroupComment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyGroupCommentHigherMapper;
import org.base.mapper.CompetencyGroupCommentMapper;
import org.base.model.CompetencyGroup;
import org.base.model.CompetencyGroupComment;
import org.base.model.CompetencyGroupCommentHigher;
import org.base.model.Evaluation;
import org.base.model.enums.EmployeeType;
import org.base.model.enums.ReviewStage;
import org.base.repository.CompetencyGroupCommentHigherRepository;
import org.base.repository.CompetencyGroupCommentRepository;
import org.base.repository.CompetencyGroupRepository;
import org.base.repository.EvaluationRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class CompetencyGroupCommentServiceImpl implements CompetencyGroupCommentService {

    @Inject
    CompetencyGroupCommentRepository competencyGroupCommentRepository;

    @Inject
    CompetencyGroupCommentHigherRepository competencyGroupCommentHigherRepository;

    @Inject
    CompetencyGroupCommentMapper competencyGroupCommentMapper;

    @Inject
    CompetencyGroupCommentHigherMapper competencyGroupCommentHigherMapper;

    @Inject
    CompetencyGroupRepository competencyGroupRepository;

    @Inject
    EvaluationRepository evaluationRepository;

    @Override
    public CompetencyGroupCommentResDto save(CompetencyGroupCommentReqDto competencyGroupCommentReqDto) {
        try {
            Evaluation evaluation = evaluationRepository.findById(competencyGroupCommentReqDto.getEvaluationId());
            if(evaluation.getReviewStage() == ReviewStage.PRE_YEAR){
                Optional<CompetencyGroupComment> existingCompetencyGroupComment = competencyGroupCommentRepository.findByCompetencyGroupCommentId(competencyGroupCommentReqDto.getCommentId());

                if (existingCompetencyGroupComment.isPresent()) {
                    throw new ResourceAlreadyExistsException("Competency group comment with employeeId " + competencyGroupCommentReqDto.getCommentId() + " already exists.");
                }
                CompetencyGroupComment competencyGroupComment = competencyGroupCommentMapper.toEntity(competencyGroupCommentReqDto);
                competencyGroupComment.setCreatedBy(null);

                competencyGroupCommentRepository.persist(competencyGroupComment);

                return competencyGroupCommentMapper.toResDto(competencyGroupComment);
            }else{
                Optional<CompetencyGroupCommentHigher> existingCompetencyGroupCommentHigher = competencyGroupCommentHigherRepository.findByCompetencyGroupCommentHigherId(competencyGroupCommentReqDto.getCommentId());

                if (existingCompetencyGroupCommentHigher.isPresent()) {
                    throw new ResourceAlreadyExistsException("Competency group comment higher with employeeId " + competencyGroupCommentReqDto.getCommentId() + " already exists.");
                }
                CompetencyGroupCommentHigher competencyGroupCommentHigher = competencyGroupCommentHigherMapper.toEntity(competencyGroupCommentReqDto);
                competencyGroupCommentHigher.setCreatedBy(null);

                competencyGroupCommentHigherRepository.persist(competencyGroupCommentHigher);

                return competencyGroupCommentHigherMapper.toResDto(competencyGroupCommentHigher);
            }

        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CompetencyGroupCommentResDto> getAll() {
        try {

            return competencyGroupCommentRepository.listAll()
                    .stream()
                    .filter(competencyGroupComment -> {
                        if(competencyGroupComment.getEvaluation() != null){
                            if (competencyGroupComment.getEvaluation().getReviewStage().equals(ReviewStage.PRE_YEAR)) {
                                return ((competencyGroupComment.getEmployeeType() == EmployeeType.EMPLOYEE || competencyGroupComment.getEmployeeType() == EmployeeType.LINE_MANAGER)
                                        && competencyGroupComment.getReviewStage() == ReviewStage.PRE_YEAR);
                            }
                        }
                        return true;
                    })
                    .map(competencyGroupCommentMapper::toResDto)
                    .toList();


//            return  competencyGroupCommentRepository.listAll()
//                    .stream()
//                    .map(competencyGroupCommentMapper::toResDto)
//                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group comments: " + e.getMessage(), e);
        }
    }

    @Override
    public CompetencyGroupCommentResDto getById(Long id) {
        CompetencyGroupComment competencyGroupComment = competencyGroupCommentRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competence group comment with ID " + id + " not found."));

        return competencyGroupCommentMapper.toResDto(competencyGroupComment);
    }

    @Override
    public List<CompetencyGroupCommentResDto> getByFilters(EmployeeType employeeType, Long competencyGroupId, Long evaluationId, Long employeeId) {
        try{
            CompetencyGroup competencyGroup = null;
            Evaluation evaluation = null;

            if(competencyGroupId != null){
                competencyGroup = competencyGroupRepository.findById(competencyGroupId);
                if(competencyGroup == null){
                    throw new ResourceNotFoundException("Competency group with ID " + competencyGroupId + " not found.");
                }
            }
            if(evaluationId != null){
                evaluation = evaluationRepository.findById(evaluationId);
                if(evaluation == null){
                    throw new ResourceNotFoundException("Evaluation with ID " + evaluationId + " not found.");
                }
            }
            List<CompetencyGroupComment> competencyGroupComments = competencyGroupCommentRepository.findByOptionalFilters(employeeType, competencyGroup, evaluation, employeeId);

            return competencyGroupComments.stream().map(competencyGroupCommentMapper::toResDto).toList();
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group comments: " + e.getMessage(), e);
        }
    }

    @Override
    public CompetencyGroupCommentResDto updateById(Long id, CompetencyGroupCommentReqDto competencyGroupCommentReqDto) {
        try {
            CompetencyGroupComment existingCompetenceGroupComment = competencyGroupCommentRepository.findByIdOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Competence group comment with ID " + id + " not found."));

            competencyGroupCommentMapper.updateEntityFromDto(competencyGroupCommentReqDto, existingCompetenceGroupComment);

            existingCompetenceGroupComment.setUpdatedBy(null);
            competencyGroupCommentRepository.getEntityManager().merge(existingCompetenceGroupComment);

            return competencyGroupCommentMapper.toResDto(existingCompetenceGroupComment);
        } catch (ResourceNotFoundException e){
            throw e;
        }catch (Exception e) {
            throw new BadRequestException("Competency group comment could not be updated" + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            competencyGroupCommentRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Competency group comment with ID " + id + " could not be deleted: " + e.getMessage());
        }
    }
}
