package formulareader;

public class FormulaReaderWithThreeArguments extends FormulaReaderWithTwoArguments {
    public FormulaReaderWithThreeArguments(String inputFunction) {
        super(inputFunction);
        setVariables("xyz");
    }

    public double calculateFormula(double x, double y, double z) {
        String oldFormulaRPE = getFormulaRPE();
        setFormulaRPE(getFormulaRPE().replaceAll("z", z + ""));
        //setFormulaRPE(getFormulaRPE().replaceAll(y));
        double res = calculateFormula(x, y);
        setVariables("xyz");
        setFormulaRPE(oldFormulaRPE);
        return res;
    }
}
