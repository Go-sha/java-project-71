package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/* asdf */
public final class Differ {
    public static String generate(String filepath1, String filepath2) throws Exception {
        Map<String, Object> data1 = getData(filepath1);
        Map<String, Object> data2 = getData(filepath2);

        return generateDiff(data1, data2);
    }
    public static Map<String, Object> getData(String filepath) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(new File(filepath), Map.class);
    }
    public static String generateDiff(Map<String, Object> data1, Map<String, Object> data2) {
        StringBuilder result = new StringBuilder();
        Set<String> keys = new TreeSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        result.append("{\n");
        for (String key: keys) {
            if (!data1.containsKey(key)) {
                result.append(" + ");
                result.append(key);
                result.append(": ");
                result.append(data2.get(key).toString());
                result.append("\n");
            } else if (!data2.containsKey(key)) {
                result.append(" - ");
                result.append(key);
                result.append(": ");
                result.append(data1.get(key).toString());
                result.append("\n");
            } else if (data1.get(key).equals(data2.get(key))) {
                result.append("   ");
                result.append(key);
                result.append(": ");
                result.append(data2.get(key).toString());
                result.append("\n");
            } else {
                result.append(" - ");
                result.append(key);
                result.append(": ");
                result.append(data1.get(key).toString());
                result.append("\n");
                result.append(" + ");
                result.append(key);
                result.append(": ");
                result.append(data2.get(key).toString());
                result.append("\n");
            }
        }
        result.append("}");

        return result.toString();
    }



}
