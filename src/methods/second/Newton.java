package methods.second;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import formulareader.FormulaReaderWithTwoArguments;
import methods.MethodsInterface;
import methods.math.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Newton implements MethodsInterface {
    //служебные
    private FormulaReaderWithTwoArguments formula;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    //Вводимые
    private double xk[][];      //втч x0
    private double[][] currentGrad;
    private String[][] grad = {{"4*x-2*y+2"},{"2*y-2*x"}};
    private double[][] matrixHesse = {{4, -2},{-2, 2}};

    //В процессе работы
    private double[][] xk_new = new double[2][1];  //xk+1
    private double eps1;
    private int k;
    private double[][] result;
    private int m;
    private double[][] dk;
    private double tk;
    private double eps2;

    public Newton() {
    }
    //2*x^2+y^2-2*x*y+2*x+6

    @Override
    public void start() {
        if (formula != null) {
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
        return 0;
    }

    private double[][] calcGrad() {
        double[][] result = new double[2][1];
        String input1;
        String input2;
        input1 = grad[0][0].replaceAll("x",xk[0][0]+"").replaceAll("y", xk[1][0]+"");
        input2 = grad[1][0].replaceAll("x",xk[0][0]+"").replaceAll("y", xk[1][0]+"");
        result[0][0] = new FormulaReader(input1).calculateFormula(0);
        result[1][0] = new FormulaReader(input2).calculateFormula(0);
        return result;
    }

    private void calcMethod() {
        boolean flag = false;
        while (true) {
            //3
            currentGrad = calcGrad();
            //4
            if (MatrixMath.norma(currentGrad) <= eps1) {
                result = xk;
                printResult();
                break;
            }
            //5
            if (k >= m) {
                result = xk;
                printResult();
                break;
            }
            //6 в полях вычислили H(x). В данной ситуации матрица врена при любых x и y
            //7
            double [][] reverseH = matrixHesse.clone();
            MatrixMath.inversion(reverseH, reverseH[0].length);
            //8
            if (MatrixMath.det2x2Matrix(reverseH) > 0) {
                //9
                dk = MatrixMath.multiple(MatrixMath.difference(new double[reverseH.length][reverseH[0].length], reverseH), currentGrad);
            }
            else {
                //8
                dk = MatrixMath.division(new double[currentGrad.length][currentGrad[0].length],currentGrad);
            }
            //10
            if (MatrixMath.det2x2Matrix(reverseH) > 0) {
                tk = 1;
            }
            else {
                // TODO: 25.05.2018 Сделать условие
                System.out.println("TODO: 25.05.2018 Сделать условие");
            }
            xk_new[0][0] = xk[0][0] + tk * dk[0][0];
            xk_new[1][0] = xk[1][0] + tk * dk[1][0];

            System.out.print("Iteration " + k + ": ");
            System.out.println("(" + xk_new[0][0] + " " + xk_new[1][0] + ")");
            //11
            if (MatrixMath.norma(MatrixMath.difference(xk_new, xk)) < eps2
                    && Math.abs(calcF(xk_new) - calcF(xk)) < eps2) {
                if (flag) {
                    result = xk_new;
                    printResult();
                    break;
                }
                flag = true;
            }
            else {
                flag = false;

            }
            k++;
            xk = xk_new.clone();
        }
    }

    private double calcF(double[][] x) {
        return formula.calculateFormula(x[0][0], x[1][0]);
    }

    private void printResult() {
        System.out.print("x* = (");
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                System.out.print(result[i][j] + " ");
            }
        }
        System.out.println(")");
        System.out.println("f(x*) = " + calcF(result));
    }

    private void initializeVariables() {
        //Ввод начальных данных
        //1
        k = 0;
        while (true) {
            try {
                System.out.println("Введите x0 через пробел:");
                String[] inputStrings = reader.readLine().split(" ");
                xk = new double[2][1];
                for (int i = 0; i < xk.length; i++) {
                    xk[i][0] = Double.parseDouble(inputStrings[i]);
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
    }
}
