package com.pcbe.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.util.HashMap;

/**
 * Unmarshalls an XML file to a Java object
 */
public abstract class XMLLoader implements ResourceLocator {
    protected HashMap<String, JAXBContext> cache = new HashMap<String, JAXBContext>();
    
    public abstract <T> T unmarshallToObject(Class<T> cls, String path);
    
    public JAXBContext getJAXBContext(Class cls) throws JAXBException {
        if(!cache.containsKey(cls.getName())) {
            JAXBContext jaxbContext = JAXBContext.newInstance(cls);
            cache.put(cls.getName(), jaxbContext);
        }
        
        return cache.get(cls.getName());
    }

    @Override
    public <T> T loadResource(Class<T> type, String path) {
        return unmarshallToObject(type, path);
    }
}
