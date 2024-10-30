package org.base.service.competencyGroupComment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.exception.BadRequestException;
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

import java.util.ArrayList;
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
                    throw new ResourceAlreadyExistsException("Comment with employeeId " + competencyGroupCommentReqDto.getCommentId() + " already exists.");
                }
                CompetencyGroupComment competencyGroupComment = competencyGroupCommentMapper.toEntity(competencyGroupCommentReqDto);
                competencyGroupComment.setCreatedBy(null);

                competencyGroupCommentRepository.persist(competencyGroupComment);

                return competencyGroupCommentMapper.toResDto(competencyGroupComment);
            }else{
                if(competencyGroupCommentReqDto.getEmployeeType() == EmployeeType.EMPLOYEE){
                    throw new BadRequestException("Employee can only add comments during pre year.");
                }
                Optional<CompetencyGroupCommentHigher> existingCompetencyGroupCommentHigher = competencyGroupCommentHigherRepository.findByCompetencyGroupCommentHigherId(competencyGroupCommentReqDto.getCommentId());

                if (existingCompetencyGroupCommentHigher.isPresent()) {
                    throw new ResourceAlreadyExistsException("Comment higher with employeeId " + competencyGroupCommentReqDto.getCommentId() + " already exists.");
                }
                CompetencyGroupCommentHigher competencyGroupCommentHigher = competencyGroupCommentHigherMapper.toEntity(competencyGroupCommentReqDto);
                competencyGroupCommentHigher.setCreatedBy(null);

                competencyGroupCommentHigherRepository.persist(competencyGroupCommentHigher);

                return competencyGroupCommentHigherMapper.toResDto(competencyGroupCommentHigher);
            }

        } catch (ResourceAlreadyExistsException e) {
            throw new ResourceAlreadyExistsException(e.getMessage());
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CompetencyGroupCommentResDto> getPaginated(int page, int size) {
        try {

            List<CompetencyGroupComment> competencyGroupComments = competencyGroupCommentRepository
                    .findAll()
                    .page(page, size)
                    .list();
            List<CompetencyGroupCommentHigher> competencyGroupCommentHighers = competencyGroupCommentHigherRepository
                    .findAll()
                    .page(page, size)
                    .list();

            List<CompetencyGroupCommentResDto> competencyGroupCommentResDtos = new ArrayList<>(competencyGroupComments
                    .stream()
                    .map(competencyGroupCommentMapper::toResDto)
                    .toList());
            competencyGroupCommentResDtos.addAll(competencyGroupCommentHighers
                    .stream()
                    .map(competencyGroupCommentHigherMapper::toResDto)
                    .toList());
            return competencyGroupCommentResDtos;
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group comments: " + e.getMessage());
        }
    }

    @Override
    public CompetencyGroupCommentResDto getByIdAndReviewStage(Long id, ReviewStage reviewStage) {
        CompetencyGroupComment competencyGroupComment = competencyGroupCommentRepository.findByIdAndReviewStageOptional(id, reviewStage)
                .orElseThrow(() -> new ResourceNotFoundException("Comment with ID " + id + " and review stage " + reviewStage + " not found."));

        return competencyGroupCommentMapper.toResDto(competencyGroupComment);
    }

    @Override
    public List<CompetencyGroupCommentResDto> getByFilters(EmployeeType employeeType, Long competencyGroupId, Long evaluationId, Long employeeId) {
        try{
            CompetencyGroup competencyGroup = null;
            Evaluation evaluation;

            if(competencyGroupId != null){
                competencyGroup = competencyGroupRepository.findById(competencyGroupId);
                if(competencyGroup == null){
                    throw new ResourceNotFoundException("Competency group with ID " + competencyGroupId + " not found.");
                }
            }
            if(evaluationId != null) {
                evaluation = evaluationRepository.findById(evaluationId);
                if (evaluation == null) {
                    throw new ResourceNotFoundException("Evaluation with ID " + evaluationId + " not found.");
                }
            }else{
                throw new BadRequestException("Evaluation id is required");
            }

            List<CompetencyGroupComment> competencyGroupComments = competencyGroupCommentRepository
                    .findByOptionalFilters(employeeType, competencyGroup, evaluation, employeeId);
            List<CompetencyGroupCommentResDto> competencyGroupCommentResDtos = new ArrayList<>(competencyGroupComments
                    .stream()
                    .map(competencyGroupCommentMapper::toResDto)
                    .toList());
           if(evaluation.getReviewStage() == ReviewStage.PRE_YEAR){
                return competencyGroupComments.stream().map(competencyGroupCommentMapper::toResDto).toList();
           }
           List<CompetencyGroupCommentHigher> competencyGroupCommentHighers = competencyGroupCommentHigherRepository
                    .findByOptionalFilters(employeeType, competencyGroup, evaluation, employeeId);
           competencyGroupCommentResDtos.addAll(competencyGroupCommentHighers
                    .stream()
                    .map(competencyGroupCommentHigherMapper::toResDto)
                    .toList());
           return competencyGroupCommentResDtos;
        } catch (ResourceNotFoundException e){
            throw new ResourceNotFoundException(e.getMessage());
        } catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public CompetencyGroupCommentResDto updateByIdAndReviewStage(Long id, CompetencyGroupCommentReqDto competencyGroupCommentReqDto, ReviewStage reviewStage) {
        try {
            if(reviewStage == ReviewStage.PRE_YEAR) {
                CompetencyGroupComment existingCompetenceGroupComment = competencyGroupCommentRepository.findByIdAndReviewStageOptional(id, reviewStage)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment with ID " + id + " and review stage " + reviewStage + " not found."));

                competencyGroupCommentMapper.updateEntityFromDto(competencyGroupCommentReqDto, existingCompetenceGroupComment);

                existingCompetenceGroupComment.setUpdatedBy(null);
                competencyGroupCommentRepository.getEntityManager().merge(existingCompetenceGroupComment);

                return competencyGroupCommentMapper.toResDto(existingCompetenceGroupComment);
            }else{
                CompetencyGroupCommentHigher existingCompetenceGroupCommentHigher = competencyGroupCommentHigherRepository.findByIdAndReviewStageOptional(id, reviewStage)
                        .orElseThrow(() -> new ResourceNotFoundException("Comment with ID " + id + " and review stage " + reviewStage + " not found."));

                competencyGroupCommentHigherMapper.updateEntityFromDto(competencyGroupCommentReqDto, existingCompetenceGroupCommentHigher);

                existingCompetenceGroupCommentHigher.setUpdatedBy(null);
                competencyGroupCommentHigherRepository.getEntityManager().merge(existingCompetenceGroupCommentHigher);

                return competencyGroupCommentHigherMapper.toResDto(existingCompetenceGroupCommentHigher);
            }
        } catch (ResourceNotFoundException e){
            throw e;
        }catch (Exception e) {
            throw new BadRequestException("Comment could not be updated" + e.getMessage());
        }
    }

    @Override
    public void deleteByIdAndReviewStage(Long id, ReviewStage reviewStage) {
        try {
            getByIdAndReviewStage(id, reviewStage);
            competencyGroupCommentRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Comment with ID " + id + " and review stage " + reviewStage + " could not be deleted: " + e.getMessage());
        }
    }

    public long countTotal() {
        return competencyGroupCommentRepository.count();
    }

}
