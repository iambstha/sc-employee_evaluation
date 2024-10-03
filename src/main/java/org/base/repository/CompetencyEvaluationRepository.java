package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyEvaluation;

@ApplicationScoped
public class CompetencyEvaluationRepository implements PanacheRepositoryBase<CompetencyEvaluation, Long> {


}
