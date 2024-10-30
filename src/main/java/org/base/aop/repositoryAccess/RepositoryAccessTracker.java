package org.base.aop.repositoryAccess;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class RepositoryAccessTracker {
    private static final ThreadLocal<List<String>> accessedRepositories = ThreadLocal.withInitial(ArrayList::new);

    public static void addRepositoryAccess(String repositoryName) {
        accessedRepositories.get().add(repositoryName);
    }

    public static List<String> getAccessedRepositories() {
        return Collections.unmodifiableList(accessedRepositories.get());
    }

    public static void clear() {
        accessedRepositories.get().clear();
    }
}
