package methods.firstPor.gradientSpusk.dfp;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import formulareader.FormulaReaderWithTwoArguments;
import methods.MethodsInterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class dfpMethod implements MethodsInterface {
    //служеб.
    private FormulaReaderWithTwoArguments formula;
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //вводимые
    private double[][] xk_old = new double[1][1];
    private double[][] xk_new = new double[1][1];
    private double eps1;
    private double eps2;
    private int m;
    //использующиеся в вычислениях
    private int k = 0;
    private double[][] aMatrix= {{1,0},{0,1}};
    private String[][] gradF = {{"4x-2y+2"},{"-2x+2y"}};
    //Появляются в вычислениях
    private double[][] result = new double[1][1];
    private double[][] currentGrad = new double[1][1];
    private double[][] deltaG;
    private double[][] deltaX;
    private double[][] a_new;


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
        //3
        currentGrad = grad(xk_new[0][0], xk_new[0][1]);
        //4
        if (normaGrad() < eps1) {
            result = xk_old;
            return;
        }
        //5
        if(k > m) {
            result = xk_old;
            return;
        }
        if(k >= 1) {
            // TODO: 12.05.2018 Переход к шагу 6
            //шаг 6
        }
        //6
        deltaG = difference(grad(xk_new[0][0], xk_new[0][1]),grad(xk_old[0][0],xk_old[0][0]));
        //7
        deltaX = difference(xk_new, xk_old);
        //8
        a_new = terribleFraction();
    }

    private double[][] terribleFraction() {
        // TODO: 12.05.2018 Реализация
        return new double[0][0];
    }

    private double[][] transpose(double[][] a) {
        double[][] res = new double[a[0].length][a.length];
        for (int i = 0; i < a[0].length; i++) {
            for (int j = 0; j < a.length; j++) {
                res[i][j] = a[j][i];
                // TODO: 12.05.2018 Доделать условие
            }
        }
        return res;
    }

    private double[][] difference(double[][] a, double[][] b) {
        if (a.length != b.length)
            return new double[0][0];
        double[][] result = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j  <a[0].length; j++) {
                result[i][j] = a[i][j] - b[i][j];
            }
        }
        return result;
    }

    private double normaGrad() {
        return Math.sqrt(Math.pow(currentGrad[0][0], 2) + Math.pow(currentGrad[0][1], 2));
    }

    private double[][] grad(double x, double y) {
        double[][] currentGrad = new double[1][1];
        String firstGrad = gradF[0][0].replaceAll("x",x + "");
        String secondGrad = gradF[1][0].replaceAll("y",y + "");

        currentGrad[0][0] = new FormulaReader(firstGrad).calculateFormula(0);
        currentGrad[0][1] = new FormulaReader(secondGrad).calculateFormula(0);
        return currentGrad;
    }

    private void initializeVariables() {
        //1
        while (true) {
            try {
                System.out.println("Введите xk_old через пробел:");
                String[] inputStrings = reader.readLine().split("");
                xk_new = new double[1][1];
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
                String[] inputStrings = reader.readLine().split("");
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
}
