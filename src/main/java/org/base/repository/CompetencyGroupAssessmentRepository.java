package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroupAssessment;

import java.util.Optional;

@ApplicationScoped
public class CompetencyGroupAssessmentRepository implements PanacheRepositoryBase<CompetencyGroupAssessment, Long> {

    public Optional<CompetencyGroupAssessment> findByCompetencyGroupAssessmentId(Long competencyGroupAssessmentId) {
        return find("competencyGroupAssessmentId", competencyGroupAssessmentId).firstResultOptional();
    }

}
