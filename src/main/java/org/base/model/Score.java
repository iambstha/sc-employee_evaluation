package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "score")
public class Score extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "score_id", nullable = false)
    private Long scoreId;

    @Column(name = "value")
    private Integer value;

    @Column(name = "description")
    private String description;

}
