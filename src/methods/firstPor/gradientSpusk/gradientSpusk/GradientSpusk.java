package methods.firstPor.gradientSpusk.gradientSpusk;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import methods.MethodsInterface;

import java.util.Scanner;

public class GradientSpusk implements MethodsInterface {
    //Вводимые данные
    private double x0;
    private double x1;
    private double eps1;
    private double eps2;
    private int m;
    //созданные методы
    private FormulaReader function;
    private int k;
    private double result;
    private Scanner sc;
    private double[] gradF;
    private double xk;
    private double xk1;

    public GradientSpusk() {
        sc = new Scanner(System.in);
    }

    @Override
    public void start() {
        String input;
        String[] strings;
        System.out.println("--------Метод наискорейшего градиентного спуска---------");
        while (true) {
            System.out.println("Введите x1 и x2 через пробел");
            input = sc.next();
            strings = input.split(" ");
            if (strings.length == 2) {
                try {
                    x0 = Double.parseDouble(strings[0]);
                    x1 = Double.parseDouble(strings[1]);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Ошибка ввода данных. Повторите попытку");
                }
                System.out.println("ERROR: Введено больше двух значений Повторите попытку");
            }
        }

    }

    @Override
    public void startMethod() {
        //2
        k = 0;
        //3
        gradF = gradF(x0, x1);
        //4


    }

    @Override
    public void inputOptions(FormulaInterface formulaReader) {
        function = (FormulaReader) formulaReader;
        String input;
        String[] strings;
        while (true) {
            System.out.println("Введите \u03b51 \u03b52 через пробел");
            input = sc.next();
            strings = input.split(" ");
            if (strings.length == 2) {
                try {
                    eps1 = Double.parseDouble(strings[0]);
                    eps2 = Double.parseDouble(strings[1]);
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("ERROR: Ошибка ввода данных. Повторите попытку");
                }
                System.out.println("ERROR: Введено больше двух значений Повторите попытку");
            }
        }

        while (true) {
            System.out.println("Введите колличество итераций M");
            try {
                m = Integer.parseInt(sc.next());
                if (m > 0)
                    break;
            } catch (NumberFormatException e) {
                System.out.print("ERROR: Некорректный ввод.");
            }
            System.out.print("ERROR: Некорректный ввод. Введите число больше 0");
        }
    }

    @Override
    public double returnResult() {
        return result;
    }

    private double[] gradF(double x1, double x2) {
        double[] fxk = new double[2];
        fxk[0] = 6*x1 + 4*x2 - 8;
        fxk[1] = 4*x2 - 12;
        return fxk;
    }

    private double normaGrad() {
        return Math.sqrt(Math.pow(gradF[0], 2) + Math.pow(gradF[1], 2));
    }
}
