package com.archimatetool.recommender.model;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.ISafeRunnable;
import org.eclipse.core.runtime.SafeRunner;
import org.eclipse.e4.core.di.annotations.Execute;

public class EvaluateRecommenderContributions {
	
	private static final String RECOMMENDER_ID =
            "com.archimatetool.recommender.engine.recommender";
    @Execute
    public void execute(IExtensionRegistry registry) {
        IConfigurationElement[] config =
                registry.getConfigurationElementsFor(RECOMMENDER_ID);
        try {
            for (IConfigurationElement e : config) {
                System.out.println("Evaluating extension");
                final Object o =
                        e.createExecutableExtension("class");
                if (o instanceof Recommender) {
                    executeExtension(o);
                }
            }
        } catch (CoreException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void executeExtension(final Object o) {
        ISafeRunnable runnable = new ISafeRunnable() {
            @Override
            public void handleException(Throwable e) {
                System.out.println("Exception in client");
            }

            @Override
            public void run() throws Exception {
                ((Recommender) o).getRecommendations();
            }
        };
        SafeRunner.run(runnable);
    }

}
