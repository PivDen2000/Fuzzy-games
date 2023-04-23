import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.stream.Stream;

public class FuzzyReader {
    public static int readNumCuts(Path inputPath) throws IOException {
        return readVar(inputPath, 'a');
    }

    private static int readVar(Path inputPath, char var) throws IOException {
        try (Stream<String> lines = Files.lines(inputPath, Charset.defaultCharset())) {
            return lines.filter(line -> line.startsWith(var + "="))
                    .map(line -> line.substring(2))
                    .findFirst()
                    .map(Integer::parseInt)
                    .orElseThrow(() -> new IllegalArgumentException("Invalid input file: cannot find '" + var + "='"));
        }
    }


    public static FuzzyNumber[][] readMatrix(Path inputPath) throws IOException {
        var m = readVar(inputPath, 'm');
        var n = readVar(inputPath, 'n');
        var matrix = new FuzzyNumber[m][n];

        var input = Files.readString(inputPath, Charset.defaultCharset());
        var lines = Arrays.stream(input.split("\r\n"))
                .filter(line -> !line.isEmpty())
                .toArray(String[]::new);

        for (int i = 3; i < m + 3; i++) {
            var values = lines[i].replaceAll("\\s+", "").split(",\\[");
            for (int j = 0; j < n; j++) {
                var value = values[j].replaceAll("[\\[\\]]", "");
                var fuzzyValues = value.split(",");
                var numbers = new Double[fuzzyValues.length];
                for (int k = 0; k < fuzzyValues.length; k++) {
                    numbers[k] = Double.parseDouble(fuzzyValues[k]);
                }
                Arrays.sort(numbers);
                matrix[i - 3][j] = new FuzzyNumber(numbers);
            }
        }
        return matrix;
    }
}
