package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.aop.repositoryAccess.RepositoryAccess;
import org.base.model.Evaluation;
import org.base.model.enums.EvaluationByType;
import org.base.model.enums.ReviewStage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
@RepositoryAccess
public class EvaluationRepository implements PanacheRepositoryBase<Evaluation, Long> {

    public Optional<Evaluation> findByEvaluationId(Long evaluationId) {
        return find("evaluationId", evaluationId).firstResultOptional();
    }

    public List<Evaluation> findByOptionalFilters(EvaluationByType evaluationByType, ReviewStage reviewStage, Long employeeId) {

        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (evaluationByType != null) {
            query.append("evaluationByType = :evaluationByType");
            params.put("evaluationByType", evaluationByType);
        }
        if (reviewStage != null) {
            if (!query.isEmpty()) query.append(" and ");
            query.append("evaluationStage = :evaluationStage");
            params.put("evaluationStage", reviewStage);
        }
        if (employeeId != null) {
            if (!query.isEmpty()) query.append(" and ");
            query.append("employeeId = :employeeId");
            params.put("employeeId", employeeId);
        }

        PanacheQuery<Evaluation> panacheQuery = find(query.toString(), params);
        return panacheQuery.stream().toList();

    }
}
