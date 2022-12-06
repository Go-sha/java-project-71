package hexlet.code;

import picocli.CommandLine;
import picocli.CommandLine.Parameters;
import picocli.CommandLine.Option;

@CommandLine.Command(name = "gendiff", mixinStandardHelpOptions = true, version = "gendiff 1.0",
        description = "Compares two configuration files and shows a difference.")
public class Differ {
    @Parameters(paramLabel = "filepath1", description = "path to first file")
    private String filepath1;
    @Parameters(paramLabel = "filepath2", description = "path to second file")
    private String filepath2;
    @Option(names = {"-f", "--format"}, description = "output format [default: stylish]")
    private String format = "stylish";
    public static void generate(String[] args) {
        int exitCode = new CommandLine(new Differ()).execute(args);
        System.exit(exitCode);
    }
}
