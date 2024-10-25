package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroup;

import java.util.Optional;

@ApplicationScoped
public class CompetencyGroupRepository implements PanacheRepositoryBase<CompetencyGroup, Long> {

    public Optional<CompetencyGroup> findByCompetencyGroupId(Long competencyGroupId) {
        return find("competencyGroupId", competencyGroupId).firstResultOptional();
    }

}
