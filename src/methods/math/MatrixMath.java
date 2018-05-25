package methods.math;

public class MatrixMath {
    public static double norma(double[][] a) {
        double bufRes = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                bufRes += Math.pow(a[i][j], 2);
            }
        }
        return Math.sqrt(bufRes);
    }

    public static double[][] difference(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    public static double[][] summa(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    public static double[][] transpose(double[][] a) {
        double[][] res = new double[a[0].length][a.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                res[i][j] = a[j][i];
            }
        }
        return res;
    }

    public static double[][] division(double[][] a, double[][] b) {
        //b - матрица 1х1 (число). делим каждый элемент матрицы на число.
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j] / b[0][0];
            }
        }
        return result;
    }

    public static double[][] multiple(double a, double[][] b) {
        double[][] result = new double[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                result[i][j] = a * b[i][j];
            }
        }
        return result;
    }

    public static double[][] multiple(double[][] a, double[][] b) {
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static void inversion(double[][] a, int n) {
        double temp;
        final double[][] E = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                E[i][j] = 0f;
                if (i == j)
                    E[i][j] = 1f;
            }

        for (int k = 0; k < n; k++) {
            temp = a[k][k];
            for (int j = 0; j < n; j++) {
                a[k][j] /= temp;
                E[k][j] /= temp;
            }
            for (int i = k + 1; i < n; i++) {
                temp = a[i][k];
                for (int j = 0; j < n; j++) {
                    a[i][j] -= a[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int k = n - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                temp = a[i][k];
                for (int j = 0; j < n; j++) {
                    a[i][j] -= a[k][j] * temp;
                    E[i][j] -= E[k][j] * temp;
                }
            }
        }
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = E[i][j];
    }

    public static double det2x2Matrix(double[][] matrix) {
        return matrix[0][0] * matrix[1][1] - matrix[1][0] * matrix[0][1];
    }

}
