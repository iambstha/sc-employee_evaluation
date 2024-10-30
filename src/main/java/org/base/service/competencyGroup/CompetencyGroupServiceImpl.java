package org.base.service.competencyGroup;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.base.dto.CompetencyGroupReqDto;
import org.base.dto.CompetencyGroupResDto;
import org.base.exception.BadRequestException;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyGroupMapper;
import org.base.model.CompetencyGroup;
import org.base.model.enums.CompetencyStatus;
import org.base.model.enums.CompetencyType;
import org.base.repository.CompetencyGroupRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@ApplicationScoped
@Transactional
public class CompetencyGroupServiceImpl implements CompetencyGroupService {

    @Inject
    CompetencyGroupRepository competencyGroupRepository;

    @Inject
    CompetencyGroupMapper competencyGroupMapper;

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
    public List<CompetencyGroupResDto> getPaginated(int page, int size) {
        try {
            page = Math.max(page, 0);
            size = Math.max(size, 0);

            return  competencyGroupRepository.findAll()
                    .page(page, size)
                    .list()
                    .stream()
                    .map(competencyGroupMapper::toResDto)
                    .toList();
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group: " + e.getMessage());
        }
    }

    @Override
    public CompetencyGroupResDto getById(Long id) {
        CompetencyGroup competencyGroup = competencyGroupRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competency group with ID " + id + " not found."));

        return competencyGroupMapper.toResDto(competencyGroup);
    }

    @Override
    public List<CompetencyGroupResDto> getByFilters(CompetencyType competencyType, CompetencyStatus status) {
        try{
            List<CompetencyGroup> competencyGroups = competencyGroupRepository.findByOptionalFilters(competencyType, status);

            return competencyGroups.stream().map(competencyGroupMapper::toResDto).toList();
        }catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competency group: " + e.getMessage());
        }
    }

    @Override
    public CompetencyGroupResDto updateById(Long id, CompetencyGroupReqDto competencyGroupReqDto) {
        try {
            CompetencyGroup existingCompetenceGroup = competencyGroupRepository.findByIdOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Competence group with ID " + id + " not found."));

            competencyGroupMapper.updateEntityFromDto(competencyGroupReqDto, existingCompetenceGroup);
            competencyGroupRepository.getEntityManager().merge(existingCompetenceGroup);

            return competencyGroupMapper.toResDto(existingCompetenceGroup);
        } catch (ResourceNotFoundException e){
            throw e;
        }catch (Exception e) {
            throw new BadRequestException("Competency group could not be updated" + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            competencyGroupRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public long countTotal() {
        return competencyGroupRepository.count();
    }

}
