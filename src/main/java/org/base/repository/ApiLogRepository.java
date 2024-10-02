package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.base.model.ApiLog;

@ApplicationScoped
public class ApiLogRepository implements PanacheRepositoryBase<ApiLog, Long> {

    @Inject
    EntityManager em;

    @Transactional
    public void save(ApiLog apiLog) {
        em.persist(apiLog);
    }

}
