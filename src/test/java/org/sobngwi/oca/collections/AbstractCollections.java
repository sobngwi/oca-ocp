package org.sobngwi.oca.collections;

import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class AbstractCollections {


   protected List<String> strings;
   protected List<StringBuilder> stringBuilders;
   protected Map<Integer, StringBuilder> integerStringMap;
   protected Map<StringBuilder, Integer> stringBuilderIntegerMap;
   protected Queue<String> stringQueue ;
   protected TreeSet<Integer> integerTreeSet;
   protected List<String> stringList;


   @Before
    public void setUp(){
       strings = new ArrayList<>(Arrays.asList("Alain", "Narcisse"));
       stringList= Arrays.asList("NARCISSE", "alain",  "SOBNGWI" , "sagueu");
       stringBuilders = Arrays.asList(new StringBuilder("Alain"));
       stringBuilders= Collections.unmodifiableList(stringBuilders);
       integerStringMap = new HashMap<>();
       integerStringMap.put(2007, new StringBuilder("Anne-Gaelle"));
       integerStringMap.put(2004, new StringBuilder ("Rudy"));
       //integerStringMap = Collections.unmodifiableMap(integerStringMap);
       stringBuilderIntegerMap = new HashMap<>();
       stringBuilderIntegerMap.put(new StringBuilder("Anne-Gaelle"), 2007);
       stringBuilderIntegerMap.put(new StringBuilder("Rudy"), 2004);

       stringQueue = new ArrayDeque<>();

       integerTreeSet = new TreeSet<>();
       integerTreeSet.addAll( Stream.iterate(1, i -> ++i  )
               .limit(10)
               .collect(Collectors.toSet()));
   }

    protected void addObject(List strings) {
        strings.add(LocalDateTime.now());
    }


}
