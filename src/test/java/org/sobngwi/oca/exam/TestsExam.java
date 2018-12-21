package org.sobngwi.oca.exam;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.UnsupportedTemporalTypeException;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.zip.DataFormatException;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class TestsExam {

    @Test
    public void binarySearchOnNonIntsSortedListMustBeonAscendingMode() {

        // What is the result of the following code?
        Comparator<Integer> c = (o1, o2) -> o2 - o1;
        List<Integer> list1 = Arrays.asList(5, 4, 7, 1);
        Collections.sort(list1, c);
        int r = Collections.binarySearch(list1, 4);
        assertTrue(r == -1);
        r = Collections.binarySearch(list1, 1);
        assertTrue(r == -1);
    }

    @Test
    public void binarySearchOnStringsSortedOnDesccendingMode() {

        List<String> list = new ArrayList<String>();
        list.add("ab");
        list.add("ba");
        list.add("bd");
        list.add("aa");

        Comparator<String> comparator = (a, b) -> b.compareToIgnoreCase(a);
        Collections.sort(list, comparator);
        ;
        int index = Collections.binarySearch(list, "ab", comparator);
        assertTrue(index == 2);
        index = Collections.binarySearch(list, "bd", comparator);
        assertTrue(index == 0);

        comparator = (a, b) -> a.compareToIgnoreCase(b);
        Collections.sort(list, comparator);
        index = Collections.binarySearch(list, "ab", comparator);
        assertTrue(index == 1);
        index = Collections.binarySearch(list, "bd", comparator);
        assertTrue(index == 3);
    }

    public static void await(CyclicBarrier cb) { // k1
        try {
            cb.await();
        } catch (InterruptedException | BrokenBarrierException e) {
// Handle exception
        }
    }

    @Test
    public void Q10() {
        CyclicBarrier cb = new CyclicBarrier(2,
                () -> System.out.println("Pen is Full")); // k2
        IntStream.iterate(1, i -> 1)
                .parallel()
                .limit(2)
                .forEach(i -> await(cb)); // k3
        assertTrue(cb.getNumberWaiting() == 0);
    }

    @Test
    public void Q17() {
        AtomicInteger monkey1 = new AtomicInteger(0); // m1
        AtomicLong monkey2 = new AtomicLong(0);

        ExecutorService service = null;
        try {
            service = Executors.newSingleThreadExecutor(); // m2

            for (int i = 0; i < 100; i++)
                service.submit(() -> monkey1.getAndIncrement()); // m3
            for (int i = 0; i < 100; i++)
                service.submit(() -> monkey2.incrementAndGet());
            //System.out.println(monkey1+" "+monkey2); // m4

            assertThat(monkey1.get(), not(equalTo(monkey2.get())));
            assertThat(monkey1.get() + "", equalTo("100"));

        } finally {
            if (service != null) service.shutdown();
        }
    }

    private static void print(List<String> list) {
        int i = 1;
        for (Object object : list) {
            if (i == 1 || i == 2 || i == 4)
                assertThat(object.toString(), equalTo("" + i++));
            else {
                assertThat(object + "", equalTo("0.3"));
                i++;
            }
        }
    }

    @Test
    public void Q18() {
        List list = new ArrayList();
        list.add(1);
        list.add("2");
        list.add(.3);
        list.add(4);
        print(list);
    }

    @Test
    public void Q23_rangeIsNotinclusive() {
        assertThat(IntStream.range(0, 2).sum(), equalTo(1));
        assertThat(IntStream.rangeClosed(0, 2).sum(), equalTo(3));
    }

    @Test
    public void Q24() {
        DoubleStream ds = DoubleStream.empty();
        OptionalDouble opt = ds.findAny();
        assertThat(opt.orElse(0), equalTo(0.0));

        ds = DoubleStream.empty();
        opt = ds.findAny();
        assertThat(opt.orElseGet(() -> 0.1), equalTo(0.1));

        ds = DoubleStream.empty();
        opt = ds.findAny();
        try {
            opt.getAsDouble();
        } catch (Exception e) { // should throw an exception as the stream is empty
            assertTrue(true);
        }
    }

    @Test(expected = UnsupportedTemporalTypeException.class)
    public void Q25_Formatting_a_date_object_as_a_date_and_time_is_not_allowed_because_there_is_no_time() {
        LocalDate d = LocalDate.of(2015, 5, 1);
        Period p = Period.of(1, 2, 3);
        d = d.minus(p);
        DateTimeFormatter f = DateTimeFormatter
                .ofLocalizedDateTime(FormatStyle.SHORT);
        System.out.println(f.format(d));
    }

    @Test
    public void Q33() {
        LocalDate date = LocalDate.of(2018, Month.APRIL, 30)
                .plusMonths(-1).plusMonths(-2).plusYears(10).plusYears(20);
        assertThat(date.getYear(), equalTo(2048));
        assertThat(date.getMonth(), equalTo(Month.JANUARY));
        assertThat(date.getDayOfMonth(), equalTo(30));

    }

    class FourLegged {
        String walk = "walk,";

        public String walk() {
            return "walk";
        }
    }

    class BabyRhino extends FourLegged {
        String walk = "toddle,";

        @Override
        public String walk() {
            return "walk";
        }
    }


    @Test
    public void Q36() {
        FourLegged f = new BabyRhino();
        BabyRhino b = new BabyRhino();
        assertThat(f.walk(), equalTo(b.walk()));

    }

    @Test
    public void Q41() {


        assertTrue(new File("c:\\text\\book\\java").mkdirs());
        try {
            Files.delete(Paths.get("c:\\text\\book\\java"));
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void Q47_sameKeyPutInMapRemoveOriginalValue() {
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        treeMap.put(3, "a");
        treeMap.put(3, "3");
        treeMap.put(2, "b");
        treeMap.put(5, "c");
        assertThat(treeMap.get(3), not(equalTo("a")));
        assertThat(treeMap.get(3), equalTo("3"));

    }

    class WhichBrowser {
        class Browser {
        }

        class Firefox extends Browser {
        }

        class IE extends Browser {
        }

        private String check() {
            Browser ref = new IE();
            if (ref instanceof Firefox) {
                return "Firefox";
            } else if (ref instanceof Browser) {
                return "Browser";
            } else if (ref instanceof IE) {
                return "IE";
            } else {
                return "None of the above";
            }
        }
    }

    @Test
    public void Q50_If_Else_Trap() {
        assertThat(new WhichBrowser().check(), equalTo("Browser"));
    }

    @Test
    public void Q60_singleThreadCanNotCauseDeadLock() throws ExecutionException, InterruptedException {
        Object o1 = new Object();
        Object o2 = new Object();
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future<?> f1 = service.submit(() -> {
            synchronized (o1) {
                synchronized (o2) {
                    return "Fox";
                } // g1
            }
        });
        Future<?> f2 = service.submit(() -> {
            synchronized (o2) {
                synchronized (o1) {
                    return "Hound";
                } // g2
            }
        });

        assertThat(f1.get(), equalTo("Fox"));
        assertThat(f2.get(), equalTo("Hound"));

    }

    @Test
    public void Q64_NoDeadLockPossibleDueToSynchronisation() throws ExecutionException, InterruptedException {
        Object o1 = new Object();
        Object o2 = new Object();
        List<String> results = new ArrayList<>();
        ExecutorService service = Executors.newFixedThreadPool(10);
        Future<String> f1 = service.submit(() -> {
            synchronized (o1) {
                synchronized (o2) {
                    return "Frog";
                } // h1
            }
        });
        Future<String> f2 = service.submit(() -> {
            synchronized (o1) {
                synchronized (o2) {
                    return "Toad";
                } // h2
            }
        });
        results.add(f1.get());
        results.add(f2.get());

        assertThat(results.toString(),
                allOf(containsString("Frog"), containsString("Toad")));

    }

    @Test(expected = UnsupportedTemporalTypeException.class)
    public void Q71_weekDurationIsnotaValid() {
        LocalDate date = LocalDate.of(2100, 5, 14);
        LocalTime time = LocalTime.of(9, 15);
        LocalDateTime dateTime = LocalDateTime.of(date, time);

        Duration.of(1, ChronoUnit.WEEKS);
    }

    @Test
    public void Q75_CopyOnWriteArrayList_PreserveOriginalListOnIteration() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> list2 = new CopyOnWriteArrayList<>(list1);

        for (Integer item : list2) list2.add(10); // h1
        assertThat(list2.size(), equalTo(6));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void ArrayList_DoesNot_PreserveOriginalListOnIteration() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> list2 = new CopyOnWriteArrayList<>(list1);


        for (Integer item : list1) list1.add(10);
    }

    @Test(expected = ConcurrentModificationException.class)
    public void SynchronizedList_DoesNot_PreserveOriginalListOnIteration() {
        List<Integer> list1 = new ArrayList<>(Arrays.asList(1, 2, 3));
        List<Integer> list2 = Collections.synchronizedList(list1);

        for (Integer item : list2) list2.remove(0);

    }

    @Test(expected = NullPointerException.class)
    public void Q77_TheRootPath_Has_No_Parent() {
        Path userDirectory = Paths.get("/coralreef/../clown/fish").normalize(); // m1
        userDirectory.getRoot().getParent().resolve("dolphin");
    }

    @Test
    public void Q80_DateTimeFormatterofPatternIstrickyonMonth() {
        LocalDateTime d = LocalDateTime.of(2015, 5, 10, 11, 22, 33);
        DateTimeFormatter f = DateTimeFormatter.ofPattern("hh:MM");
        assertThat(d.format(f), containsString("11:05"));
        assertThat(d.format(f), not(containsString("11:22")));

    }

    @Test
    public void Q82_merge() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 10);
        map.put(2, 20);
        map.put(3, null);

        map.merge(1, 3, (a,b) -> null);
        map.merge(3, 3, (a,b) -> null);
        // key wwas there and value was there , function return null , then the key goes out
        // key was there value was null, the function returns null , then the value is the proposed value
        assertThat(map.toString(), containsString("{2=20, 3=3}"));
    }

   static class WhatisIt {
        static interface Furry { }
        static class Chipmunk { }
        static class FurryChipmunk implements Furry { }
    }
    @Test
    public void code_involving_instanceof_does_not_compile_when_there_is_no_way_for_it_to_evaluate_to_true() {
        // ie Cast not possble => compile NOK
        WhatisIt.Chipmunk c = new WhatisIt.Chipmunk();
        int result = 0;
        if (c instanceof WhatisIt.Furry) result += 1;
        if (c instanceof WhatisIt.Chipmunk) result +=2;
        if (null instanceof WhatisIt.FurryChipmunk) result += 4;
        assertThat(result, equalTo(2));

    }

     class Panda {
        String name;
        Panda(String name) { this.name = name; }

         @Override
         public boolean equals(Object o) {
             if (this == o) return true;
             if (!(o instanceof Panda)) return false;
             Panda panda = (Panda) o;

             return name.equals(panda.name);
         }
         @Override
         public int hashCode() {
             return Objects.hash(name);
         }
     }

     @Test
    public void Q97() {
        Set s = new HashSet<>();
        s.add(new Panda("Bao Bao"));
        s.add(new Panda("Mei Xiang"));
        s.add(new Panda("Bao Bao"));
        
        assertThat(s.size(), equalTo(2));
    }

}





