package methods;

import formulareader.FormulaReader;
import methods.dichotomyMethod.DichotomyMethod;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class StartClass {
    StartMethodsInterface method;
    FormulaReader formula;
    BufferedReader reader;
    double result;

    public StartClass(StartMethodsInterface method) {
        this.method = method;
        String formulaStr = "";
        reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            formulaStr = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.formula = new FormulaReader(formulaStr);

        method.startMethod(this.formula);
        result = method.returnResult();
    }

    public void ShowResult(){
        System.out.println(result);
    }

    public static void main(String[] args) {
        new StartClass(new DichotomyMethod());
    }
}
