package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;
import org.base.model.enums.CompetencyStatus;
import org.base.model.enums.CompetencyType;

import java.math.BigDecimal;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "competency_group")
public class CompetencyGroup extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_group_id", nullable = false)
    private Long competencyGroupId;

    @Enumerated(EnumType.STRING)
    @Column(name = "competency_type")
    private CompetencyType competencyType;

    @Column(name = "sub_competency_type")
    private String subCompetencyType;

    @Column(name = "weightage")
    private BigDecimal weightage;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private CompetencyStatus status;

    @OneToMany(mappedBy = "competencyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetencyGroupComment> competencyGroupComments;

    @OneToMany(mappedBy = "competencyGroup", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetencyGroupCommentHigher> competencyGroupCommentHighers;

}
