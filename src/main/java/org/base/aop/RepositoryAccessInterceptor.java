package org.base.aop;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;

@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
@RepositoryAccess
public class RepositoryAccessInterceptor {

    @Inject
    RepositoryAccessTracker repositoryAccessTracker;

    @AroundInvoke
    public Object trackRepositoryAccess(InvocationContext context) throws Exception {
        String repositoryName = context.getTarget().getClass().getSimpleName();

        int underscoreIndex = repositoryName.indexOf('_');
        if (underscoreIndex != -1) {
            repositoryName = repositoryName.substring(0, underscoreIndex);
        }

        repositoryAccessTracker.addRepositoryAccess(repositoryName);
        return context.proceed();
    }

}