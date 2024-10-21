package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "competency_evaluation")
public class CompetencyEvaluation extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_evaluation_id", nullable = false)
    private Long competencyEvaluationId;

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "competency_id", nullable = false)
    private Competency competency;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "score_id", nullable = false)
    private Score score;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "employee_comments")
    private String employeeComments;

}
