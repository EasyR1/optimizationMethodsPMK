package methods;

import formulareader.FormulaReader;

import java.util.Scanner;

public class DichotomyMethod {
    double eps;
    double t;
    double x0;
    double a;
    double b;
    double y;
    double z;
    double l;
    int k;
    double x;
    FormulaReader function;

    public DichotomyMethod(FormulaReader function) {
        Scanner sc = new Scanner(System.in);
        this.function = function;
        try {
            System.out.println("Epsilon: ");
            eps = sc.nextDouble();
        } catch (NumberFormatException e) {
            System.out.println("ERROR: Не корректный эпсилон. Испольуется значение по умолчанию" + "\n" +
                               "     ! Не целые числа записываются через \",\" Например: 0,001");
            eps = 0.001;
        }

        Svenn svenn = new Svenn(function);
        a = svenn.getA();
        b = svenn.getB();



        System.out.println("--------Алгоритм забыл чего одномерной минимизации-------");

        System.out.print("l = ");
        l = sc.nextDouble();


        x = calcMethod();

        System.out.println("x* = " + x +
                "\nf(x*) = " + function.calculateFormula(x));
        System.out.println("-----------------------------end-------------------------");
    }

    private double calcMethod() {
        int k = -1;
        double x;

        do {
            k++;
            y = (a+b-eps)/2;
            z = (a+b+eps)/2;

            if (function.calculateFormula(y) <= function.calculateFormula(z)) {
                //a не меняется
                b = z;
            }
            else {
                a = y;
                //b не меняется
            }
        } while (Math.abs(b-a) > l);

        x = (a+b)/2;


        return x;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Formula: ");
        FormulaReader formulaReader = new FormulaReader(sc.next());
        DichotomyMethod dichotomyMethod = new DichotomyMethod(formulaReader);

        /*
        Scanner sc = new Scanner(System.in);

        System.out.print("Function: ");
        FormulaReader function = new FormulaReader(sc.next());

        double eps = 0.1;
        System.out.print("t: ");
        double t = sc.nextDouble();
        System.out.print("x0: ");
        double x0 = sc.nextDouble();
        Svenn svenn = new Svenn(function, t, x0);
        double a = svenn.getA();
        double b = svenn.getB();
        double y;
        double z;
        double l = 0.2;
        int k = -1;
        double x;

        svenn.printResult();

        do {
            k++;
            y = (a+b-eps)/2;
            z = (a+b+eps)/2;

            if (function.calculateFormula(y) <= function.calculateFormula(z)) {
                //a не меняется
                b = z;
            }
            else {
                a = y;
                //b не меняется
            }
        } while (Math.abs(b-a) > l);

        x = (a+b)/2;

        System.out.println("x* = " + x +
                "\nf(x*) = " + function.calculateFormula(x));
       */
    }
}
