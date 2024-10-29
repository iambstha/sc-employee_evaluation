package org.base.dto;

import lombok.Data;
import org.base.model.enums.EmployeeType;
import org.base.model.enums.ReviewStage;

@Data
public class CompetencyGroupCommentReqDto {

    private Long commentId;
    private Long competencyGroupId;
    private Long evaluationId;
    private ReviewStage reviewStage;
    private Long employeeId;
    private EmployeeType employeeType;
    private String comment;

}
