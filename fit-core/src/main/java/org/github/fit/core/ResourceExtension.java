package org.github.fit.core;

import lombok.Getter;
import org.github.fit.api.Resource;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import java.util.HashSet;
import java.util.Set;

public class ResourceExtension implements Extension {

    @Getter
    private Set<Class> resources = new HashSet<Class>();

    public void scanForResources(@Observes @WithAnnotations(Resource.class) ProcessAnnotatedType pat) {
        this.resources.add(pat.getAnnotatedType().getJavaClass());
    }
}
