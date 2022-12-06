package hexlet.code;

public class App {
    public static void main(String[] args) {
        try {
            System.out.println(Differ.generate(args));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}