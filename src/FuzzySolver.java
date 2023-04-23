import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

public class FuzzySolver {
    public static Double[][] calcAlphaCutsFuzzyValue(FuzzyNumber[][] matrix, int numCuts) {
        var cuts = IntStream.range(0, numCuts)
                .mapToDouble(i -> i * 1.0 / (numCuts - 1))
                .boxed()
                .toArray(Double[]::new);

        return new Double[][]{
                cuts,
                Arrays.stream(cuts)
                        .map(cut -> solve(fuzzyToNum(matrix, cut, true), false))
                        .toArray(Double[]::new),
                Arrays.stream(cuts)
                        .map(cut -> solve(fuzzyToNum(matrix, cut, false), true))
                        .toArray(Double[]::new)
        };
    }

    private static double[][] fuzzyToNum(FuzzyNumber[][] matrix, double cut, boolean isMax) {
        var result = new double[matrix.length][matrix[0].length];

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                var values = matrix[i][j].arr;

                if (values.length == 1) {
                    result[i][j] = values[0];
                    continue;
                }

                var max = values[values.length - 1];
                var min = values[0];
                var mid = Arrays.copyOfRange(values, 1, values.length - 1);

                var maxCutValue = max - cut * (max - mid[mid.length - 1]);
                var minCutValue = min + cut * (mid[0] - min);

                result[i][j] = isMax ? maxCutValue : minCutValue;
            }
        }

        return result;
    }

    public static Double solve(double[][] matrix, boolean isMax) {
        var m = matrix.length;
        var n = matrix[0].length;

        var c = new double[n + 1];
        c[n] = 1;
        LinearObjectiveFunction f = new LinearObjectiveFunction(c, 0);

        if (isMax) {
            matrix = MatrixUtils.createRealMatrix(matrix).transpose().getData();
        }

        var A = new double[2 * m + 1][n + 1];
        for (int i = 0; i < m; i++) {
            System.arraycopy(matrix[i], 0, A[i], 0, n);
            A[i][n] = -1;
        }
        Arrays.fill(A[m], 1);
        A[m][n] = 0;
        Arrays.fill(A, m + 1, A.length,
                IntStream.range(0, m)
                        .mapToDouble(i -> i == m - 1 ? 1 : 0)
                        .toArray());

        var b = new int[2 * m + 1];
        b[m] = 1;

        Relationship[] r = new Relationship[2 * m + 1];
        Arrays.fill(r, m + 1, 2 * m + 1, Relationship.GEQ);
        r[m] = Relationship.EQ;
        Arrays.fill(r, 0, m, isMax ? Relationship.GEQ : Relationship.LEQ);

        var constraints = new ArrayList<LinearConstraint>();
        for (int i = 0; i < A.length; i++) {
            constraints.add(new LinearConstraint(A[i], r[i], b[i]));
        }

        var solver = new SimplexSolver();
        var optSolution = solver.optimize(new MaxIter(100), f, new LinearConstraintSet(constraints),
                isMax ? GoalType.MAXIMIZE : GoalType.MINIMIZE, new NonNegativeConstraint(false));

        return optSolution.getValue();
    }
}
