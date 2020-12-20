package com.seezoon.generator.io;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

/**
 * @author hdf
 */
public class PathTest {

    @Test
    public void testResolve() {
        final Path path = Paths.get("/a/b/");
        // 项目路径得到路径
        assertTrue(path.resolve("c").toString().equals("/a/b/c"));
        // 获取相对路径
        assertTrue(path.relativize(Path.of("/a/b/c/")).toString().equals("c"));
    }
}
