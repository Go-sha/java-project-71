package hexlet.code;

import com.fasterxml.jackson.databind.ObjectMapper;
import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

import java.io.File;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class Differ implements Callable<Integer> {
    private static String diff;
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filepath1;
    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filepath2;
    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";
    @Override
    public Integer call() throws Exception {
        StringBuilder result = new StringBuilder();
        Map<String, Object> data1 = getData(filepath1);
        Map<String, Object> data2 = getData(filepath2);
        Set<String> keys = new TreeSet<>(data1.keySet());
        keys.addAll(data2.keySet());

        for (String key: keys) {
            if (!data1.containsKey(key)) {
                result.append("+");
                result.append(key);
                result.append(":");
                result.append(data2.get(key).toString());
                result.append("\n");
            } else if (!data2.containsKey(key)) {
                result.append("-");
                result.append(key);
                result.append(":");
                result.append(data1.get(key).toString());
                result.append("\n");
            } else if (data1.get(key).equals(data2.get(key))) {
                result.append(key);
                result.append(":");
                result.append(data2.get(key).toString());
                result.append("\n");
            } else {
                result.append("-");
                result.append(key);
                result.append(":");
                result.append(data1.get(key).toString());
                result.append("\n");
                result.append("+");
                result.append(key);
                result.append(":");
                result.append(data2.get(key).toString());
                result.append("\n");
            }
        }
        diff = result.toString();
        return 0;
    }
    public static String generate(String[] args) throws Exception {
        int exitCode = new CommandLine(new Differ()).execute(args);
        if (exitCode != 0) {
            throw new Exception("Failed to get diff");
        }
        return diff;
    }

    public static Map<String, Object> getData(String filepath) throws Exception {
        File file = new File(filepath);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(file, Map.class);
    }
}
