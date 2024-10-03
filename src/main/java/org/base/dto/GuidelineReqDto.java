package org.base.dto;

import lombok.Data;

@Data
public class GuidelineReqDto {

    private Long guidelineId;
    private Long competencyId;
    private String indicatorDescription;

}
