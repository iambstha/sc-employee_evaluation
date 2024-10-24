package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;
import org.base.model.enums.CompetencyPriority;
import org.base.model.enums.CompetencyStatus;
import org.base.model.enums.CompetencyType;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "competency_group_assessment")
public class CompetencyGroupAssessment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_group_assessment_id", nullable = false)
    private Long competencyGroupAssessmentId;

    @Column(name = "competency_group_number")
    private Long competencyGroupNumber;

    @Column(name = "weightage")
    private BigDecimal weightage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompetencyStatus status;

    @OneToMany(mappedBy = "competencyGroupAssessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetencyGroupComment> competencyGroupComments;

}
