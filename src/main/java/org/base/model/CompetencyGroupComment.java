package org.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;
import org.base.model.enums.EmployeeType;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "competency_group_comment")
public class CompetencyGroupComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_group_comment_id", nullable = false)
    private Long competencyGroupCommentId;

    @ManyToOne
    @JoinColumn(name = "competency_group_id", nullable = false)
    @JsonIgnore
    private CompetencyGroup competencyGroup;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_type")
    private EmployeeType employeeType;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

}
