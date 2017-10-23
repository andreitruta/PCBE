package com.pcbe.resources;

import com.pcbe.core.LifeGameBootstrap;
import com.pcbe.util.ResourceLocator;

public class ResourcesLoader {
    public static final String DEFAULT_RESOURCES_PATH_XML = "/resources.xml";
    private static ResourceLocator resourceLocator = LifeGameBootstrap.getInstance().getResourceLocator();
    
    public static Resources loadResourcesTypes(String path) {
        return resourceLocator.loadResource(Resources.class, path);
    }
    
    public static Resources loadResourcesTypes() {
        return resourceLocator.loadResource(Resources.class, DEFAULT_RESOURCES_PATH_XML);
    }
}
