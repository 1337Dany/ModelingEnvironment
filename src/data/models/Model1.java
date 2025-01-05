package data.models;

import data.annotations.Bind;

public class Model1 implements Model {
    /*
     * The data passed through this model will look like:
     * <p>
     * LATA  2015       2016         2017          2018            2019
     * twKI  1.03       1.03         1.03          1.03            1.03
     * twKS  1.04       1.04         1.04          1.04            1.04
     * twINW 1.12       1.12         1.12          1.12            1.12
     * twEKS 1.13       1.13         1.13          1.13            1.13
     * twIMP 1.14       1.14         1.14          1.14            1.14
     * KI    1023752.2  1054464.766  1086098.70898 1118681.6702494 1152242.1203568822
     * KS    315397.0   328012.88    341133.3952   354778.731008   368969.88024832006
     * INW   348358.0   390160.96    436980.2752   489417.908224   548148.0572108802
     * EKS   811108.6   916552.718   1035704.57134 1170346.1656142 1322491.1671440455
     * IMP   784342.4   894150.336   1019331.38304 1162037.7766656 1324723.0653987834
     * PKB   1714273.4  1795040.988  1880585.56768 1971186.69843   2067128.1595613444
     */
    @Bind
    private int LL; // number of years
    @Bind
    private double[] twKI; // the growth rate of private consumption
    @Bind
    private double[] twKS; // the growth rate of public consumption
    @Bind
    private double[] twINW; // investment growth
    @Bind
    private double[] twEKS; // export growth
    @Bind
    private double[] twIMP; // import growth
    @Bind
    private double[] KI; // private consumption
    @Bind
    private double[] KS; // public consumption
    @Bind
    private double[] INW; // investments
    @Bind
    private double[] EKS; // export
    @Bind
    private double[] IMP; // import
    @Bind
    private double[] PKB; // GDP
    private double temp; // this field is not associated with the data model or with the results

    public Model1() {
    }

    @Override
    public void run() {
        PKB = new double[LL];
        PKB[0] = KI[0] + KS[0] + INW[0] + EKS[0] - IMP[0];
        for (int t = 1; t < LL; t++) {
            KI[t] = twKI[t] * KI[t - 1];
            KS[t] = twKS[t] * KS[t - 1];
            INW[t] = twINW[t] * INW[t - 1];
            EKS[t] = twEKS[t] * EKS[t - 1];
            IMP[t] = twIMP[t] * IMP[t - 1];
            PKB[t] = KI[t] + KS[t] + INW[t] + EKS[t] - IMP[t];
        }
    }
}
