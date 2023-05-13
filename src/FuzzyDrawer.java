import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public class FuzzyDrawer {

    public record Point(double x, double y, int intX, int intY) {
    }

    public static void draw(Double[][] matrix, String path) {

        final int width = 400;
        final int height = 400;

        double zero = 0.0;
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;

        var data = new Double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            System.arraycopy(matrix[i], 0, data[i], 0, matrix[i].length);
        }

        for (int i = 1; i < data.length; i++) {
            for (var value : data[i]) {
                min = Math.min(min, value);
                max = Math.max(max, value);
            }
        }

        min = Math.min(min, zero);
        max = Math.max(max, zero);
        zero = (zero - min) / (max - min);

        for (int i = 1; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = (data[i][j] - min) / (max - min);
            }
        }

        var minX = (int) (0.125 * width);
        var minY = (int) (0.875 * height);
        var maxX = (int) (0.875 * width);
        var maxY = (int) (0.125 * height);
        var zeroX = (int) ((0.125 + zero * 0.75) * width);
        var zeroY = (int) (0.875 * height);

        var points = new ArrayList<Point>();

        StringBuilder polygon = new StringBuilder("<polygon points=\"");
        for (int i = 0; i < data[0].length; i++) {
            points.add(new Point(matrix[1][i], matrix[0][i],
                    (int) (minX + data[1][i] * (maxX - minX)),
                    (int) (minY + data[0][i] * (maxY - minY))));
        }
        for (int i = data[0].length - 1; i >= 0; i--) {
            points.add(new Point(matrix[2][i], matrix[0][i],
                    (int) (minX + data[2][i] * (maxX - minX)),
                    (int) (minY + data[0][i] * (maxY - minY))));
        }
        points.forEach(point -> polygon.append(point.intX).append(",").append(point.intY).append(" "));
        polygon.append("\" fill=\"none\" stroke=\"black\" />");

        points.add(new Point(0.0, 0.0, zeroX, zeroY));
        points.add(new Point(max, 0.0, maxX, minY));
        points.add(new Point(max, 1.0, maxX, maxY));

        StringBuilder svgContent = new StringBuilder("<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"" + width + "\" height=\"" + height + "\">"
                + "<line x1=\"0\" y1=\"" + zeroY + "\" x2=\"" + width + "\" y2=\"" + zeroY + "\" stroke=\"black\" />"
                + "<line x1=\"" + zeroX + "\" y1=\"0\" x2=\"" + zeroX + "\" y2=\"" + height + "\" stroke=\"black\" />");

        points.forEach(point -> svgContent
                .append("<text x=\"").append(point.intX + 3)
                .append("\" y=\"").append(point.intY - 3)
                .append("\" font-family=\"Arial\" font-size=\"3\" fill=\"black\">(")
                .append(Math.round(point.x * 100.0) / 100.0).append(", ")
                .append(Math.round(point.y * 100.0) / 100.0).append(")</text>"));
        svgContent.append(polygon).append("</svg>");

        try (Writer writer = new FileWriter(path)) {
            writer.write(svgContent.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
