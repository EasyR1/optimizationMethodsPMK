package formulareader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * Пример использования FormulaReader без интерфейса
 * Используется при проверки правильности вычисления formulareader
 *
 * @deprecated нет необходимости в использовании
 */
public class ExampleUse {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Function: ");
        FormulaReader formulaReader = new FormulaReader(sc.next());

        //Вывод обратной польской записи (не обязательно)
        //System.out.print(formulaReader.getFormulaRPE() + "\n");
        //Ввод х для подсчета
        while (true) {
            System.out.print("What I should do? ");
            switch (sc.nextInt()) {
                case 1:
                    System.out.print("x = ");
                    double x = Double.parseDouble(reader.readLine());
                    //Вывод результата
                    System.out.println(formulaReader.calculateFormula(x));
                    break;
                case 2:
                    System.out.print("x1 = ");
                    double x1 = sc.nextDouble();
                    System.out.print("x2 = ");
                    double x2 = sc.nextDouble();
                    System.out.print("x3 = ");
                    double x3 = sc.nextDouble();
                    double res = 1.0/2.0 * ((formulaReader.calculateFormula(x1) * (Math.pow(x2, 2) - Math.pow(x3, 2)) +
                            formulaReader.calculateFormula(x2) * (Math.pow(x3, 2) - Math.pow(x1, 2)) +
                            formulaReader.calculateFormula(x3) * (Math.pow(x1, 2) - Math.pow(x2, 2))) /
                                    ((x2-x3) * formulaReader.calculateFormula(x1) +
                                    (x3-x1) * formulaReader.calculateFormula(x2) +
                                    (x1-x2) * formulaReader.calculateFormula(x3)));
                    System.out.println("res = " + res);
                    break;
            }


        }

    }
}
