package methods.dichotomyMethod;

import formulareader.FormulaReader;
import methods.StartMethodsInterface;
import methods.svenn.Svenn;

import java.util.InputMismatchException;
import java.util.Scanner;

public class DichotomyMethod implements StartMethodsInterface{
    private Scanner sc;
    private double eps;
    private double t;
    private double x0;
    private double a;
    private double b;
    private double y;
    private double z;
    private double l;
    private int k;
    private Double result;
    private FormulaReader function;

    public DichotomyMethod() {
        sc = new Scanner(System.in);
    }

    public DichotomyMethod(FormulaReader function) {
        sc = new Scanner(System.in);
        this.function = function;
    }

    @Override
    public void startMethod(FormulaReader function) {
        setFunction(function);
        inputEps();             //Вводим eps
        inputSegment();         //Вводим отрезок


        System.out.println("---------------------Метод Дихотомии---------------------");
        inputStep();
        result = calcMethod();

        System.out.println("x* = " + result +
                "\nf(x*) = " + function.calculateFormula(result));
        System.out.println("-----------------------------end-------------------------");
    }

    @Override
    public void startMethod() {
        if (function != null)
            startMethod(function);
    }

    @Override
    public double returnResult() {
        if (result != null)
            return result;
        throw new NullPointerException();
    }

    private void inputEps(){
        try {
            System.out.println("Epsilon: ");
            eps = sc.nextDouble();
        } catch (InputMismatchException e) {
            System.out.println("ERROR: Не корректный эпсилон. Испольуется значение по умолчанию" + "\n" +
                    "     ! Не целые числа записываются через \",\" Например: 0,001");
            eps = 0.001;
        }

    }



    private void inputSegment(){
        Svenn svenn = new Svenn(function);
        a = svenn.getA();
        b = svenn.getB();
    }

    private void inputStep(){
        System.out.print("l = ");
        l = sc.nextDouble();
    }

    private double calcf(double x){
        return function.calculateFormula(x);
    }

    private double calcMethod() {
        int k = -1;
        double x;

        do {
            k++;
            y = (a + b - eps) / 2;
            z = (a + b + eps) / 2;

            if (calcf(y) <= calcf(z)) {
                //a не меняется
                b = z;
            } else {
                a = y;
                //b не меняется
            }
        } while (Math.abs(b - a) > l);

        x = (a + b) / 2;


        return x;
    }

    private void setFunction(FormulaReader function) {
        this.function = function;
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
