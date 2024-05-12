package hexlet.code;

import hexlet.code.utils.DiffConstructor;
import hexlet.code.utils.FileUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Differ {

    public static String generate(String filepath1, String filepath2) throws IOException {
        Map<String, Object> map1 = FileUtils.getData(filepath1);
        Map<String, Object> map2 = FileUtils.getData(filepath2);

        Set<String> keys1 = map1.keySet();
        Set<String> keys2 = map2.keySet();

        Set<String> mergedKeys = new HashSet<>(Set.copyOf(keys1));
        mergedKeys.addAll(Set.copyOf(keys2));

        List<String> sortedKeys = mergedKeys.stream().sorted().toList();
        DiffConstructor constructor = new DiffConstructor();

        sortedKeys.forEach(key -> {
            Object value1 = map1.getOrDefault(key, null);
            Object value2 = map2.getOrDefault(key, null);

            constructor.addDiff(key, value1, value2);
        });

        return constructor.getDiffString();
    }
}
