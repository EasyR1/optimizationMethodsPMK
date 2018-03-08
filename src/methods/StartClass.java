package methods;

import formulareader.FormulaReader;
import methods.dichotomyMethod.DichotomyMethod;
import methods.parabolMethod.ParabolMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartClass {
    private MethodsInterface method;
    private FormulaReader formula;
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
        this.formula = new FormulaReader(formulaStr);

        method.inputOptions(this.formula);
        method.startMethod();
        result = method.returnResult();
    }

    public void ShowResult(){
        System.out.println(result);
    }

    public static void main(String[] args) {
        new StartClass(new ParabolMethod());
    }
}
