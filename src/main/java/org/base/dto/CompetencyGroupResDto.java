package org.base.dto;

import lombok.Data;
import org.base.model.CompetencyGroupComment;
import org.base.model.enums.CompetencyStatus;
import org.base.model.enums.CompetencyType;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CompetencyGroupResDto {

    private Long competencyGroupId;
    private CompetencyType competencyType;
    private String subCompetencyType;
    private BigDecimal weightage;
    private CompetencyStatus status;
    private List<CompetencyGroupCommentResDto> competencyGroupComments;

}
