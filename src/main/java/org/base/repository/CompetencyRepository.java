package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.Competency;

import java.util.Optional;

@ApplicationScoped
public class CompetencyRepository implements PanacheRepositoryBase<Competency, Long> {


    public Optional<Competency> findByCompetencyId(Long competencyId) {
        return find("competencyId", competencyId).firstResultOptional();
    }
}
