import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FuzzyMatrixParser {

    public static FuzzyInteger[][] readMatrixFromFile(Path inputPath) throws IOException {
        String input = Files.readString(inputPath, Charset.defaultCharset());
        return parse(input);
    }
    public static FuzzyInteger[][] parse(String input) {
        List<List<FuzzyInteger>> matrix = new ArrayList<>();

        String[] lines = Arrays.stream(input.split("\r\n"))
                .filter(line -> !line.isEmpty())
                .toArray(String[]::new);

        int n = Integer.parseInt(lines[0].substring(2));
        int m = Integer.parseInt(lines[1].substring(2));

        for (int i = 2; i < n + 2; i++) {
            String[] values = lines[i].replaceAll("\\s+", "").split(",\\[");
            List<FuzzyInteger> row = new ArrayList<>();
            for (String value : values) {
                value = value.replaceAll("[\\[\\]]", "");
                String[] fuzzyValues = value.split(",");
                Integer[] integers = new Integer[fuzzyValues.length];
                for (int j = 0; j < fuzzyValues.length; j++) {
                    integers[j] = Integer.parseInt(fuzzyValues[j]);
                }
                if (integers.length == 1) {
                    row.add(new FuzzyInteger(integers[0]));
                } else if (integers.length == 2) {
                    row.add(new FuzzyInteger(integers[0], integers[1]));
                } else if (integers.length == 3) {
                    row.add(new FuzzyInteger(integers[0], integers[1], integers[2]));
                } else if (integers.length == 4) {
                    row.add(new FuzzyInteger(integers[0], integers[1], integers[2], integers[3]));
                }
            }
            matrix.add(row);
        }

        FuzzyInteger[][] result = new FuzzyInteger[n][m];
        for (int i = 0; i < n; i++) {
            List<FuzzyInteger> row = matrix.get(i);
            for (int j = 0; j < m; j++) {
                result[i][j] = row.get(j);
            }
        }

        return result;
    }

    public static void writeMatrixToFile(FuzzyInteger[][] matrix, String filePath) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        writer.write("n=" + matrix.length + "\nm=" + matrix[0].length + "\n");
        writer.newLine();
        for (FuzzyInteger[] row : matrix) {
            writer.write(String.join(", ", Arrays.stream(row)
                    .map(FuzzyInteger::toString)
                    .toArray(String[]::new)));
            writer.newLine();
        }
        writer.close();
    }

}
