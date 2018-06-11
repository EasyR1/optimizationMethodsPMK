package methods.zero;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import methods.MethodsInterface;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.InputMismatchException;
import java.util.Scanner;

public class DichotomyMethod implements MethodsInterface {
    private Scanner sc;
    private double eps;
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
    public void start() {
        if (function == null)
            return;

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
            start();
    }

    @Override
    public void inputOptions(FormulaInterface function) {
        this.function = (FormulaReader) function;
        inputEps();     //Вводим eps
        inputSegment(); //Вводим отрезок
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
        String answer;
        while (true) {
            System.out.println("Найти интервал методом Свенна y/n?");
            answer = sc.next();
            answer.toLowerCase();
            if (answer.equals("y"))  {
                useSvenn();
                break;
            }
            else
                if(answer.equals("n")) {
                    useManualInput();
                    break;
                }
                else {
                    System.out.println("Некорректный ввод. Повторите попытку");
                }
        }

    }

    private void useSvenn() {
        Svenn svenn = new Svenn(function);
        a = svenn.getA();
        b = svenn.getB();
    }

    private void useManualInput() {
        while (true) {
            System.out.println("Введите интервал: ");

            try {
                System.out.print("a = ");
                a = sc.nextDouble();
                System.out.print("b = ");
                b = sc.nextDouble();
                break;

            } catch (InputMismatchException e) {
                System.out.println("ERROR: Не корректный отрезок." + "\n" +
                        "     ! Не целые числа записываются через \",\" Например: 1,5");
            }
        }


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
                b = y;
            } else {
                a = z;
                //b не меняется
            }
        } while (Math.abs(b - a) > l);

        x = (a + b) / 2;
        return x;
    }

}
