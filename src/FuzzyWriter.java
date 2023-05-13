// Copyright (c) 2023 Denys Piven
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

public class FuzzyWriter {
    public static void writeResult(Double[][] matrix, String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (var row : matrix) {
                writer.write(String.join("\t", Arrays.stream(row)
                        .map(val -> String.format("%.8f", val))
                        .toArray(String[]::new)));
                writer.newLine();
            }
            System.out.println("Matrix has been written to file.");
        } catch (IOException e) {
            System.err.println("Error writing matrix to file: " + e.getMessage());
        }
    }
}
