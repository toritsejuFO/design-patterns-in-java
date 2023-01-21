package co.learn.designpatterns.patterns.builder;

import java.util.HashMap;
import java.util.Map;

class CodeBuilder {
    private String className;
    private Map<String, String> fields = new HashMap<>();

    public CodeBuilder(String className) {
        this.className = className;
    }

    public CodeBuilder addField(String fieldName, String fieldType) {
        fields.put(fieldName, fieldType);
        return this;
    }

    private String generateFieldCode() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> field : fields.entrySet()) {
            sb.append("  public ").append(field.getValue()).append(" ").append(field.getKey()).append(";\n");
        }
        return sb.toString();
    }

    private String generateClassCode() {
        StringBuilder sb = new StringBuilder();
        sb.append("public class ").append(className).append('\n')
                .append("{\n")
                .append(generateFieldCode())
                .append("}\n");
        return sb.toString();
    }

    @Override
    public String toString() {
        return generateClassCode();
    }
}

public class Exercise {
    public static void main(String[] args) {
        CodeBuilder cb = new CodeBuilder("Person")
                .addField("name", "String")
                .addField("age", "int");
        System.out.println(cb);
    }
}
