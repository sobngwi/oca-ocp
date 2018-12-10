package org.sobngwi.oca.concurrency.forkjoin;

import java.util.concurrent.*;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class WeigthAnimalTask extends RecursiveTask {

    private static Logger log = Logger.getLogger(WeigthAnimalTask.class.getName());
    private int start, end ;
    private Double[] weigths;

    public WeigthAnimalTask(int start, int end, Double[] weigths) {
        this.start = start;
        this.end = end;
        this.weigths = weigths;
    }

    @Override
    protected Double compute() {

        if ( (end - start) <= 3 ){
            double result = 0;
            for ( int i = start ; i< end ; i++){
                weigths[i] = ThreadLocalRandom.current().nextDouble(10,300);
                log.finest("Animal Weigth : " + i);

                result +=  weigths[i] ;
            }
            return result;
        }
        else {
            int middle = start + ( (end -start) /2 );
            log.finest("[start= " + start + " middle= " + middle +  ", end= " +  end + "]");
            RecursiveTask<Double> otherTask =  new WeigthAnimalTask(start, middle, weigths);
            otherTask.fork();
            return new  WeigthAnimalTask(middle, end,  weigths).compute() + otherTask.join() ;
        }
    }

}
