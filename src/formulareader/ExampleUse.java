package formulareader;

import java.util.Scanner;

public class ExampleUse {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Function: ");
        FormulaReader formulaReader = new FormulaReader(sc.next());

        //Вывод обратной польской записи (не обязательно)
        //System.out.print(formulaReader.getFormulaRPE() + "\n");
        //Ввод х для подсчета
        System.out.print("x = ");
        double x = sc.nextDouble();
        //Вывод результата
        System.out.println(formulaReader.calculateFormula(x));
    }
}
