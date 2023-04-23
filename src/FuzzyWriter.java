import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class FuzzyWriter {

    public static void writeResultToFile(Double[][] matrix, String fileName) {
        try {
            String[] labels = {"a","Z^U_a","Z^L_a"};
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            for (int i = 0; i < matrix.length; i++) {
                writer.write(labels[i] + "\t");
                for (int j = 0; j < matrix[i].length; j++) {
                    if (matrix[i][j] == null) {
                        writer.write("0\t");
                    } else {
                        var acc = 10000.0;
                        writer.write(Math.round(matrix[i][j] * acc) / acc + "\t");
                    }
                }
                writer.newLine();
            }
            writer.close();
            System.out.println("Matrix has been written to file.");
        } catch (IOException e) {
            System.err.println("Error writing matrix to file: " + e.getMessage());
        }
    }

}
