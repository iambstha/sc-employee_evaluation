package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroup;
import org.base.model.Evaluation;
import org.base.model.enums.CompetencyStatus;
import org.base.model.enums.CompetencyType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@ApplicationScoped
public class CompetencyGroupRepository implements PanacheRepositoryBase<CompetencyGroup, Long> {

    public Optional<CompetencyGroup> findByCompetencyGroupId(Long competencyGroupId) {
        return find("competencyGroupId", competencyGroupId).firstResultOptional();
    }

    public List<CompetencyGroup> findByOptionalFilters(CompetencyType competencyType, CompetencyStatus status) {

        StringBuilder query = new StringBuilder();
        Map<String, Object> params = new HashMap<>();

        if (competencyType != null) {
            query.append("competencyType = :competencyType");
            params.put("competencyType", competencyType);
        }
        if (status != null) {
            if (!query.isEmpty()) query.append(" and ");
            query.append("status = :status");
            params.put("status", status);
        }

        PanacheQuery<CompetencyGroup> panacheQuery = find(query.toString(), params);
        return panacheQuery.stream().toList();

    }
}
