package org.base.service.competencyGroup;

import org.base.dto.CompetencyGroupReqDto;
import org.base.dto.CompetencyGroupResDto;

import java.util.List;

public interface CompetencyGroupService {

    CompetencyGroupResDto save(CompetencyGroupReqDto competencyGroupReqDto);

    List<CompetencyGroupResDto> getAll();

    CompetencyGroupResDto getById(Long id);

    CompetencyGroupResDto updateById(Long id, CompetencyGroupReqDto competencyGroupReqDto);

    void deleteById(Long id);

}
