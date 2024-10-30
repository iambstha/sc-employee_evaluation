package org.base.model;

import io.smallrye.common.constraint.NotNull;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;
import org.base.model.enums.ApprovalStage;
import org.base.model.enums.EvaluationByType;
import org.base.model.enums.ReviewStage;

import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "evaluation")
public class Evaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "evaluation_id", nullable = false)
    private Long evaluationId;

    @Column(name = "employee_id")
    @NotNull
    private Long employeeId;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetencyEvaluation> competencyEvaluations;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetencyGroupComment> competencyGroupComments;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetencyGroupCommentHigher> competencyGroupCommentHighers;

    @Enumerated(EnumType.STRING)
    @Column(name = "review_stage")
    @NotNull
    private ReviewStage reviewStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "approval_stage")
    @NotNull
    private ApprovalStage approvalStage;

    @Enumerated(EnumType.STRING)
    @Column(name = "evaluation_by_type")
    @NotNull
    private EvaluationByType evaluationByType;

    @Column(name = "period_from", nullable = false)
    private LocalDateTime periodFrom;

    @Column(name = "period_to", nullable = false)
    private LocalDateTime periodTo;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "employee_comments")
    private String employeeComments;

}
