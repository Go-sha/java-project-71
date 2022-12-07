package hexlet.code;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class DifferTest {
    private static final String FILEPATH_1 = "jsonFile1";
    private static final String FILEPATH_2 = "jsonFile2";
    @BeforeAll
    static void prepareData() throws IOException {
        Path jsonFilepath1 = Paths.get(FILEPATH_1);
        Path jsonFilepath2 = Paths.get(FILEPATH_2);

        Files.createFile(jsonFilepath1);
        Files.createFile(jsonFilepath2);

        String data = """
                {
                 "host": "hexlet.io",
                 "timeout": 50,
                 "proxy": "123.234.53.22",
                 "follow": false
                }""";
        byte[] dataToBytes = data.getBytes();
        Files.write(jsonFilepath1, dataToBytes);

        data = """
            {
             "timeout": 20,
             "verbose": true,
             "host": "hexlet.io"
            }""";
        dataToBytes = data.getBytes();
        Files.write(jsonFilepath2, dataToBytes);
    }

    @Test
    void checkDiffResult() throws Exception {
        Path jsonFilepath1 = Paths.get(FILEPATH_1);
        Path jsonFilepath2 = Paths.get(FILEPATH_2);
        String expected = """
                {
                 - follow: false
                   host: hexlet.io
                 - proxy: 123.234.53.22
                 - timeout: 50
                 + timeout: 20
                 + verbose: true
                }""";

        assertThat(Differ.generate(jsonFilepath1.toString(),
                                   jsonFilepath2.toString()))
                .isEqualTo(expected);
    }
    @AfterAll
    static void removeData() throws IOException {
        Path jsonFilepath1 = Paths.get(FILEPATH_1);
        Path jsonFilepath2 = Paths.get(FILEPATH_2);
        Files.delete(jsonFilepath1);
        Files.delete(jsonFilepath2);
    }
}
