package com.pcbe.util;

/**
 * Used to find resources(e.g XML, DB, etc.) and turn them into POJOs
 */
public interface ResourceLocator {
    <T> T loadResource(Class<T> type, String path);
}
