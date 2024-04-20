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
        if (fileValue1 != null && fileValue2 != null) {
            if (!fileValue1.equals(fileValue2)) {
                String formatted1 = "- " + fieldName;
                String formatted2 = "- " + fieldName;
                ObjectNode node1 = constructNode(formatted1, fileValue1);
                ObjectNode node2 = constructNode(formatted2, fileValue2);
                rootNode.setAll(node1);
                rootNode.setAll(node2);
                return;
            }
        }

        Object nodeValue = fileValue1;

        if (fileValue2 == null) {
            fieldName = "- " + fieldName;
        }

        if (fileValue1 == null) {
            fieldName = "+ " + fieldName;
            nodeValue = fileValue2;
        }

        ObjectNode node = constructNode(fieldName, nodeValue);
        rootNode.setAll(node);
    }

    public String getJSONString() throws JsonProcessingException {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
    }
}
