package com.github.fit.annotations;

import org.junit.rules.TestRule;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

import java.lang.annotation.Annotation;
import java.util.List;

public class FitITestRunner extends BlockJUnit4ClassRunner {
    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     *
     * @param klass
     * @throws InitializationError if the test class is malformed.
     */
    public FitITestRunner(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override
    public void run(RunNotifier notifier) {
        super.run(notifier);
    }

    @Override
    protected Statement withBeforeClasses(Statement statement) {
        return super.withBeforeClasses(statement);
    }

    @Override
    protected Statement withAfterClasses(Statement statement) {
        return super.withAfterClasses(statement);
    }

    @Override
    protected List<TestRule> classRules() {
        return super.classRules();
    }

    @Override
    protected Annotation[] getRunnerAnnotations() {
        return super.getRunnerAnnotations();
    }

    @Override
    protected Statement withBefores(FrameworkMethod method, Object target, Statement statement) {
        return super.withBefores(method, target, statement);
    }

    @Override
    protected Statement withAfters(FrameworkMethod method, Object target, Statement statement) {
        return super.withAfters(method, target, statement);
    }
}
