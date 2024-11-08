package org.base.service.competency;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.base.dto.CompetencyReqDto;
import org.base.dto.CompetencyResDto;
import org.base.exception.BadRequestException;
import org.base.exception.IllegalArgumentException;
import org.base.exception.ResourceAlreadyExistsException;
import org.base.exception.ResourceNotFoundException;
import org.base.mapper.CompetencyMapper;
import org.base.model.Competency;
import org.base.model.Guideline;
import org.base.repository.CompetencyRepository;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
public class CompetencyServiceImpl implements CompetencyService {

    @Inject
    CompetencyRepository competencyRepository;

    @Inject
    CompetencyMapper competencyMapper;

    private static final List<String> ALLOWED_SORT_COLUMNS = List.of("competencyId", "competencyGroup", "name");

    @Override
    public CompetencyResDto save(CompetencyReqDto competencyReqDto) {
        try {
            Optional<Competency> existingCompetency = competencyRepository.findByCompetencyId(competencyReqDto.getCompetencyId());

            if (existingCompetency.isPresent()) {
                throw new ResourceAlreadyExistsException("Competency with employeeId " + competencyReqDto.getCompetencyId() + " already exists.");
            }

            Competency competency = competencyMapper.toEntity(competencyReqDto);
            List<Guideline> guidelines = competencyMapper.mapGuidelinesFromDtos(competencyReqDto.getGuidelines(), competency);
            competency.setGuidelines(guidelines);
            competency.setCreatedBy(null);

            competencyRepository.persist(competency);

            return competencyMapper.toResDto(competency);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    @Override
    public List<CompetencyResDto> getPaginated(int page, int size, String sortDirection, String sortColumn) {
        try {
            page = Math.max(page, 0);
            size = Math.max(size, 0);

            String direction = (sortDirection != null && sortDirection.equalsIgnoreCase("desc")) ? "Descending" : "Ascending";
            String sortBy = (sortColumn != null && !sortColumn.isEmpty()) ? sortColumn : ALLOWED_SORT_COLUMNS.getFirst();

            if (!ALLOWED_SORT_COLUMNS.contains(sortBy)) {
                throw new BadRequestException("Invalid sort column: " + sortBy);
            }

            return  competencyRepository
                    .findAll(Sort.by(sortBy, Sort.Direction.valueOf(direction)))
                    .page(Page.of(page, size))
                    .list()
                    .stream()
                    .map(competencyMapper::toResDto)
                    .toList();
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Invalid sort direction: " + sortDirection);
        } catch (Exception e){
            throw new BadRequestException("Error occurred while fetching competencies: " + e.getMessage());
        }
    }

    @Override
    public CompetencyResDto getById(Long id) {
        Competency competence = competencyRepository.findByIdOptional(id)
                .orElseThrow(() -> new ResourceNotFoundException("Competence with ID " + id + " not found."));

        return competencyMapper.toResDto(competence);
    }

    @Override
    public CompetencyResDto updateById(Long id, CompetencyReqDto competencyReqDto) {
        try {
            Competency existingCompetence = competencyRepository.findByIdOptional(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Competence with ID " + id + " not found."));

            competencyMapper.updateEntityFromDto(competencyReqDto, existingCompetence);

            existingCompetence.getGuidelines().clear();
            List<Guideline> guidelines = competencyMapper.mapGuidelinesFromDtos(competencyReqDto.getGuidelines(), existingCompetence);
            existingCompetence.getGuidelines().addAll(guidelines);

            competencyRepository.getEntityManager().merge(existingCompetence);

            return competencyMapper.toResDto(existingCompetence);
        } catch (ResourceNotFoundException e){
            throw e;
        }catch (Exception e) {
            throw new BadRequestException("Competency could not be updated" + e.getMessage());
        }
    }

    @Override
    public void deleteById(Long id) {
        try {
            getById(id);
            competencyRepository.deleteById(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException(e.getMessage());
        }catch (Exception e){
            throw new BadRequestException(e.getMessage());
        }
    }

    public long countTotal() {
        return competencyRepository.count();
    }

}
