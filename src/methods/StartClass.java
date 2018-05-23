package methods;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;
import formulareader.FormulaReaderWithTwoArguments;
import methods.firstPor.gradientSpusk.dfp.DFPMethod;
import methods.goldenRatioMethod.GoldenRatio;
import methods.parabolMethod.ParabolMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartClass {
    private MethodsInterface method;
    private FormulaInterface formula;
    private BufferedReader reader;
    private double result;

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

    public void ShowResult(){
        System.out.println(result);
    }

    public static void main(String[] args) {
        new StartClass(new DFPMethod());
        //2*x^2+y^2-2*x*y+2*x+6
    }
}
