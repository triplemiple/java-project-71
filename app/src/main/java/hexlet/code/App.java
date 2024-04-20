package hexlet.code;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--format=format"}, description = "output format [default: stylish]", defaultValue = "stylish")
    String format = "stylish";

    @Parameters(paramLabel = "filepath1", description = " path to first file")
    private String filepath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filepath2;

    @Override
    public Integer call() throws IOException {
        Path path1 = Path.of(filepath1).normalize().toAbsolutePath();
        Path path2 = Path.of(filepath2).normalize().toAbsolutePath();
        String file1;
        String file2;

        file1 = Files.readString(path1);
        file2 = Files.readString(path2);

        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, Object> map1 = objectMapper.readValue(file1, new TypeReference<>() {
        });

        HashMap<String, Object> map2 = objectMapper.readValue(file2, new TypeReference<>() {
        });

        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();

        HashSet<String> mergedKeys = new HashSet<>(Set.copyOf(keys1));
        mergedKeys.addAll(Set.copyOf(keys2));

        List<String> sortedKeys = mergedKeys.stream().sorted().toList();

        ObjectNode root = objectMapper.createObjectNode();

        for (String key : sortedKeys) {
            Object firstValue = map1.getOrDefault(key, null);
            Object secondValue = map2.getOrDefault(key, null);

            if (firstValue != null && secondValue == null) {
                ObjectNode node = objectMapper.createObjectNode();
                String formatted = String.format("- %s", key);
                Object fileValue = map1.get(key);
                node.set(formatted, objectMapper.valueToTree(fileValue));
                root.setAll(node);
            }

            if (firstValue == null && secondValue != null) {
                ObjectNode node = objectMapper.createObjectNode();
                String formatted = String.format("+ %s", key);
                Object fileValue = map2.get(key);
                node.set(formatted, objectMapper.valueToTree(fileValue));
                root.setAll(node);
            }

            if (firstValue != null && secondValue != null) {
                if (firstValue.equals(secondValue)) {
                    ObjectNode node = objectMapper.createObjectNode();
                    Object fileValue = map2.get(key);
                    node.set(key, objectMapper.valueToTree(fileValue));
                    root.setAll(node);
                } else {
                    ObjectNode node1 = objectMapper.createObjectNode();
                    ObjectNode node2 = objectMapper.createObjectNode();
                    String formatted1 = String.format("- %s", key);
                    String formatted2 = String.format("+ %s", key);
                    Object fileValue1 = map1.get(key);
                    Object fileValue2 = map2.get(key);
                    node1.set(formatted1, objectMapper.valueToTree(fileValue1));
                    node2.set(formatted2, objectMapper.valueToTree(fileValue2));
                    root.setAll(node1);
                    root.setAll(node2);
                }
            }
        }

        String output = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
        System.out.println(output);

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}