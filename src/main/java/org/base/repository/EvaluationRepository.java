package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroupComment;
import org.base.model.Evaluation;
import org.base.model.enums.EvaluationByType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ApplicationScoped
public class EvaluationRepository implements PanacheRepositoryBase<Evaluation, Long> {


    public List<Evaluation> findByOptionalFilters(EvaluationByType evaluationByType, Long employeeId) {

        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (evaluationByType != null) {
            query.append("evaluationByType = :evaluationByType");
            params.put("evaluationByType", evaluationByType);
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
