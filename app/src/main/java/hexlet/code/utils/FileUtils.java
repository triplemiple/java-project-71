package hexlet.code.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class FileUtils {

    public static Map<String, Object> getData(String filePath) throws IOException {
        Path path = Path.of(filePath).normalize().toAbsolutePath();
        String fileContent = Files.readString(path);

        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(fileContent, new TypeReference<>() {
        });
    }
}
