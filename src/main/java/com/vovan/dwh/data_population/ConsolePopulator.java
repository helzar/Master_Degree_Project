package com.vovan.dwh.data_population;

import com.vovan.dwh.data_population.generator.ConsumptionLogsGeneratorFactory;
import com.vovan.dwh.models.PowerConsumption;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.StreamSupport;

/**
 * Created by Volodymyr Roman on 25.11.2016.
 * <p/>
 * Just for manual testing
 */
public class ConsolePopulator {

    //       "logId", "transformerId", "generationTimestamp", "losses", "electricPower", "wasEnabled"
    public static void main(String[] args) throws IOException {
        Iterable<PowerConsumption> iterable = () -> ConsumptionLogsGeneratorFactory
                .create("2016-11-29T11:00:30", OptionalInt.of(1), Arrays.asList(1, 2), 100, true);

//        StreamSupport.stream(iterable.spliterator(), false)
////                .filter(a -> !a.isWasEnabled())
//                .forEach(System.out::println);

//        System.out.println(StreamSupport.stream(iterable.spliterator(), false).count());

//        StreamSupport.stream(iterable.spliterator(), false)
//                .map(a -> a.toLineFormat()).forEach(System.out::println);



        String filePath = "sample_logs_data.csv";
        Files.write(
                Paths.get(filePath),
                (Iterable<String>) StreamSupport.stream(iterable.spliterator(), false).map(a -> a.toLineFormat())::iterator
        );

    }
}
