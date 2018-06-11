package methods.zero;

import formulareader.FormulaInterface;

import static java.lang.Math.sqrt;

import formulareader.FormulaReaderWithTwoArguments;
import methods.MethodsInterface;
import methods.SimpleStarting;

import static java.lang.Math.abs;

public class PairedGuides implements MethodsInterface {
    private double []x;
    private double []x1;
    private double e;
    private double [][]d={{0,1,0},{1,0,1}};// Шаг 1
    private double [][]ds={{0,1,0},{1,0,1}};
    private int i=0; // Шаг 1
    private int k=0; //Шаг 1
    private double []y0;
    private double []y1;
    private double []y2;
    private double []y3;
    private double t;
    private int n=2;
    private double [] result;
    private double []tx=new double[2];
    private double []td=new double[2];
    private double det;
    private FormulaReaderWithTwoArguments formula;

    public PairedGuides() {
        y1=new double[2];
        y2=new double[2];
        y3=new double[2];
        result =new double[2]; // тут будет ответ
    }

    public PairedGuides(double[] x, double e) {
        // Шаг 1
        this.x=x.clone();//шаг 1
        y0=x.clone(); //шаг 1
        y1=new double[2];
        y2=new double[2];
        y3=new double[2];
        result =new double[2]; // тут будет ответ
        this.e = e; //шаг 1
        calculating();
    }

    public void calculating() {
        System.out.println("----------------Метод сопряженных направляющих---------------");
        if(checking()==false)
            step2();
        System.out.println("k = "+k);
        System.out.println("x* = ("+ result[0]+";"+ result[1]+" )");
        System.out.println("f(x*) = " + formula.calculateFormula(result[0], result[1]));

    }

    public boolean checking() {
        if(e<=0)
        {
            if(e<0)
                System.out.println("ERROR: , e<0");
            else
                System.out.println("ERROR: , e=0");
            return true;
        }
        else
            return false;
    }

    private void step2() {
        String x1="";
        String x2="";
        if(i==0)
        {
            tx=y0.clone();
            td[0]=d[0][0];
            td[1]=d[1][0];
        }
        else
        {
            if(i==1)
            {
                tx=y1.clone();
                td[0]=d[0][1];
                td[1]=d[1][1];
            }
            else
            {
                if(i==2)
                {
                    tx=y2.clone();
                    td[0]=d[0][2];
                    td[1]=d[1][2];
                }   
            } 
        }
        System.out.println(td[0]);
        System.out.println(td[1]);
        t=0.0;
        String s = formula.getInputFunction();
        s = s.replaceAll("x","(" + (tx[0] > 0? tx[0]:("(0" + tx[0] + ")")) + "+" + (td[0] >= 0? td[0]:("(0" + td[0] + ")")) + "*x)");
        s = s.replaceAll("y","(" + (tx[1] > 0? tx[1]:("(0" + tx[1] + ")")) + "+" + (td[1] >= 0? td[1]:("(0" + td[1] + ")")) + "*x)");
        SimpleStarting simple=new GoldenRatio();
       
        System.out.println(s);
        t=simple.simpleStart(s);
        System.out.println("t="+t);
        step3();
        return;
    }
    
    private void step3() {
        if(i<(n-1))
        {
            y1[0]=tx[0]+t*td[0];
            y1[1]=tx[1]+t*td[1]; 
            System.out.println("y1=("+y1[0]+";"+y1[1]+")");
            i++;
            step2();
            return;
        }
        if(i==(n-1))
        {
            y2[0]=tx[0]+t*td[0];
            y2[1]=tx[1]+t*td[1]; 
            System.out.println("y2=("+y2[0]+";"+y2[1]+")");
            if((Math.abs(y2[0]-y0[0])<e) & (Math.abs(y2[1]-y0[1])<e))
            {
                result =y2.clone();
                return;
            }
            else
            {
                i++;
                step2();
                return;
            }
        }
        if(i==n)
        {
            y3[0]=tx[0]+t*td[0];
            y3[1]=tx[1]+t*td[1]; 
            System.out.println("y3=("+y3[0]+";"+y3[1]+")");
            if((Math.abs(y3[0]-y1[0])<e) & (Math.abs(y3[1]-y1[1])<e))
            {
                result =y3.clone();
                return;
            }
            else
            {
                step4();
                return;
            } 
        }       
    }

    private void step4() {
        x1=y3.clone();
        System.out.println("norna="+norm(x1,x));
        System.out.println("");
        if(norm(x1,x)<e)
        {
            result =x1.clone();
            return;
        }
        else
        {
            ds=d.clone();
            ds[0][0]=y3[0]-y1[0];
            ds[1][0]=y3[1]-y1[1];
            //ds[0][2]=y3[0]-y1[0];
            //ds[1][2]=y3[1]-y1[1];
            det=ds[0][1]*ds[1][2]-ds[0][2]*ds[1][1];
            if(det!=0.0)
            {
                //d=ds.clone();
                y0=x1.clone();
                k++;
                i=0;
                x=x1.clone();
                step2();
                return;
            }
            else
            {
                y0=x1.clone();
                k++;
                i=0;
                x=x1.clone();
                step2();
                return;
            }
            
        }
    }
    
    private double norm(double []a, double []b) {
        return (sqrt(Math.pow((a[0]-b[0]), 2)+Math.pow((a[1]-b[1]), 2)));
    }


    @Override
    public void start() {
        if (formula != null) {
            startMethod();
        }
    }

    @Override
    public void startMethod() {
        double[] xStart = {4, 0};
        y0 = xStart;
        e = 0.001;
        calculating();
    }

    @Override
    public void inputOptions(FormulaInterface formulaReader) {
        formula = (FormulaReaderWithTwoArguments) formulaReader;
    }

    @Override
    public double returnResult() {
        return 0;
    }
}
