package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.aop.repositoryAccess.RepositoryAccess;
import org.base.model.Score;

@ApplicationScoped
@RepositoryAccess
public class ScoreRepository implements PanacheRepositoryBase<Score, Long> {



}
