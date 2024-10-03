package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.model.Guideline;

@ApplicationScoped
public class GuidelineRepository implements PanacheRepositoryBase<Guideline, Long> {



}
