package org.base.dto;

import lombok.Data;
import org.base.model.enums.EmployeeType;

@Data
public class CompetencyGroupCommentResDto {

    private Long competencyGroupCommentId;
    private Long competencyGroupAssessmentId;
    private Long employeeId;
    private EmployeeType employeeType;
    private String comment;

}
