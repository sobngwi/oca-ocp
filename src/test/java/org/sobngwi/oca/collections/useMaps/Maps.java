package org.sobngwi.oca.collections.useMaps;

import org.junit.Test;
import org.sobngwi.oca.collections.AbstractCollections;

import java.util.Collections;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class Maps extends AbstractCollections {

    @Test
    public void unModifiableMapsConstainsModifiableElements(){

        integerStringMap = Collections.unmodifiableMap(integerStringMap);
        Integer result = integerStringMap.keySet()
                .stream()
                .filter(k -> k.equals(2007))
                .findFirst()
                .orElse(-1);
        integerStringMap.get(result).append(" SOBNGWI");
       // StringBuilder result = integerStringMap.compute(2007, (key, value) -> value.append(" SOBNGWI"));

        assertThat(result , equalTo(2007));
        assertThat(integerStringMap.get(result).toString(), allOf(containsString("SOBNGWI"),
                containsString("Anne-Gaelle")));
    }

    @Test
    public void mapsWithEntrykeysThatDoesntOverrideEqualsMethods(){

         Integer result=  stringBuilderIntegerMap.get(new StringBuilder("Anne-Gaelle"));

         assertThat(stringBuilderIntegerMap.keySet().toString() , allOf(containsString("Anne-Gaelle"),
                containsString("Rudy")));
         assertNotEquals(result, new Integer(2007));
         assertEquals(result, null);

    }

    @Test
    public void mergeMapKeyNotExistAndValueIsNotNull(){
        integerStringMap.merge(2008, new StringBuilder("Youmsi"), (k , v ) -> new StringBuilder("tata") ) ;
        //integerStringMap.forEach((k,v) -> System.out.println(String.format(" Key : %s , value %s ", k, v)));


        assertThat(integerStringMap.keySet().toString() , allOf(containsString("2004"),
                containsString("2007"), containsString("2008")));
        assertThat(integerStringMap.values().toString(), containsString("Youmsi"));

    }
    @Test ( expected = NullPointerException.class)
    public void mergeMapKeyNotExistAndValeIsNull(){
        integerStringMap.merge(2008, null, (k , v ) -> new StringBuilder("tata") ) ;
        integerStringMap.forEach((k,v) -> System.out.println(String.format(" Key : %s , value %s ", k, v)));
        fail();
    }

    @Test ( expected = NullPointerException.class)
    public void mergeMapKeyExistAndValeIsNull(){
        integerStringMap.merge(2007, null, (k , v ) -> new StringBuilder("tata") ) ;
        integerStringMap.forEach((k,v) -> System.out.println(String.format(" Key : %s , value %s ", k, v)));
        fail();
    }

    @Test
    public void mergeMapKeyExistAndValeIsNotNullAndFunctionreturnNull(){
        integerStringMap.merge(2007, new StringBuilder("Youmsi"), (k , v ) -> null)  ;
        integerStringMap.forEach((k,v) -> System.out.println(String.format(" Key : %s , value %s ", k, v)));

        assertThat(integerStringMap.keySet().toString() , allOf(containsString("2004"),
                not(containsString("2007"))));
    }

    @Test
    public void mergeMapKeyExistAndValeIsNotNullAndFunctionreturnNonNull(){
        integerStringMap.merge(2007, new StringBuilder("Youmsi"), (k , v ) -> new StringBuilder("Sagueu"))  ;
        integerStringMap.forEach((k,v) -> System.out.println(String.format(" Key : %s , value %s ", k, v)));

        assertThat(integerStringMap.keySet().toString() , allOf(containsString("2004"),
                (containsString("2007"))));
        assertThat(integerStringMap.values().toString(),
                allOf( containsString("Sagueu"), not(containsString("Youmsi"))));
    }



}
