package methods.parabolMethod;

import formulareader.FormulaReader;
import methods.MethodsInterface;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ParabolMethod implements MethodsInterface {
    private FormulaReader function;
    private Scanner sc;
    //Вводимые
    private double x1;
    private double eps1;
    private double eps2;
    private double deltaX;
    //В процессе работы
    private double x2;
    private double x3;
    private double f1;
    private double f2;
    private double f3;
    private double xMin;
    private double fMin;

    private double result;

    public ParabolMethod() {
        sc = new Scanner(System.in);
    }

    private void inputXandEps() {
        do {
            try {
                System.out.println("Введите x1, deltaX, eps1, eps2");
                System.out.print("x1 = ");
                x1 = sc.nextDouble();
                System.out.print("deltaX = ");
                deltaX = sc.nextDouble();
                System.out.print("eps1 = ");
                eps1 = sc.nextDouble();
                System.out.print("eps2 = ");
                eps2 = sc.nextDouble();
                break;
            } catch (InputMismatchException e) {
                System.out.println("ERROR: Не корректный ввод. Испольуется значение по умолчанию" + "\n" +
                        "     ! Не целые числа записываются через \",\" Например: 0,001");
                continue;       //Проверить
            }


        } while (true);
    }

    @Override
    public void start() {
        //2
        x2 = x1 + deltaX;
        //3
        f1 = function.calculateFormula(x1);
        f2 = function.calculateFormula(x2);
        //4
        if (f1 > f2)
            x3 = x1 + 2 * deltaX;
        else
            x3 = x1 - 2 * deltaX;
        //5
        f3 = function.calculateFormula(x3);
        //6
        fMin = Math.min(f1, Math.min(f2, f3));
        xMin = fMin == f1? f1 : fMin == f2? f2 : f3;

    }

    @Override
    public void startMethod() {
        if (function == null)
            return;
        start();
    }

    @Override
    public void inputOptions(FormulaReader formulaReader) {
        function = formulaReader;
        inputXandEps();
    }

    @Override
    public double returnResult() {
        return 0;
    }
}
