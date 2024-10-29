package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroup;
import org.base.model.CompetencyGroupComment;
import org.base.model.Evaluation;
import org.base.model.enums.EmployeeType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class CompetencyGroupCommentRepository implements PanacheRepositoryBase<CompetencyGroupComment, Long> {

    public Optional<CompetencyGroupComment> findByCompetencyGroupCommentId(Long competencyGroupCommentId) {
        return find("competencyGroupCommentId", competencyGroupCommentId).firstResultOptional();
    }

    public List<CompetencyGroupComment> findByEmployeeType(EmployeeType employeeType) {
        return find("employeeType", employeeType).stream().toList();
    }

    public List<CompetencyGroupComment> findByOptionalFilters(EmployeeType employeeType, CompetencyGroup competencyGroup, Evaluation evaluation, Long employeeId) {
        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (employeeType != null) {
            query.append("employeeType = :employeeType");
            params.put("employeeType", employeeType);
        }
        if (competencyGroup != null) {
            if (!query.isEmpty()) query.append(" and ");
            query.append("competencyGroup = :competencyGroup");
            params.put("competencyGroup", competencyGroup);
        }
        if (evaluation != null) {
            if (!query.isEmpty()) query.append(" and ");
            query.append("evaluation = :evaluation");
            params.put("evaluation", evaluation);
        }
        if (employeeId != null) {
            if (!query.isEmpty()) query.append(" and ");
            query.append("employeeId = :employeeId");
            params.put("employeeId", employeeId);
        }

        PanacheQuery<CompetencyGroupComment> panacheQuery = find(query.toString(), params);
        return panacheQuery.stream().toList();
    }

}
