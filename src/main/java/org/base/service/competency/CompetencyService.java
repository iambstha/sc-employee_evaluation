package org.base.service.competency;

import org.base.dto.CompetencyReqDto;
import org.base.dto.CompetencyResDto;

import java.util.List;

public interface CompetencyService {

    CompetencyResDto save(CompetencyReqDto competencyReqDto);

    List<CompetencyResDto> getAll();

    CompetencyResDto getById(Long id);

    CompetencyResDto updateById(Long id, CompetencyReqDto competencyReqDto);

    void deleteById(Long id);

}
