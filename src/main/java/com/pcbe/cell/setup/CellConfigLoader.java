package com.pcbe.cell.setup;

import com.pcbe.core.LifeGameBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CellConfigLoader {
    private static final Logger LOG = LoggerFactory.getLogger(CellConfigLoader.class);
    public static final String DEFAULT_CONFIG_FILE_XML = "/cellConfig.xml";

    public static void loadConfig() {
        loadConfig(DEFAULT_CONFIG_FILE_XML);
    }

    public static void loadConfig(String path) {
        LifeGameBootstrap.getInstance().getResourceLocator().loadResource(CellConfig.class, path);
    }
}
