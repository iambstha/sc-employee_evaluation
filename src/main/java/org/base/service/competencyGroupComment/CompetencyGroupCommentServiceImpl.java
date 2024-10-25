package org.base.service.competencyGroupComment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import org.base.dto.CompetencyGroupCommentReqDto;
import org.base.dto.CompetencyGroupCommentResDto;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyGroupCommentMapper;
import org.base.model.CompetencyGroup;
import org.base.model.CompetencyGroupComment;
import org.base.model.enums.EmployeeType;
import org.base.repository.CompetencyGroupCommentRepository;
import org.base.repository.CompetencyGroupRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CompetencyGroupCommentServiceImpl implements CompetencyGroupCommentService {

    @Inject
    CompetencyGroupCommentRepository competencyGroupCommentRepository;

    @Inject
    CompetencyGroupCommentMapper competencyGroupCommentMapper;

    @Inject
    CompetencyGroupRepository competencyGroupRepository;


    @Override
    public CompetencyGroupCommentResDto save(CompetencyGroupCommentReqDto competencyGroupCommentReqDto) {
        try {
            Optional<CompetencyGroupComment> existingCompetencyGroupComment = competencyGroupCommentRepository.findByCompetencyGroupCommentId(competencyGroupCommentReqDto.getCompetencyGroupCommentId());

            if (existingCompetencyGroupComment.isPresent()) {
                throw new ResourceAlreadyExistsException("Competency group comment with employeeId " + competencyGroupCommentReqDto.getCompetencyGroupCommentId() + " already exists.");
            }

            CompetencyGroupComment competencyGroupComment = competencyGroupCommentMapper.toEntity(competencyGroupCommentReqDto);
            competencyGroupComment.setCreatedBy(null);

            competencyGroupCommentRepository.persist(competencyGroupComment);

            return competencyGroupCommentMapper.toResDto(competencyGroupComment);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CompetencyGroupCommentResDto> getAll() {
        try {
            return  competencyGroupCommentRepository.listAll()
                    .stream()
                    .map(competencyGroupCommentMapper::toResDto)
                    .toList();
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
    public List<CompetencyGroupCommentResDto> getByFilters(EmployeeType employeeType, Long competencyGroupId, Long employeeId) {
        try{
            CompetencyGroup competencyGroup = null;

            if(competencyGroupId != null){
                competencyGroup = competencyGroupRepository.findById(competencyGroupId);
            }
            List<CompetencyGroupComment> competencyGroupComments = competencyGroupCommentRepository.findByOptionalFilters(employeeType, competencyGroup, employeeId);

            return competencyGroupComments.stream().map(competencyGroupCommentMapper::toResDto).toList();
        }catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group comments: " + e.getMessage(), e);
        }
    }

    @Override
    public CompetencyGroupCommentResDto updateById(Long id, CompetencyGroupCommentReqDto competencyGroupCommentReqDto) {
        try {
            CompetencyGroupComment existingCompetenceGroupComment = competencyGroupCommentRepository.findByIdOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Competence group comment with ID " + id + " not found."));

            competencyGroupCommentMapper.updateEntityFromDto(competencyGroupCommentReqDto, existingCompetenceGroupComment);

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
