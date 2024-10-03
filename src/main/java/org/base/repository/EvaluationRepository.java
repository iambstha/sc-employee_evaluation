package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.Evaluation;

@ApplicationScoped
public class EvaluationRepository implements PanacheRepositoryBase<Evaluation, Long> {



}
