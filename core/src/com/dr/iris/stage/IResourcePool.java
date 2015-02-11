package com.dr.iris.stage;

/**
 * Created by Rayer on 2/12/15.
 */
public interface IResourcePool {
    void preloadResource(ResourceDescriptor descriptor);

    void disposeAll();

    void dispose(DisposeStrategy strategy);

    enum DisposeStrategy {
        ALL,
        PRE_LOADED,
        NON_PRELOADED,
        NON_PRELOADED_HIGH_CONSUME
    }
}
