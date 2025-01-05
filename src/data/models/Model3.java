package data.models;

import data.annotations.Bind;

public class Model3 implements Model {

    @Bind
    private int LL;

    @Bind
    private double[] twKI;
    @Bind
    private double[] twKS;
    @Bind
    private double[] twINW;
    @Bind
    private double[] twEKS;
    @Bind
    private double[] twIMP;

    @Bind
    private double[] KI;
    @Bind
    private double[] KS;
    @Bind
    private double[] INW;
    @Bind
    private double[] EKS;
    @Bind
    private double[] IMP;
    @Bind
    private double[] PKB;

    // New variables without annotation
    private double[] savings;
    private double[] investments;
    private double[] growthRate;

    private double tempFactor;

    public Model3() {
    }

    @Override
    public void run() {
        // Initialize new variables
        PKB = new double[LL];
        savings = new double[LL];
        investments = new double[LL];
        growthRate = new double[LL];

        // First year calculation
        PKB[0] = (2.0 * KI[0])
                + (3.0 * KS[0])
                + (1.5 * INW[0])
                + (2.0 * EKS[0])
                - (1.1 * IMP[0]);

        savings[0] = 0.2 * PKB[0]; // Savings as 20% of PKB
        investments[0] = 0.1 * PKB[0]; // Investments as 10% of PKB
        growthRate[0] = (PKB[0] - IMP[0]) / PKB[0]; // Basic growth rate calculation

        // Compute subsequent years
        for (int t = 1; t < LL; t++) {
            KI[t] = twKI[t] * KI[t - 1];
            KS[t] = twKS[t] * KS[t - 1];
            INW[t] = twINW[t] * INW[t - 1];
            EKS[t] = twEKS[t] * EKS[t - 1];
            IMP[t] = twIMP[t] * IMP[t - 1];

            PKB[t] = (2.0 * KI[t])
                    + (3.0 * KS[t])
                    + (1.5 * INW[t])
                    + (2.0 * EKS[t])
                    - (1.1 * IMP[t]);

            // Additional calculations for savings, investments, and growth rate
            savings[t] = savings[t - 1] + (0.2 * PKB[t]);
            investments[t] = investments[t - 1] + (0.1 * PKB[t]);

            tempFactor = (savings[t] + investments[t]) / PKB[t];
            growthRate[t] = ((PKB[t] - IMP[t]) * tempFactor) / PKB[t];
        }
    }
}
