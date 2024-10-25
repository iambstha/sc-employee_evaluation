package org.base.service.competencyGroup;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.base.dto.CompetencyGroupReqDto;
import org.base.dto.CompetencyGroupResDto;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyGroupMapper;
import org.base.mapper.CompetencyGroupCommentMapper;
import org.base.model.CompetencyGroup;
import org.base.repository.CompetencyGroupRepository;
import org.base.repository.CompetencyGroupCommentRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class CompetencyGroupServiceImpl implements CompetencyGroupService {

    @Inject
    CompetencyGroupRepository competencyGroupRepository;

    @Inject
    CompetencyGroupCommentRepository competencyGroupCommentRepository;

    @Inject
    CompetencyGroupMapper competencyGroupMapper;

    @Inject
    CompetencyGroupCommentMapper competencyGroupCommentMapper;

    @Override
    public CompetencyGroupResDto save(CompetencyGroupReqDto competencyGroupReqDto) {
        try {
            if(competencyGroupReqDto.getCompetencyGroupId() != null){
                Optional<CompetencyGroup> existingCompetencyGroup = competencyGroupRepository.findByCompetencyGroupId(competencyGroupReqDto.getCompetencyGroupId());

                if (existingCompetencyGroup.isPresent()) {
                    throw new ResourceAlreadyExistsException("Competency group with id " + competencyGroupReqDto.getCompetencyGroupId() + " already exists.");
                }
            }

            CompetencyGroup competencyGroup = competencyGroupMapper.toEntity(competencyGroupReqDto);
            competencyGroup.setCreatedBy(null);

            competencyGroupRepository.persist(competencyGroup);

            return competencyGroupMapper.toResDto(competencyGroup);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CompetencyGroupResDto> getAll() {
        try {
            return  competencyGroupRepository.listAll()
                    .stream()
                    .map(competencyGroupMapper::toResDto)
                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group: " + e.getMessage(), e);
        }
    }

    @Override
    public CompetencyGroupResDto getById(Long id) {
        CompetencyGroup competencyGroup = competencyGroupRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competency group with ID " + id + " not found."));

        return competencyGroupMapper.toResDto(competencyGroup);
    }

    @Override
    public CompetencyGroupResDto updateById(Long id, CompetencyGroupReqDto competencyGroupReqDto) {
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            competencyGroupRepository.deleteById(id);
        } catch (Exception e) {
            throw new ResourceNotFoundException("Competency group with ID " + id + " could not be deleted: " + e.getMessage());
        }
    }
}
