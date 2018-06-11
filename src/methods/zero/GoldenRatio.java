package methods.zero;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import methods.MethodsInterface;
import methods.SimpleStarting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class GoldenRatio implements MethodsInterface, SimpleStarting {
    //имеющиеся для работы
    private FormulaReader formula;
    private BufferedReader reader;
    private Svenn svenn;
    //переменные метода
    private int k;
    private double l;
    private double delta;
    private double yk;
    private double zk;
    private double ak1;
    private double ak;
    private double bk1;
    private double yk1;
    private double zk1;
    private double bk;
    private double result;


    public GoldenRatio() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    @Override
    public void start() {
        System.out.println();
        inputSegment();
        calcMethod();
        System.out.println("x*    = " + result);
        System.out.println("f(x*) = " + calcF(result));
        System.out.println("----------------------------end--------------------------");

    }

    @Override
    public void startMethod() {
        if (formula == null)
            return;
        start();
    }

    @Override
    public void inputOptions(FormulaInterface formulaReader) {
        formula = (FormulaReader) formulaReader;
        System.out.println("Введите точность l > 0");
        try {
            do {
                l = Double.parseDouble(reader.readLine());
                if (l <= 0)
                    System.out.println("l <= 0, Повторите рорытку");
            } while (!(l > 0));
        } catch (NumberFormatException e) {
            System.out.print("ERROR: Некорректный ввод, используется l = 0.01");
            l = 0.1;
        } catch (IOException e) {
            e.printStackTrace();
            l = 0.1;
        }
    }

    @Override
    public double returnResult() {
        return result;
    }

    private void calcMethod() {
        //2
        k = 0;
        //3
        yk = ak + (3.0 - Math.sqrt(5.0)) / 2.0 * (bk - ak);
        zk = ak + bk - yk;

        do {
            //4,5
            if (calcF(yk) <= calcF(zk)) {
                ak1 = ak;
                bk1 = zk;
                yk1 = ak1 + bk1 - yk;
                zk1 = yk;
            }
            //6
            if (calcF(yk) > calcF(zk)) {
                ak1 = yk;
                bk1 = bk;
                yk1 = zk;
                zk1 = ak1 + bk1 - zk;
            }
            //7
            delta = Math.abs(ak1 - bk1);
            if (delta > l) {
                k++;
                doNextStep();
            }
        } while (delta > l);
        result = (ak1 + bk1) / 2;


    }

    private void doNextStep() {
        //при переходе к следующему шагу увеличиваем k на 1, т.е записываем в старое k новое k1
        ak = ak1;
        bk = bk1;
        yk = yk1;
        zk = zk1;
    }

    private double calcF(double x) {
        return formula.calculateFormula(x);
    }

    private void inputSegment() {
        String answer;
        try {
            while (true) {
                System.out.println("Найти интервал методом Свенна y/n?");
                answer = reader.readLine();
                answer.toLowerCase();
                if (answer.equals("y")) {
                    useSvenn();
                    break;
                } else if (answer.equals("n")) {
                    useManualInput();
                    break;
                } else {
                    System.out.println("Некорректный ввод. Повторите попытку");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            useManualInput();
        }
    }

    private void useSvenn() {
        svenn = new Svenn(formula);
        ak = svenn.getA();
        bk = svenn.getB();
    }

    private void useManualInput() {
        while (true) {
            System.out.println("Введите интервал: ");

            try {
                System.out.print("a = ");
                ak = Double.parseDouble(reader.readLine());
                System.out.print("b = ");
                bk = Double.parseDouble(reader.readLine());
                break;

            } catch (NumberFormatException e) {
                System.out.println("ERROR: Не корректный отрезок." + "\n" +
                        "     ! Не целые числа записываются через \",\" Например: 1.5");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public double simpleStart(String formula) {
        this.formula = new FormulaReader(formula);
        l = 0.000001;
        ak = -100;
        bk = 100;
        calcMethod();
        return result;
    }
}