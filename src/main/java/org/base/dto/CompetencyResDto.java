package org.base.dto;

import lombok.Data;
import org.base.model.enums.CompetencyType;

import java.util.List;

@Data
public class CompetencyResDto {

    private Long competencyId;
    private Long competencyGroupNumber;
    private CompetencyType competencyType;
    private String subCompetencyType;
    private String name;
    private String description;
    private List<GuidelineResDto> guidelines;

}
