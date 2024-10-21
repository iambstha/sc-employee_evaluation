package org.base.dto;

import lombok.Data;

@Data
public class CompetencyEvaluationReqDto {

    private Long competencyEvaluationId;
    private Long competencyId;
    private ScoreReqDto scoreReqDto;
    private String managerComments;
    private String employeeComments;

}
