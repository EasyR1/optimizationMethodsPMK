package methods;

/**
 * Обеспечивает простой старт для метода
 * Без ввода информации на экран
 */
public interface SimpleStarting {
    /**
     * Задает FormulaReader класса метода оптимизациии и начальные параметры нулевой итерации
     *
     * @param formula в строковом представлении
     * @return результат вычисления методом оптимизации
     */
    double simpleStart(String formula);
}
