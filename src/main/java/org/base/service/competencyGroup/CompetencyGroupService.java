package org.base.service.competencyGroup;

import org.base.dto.CompetencyGroupReqDto;
import org.base.dto.CompetencyGroupResDto;
import org.base.model.enums.CompetencyStatus;
import org.base.model.enums.CompetencyType;

import java.util.List;

public interface CompetencyGroupService {

    CompetencyGroupResDto save(CompetencyGroupReqDto competencyGroupReqDto);

    List<CompetencyGroupResDto> getPaginated(int page, int size, String sortDirection, String sortColumn);

    CompetencyGroupResDto getById(Long id);

    List<CompetencyGroupResDto> getByFilters(CompetencyType competencyType, CompetencyStatus competencyStatus);

    CompetencyGroupResDto updateById(Long id, CompetencyGroupReqDto competencyGroupReqDto);

    void deleteById(Long id);

    long countTotal();

}
