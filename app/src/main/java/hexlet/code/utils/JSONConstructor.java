package hexlet.code.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JSONConstructor {

    private final ObjectMapper mapper = new ObjectMapper();

    private final ObjectNode rootNode = mapper.createObjectNode();

    private ObjectNode constructNode(String fieldName, Object value) {
        ObjectNode node = mapper.createObjectNode();
        return node.set(fieldName, mapper.valueToTree(value));
    }

    public void addNodeToRoot(String fieldName, Object fileValue1, Object fileValue2) {
        if (fileValue1 == null && fileValue2 == null) {
            return;
        }

        if (fileValue2 == null) {
            String formatted = "- %s" + fieldName;
            ObjectNode node = constructNode(formatted, fileValue1);
            rootNode.setAll(node);
            return;
        }

        if (fileValue1 == null) {
            String formatted = "+ %s" + fieldName;
            ObjectNode node = constructNode(formatted, fileValue2);
            rootNode.setAll(node);
            return;
        }

        if (!fileValue1.equals(fileValue2)) {
            String formatted1 = "- %s" + fieldName;
            String formatted2 = "+ %s" + fieldName;
            ObjectNode node1 = constructNode(formatted1, fileValue1);
            ObjectNode node2 = constructNode(formatted2, fileValue2);
            rootNode.setAll(node1);
            rootNode.setAll(node2);
            return;
        }

        ObjectNode node = constructNode(fieldName, fileValue1);
        rootNode.setAll(node);
    }

    public String getJSONString() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
}
