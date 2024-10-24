package org.base.service.competencyGroupAssessment;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.base.dto.CompetencyGroupAssessmentReqDto;
import org.base.dto.CompetencyGroupAssessmentResDto;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyGroupAssessmentMapper;
import org.base.mapper.CompetencyGroupCommentMapper;
import org.base.model.CompetencyGroupAssessment;
import org.base.model.CompetencyGroupComment;
import org.base.model.Guideline;
import org.base.repository.CompetencyGroupAssessmentRepository;
import org.base.repository.CompetencyGroupCommentRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class CompetencyGroupAssessmentServiceImpl implements CompetencyGroupAssessmentService {

    @Inject
    CompetencyGroupAssessmentRepository competencyGroupAssessmentRepository;

    @Inject
    CompetencyGroupCommentRepository competencyGroupCommentRepository;

    @Inject
    CompetencyGroupAssessmentMapper competencyGroupAssessmentMapper;

    @Inject
    CompetencyGroupCommentMapper competencyGroupCommentMapper;

    @Override
    public CompetencyGroupAssessmentResDto save(CompetencyGroupAssessmentReqDto competencyGroupAssessmentReqDto) {
        try {
            Optional<CompetencyGroupAssessment> existingCompetencyGroupAssessment = competencyGroupAssessmentRepository.findByCompetencyGroupAssessmentId(competencyGroupAssessmentReqDto.getCompetencyGroupAssessmentId());

            if (existingCompetencyGroupAssessment.isPresent()) {
                throw new ResourceAlreadyExistsException("Competency group assessment with id " + competencyGroupAssessmentReqDto.getCompetencyGroupAssessmentId() + " already exists.");
            }

            CompetencyGroupAssessment competencyGroupAssessment = competencyGroupAssessmentMapper.toEntity(competencyGroupAssessmentReqDto);
            List<CompetencyGroupComment> competencyGroupComments = competencyGroupAssessmentMapper.mapCompetencyGroupCommentsFromDtos(competencyGroupAssessmentReqDto.getCompetencyGroupComments(), competencyGroupAssessment);
            competencyGroupAssessment.setCompetencyGroupComments(competencyGroupComments);
            competencyGroupAssessment.setCreatedBy(null);

            competencyGroupAssessmentRepository.persist(competencyGroupAssessment);

            return competencyGroupAssessmentMapper.toResDto(competencyGroupAssessment);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CompetencyGroupAssessmentResDto> getAll() {
        try {
            return  competencyGroupAssessmentRepository.listAll()
                    .stream()
                    .map(competencyGroupAssessmentMapper::toResDto)
                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group assessments: " + e.getMessage(), e);
        }
    }

    @Override
    public CompetencyGroupAssessmentResDto getById(Long id) {
        CompetencyGroupAssessment competencyGroupAssessment = competencyGroupAssessmentRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competency group assessment with ID " + id + " not found."));

        return competencyGroupAssessmentMapper.toResDto(competencyGroupAssessment);
    }

    @Override
    public CompetencyGroupAssessmentResDto updateById(Long id, CompetencyGroupAssessmentReqDto competencyGroupAssessmentReqDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            competencyGroupAssessmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Competency group assessment with ID " + id + " could not be deleted: " + e.getMessage());
        }
    }
}
