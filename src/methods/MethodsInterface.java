package methods;

import formulareader.FormulaInterface;
import formulareader.FormulaReader;

/**
 * Классические методы оптимизации
 */
public interface MethodsInterface {
    /**
     * Проверка возможности запуска метода.
     * Должен проверить наличие FormulaReader и запустить startMethod()
     */
    void start();

    /**
     * Запускает вычисление методом
     */
    void startMethod();

    /**
     * Устанавливает FormulaReader. Вызывается StartClass
     * @see StartClass
     * @param formulaReader
     */
    void inputOptions(FormulaInterface formulaReader);

    /**
     * Возвращает результат на экран в случае функции одной переменной
     * В противном случае не работает
     *
     * @return результат вычисления методом (result) или 0, если функция двух или более переменных
     *
     */
    double returnResult();
}
