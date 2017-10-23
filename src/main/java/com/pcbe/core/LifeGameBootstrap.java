package com.pcbe.core;

import com.pcbe.util.ResourceLocator;

public class LifeGameBootstrap {
    private static LifeGameBootstrap SINGLETON;
    private ResourceLocator resourceLocator;
    
    public LifeGameBootstrap() {
        if(SINGLETON == null) {
            SINGLETON = this;
        } else {
            //throw new RuntimeException("LG Bootstrap already instantiated!");
        }
    }
    
    public static LifeGameBootstrap getInstance() {
        if(SINGLETON == null) {
            throw new RuntimeException("LG Bootstrap not started");
        }
        return SINGLETON;
    }
    
    public void bindResourceLocator(ResourceLocator resourceLocator) {
        this.resourceLocator = resourceLocator;
    }

    public ResourceLocator getResourceLocator() {
        return resourceLocator;
    }
}
