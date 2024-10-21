package org.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;

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
    @JoinColumn(name = "competency_group_assessment_id", nullable = false)
    @JsonIgnore
    private CompetencyGroupAssessment competencyGroupAssessment;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;

}
