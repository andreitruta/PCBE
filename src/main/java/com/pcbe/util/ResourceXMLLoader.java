package com.pcbe.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.URL;

/**
 * Unmarshalls an XML file retrieved as a resources from the current module
 */
public class ResourceXMLLoader extends XMLLoader {
    private static final Logger LOG = LoggerFactory.getLogger(ResourceXMLLoader.class);
    
    public <T> T unmarshallToObject(Class<T> cls, String path) {
        try {
            URL fileURL = ResourceXMLLoader.class.getResource(path);
            Unmarshaller jaxbUnmarshaller = getJAXBContext(cls).createUnmarshaller();
            T obj = (T) jaxbUnmarshaller.unmarshal(fileURL);
            LOG.debug("Retrieved " + cls.getName() + " from " + path);
            return obj;

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }
}
