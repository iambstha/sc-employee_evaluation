package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "functional_competency")
public class FunctionalCompetency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "functional_competency_id", nullable = false)
    private Long functionalCompetencyId;

    @ManyToOne
    @JoinColumn(name = "evaluation_id", nullable = false)
    private Evaluation evaluation;

    @Column(name = "competency_name")
    private String competencyName;

    @Column(name = "manager_comments")
    private String managerComments;

    @Column(name = "employee_comments")
    private String employeeComments;

}
