import hexlet.code.Differ;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SampleTests {

    @Test()
    public void test1() throws IOException {
        String expected = """
                {
                  - follow: false
                    host: hexlet.io
                  - proxy: 123.234.53.22
                  - timeout: 50
                  + timeout: 20
                  + verbose: true
                }""";

        String actual = Differ.generate("src/test/resources/file1.json", "src/test/resources/file2.json");
        assertEquals(expected, actual);
    }

    @Test()
    public void test2() throws IOException {
        String expected = """
                {
                    follow: false
                    host: hexlet.io
                    proxy: 123.234.53.22
                    timeout: 50
                }""";

        String actual = Differ.generate("src/test/resources/file1.json", "src/test/resources/file1.json");
        assertEquals(expected, actual);
    }
}
