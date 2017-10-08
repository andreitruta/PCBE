package com.pcbe.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class XMLLoaderFactory {
    private static final Logger LOG = LoggerFactory.getLogger(XMLLoaderFactory.class);
    private static HashMap<String, XMLLoader> cache = new HashMap<String, XMLLoader>();
    
    public static XMLLoader getXMLLoader(Class<? extends XMLLoader> cls) {
        try {
            if (!cache.containsKey(cls.getName())) {
                cache.put(cls.getName(), cls.newInstance());
            }
        } catch(InstantiationException|IllegalAccessException e) {
            LOG.error("Could not retrieve XMLLoader " + cls.getSimpleName());
            //FIXME
            throw new RuntimeException(e);
        }
        return cache.get(cls.getName());
    }
}
