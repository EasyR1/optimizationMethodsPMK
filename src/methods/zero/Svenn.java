package methods.zero;

import formulareader.FormulaReader;

import java.util.Scanner;

public class Svenn {
    private FormulaReader function;
    private double a;
    private double b;
    private double t;
    private int k;
    private double[] x;
    private double x0;
    private double xk2;
    private double xk1;
    private double delta;

    public Svenn(FormulaReader function, double t, double x0) {
        this.function = function;
        this.t = t;
        x = new double[80];
        x[0] = x0;
        k = 0;
        doMethod();
    }

    public Svenn(FormulaReader function) {
        System.out.println("-----------------------Start Svenn----------------------");
        Scanner sc = new Scanner(System.in);
        this.function = function;
        x = new double[80];
        while (true) {
            try {
                System.out.print("t: ");
                t = sc.nextDouble();
                System.out.print("x0: ");
                x[0] = sc.nextDouble();
                break;
            } catch (NumberFormatException e) {
                System.out.print("ERROR: Ошибка t или x0. Повторите ввод" + "\n" +
                        "     ! Не целые числа записываются через \",\" Например: 0,001");
            }
        }
        k = 0;
        doMethod();
        printResult();
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getK() {
        return k;
    }

    private double calcF(double x) {
        return function.calculateFormula(x);
    }

    private void doMethod() {
        //3
        if (calcF(x[0]-t) >= calcF(x[0])
                && calcF(x[0]+t) >= calcF(x[0])) {
            a = x[0] - t;
            b = x[0] + t;
            return;
        } else 
            if (calcF(x[0]-t) <= calcF(x[0])
                    && calcF(x[0]+t) <= calcF(x[0])) {
                System.out.println("Не унимодальная функция, возьмите другой x0");
            }
        //4    
        if (calcF(x[0]-t) >= calcF(x[0])
                && calcF(x[0]+t) <= calcF(x[0])){
            delta = t;
            a = x[0];
            x[1] = x[0] + t;
            k = 1;
        }
        
        if (calcF(x0-t) <= calcF(x0)
                && calcF(x0+t) >= calcF(x0)) {
            delta = -t;
            b = x[0];
            x[1] = x[0] - t;
            k = 1;
        }

        //5-6
        while (true) {
            x[k+1] = x[k] + Math.pow(2.0, k) * delta;

            if (calcF(x[k+1]) < calcF(x[k]) && delta == t)
                a = x[k];

            if (calcF(x[k+1]) < calcF(x[k]) && delta == -t)
                b = x[k];

            if (calcF(x[k+1]) >= calcF(x[k]) && delta == t) {
                b = x[k+1];
                break;
            }

            if (calcF(x[k+1]) >= calcF(x[k]) && delta == -t) {
                a = x[k+1];
                break;
            }
            k++;
        }
    }

    public void printResult() {
        System.out.println("Отрезок на " + k + " итерации:  [" + a + ";" + b + "]" + "\n" +
                            "-------------------------End Svenn-----------------------");
    }
}
