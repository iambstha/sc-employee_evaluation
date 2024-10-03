package org.base.dto;

import lombok.Data;
import org.base.model.enums.CompetencyType;

import java.util.List;

@Data
public class CompetencyResDto {

    private Long competencyId;
    private CompetencyType competencyType;
    private String name;
    private String description;
//    private List<Long> guidelineIds;
private List<GuidelineResDto> guidelineResDtos;
}