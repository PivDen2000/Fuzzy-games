// Copyright (c) 2023 Denys Piven
import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        try {
            var inputOutputFolder = Paths.get("./input_output/");
            var inputPath = inputOutputFolder.resolve("input.txt");
            var outputPath = inputOutputFolder.resolve("output.txt");
            var outputImage = inputOutputFolder.resolve("image.svg");

            var numCuts = FuzzyReader.readNumCuts(inputPath);
            var matrix = FuzzyReader.readMatrix(inputPath);
            var result = FuzzySolver.calcAlphaCutsFuzzyValue(matrix, numCuts);
            FuzzyWriter.writeResult(result, outputPath.toString());
            FuzzyDrawer.draw(result, outputImage.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
