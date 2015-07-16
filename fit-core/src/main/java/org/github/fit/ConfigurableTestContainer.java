package org.github.fit;

/**
 * Created by thomas on 15.07.15.
 */
public abstract class ConfigurableTestContainer implements TestContainer {
    protected abstract void configure();

}
