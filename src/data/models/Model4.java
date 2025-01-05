package data.models;

import data.annotations.Bind;

public class Model4 implements Model {
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

    // Additional fields for new metrics
    private double[] netExports; // Net exports = Export - Import
    private double[] savingsRate; // Savings rate as a percentage of GDP
    private double[] investmentToGDP; // Investments as a percentage of GDP
    private double[] governmentSpendingToGDP; // Public consumption as a percentage of GDP
    private double[] privateSpendingToGDP; // Private consumption as a percentage of GDP
    private double[] inflationAdjustedGDP; // GDP adjusted for inflation
    private double[] debtToGDP; // Hypothetical debt-to-GDP ratio
    private double[] exportToGDP; // Exports as a percentage of GDP
    private double[] importToGDP; // Imports as a percentage of GDP
    private double[] realGrowthRate; // Real growth rate of GDP

    public Model4() {
    }

    @Override
    public void run() {
        PKB = new double[LL];
        netExports = new double[LL];
        savingsRate = new double[LL];
        investmentToGDP = new double[LL];
        governmentSpendingToGDP = new double[LL];
        privateSpendingToGDP = new double[LL];
        inflationAdjustedGDP = new double[LL];
        debtToGDP = new double[LL];
        exportToGDP = new double[LL];
        importToGDP = new double[LL];
        realGrowthRate = new double[LL];

        // Assume some constant inflation rate for simplicity
        double inflationRate = 0.02; // 2% inflation per year

        PKB[0] = KI[0] + KS[0] + INW[0] + EKS[0] - IMP[0];
        inflationAdjustedGDP[0] = PKB[0] / (1 + inflationRate);
        netExports[0] = EKS[0] - IMP[0];
        savingsRate[0] = 0.2; // Assume initial savings rate is 20% of GDP
        investmentToGDP[0] = INW[0] / PKB[0];
        governmentSpendingToGDP[0] = KS[0] / PKB[0];
        privateSpendingToGDP[0] = KI[0] / PKB[0];
        debtToGDP[0] = 0.5; // Hypothetical 50% debt-to-GDP ratio
        exportToGDP[0] = EKS[0] / PKB[0];
        importToGDP[0] = IMP[0] / PKB[0];
        realGrowthRate[0] = 0.03; // Assume 3% initial growth rate

        for (int t = 1; t < LL; t++) {
            KI[t] = twKI[t] * KI[t - 1];
            KS[t] = twKS[t] * KS[t - 1];
            INW[t] = twINW[t] * INW[t - 1];
            EKS[t] = twEKS[t] * EKS[t - 1];
            IMP[t] = twIMP[t] * IMP[t - 1];

            PKB[t] = KI[t] + KS[t] + INW[t] + EKS[t] - IMP[t];
            inflationAdjustedGDP[t] = PKB[t] / Math.pow(1 + inflationRate, t);
            netExports[t] = EKS[t] - IMP[t];
            savingsRate[t] = savingsRate[t - 1] * 1.01; // Assume slight increase in savings rate
            investmentToGDP[t] = INW[t] / PKB[t];
            governmentSpendingToGDP[t] = KS[t] / PKB[t];
            privateSpendingToGDP[t] = KI[t] / PKB[t];
            debtToGDP[t] = debtToGDP[t - 1] * 1.01; // Assume debt grows by 1% per year
            exportToGDP[t] = EKS[t] / PKB[t];
            importToGDP[t] = IMP[t] / PKB[t];
            realGrowthRate[t] = (PKB[t] - PKB[t - 1]) / PKB[t - 1];
        }
    }
}
