package methods.zero;

import formulareader.FormulaInterface;
import formulareader.FormulaReaderWithTwoArguments;
import methods.MethodsInterface;
import methods.SimpleStarting;
import methods.zero.ParabolMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ConjugatedDirections implements MethodsInterface{
    //служебные
    private BufferedReader reader;
    private FormulaReaderWithTwoArguments formula;
    //Вводимые
    private double[] x0;
    private double[][] d = {{0, 0}, {1, 0}, {0, 1}};
    private double eps;

    //Появившиеся в процессе работы
    private double[][] dCop;
    private double[] yi;
    private double[] yi1;
    private double[] y0;
    private double ti;
    private int n;
    private int i;
    private int k;
    private double[] result;
    private double[] xi1;
    private double[] xi;
    private double[] y1;

    public ConjugatedDirections() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void start() {
        if (formula != null) {
            startMethod();
        }
    }

    @Override
    public void startMethod() {
        System.out.println("-------Метод наискорейшего градиентного спуска-----------");
        initializeDataForStep1();
        calcMethod();

    }

    @Override
    public void inputOptions(FormulaInterface formulaReader) {
        formula = (FormulaReaderWithTwoArguments) formulaReader;    //Стартовый класс более узкого типа, исправить. Либо наклепать новый интерфейс
        System.out.println("Введите точность eps > 0");
        try {
            do {
                eps = Double.parseDouble(reader.readLine());
                if (eps <= 0)
                    System.out.println("eps <= 0, Повторите попытку");
            } while (!(eps > 0));
        } catch (NumberFormatException e) {
            System.out.print("ERROR: Некорректный ввод, используется eps = 0.01");
            eps = 0.01;
        } catch (IOException e) {
            e.printStackTrace();
            eps = 0.01;
        }

    }

    private void initializeDataForStep1() {
        n = 2;      //2 переменных x и y

        System.out.println("Введите вектор х0: ");
        try {
            String input = reader.readLine();
            x0 = new double[n];
            int i = 0;
            for (String x : input.split(" ")) {
                x0[i] = Double.parseDouble(x);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initializeOtherVariables() {
        yi = new double[n];
        yi1 = new double[n];
    }

    @Override
    public double returnResult() {
        return 0;
    }

    private void calcMethod() {
        System.out.println(this);
        //1
        d[0][0] = d[n][0];
        d[0][0] = d[n][0];
        i = 0;
        k = 0;
        yi = copy(x0);
        do {
            //2
            ti = findMin();
            yi1 = summ(yi, multiplication(ti, d[i]));
            //3
            if (i < n-1) {
                i++;
                //к шагу 2
                renameForStep2();
                continue;
            }
            if (i == n-1) {
                if (cheсkSimilarity(yi, y0)) {      //yi == yn?
                    result = yi;                    //yi == yn?
                    break;
                }
                else {
                    i++;
                    //к шагу 2
                    renameForStep2();
                    continue;
                }
            }
            if (yi1 == y0) {
                result = yi1;
                break;
            }
            //4
            xi1 = copy(yi1);
            dCop = copy(d);
            if ((calcNorma(subtraction(xi1, xi))) < eps) {
                result = xi1;
                break;
            }
            else {
                dCop[0] = copy(subtraction(yi1, y1));
                dCop[n] = copy(subtraction(yi1, y1));
                for (int i = 1; i < n; i++) {       //n-1 т.е n
                    dCop[i] = d[i+1];
                }
            }

            if (rank(d[1], d[2]) == n) {
                for(int j = 0; j < n+1; j++) {      //До n. т.е до n+1
                    d[j] = dCop[j];
                }
                y0 = xi1;
                k++;
                i = 0;
                //к шагу 2
                renameForStep2();
                continue;

            }

            if (rank(d[1], d[2]) < n) {
                //Возможна ошибка
                //d[j] = d[j]
                y0 = xi1;
                k++;
                i = 0;
            }
        } while (renameForStep2());
        System.out.println("x = " + result + "\nf(x*) = " + formula.calculateFormula(result[0], result[1]));
    }

    private boolean renameForStep2() {
        yi = yi1;
        xi = xi1;
        return true;
    }

    private double[] subtraction(double[] a, double[] b) {
        double[] result = new double[a.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = a[i] - b[i];
        }
        return result;
    }

    private double calcNorma(double[] a) {
        double result = 0;
        for (int i = 0; i < a.length; i++) {
            result += Math.pow(a[i], 2);
        }
        return Math.sqrt(result);
    }

    private boolean cheсkSimilarity(double[] yn, double[] yi) {
        if (yn.length != yi.length)
            return false;
        for (int i = 0; i < yn.length; i++) {
            if (yn[i] != yi[i]) {
                return false;
            }
        }
        return true;
    }

    private double findMin() {
        SimpleStarting method = new ParabolMethod();
        return method.simpleStart(convertToStringFormula(yi));
    }

    private int rank(double[] d1, double[] d2) {
        double[][] a = new double[2][2];
        a[0] = d1;
        a[1] = d2;
        if ((a[0][0] * a[1][1] - a[0][0] * a[0][1]) != 0)
            return 2;
        if (a[0][0] != 0 || a[0][1] != 0 || a[1][0] != 0 || a[1][1] != 0)
            return 1;
        return 0;
    }

    private double[] copy(double[] cop) {
        double[] resArray = new double[cop.length];
        for (int i = 0; i < cop.length; i++) {
            resArray[i] = cop[i];
        }
        return resArray;
    }

    private double[][] copy(double[][] d) {
        double[][] result = new double[3][2];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                result[i][j] = d[i][j];
            }
        }
        return result;
    }

    private double[] summ(double[] array1, double[] array2) {
        double[] resArray = new double[array1.length];
        for (int i = 0; i < array1.length; i++) {
            resArray[i] = array1[i] + array2[i];
        }
        return resArray;
    }

    private double[] multiplication(double a, double[] array) {
        double[] resArray = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            resArray[i] = a * array[i];
        }
        return resArray;
    }

    private String convertToStringFormula(double[] y) {
        String func = formula.getInputFunction();
        //Меняем х на первую строку матрицы уi
        func = func.replace("x", "(" + y[0] + "+" + d[this.i][0] + "*x)");     // х - это искомое t
        //Меняем y на вторую строку матрицы xi
        func = func.replace("y", "(" + y[1] + "+" + d[this.i][1] + "*x)");     // х - это искомое t
        return func;
    }

    @Override
    public String toString() {
        return "ConjugatedDirections{" +
                "x0=" + Arrays.toString(x0) +
                ", yi=" + Arrays.toString(yi) +
                ", yi1=" + Arrays.toString(yi1) +
                ", y0=" + Arrays.toString(y0) +
                ", xi1=" + Arrays.toString(xi1) +
                ", xi=" + Arrays.toString(xi) +
                ", y1=" + Arrays.toString(y1) +
                '}';
    }
}
