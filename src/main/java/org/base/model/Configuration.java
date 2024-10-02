package org.base.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.base.domain.BaseEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "configuration")
public class Configuration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configuration_id", nullable = false)
    private Long configurationId;

    @Column(unique = true, nullable = false)
    private String key;

    @Column(nullable = false)
    private String value;

}
