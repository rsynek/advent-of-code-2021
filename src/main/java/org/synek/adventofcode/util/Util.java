package org.synek.adventofcode.util;

import java.net.URI;
import java.net.URISyntaxException;

public final class Util {

    public static int requireNotNegative(int value) {
        if (value < 0) {
            throw new IllegalArgumentException("A value " + value + " cannot be negative.");
        }
        return value;
    }

    public static URI classpathResourceURI(Class<?> clazz, String resource) {
        try {
            return clazz.getResource(resource).toURI();
        } catch (URISyntaxException uriSyntaxException) {
            throw new IllegalArgumentException(uriSyntaxException);
        }
    }

    private Util() {
        throw new UnsupportedOperationException();
    }
}
