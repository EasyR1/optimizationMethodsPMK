package methods.firstPor.gradientSpusk.dfp;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import formulareader.FormulaReaderWithTwoArguments;
import methods.MethodsInterface;
import methods.SimpleStarting;
import methods.goldenRatioMethod.GoldenRatio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class DFPMethod implements MethodsInterface {
    //служеб.
    private FormulaReaderWithTwoArguments formula;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //вводимые
    private double[][] xk_old = new double[2][1];
    private double[][] xk_new = new double[2][1];
    private double eps1;
    private double eps2;
    private int m;
    //использующиеся в вычислениях
    private int k = 0;
    private double[][] aMatrix= {{1,0},{0,1}};
    private String[][] gradF = {{"4*x-2*y+2"},{"-2*x+2*y"}};
    //Появляются в вычислениях
    private double[][] result;
    private double[][] currentGrad = new double[2][1];
    private double[][] deltaG;
    private double[][] deltaX;
    private double[][] a_new;
    private double[][] dk;
    private double tk;
    private boolean flag = false;


    @Override
    public void start() {
        if(formula != null) {
            startMethod();
        }
    }

    @Override
    public void startMethod() {
        initializeVariables();
        calcMethod();
    }

    @Override
    public void inputOptions(FormulaInterface formulaReader) {
        formula = (FormulaReaderWithTwoArguments) formulaReader;
    }

    @Override
    public double returnResult() {
        return result[0][0];
    }

    private void calcMethod() {
        while (true) {
            //3
            currentGrad = grad(xk_new[0][0], xk_new[1][0]);
            //4
            if (normaGrad() < eps1) {
                result = xk_new;
                printResult();
                return;
            }
            //5
            if(k > m) {
                result = xk_new;
                printResult();
                return;
            }
            if(k >= 1) {
                //6
                deltaG = difference(grad(xk_new[0][0], xk_new[1][0]),grad(xk_old[0][0],xk_old[1][0]));
                //7
                deltaX = difference(xk_new, xk_old);
                //8
                a_new = terribleFraction();
                //9
                aMatrix = summa(aMatrix,a_new);
            }
            //10
            dk = multiple(difference(new double[2][2],aMatrix),currentGrad);
            //11
            tk = findMin();
            //12
            double[][] bufxk = xk_new.clone();
            xk_new = summa(xk_new, multiple(tk, dk));   //Перегрузка multiple. Умножение матрицы на число
            xk_old = bufxk.clone();
            //13
            if (norma(difference(xk_new, xk_old)) < eps2 &&
                    Math.abs(calcF(xk_new[0][0], xk_new[1][0]) - calcF(xk_old[0][0], xk_old[1][0])) < eps2) {
                if (flag) {
                    result = xk_new;
                    printResult();
                    return;
                }
                flag = true;    //Проверка для k и k+1;
            }
            else {
                k++;
                System.out.println(k + "\nIteration compleate tk = " + tk);
                continue;       //к шагу 3
            }
        }
    }

    private void printResult() {
        System.out.print("(");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
            }
        }
        System.out.print(")");
    }

    private double[][] terribleFraction() {
        double[][] drob1 = division(multiple(deltaX, transpose(deltaX)),
                                    multiple(transpose(deltaX), deltaG));
        double[][] drob2 = division(multiple(multiple(multiple(aMatrix,deltaG),transpose(deltaG)),aMatrix),
                                    multiple(multiple(transpose(deltaG), aMatrix),deltaG));
        return difference(drob1, drob2);
    }

    private double findMin() {
        SimpleStarting method = new GoldenRatio();
        return method.simpleStart(convertToStringFormula());        // FIXME: 12.05.2018 Critical Bug for FormulaReader
    }

    private String convertToStringFormula() {
        //Начало поиска минимума
        String func = formula.getInputFunction();   //Получение строковой формулы
        //Меняем х на первую строку матрицы уi
        func = func.replaceAll("x",
                "(" + (xk_new[0][0] > 0? xk_new[0][0]:("(0" + xk_new[0][0] + ")"))  + "+" + (dk[0][0] > 0? dk[0][0]:("(0" + dk[0][0] + ")")) + "*x)");     // х - это искомое t
        //Меняем y на вторую строку матрицы xi
        func = func.replaceAll("y",
                "(" + (xk_new[1][0] > 0? xk_new[1][0]:("(0" + xk_new[1][0] + ")")) + "+" + (dk[1][0] > 0? dk[1][0]:("(0" + dk[1][0] + ")")) + "*x)");     // х - это искомое t
        return func;
    }


    private double[][] multiple(double[][] a, double[][] b) {
        //Правильно? да
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

    private double[][] multiple(double a, double[][] b) {
        //Правильно? да
        double[][] result = new double[b.length][b[0].length];
        for (int i = 0; i < b.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                    result[i][j] = a * b[i][j];
            }
        }
        return result;
    }

    private double[][] division(double[][] a, double[][] b) {
        //b - матрица 1х1 (число). делим каждый элемент матрицы на число.
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                result[i][j] = a[i][j]/b[0][0];
            }
        }
        return result;
    }

    private double[][] transpose(double[][] a) {
        double[][] res = new double[a[0].length][a.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                res[i][j] = a[j][i];
            }
        }
        return res;
    }

    private double[][] summa(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j  < a[0].length; j++) {
                result[i][j] = a[i][j] + b[i][j];
            }
        }
        return result;
    }

    private double[][] difference(double[][] a, double[][] b) {
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j  < a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    private double normaGrad() {
        return Math.sqrt(Math.pow(currentGrad[0][0], 2) + Math.pow(currentGrad[1][0], 2));
    }

    private double norma(double[][] a) {
        double bufRes = 0;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                bufRes += Math.pow(a[i][j], 2);
            }
        }
        return Math.sqrt(bufRes);
    }

    private double[][] grad(double x, double y) {
        double[][] currentGrad = new double[2][1];
        String firstGrad = gradF[0][0].replaceAll("x",x + "").replaceAll("y",y + "");
        String secondGrad = gradF[1][0].replaceAll("x",x + "").replaceAll("y",y + "");

        if (firstGrad.charAt(0) == '-') {
            firstGrad = '0' + firstGrad;
        }
        if (secondGrad.charAt(0) == '-') {
            secondGrad = '0' + secondGrad;
        }

        currentGrad[0][0] = new FormulaReader(firstGrad).calculateFormula(0);
        currentGrad[1][0] = new FormulaReader(secondGrad).calculateFormula(0);  // FIXME: 12.05.2018 Critical Bug FormulaReader for -x
        return currentGrad;
    }


    private double calcF(double x, double y) {
        return formula.calculateFormula(x, y);
    }

    private void initializeVariables() {
        //Ввод начальных данных
        //1
        while (true) {
            try {
                System.out.println("Введите x0 через пробел:");
                String[] inputStrings = reader.readLine().split(" ");
                xk_new = new double[2][1];
                for (int i = 0; i < xk_old.length; i++) {
                    xk_new[i][0] = Double.parseDouble(inputStrings[i]);
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода");
            }
        }

        while (true) {
            try {
                System.out.println("Введите eps1, eps2 через пробел:");
                String[] inputStrings = reader.readLine().split(" ");
                eps1 = Double.parseDouble(inputStrings[0]);
                eps2 = Double.parseDouble(inputStrings[1]);
                if(eps1 <= 0 || eps2 <= 0) {
                    System.out.println("Одно значение меньше 0, повторите попытку ввода");
                    continue;
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода");
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Введите два epsilon через пробел");
            }
        }

        while (true) {
            try {
                System.out.println("Введите предельное число итераций M:");
                m = Integer.parseInt(reader.readLine());
                if (m <= 0) {
                    System.out.println("Введите число итераций отличное от 0");
                    continue;
                }
                break;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (NumberFormatException e) {
                System.out.println("Ошибка ввода");
            }
        }
        k = 0;
    }

    public static void main(String[] args) {
        double [][] a = {{1, 2},{3, 4}, {5, 6}};
        System.out.println(a.length + " " + a[0].length);
    }
}
