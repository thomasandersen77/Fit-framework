package org.github.fit.core;

import lombok.Getter;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;
import javax.enterprise.inject.spi.WithAnnotations;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.Set;

public class ProviderExtension implements Extension {

    @Getter
    private Set<Class> providers = new HashSet<Class>();

    public void scanForResources(@Observes @WithAnnotations(Provider.class) ProcessAnnotatedType pat) {
        this.providers.add(pat.getAnnotatedType().getJavaClass());
    }
}
