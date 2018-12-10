package org.sobngwi.oca.concurrency.forkjoin;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Stream;

public class WeigthAnimalAction extends RecursiveAction {

    private int start, end ;
    private Double[] weigths;

    public WeigthAnimalAction(int start, int end, Double[] weigths) {
        this.start = start;
        this.end = end;
        this.weigths = weigths;
    }

    @Override
    protected void compute() {

        if ( (end - start) <= 3 ){
            for ( int i = start ; i< end ; i++){
                weigths[i] = ThreadLocalRandom.current().nextDouble(10,300);
                System.out.println("Animal Weigth : " + i);
            }
        }
        else {
            int middle = start + ( (end -start) /2 );
            System.out.println("[start= " + start + " middle= " + middle +  ", end= " +  end + "]");
            invokeAll(new WeigthAnimalAction(start, middle, weigths),
            new WeigthAnimalAction(middle, end, weigths));
        }
    }

    public static void main(String[] args) {
        Double[] weigths = new Double[10];

        ForkJoinTask<?> task = new WeigthAnimalAction(0,weigths.length, weigths);
        ForkJoinPool pool = new ForkJoinPool();
        pool.invoke(task);

        Stream.of(weigths).forEachOrdered(w -> System.out.print(w + " "));
    }
}
