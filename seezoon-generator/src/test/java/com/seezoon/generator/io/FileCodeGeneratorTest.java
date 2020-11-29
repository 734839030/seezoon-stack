package com.seezoon.generator.io;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */

class FileCodeGeneratorTest {

    @Test
    void getGeneratedSourcesFolder() throws IOException {
        final Path generatedSourcesFolder = new FileCodeGenerator().getReadyGeneratedSourcesFolder();
        assertTrue(generatedSourcesFolder.toString().endsWith(FileCodeGenerator.GENERATED_SOURCES_FOLDER));
    }
}