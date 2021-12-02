package org.synek.adventofcode.day1;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.synek.adventofcode.util.Util;

abstract class AbstractCommonDay1Challenge {

    private static final String INPUT_RESOURCE = "input";

    protected int[] readInput() {
        try {
            return Files.lines(Paths.get(Util.classpathResourceURI(getClass(), INPUT_RESOURCE)))
                    .mapToInt(Integer::parseInt)
                    .toArray();
        } catch (IOException ioException) {
            throw new IllegalStateException("Reading the input file (" + INPUT_RESOURCE + ") has failed.", ioException);
        }

    }
}
