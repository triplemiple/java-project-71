package hexlet.code;

import hexlet.code.utils.FileUtils;
import hexlet.code.utils.JSONConstructor;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;

@Command(name = "gendiff", mixinStandardHelpOptions = true,
        description = "Compares two configuration files and shows a difference.")
public class App implements Callable<Integer> {

    @Option(names = {"-f", "--format=format"}, description = "output format [default: stylish]",
            defaultValue = "stylish")
    String format = "stylish";

    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filepath1;

    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filepath2;

    @Override
    public Integer call() throws IOException {
        Map<String, Object> map1 = FileUtils.getData(filepath1);
        Map<String, Object> map2 = FileUtils.getData(filepath2);

        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();

        HashSet<String> mergedKeys = new HashSet<>(Set.copyOf(keys1));
        mergedKeys.addAll(Set.copyOf(keys2));

        List<String> sortedKeys = mergedKeys.stream().sorted().toList();
        JSONConstructor constructor = new JSONConstructor();

        sortedKeys.forEach(key -> {
            Object value1 = map1.getOrDefault(key, null);
            Object value2 = map2.getOrDefault(key, null);

            constructor.addNodeToRoot(key, value1, value2);
        });

        System.out.println(constructor.getJSONString());

        return 0;
    }

    public static void main(String[] args) {
        int exitCode = new CommandLine(new App()).execute(args);
        System.exit(exitCode);
    }
}
