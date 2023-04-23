import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FuzzyReader {

    public static FuzzyNumber[][] readMatrixFromFile(Path inputPath) throws IOException {
        String input = Files.readString(inputPath, Charset.defaultCharset());
        return parse(input);
    }
    public static FuzzyNumber[][] parse(String input) {
        List<List<FuzzyNumber>> matrix = new ArrayList<>();

        String[] lines = Arrays.stream(input.split("\r\n"))
                .filter(line -> !line.isEmpty())
                .toArray(String[]::new);

        int n = Integer.parseInt(lines[0].substring(2));
        int m = Integer.parseInt(lines[1].substring(2));

        for (int i = 2; i < n + 2; i++) {
            String[] values = lines[i].replaceAll("\\s+", "").split(",\\[");
            List<FuzzyNumber> row = new ArrayList<>();
            for (String value : values) {
                value = value.replaceAll("[\\[\\]]", "");
                String[] fuzzyValues = value.split(",");
                var numbers = new Double[fuzzyValues.length];
                for (int j = 0; j < fuzzyValues.length; j++) {
                    numbers[j] = Double.parseDouble(fuzzyValues[j]);
                }
                Arrays.sort(numbers);
                row.add(new FuzzyNumber(numbers));
            }
            matrix.add(row);
        }

        FuzzyNumber[][] result = new FuzzyNumber[n][m];
        for (int i = 0; i < n; i++) {
            List<FuzzyNumber> row = matrix.get(i);
            for (int j = 0; j < m; j++) {
                result[i][j] = row.get(j);
            }
        }

        return result;
    }
}
