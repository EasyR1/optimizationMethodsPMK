package methods;

import formulareader.FormulaReader;
import methods.dichotomyMethod.DichotomyMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class StartClass {
    MethodsInterface method;
    FormulaReader formula;
    BufferedReader reader;
    double result;

    public StartClass(MethodsInterface method) {
        this.method = method;
        String formulaStr = "";
        reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            formulaStr = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
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
        new StartClass(new DichotomyMethod());
    }
}
