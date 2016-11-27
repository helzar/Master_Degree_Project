package com.vovan.dwh.spark;

import org.apache.spark.SparkContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Created by Volodymyr Roman on 27.11.2016.
 */
// TODO: implement this singletone and add REST controller for launching Spark jobs
@Component
@Scope("singleton")
public class SparkContextProvider {
    private SparkContext context;

    public SparkContext get() {
        throw new UnsupportedOperationException();
//        if (context == null) {
//            context = new SparkContext();
//        }
//        return context;
    }
}
