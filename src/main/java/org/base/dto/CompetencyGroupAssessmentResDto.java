package org.base.dto;

import lombok.Data;
import org.base.model.enums.CompetencyPriority;
import org.base.model.enums.CompetencyStatus;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompetencyGroupAssessmentResDto {

    private Long competencyGroupAssessmentId;
    private Long competencyGroupNumber;
    private BigDecimal weightage;
    private CompetencyStatus status;
    private List<CompetencyGroupCommentResDto> competencyGroupComments;

}
