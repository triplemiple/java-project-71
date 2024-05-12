package hexlet.code.utils;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DiffConstructor {

    private final ArrayList<String> diffList = new ArrayList<>();

    public void addDiff(String fieldName, Object fileValue1, Object fileValue2) {
        if (fileValue1 != null && fileValue2 != null && !fileValue1.equals(fileValue2)) {
            String formatted1 = "- " + fieldName + ": " + fileValue1;
            String formatted2 = "+ " + fieldName + ": " + fileValue2;
            diffList.add("  " + formatted1);
            diffList.add("  " + formatted2);
            return;
        }

        Object diffValue = fileValue1;
        String formatted = "  " + fieldName;

        if (fileValue2 == null) {
            formatted = "- " + fieldName;
        }

        if (fileValue1 == null) {
            formatted = "+ " + fieldName;
            diffValue = fileValue2;
        }

        formatted = formatted + ": " + diffValue;
        diffList.add("  " + formatted);
    }

    public String getDiffString() {
        return diffList.stream().collect(Collectors.joining("\n", "{\n", "\n}"));
    }
}
