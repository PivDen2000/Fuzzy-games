import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            Path inputOutputFolder = Paths.get("./input_output/");
            Path inputPath = Paths.get(inputOutputFolder + "/input.txt");
            Path outputPath = Paths.get(inputOutputFolder + "/output.txt");

            FuzzyNumber[][] matrix = FuzzyReader.readMatrixFromFile(inputPath);

            var result = FuzzySolver.calcAlphaCutsFuzzyValue(matrix, 11);

            FuzzyWriter.writeResultToFile(result, outputPath.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
