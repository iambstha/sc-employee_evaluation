package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroupComment;

import java.util.Optional;

@ApplicationScoped
public class CompetencyGroupCommentRepository implements PanacheRepositoryBase<CompetencyGroupComment, Long> {

    public Optional<CompetencyGroupComment> findByCompetencyGroupCommentId(Long competencyGroupCommentId) {
        return find("competencyGroupCommentId", competencyGroupCommentId).firstResultOptional();
    }

}
