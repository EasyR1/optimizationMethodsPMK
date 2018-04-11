package formulareader;

import java.util.HashMap;
import java.util.Stack;

public class FunctionToRPE {
    private String inputString;
    private String outputString;
    private Stack<String> stack;
    private HashMap<String, Integer> priority;
    private String numbers;
    private String operators;
    private String variables;
    private String functions;
    private boolean testFlag = false;

    //Конструктор со стандартными параметрами
    public FunctionToRPE(String inputString) {
        this(
                inputString, "0123456789.",
                "^*/+-", "xXyY", "sin|cos|tg|ctg|exp|sqrt|ln"
        );
    }

    //Конструктор со своими операторами и цифрами
    public FunctionToRPE(String inputString, String numbers, String operators, String variables, String functions) {
        outputString = "";
        stack = new Stack<>();
        this.numbers = numbers;
        this.operators = operators;
        this.variables = variables;
        this.functions = functions;
        this.inputString = parseString(inputString);
        priority = new HashMap<>();
        priority.put("(", 1);
        priority.put(")", 1);
        priority.put("^", 4);
        priority.put("*", 3);
        priority.put("/", 3);
        priority.put("+", 2);
        priority.put("-", 2);
        priority.put("sin", 5);
        priority.put("cos", 5);
        priority.put("tg", 5);
        priority.put("ctg", 5);
        priority.put("sqrt", 5);
        priority.put("exp", 5);
        priority.put("ln", 5);
    }

    public String convertationToRPE() {
        convertFunctToRPE();
        addStack();
        return outputString;
    }

    private String parseString(String inputString) {
        if (inputString != null || !inputString.equals("")) {
            char[] inString = new char[inputString.length()];
            inString = inputString.toCharArray();
            char oldSymbol = inString[0];
            String parseString = "" + inString[0];

            for (int i = 1; i < inString.length; i++) {
                if (variables.contains("" + inString[i]) &&
                        (!operators.contains("" + oldSymbol) && !functions.contains("" + oldSymbol)) &&
                        !(oldSymbol + "").equals("(")
                        ) {
                    parseString += "*";
                }
                parseString += inString[i];
                oldSymbol = inString[i];
            }

            return parseString;
        }
        return null;

    }

    private void convertFunctToRPE() {
        String currSym;
        for (int i = 0; i < inputString.length(); i++) {
            //TestMode
            if (testFlag) {
                System.out.print("iteration " + i + ":\ninput string: " + inputString + "\nstack:");
                for (String x : stack) {
                    System.out.print(x + " ");
                }
                System.out.println("\nresult: " + outputString + "\n");
            }

            currSym = "" + inputString.charAt(i);

            //Numbers:
            if (numbers.contains(currSym)) {
                i = doNumber(currSym, i);
                continue;
            }

            //Variables
            if (variables.contains(currSym)) {
                outputString += currSym + " ";
                continue;
            }

            //SkobkaOpen
            if ("(".contains(currSym)) {
                stack.push("" + currSym.charAt(0));
                continue;
            }

            //SkobkaClouse
            if (")".contains(currSym)) {
                while (!stack.peek().equals("(")) {
                    outputString += stack.pop() + " ";
                }
                stack.pop();
                continue;
            }

            //OperatorsLeftAssociation
            if (operators.contains(currSym)) {
                while (!stack.empty() &&
                        (priority.get(currSym) <= priority.get(stack.peek()))
                        ) {
                    outputString += stack.pop() + " ";
                }
                stack.push(currSym.charAt(0) + "");
                continue;
            }

            //UnaryOperators
            if (functions.contains(currSym)) {
                i = doFunctions(currSym, i);
            }

        }
    }

    private int doFunctions(String currSym, int iter) {
        String buffer = "";
        while (!currSym.equals(" ") && functions.contains(currSym)) {
            buffer += currSym;
            iter++;
            currSym = inputString.charAt(iter) + "";
        }
        while (!stack.empty() &&
                (priority.get(buffer) <= priority.get(stack.peek().charAt(0) + ""))
                ) {
            outputString += stack.pop() + " ";
        }
        stack.push(buffer + "");
        return iter - 1;
    }

    //Возвращает индекс конца цисла
    private int doNumber(String currSym, int iter) {
        String buffer = "";
        while (numbers.contains(currSym)) {
            buffer += currSym;
            iter++;
            if (iter < inputString.length())
                currSym = "" + inputString.charAt(iter);
            else
                break;
        }
        outputString += buffer + " ";

        return iter - 1;
    }

    private void addStack() {
        while (!stack.empty()) {
            outputString += stack.pop() + " ";
        }
    }


    public void test() {
        testFlag = true;
        convertFunctToRPE();
        addStack();
        System.out.print("stack: ");
        for (String x : stack) {
            System.out.print(x + " ");
        }
        System.out.println("\nresult: " + outputString + "\n");
    }

    public String getOutputString() {
        return outputString;
    }
}
