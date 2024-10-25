package org.base.dto;

import lombok.Data;

import java.util.List;

@Data
public class CompetencyResDto {

    private Long competencyId;
    private Long competencyGroupId;
    private String name;
    private String description;
    private List<GuidelineResDto> guidelines;

}
