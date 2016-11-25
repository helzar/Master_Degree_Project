package com.vovan.dwh.data_population;

import com.vovan.dwh.data_population.generator.ConsumptionLogsGeneratorFactory;
import com.vovan.dwh.models.PowerConsumption;

import java.util.Arrays;
import java.util.stream.StreamSupport;

/**
 * Created by Volodymyr Roman on 25.11.2016.
 * <p/>
 * Just for manual testing
 */
public class ConsolePopulator {

    public static void main(String[] args) {
        Iterable<PowerConsumption> iterable = () -> ConsumptionLogsGeneratorFactory
                .create("29-11-2016T11:00:30", 1, Arrays.asList(1, 2), 300);

        StreamSupport.stream(iterable.spliterator(), false)
                .filter(a -> !a.isWasEnabled())
                .forEach(System.out::println);

        System.out.println(StreamSupport.stream(iterable.spliterator(), false).count());
    }
}
