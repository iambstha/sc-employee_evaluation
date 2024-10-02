package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;
import org.base.model.enums.CompetencyType;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "competency")
public class Competency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "competency_id", nullable = false)
    private Long competencyId;

    @Enumerated(EnumType.STRING)
    private CompetencyType type;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "competency", cascade = CascadeType.ALL)
    private List<Guideline> guidelines;

}
