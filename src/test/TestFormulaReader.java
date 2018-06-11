package test;

import formulareader.FormulaReader;
import junit.framework.TestCase;

public class TestFormulaReader extends TestCase {
    FormulaReader formulaReader;
    public void testBaseOperationsWithoutVariable () {

        formulaReader = new FormulaReader("2+2");
        assertEquals(4.0, formulaReader.calculateFormula(0));

        formulaReader = new FormulaReader("2-2");
        assertEquals(0.0,formulaReader.calculateFormula(0));

        formulaReader = new FormulaReader("3/2");
        assertEquals(3.0/2.0, formulaReader.calculateFormula(0));

        formulaReader = new FormulaReader("3*2");
        assertEquals(6.0, formulaReader.calculateFormula(0));
    }

    public void testBaseOperationsWithVariable () {
        formulaReader = new FormulaReader("x+2");
        assertEquals(4.0, formulaReader.calculateFormula(2));

        formulaReader = new FormulaReader("x-2");
        assertEquals(0.0, formulaReader.calculateFormula(2));

        formulaReader = new FormulaReader("x/2");
        assertEquals(3.0/2.0, formulaReader.calculateFormula(3));

        formulaReader = new FormulaReader("x*2");
        assertEquals(6.0, formulaReader.calculateFormula(3));
    }

    public void testMathFunctions () {
        formulaReader = new FormulaReader("sin(1.0)");
        assertEquals(Math.sin(1.0), formulaReader.calculateFormula(0));

        formulaReader = new FormulaReader("cos(1.0)");
        assertEquals(Math.cos(1.0), formulaReader.calculateFormula(0));

        formulaReader = new FormulaReader("tg(1.0)");
        assertEquals(Math.tan(1.0), formulaReader.calculateFormula(0));

        formulaReader = new FormulaReader("ctg(1.0)");
        assertEquals(1/Math.tan(1.0), formulaReader.calculateFormula(0));
    }

    public void testExceptionSituation () {

    }
}
