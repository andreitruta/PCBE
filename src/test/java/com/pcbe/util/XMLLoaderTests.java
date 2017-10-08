package com.pcbe.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class XMLLoaderTests {
    private static final String XML_TEST_FILE = "/testXMLLoaderFile.xml";
    private static final String TEST_STRING = "JUST_TESTING";
    
    @Test
    public void testResourceXMLLoader() {
        XMLLoader xmlLoader = new ResourceXMLLoader();
        TestXMLLoader testXMLLoaderInstance = xmlLoader.unmarshallToObject(TestXMLLoader.class, XML_TEST_FILE);
        assertEquals(testXMLLoaderInstance.getTestString(), TEST_STRING);
    }
}
