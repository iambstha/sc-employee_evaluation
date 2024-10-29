package org.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;
import org.base.model.enums.EmployeeType;
import org.base.model.enums.ReviewStage;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "competency_group_comment_higher")
public class CompetencyGroupCommentHigher extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_group_comment_higher_id", nullable = false)
    private Long competencyGroupCommentHigherId;

    @ManyToOne
    @JoinColumn(name = "competency_group_id", nullable = false)
    @JsonIgnore
    private CompetencyGroup competencyGroup;

    @Column(name = "employee_id")
    private Long employeeId;

    @Enumerated(EnumType.STRING)
    @Column(name = "employee_type")
    private EmployeeType employeeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_stage")
    private ReviewStage reviewStage;

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

}
