package org.base.dto;

import lombok.Data;
import org.base.model.enums.EmployeeType;

@Data
public class CompetencyGroupCommentReqDto {

    private Long competencyGroupCommentId;
    private Long competencyGroupId;
    private Long employeeId;
    private EmployeeType employeeType;
    private String comment;

}
