package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;

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
    private Long employeeId;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<CompetencyEvaluation> competencyEvaluations;

    @OneToMany(mappedBy = "evaluation", cascade = CascadeType.ALL)
    private List<FunctionalCompetency> functionalCompetencies;

    @Column(name = "evaluation_date", nullable = false)
    private LocalDateTime evaluationDate;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "employee_comments")
    private String employeeComments;

}
