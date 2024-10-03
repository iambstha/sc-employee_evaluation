package org.base.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "guideline")
public class Guideline extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "guideline_id", nullable = false)
    private Long guidelineId;

    @ManyToOne
    @JoinColumn(name = "competency_id", nullable = false)
    @JsonIgnore
    private Competency competency;

    @Column(name = "indicator_description")
    private String indicatorDescription;

}
