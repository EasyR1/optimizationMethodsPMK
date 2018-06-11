package formulareader;

import java.util.Scanner;

public class FormulaReaderWithTwoArguments extends FormulaReader implements FormulaInterface{
    public FormulaReaderWithTwoArguments(String inputFunction) {
        super(inputFunction);
        setVariables("xy");
    }

    public double calculateFormula(double x, double y) {
        String oldFormulaRPE = getFormulaRPE();
        setFormulaRPE(getFormulaRPE().replaceAll("y", y + ""));
        setVariables("xX");
        double res = calculateFormula(x);
        setVariables("xy");
        setFormulaRPE(oldFormulaRPE);
        return res;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Function: ");
        FormulaReaderWithTwoArguments formulaReader = new FormulaReaderWithTwoArguments(sc.nextLine());
        //System.out.print(formulaReader.getFormulaRPE() + "\nx, y = ");
        System.out.println(formulaReader.calculateFormula(sc.nextDouble(), sc.nextDouble()));
    }
}
