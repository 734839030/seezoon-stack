package com.seezoon.generator.io;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */

class FileCodeGeneratorTest {

    @Test
    void getGeneratedSourcesFolder() throws IOException {
        final String generatedSourcesFolder = new FileCodeGenerator().getGeneratedSourcesFolder();
        assertTrue(generatedSourcesFolder.endsWith(FileCodeGenerator.GENERATED_SOURCES_FOLDER));
    }
}