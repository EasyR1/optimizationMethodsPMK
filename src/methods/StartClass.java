package methods;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import formulareader.FormulaReaderWithTwoArguments;
import methods.first.DFPMethod;
import methods.first.GradientSpusk;
import methods.second.Newton;
import methods.zero.DichotomyMethod;
import methods.zero.GoldenRatio;
import methods.zero.PairedGuides;
import methods.zero.ParabolMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Start Class.
 * Служит для запуска любого из методов.
 * У метода должен быть реализован интерфейс MethodInterface
 */
public class StartClass {
    private MethodsInterface method;
    private FormulaInterface formula;
    private BufferedReader reader;
    private double result;

    /**
     * Запускает один из методов
     * Порядок выхова:
     * 1) Ввод формулы в строковом виде
     * 2) Запуск метода inputOptions()
     * 3) Запуск метода startMethod()
     * @param method
     */
    public StartClass(MethodsInterface method) {
        this.method = method;
        String formulaStr;
        reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Formula: ");
        try {
            formulaStr = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        if (formulaStr.contains("x") && formulaStr.contains("y")) {
            this.formula = new FormulaReaderWithTwoArguments(formulaStr);
        }
        else {
            this.formula = new FormulaReader(formulaStr);
        }

        method.inputOptions(this.formula);
        method.startMethod();
        result = method.returnResult();
    }

    /**
     * Выводит результат работы метода на экран
     */
    public void ShowResult(){
        System.out.println(result);
    }

    public static void main(String[] args) {
        double[] arr = {-1,1};
        new StartClass(new PairedGuides());
        //2*x^2+y^2-2*x*y+2*x+6
    }
}
