package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.CompetencyGroupComment;

@ApplicationScoped
public class CompetencyGroupCommentRepository implements PanacheRepositoryBase<CompetencyGroupComment, Long> {



}
