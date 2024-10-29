package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroupComment;
import org.base.model.CompetencyGroupCommentHigher;

import java.util.Optional;

@ApplicationScoped
public class CompetencyGroupCommentHigherRepository implements PanacheRepositoryBase<CompetencyGroupCommentHigher, Long> {

    public Optional<CompetencyGroupCommentHigher> findByCompetencyGroupCommentHigherId(Long competencyGroupCommentHigherId) {
        return find("competencyGroupCommentHigherId", competencyGroupCommentHigherId).firstResultOptional();
    }

}
