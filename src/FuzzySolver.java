import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class FuzzySolver {
        public static Double[][] calcAlphaCutsFuzzyValue(FuzzyNumber[][] matrix, int numCuts) {
            var result = new Double[3][numCuts];

            var cuts = new Double[numCuts];
            Double increment = 1.0 / (numCuts - 1);
            cuts[0] = 0.0;
            cuts[numCuts - 1] = 1.0;
            for (int i = 1; i < numCuts - 1; i++) {
                cuts[i] = cuts[i - 1] + increment;
            }
            result[0] = cuts;

            for (var i = 0; i < cuts.length; i++) {
                var numMatrix = fuzzyToNum(matrix, cuts[i], true);
                result[1][i] = solve(numMatrix, true);
                //result[2][i] = solve(intMatrix, false);
            }

            return result;
        }

        private static Double[][] fuzzyToNum(FuzzyNumber[][] matrix, Double cut) {
            int m = matrix.length;
            int n = matrix[0].length;
            Double[][] result = new Double[m][n];

            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    var values = matrix[i][j].arr;

                    var min = values[0];
                    var max = values[values.length - 1];
                    var cutValue = cut * (max - min) + min;

                    var index = -1;
                    var diff = Double.POSITIVE_INFINITY;
                    for (int k = 0; k < values.length; k++) {
                        var currentDiff = Math.abs(cutValue - values[k]);
                        if (currentDiff < diff) {
                            diff = currentDiff;
                            index = k;
                        }
                    }
                    result[i][j] = values[index];
                }
            }
            return result;
        }
    private static Double[][] fuzzyToNum(FuzzyNumber[][] matrix, Double cut, boolean beta) {
        int m = matrix.length;
        int n = matrix[0].length;
        var result = new Double[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                var values = matrix[i][j].arr;

                var min = values[0];
                var max = values[values.length - 1];
                var cutValue = cut * (max - min) + min;

                result[i][j] = cutValue;
            }
        }
        return result;
    }

        public static Double solve(Double[][] matrix, boolean max) {
            int m = matrix.length;
            int n = matrix[0].length;

            double[] c = new double[n + 1];
            c[n] = 1;
            LinearObjectiveFunction f = new LinearObjectiveFunction(c, 0);

            double[][] A = new double[2*m + 1][n + 1];
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    A[i][j] = matrix[i][j];
                }
                A[i][n] = -1;
            }
            Arrays.fill(A[m], 1);
            A[m][n] = 0;
            for(int i = 0; i<m; i++){
                A[m+1+i][i] = 1;
            }

            int[] b = new int[2*m + 1];
            b[m] = 1;

            Relationship[] r = new Relationship[2*m + 1];
            for (int i = 0; i < m; i++) {
                r[i] = Relationship.LEQ;
                r[i + m + 1] = Relationship.GEQ;
            }
            r[m] = Relationship.EQ;

            Collection<LinearConstraint> constraints = new
                    ArrayList<LinearConstraint>();
            for(int i=0; i<A.length; i++) {
                constraints.add(new LinearConstraint(A[i], r[i], b[i]));
            }

            SimplexSolver solver = new SimplexSolver();
            PointValuePair optSolution = solver.optimize(new MaxIter(100), f, new
                            LinearConstraintSet(constraints),
                    GoalType.MINIMIZE, new NonNegativeConstraint(false));

            return optSolution.getValue();
        }
    }