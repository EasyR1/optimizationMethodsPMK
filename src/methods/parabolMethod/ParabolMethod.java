package methods.parabolMethod;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import methods.MethodsInterface;
import methods.SimpleStarting;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ParabolMethod implements MethodsInterface, SimpleStarting {
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
    private double xSh;         //x c чертой

    private double result;

    public ParabolMethod() {
        sc = new Scanner(System.in);
    }

    private double calcF(double x) {
        return function.calculateFormula(x);
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
                //continue;       //Проверил, не работает. Все плохо
            }


        } while (true);
    }

    @Override
    public void start() {
        System.out.println("----------------------Метод Парабол----------------------");
        calcMethod();
        System.out.println("x* =    " + result);
        System.out.println("f(x*) = " + calcF(result));
        System.out.println("-----------------------------end-------------------------");
    }

    private void calcMethod() {
        boolean isNextStep6;
            boolean isNextStep2;
            do {
        isNextStep2 = false;
        //2
        x2 = x1 + deltaX;
        //3
        f1 = calcF(x1);
        f2 = calcF(x2);
        //4
        if (f1 > f2)
            x3 = x1 + 2 * deltaX;
        else
            x3 = x1 - 2 * deltaX;
        //5
        f3 = calcF(x3);
        do {
            isNextStep6 = false;
            //6
            fMin = Math.min(f1, Math.min(f2, f3));
            xMin = fMin == f1? x1 : fMin == f2? x2 : x3;

            //7
            xSh = 1.0/2.0 * (
                    ( f1 * (Math.pow(x2, 2) - Math.pow(x3, 2)) +
                            f2 * (Math.pow(x3, 2) - Math.pow(x1, 2)) +
                            f3 * (Math.pow(x1, 2) - Math.pow(x2, 2))
                    ) /
                            ( (x2-x3) * f1 + (x3-x1) * f2 + (x1-x2) * f3) );
            //8
            if (
                    Math.abs( (fMin - calcF(xSh))/calcF(xSh) ) < eps1 &&
                            Math.abs((xMin - xSh) / xSh) > eps2) {
                result = xSh;
            }
            else {
                if (xSh >= x1 && xSh <= x3) {               //x с чертой принадлежит [x1, x3]?
                    double buf = chooseBetterPoint();       //Записываем результат в промежуточную переменную
                    rewriteVariabe(buf);                    //Переприсваиваем
                    isNextStep6 = true;
                }
                else {
                    x1 = xSh;
                    isNextStep2 = true;
                }
            }
        } while (isNextStep6);

    } while (isNextStep2);
    }

    private double chooseBetterPoint() {
        if (calcF(xMin) < calcF(xSh))
            return xMin;
        else
            return xSh;
    }

    private void rewriteVariabe(double selectedX) {
        //Уточнить переприсваивание
        //Всегда ли выбранная наилучшая точка находится между какими либо двумя точками из x1, x2, x3?
        //Проверить логику
        ArrayList<Double> leftPoints = new ArrayList<>();
        ArrayList<Double> rightPoints = new ArrayList<>();

        if (selectedX > x1 )
            leftPoints.add(x1);
        else if (selectedX < x1)
            rightPoints.add(x1);

        if (selectedX > x2)
            leftPoints.add(x2);
        else if (selectedX < x2)
            rightPoints.add(x2);

        if (selectedX > x3)
            leftPoints.add(x3);
        else if (selectedX < x3)
            rightPoints.add(x3);

        x1 = getMaximum(leftPoints);
        x2 = selectedX;
        x3 = getMinimum(rightPoints);

        f1 = calcF(x1);
        f2 = calcF(x2);
        f3 = calcF(x3);
    }

    private double getMaximum(ArrayList<Double> points) {
        double max = points.get(0);
        for (double x : points)
            if (x > max)
                max = x;
        return max;
    }

    private double getMinimum(ArrayList<Double> points) {
        double min = points.get(0);
        for (double x : points)
            if (x < min)
                min = x;
        return min;
    }

    @Override
    public void startMethod() {
        if (function == null)
            return;
        start();
    }

    @Override
    public void inputOptions(FormulaInterface formulaReader) {
        function = (FormulaReader) formulaReader;
        inputXandEps();
    }

    @Override
    public double returnResult() {
        return result;
    }

    @Override
    public double simpleStart(String function) {
        this.function = new FormulaReader(function);
        x1 = 0;
        deltaX = 0.01;
        eps1 = 0.001;
        eps2 = 0.002;
        calcMethod();
        return returnResult();
    }
}
