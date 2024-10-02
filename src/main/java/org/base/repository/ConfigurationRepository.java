package org.base.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;
import org.base.exception.ResourceNotFoundException;
import org.base.model.Configuration;

import java.util.Optional;

@ApplicationScoped
public class ConfigurationRepository implements PanacheRepositoryBase<Configuration, Long> {

    public Optional<Configuration> findByKey(String key) {
        return find("key", key).firstResultOptional();
    }

    public void saveOrUpdate(Configuration configuration) {
        if (configuration.getConfigurationId() == null) {
            persist(configuration);
        } else {
            getEntityManager().merge(configuration);
        }
    }

    public void deleteByKey(String key) {
        Configuration configuration = find("key", key).firstResult();

        if (configuration != null) {
            delete(configuration);
        } else {
            throw new ResourceNotFoundException("Configuration with key " + key + " not found.");
        }
    }
}
