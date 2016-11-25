package com.vovan.dwh.data_population.generator;

import java.util.Random;

/**
 * Created by Volodymyr Roman on 24.11.2016.
 */
public class RandomMetricsGenerator {
    private static int MIN_ELECTRIC_POWER = 10000;
    private static int MAX_ELECTRIC_POWER = 50000;
    private static double MIN_LOSSES = 0.001;
    private static double MAX_LOSSES = 0.01;
    private static double ELECTRIC_POWER_AND_LOSSES_CORRELATION = 0.3;
    private static double WAS_ENABLED_PROBABILITY = 0.99;

    private final Random rand = new Random();

    public int generateElectricPower() {
        return rand.nextInt((MAX_ELECTRIC_POWER - MIN_ELECTRIC_POWER) + 1) + MIN_ELECTRIC_POWER;
    }

    public double generateLossesBaseWithCorrelationToElectricPower(int electricPower) {
        double localCenterPercentage = electricPower / (double) (MAX_ELECTRIC_POWER - MIN_ELECTRIC_POWER);

        double localMinLosses = MIN_LOSSES;
        double localMinPercentage = localCenterPercentage - ELECTRIC_POWER_AND_LOSSES_CORRELATION;
        if (localMinPercentage > 0) {
            localMinLosses = localMinPercentage * (MAX_LOSSES - MIN_LOSSES) + MIN_LOSSES;
        }

        double localMaxLosses = MAX_LOSSES;
        double localMaxPercentage = localCenterPercentage + ELECTRIC_POWER_AND_LOSSES_CORRELATION;
        if (localMaxPercentage < 1) {
            localMaxLosses = localMaxPercentage * (MAX_LOSSES - MIN_LOSSES) + MIN_LOSSES;
        }

        return localMinLosses + (localMaxLosses - localMinLosses) * rand.nextDouble();
    }

    public boolean generateWasEnabledParameter() {
        return rand.nextDouble() <= WAS_ENABLED_PROBABILITY ? true : false;
    }
}
