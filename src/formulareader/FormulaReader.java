package formulareader;

import java.util.Scanner;
import java.util.Stack;

public class FormulaReader implements FormulaInterface{
    private String formulaRPE;
    private Stack<String> stack;
    private String inputFunction;
    private String numbers;
    private String operators;
    private String variables;
    private String functions;
    private String currentSymbol;
    private String lastSymbol = "";

    public FormulaReader(String inputFunction) {
        stack = new Stack<>();
        numbers = "0123456789.";
        operators = "^*/+-";
        variables = "XxYy";
        functions = "sin|cos|tg|ctg|exp|sqrt";
        this.inputFunction = convert(inputFunction);
        FunctionToRPE convertToRPE = new FunctionToRPE(this.inputFunction, numbers, operators, variables, functions);
        formulaRPE = convertToRPE.convertationToRPE();

    }

    private String convert(String inputFunction) {
        boolean flag = false;
        char[] input = inputFunction.toCharArray();
        String result = "";
        for (int i = 0; i < inputFunction.length(); i++) {
            if (flag && operators.contains(input[i]+"")) {
                flag = false;
                result += ")";
            }
            if (operators.contains(input[i] + "") && operators.contains(input[i+1] + "")) {
                flag = true;
                result += input[i++] + "(0" ;
            }
            result += input[i];
        }
        if (flag)
            result += ")";
        return result;
    }

    public double calculateFormula(double x) {
        double a;
        double b;
        for (int i = 0; i < formulaRPE.length(); i++) {
            currentSymbol = formulaRPE.charAt(i) + "";
            if (numbers.contains(currentSymbol)) {
                i = doNumbers(i);
                stack.push(currentSymbol);
                continue;
            }

            if (variables.contains(currentSymbol)) {
                stack.push(x + "");
                continue;
            }

            if (functions.contains(currentSymbol)) {
                i = doFunctions(currentSymbol, i);
                continue;
            }

            if (operators.contains(currentSymbol)) {
                b = Double.parseDouble(stack.pop());
                if (stack.isEmpty())
                    a = 0;
                else
                    a = Double.parseDouble(stack.pop());
                switch (currentSymbol) {
                    case "+":
                        stack.push((a + b) + "");
                        break;
                    case "-":
                        stack.push((a - b) + "");
                        break;
                    case "*":
                        stack.push((a * b) + "");
                        break;
                    case "/":
                        stack.push((a / b) + "");
                        break;
                    case "^":
                        stack.push(Math.pow(a, b) + "");
                        break;
                }
            }
        }
        return Double.parseDouble(stack.pop());
    }

    private int doNumbers(int iter) {
        String buff = "";
        while (!currentSymbol.equals(" ") && iter < formulaRPE.length()) {
            buff += currentSymbol;
            currentSymbol = formulaRPE.charAt(++iter) + "";
        }
        currentSymbol = buff;
        return iter-1;
    }

    private int doFunctions(String currentSymbol, int iter){
        String buff = "";
        double a;
        while (functions.contains(currentSymbol)) {
            buff += currentSymbol;
            iter++;
            if (iter < formulaRPE.length())
                currentSymbol = "" + formulaRPE.charAt(iter);
            else
                break;
        }

        switch (buff) {
            case "sin":
                a = Double.parseDouble(stack.pop());
                stack.push("" + Math.sin(a));
                break;
            case "cos":
                a = Double.parseDouble(stack.pop());
                stack.push("" + Math.cos(a));
                break;
            case "tg":
                a = Double.parseDouble(stack.pop());
                stack.push("" + Math.tan(a));
                break;
            case "ctg":
                a = Double.parseDouble(stack.pop());
                stack.push("" + 1.0/Math.tan(a));
                break;
            case "exp":
                a = Double.parseDouble(stack.pop());
                stack.push("" + Math.exp(a));
                break;
        }

        return iter-1;
    }

    public String getFormulaRPE() {
        return formulaRPE;
    }

    public void setVariables(String variables) {
        this.variables = variables;
    }

    public void setFormulaRPE(String formulaRPE) {
        this.formulaRPE = formulaRPE;
    }

    public String getVariables() {
        return variables;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Function: ");
        FormulaReader formulaReader = new FormulaReader(sc.next());
        System.out.print(formulaReader.getFormulaRPE() + "\nx = ");
        System.out.println(formulaReader.calculateFormula(sc.nextDouble()));
    }

    public String getInputFunction() {
        return inputFunction;
    }
}
