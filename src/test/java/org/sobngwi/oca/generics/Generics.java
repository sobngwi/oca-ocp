package org.sobngwi.oca.generics;

import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.Assert.*;

public class Generics {


    @Test
    public void typeInference() {

        assertThat((new Hello<String>("hi")).toString().concat( new Hello("there").toString()), equalTo("hithere"));

    }

    @Test
    public void postincrementationAndJoigningOperator() {
       assertThat (Stream.iterate(1, x -> x+1)
                .limit(5)
                .map(x -> x + "")
                .collect(Collectors.joining(",")), equalTo("1,2,3,4,5"));
    }

    @Test
    public void partitioningByStartbyTheFalse() {

        Stream<String> s = Stream.empty();
        Stream<String> s2 = Stream.empty();
        Map<Boolean, List<String>> partitioningBy = s.collect(
                Collectors.partitioningBy(b -> b.startsWith("c")));
        Map<Boolean, List<String>> groupingBy = s2.collect(
                Collectors.groupingBy(b -> b.startsWith("c")));

        assertThat(partitioningBy.toString(), equalTo("{false=[], true=[]}"));
        assertThat(groupingBy.toString(), equalTo("{}"));
    }

    @Test
    public void resolvePathTakeAStringasParamAndDoesNotNormalizePath() {
        Path path = Paths.get("/user/.././root","../kodiacbear.txt");
        assertThat(path.toString(), containsString(".."));
                assertNotNull(path.resolve("joey"));
    }

    @Test
    public void relativePathTakeAPathAsParam() {

        Path path = Paths.get("/user/.././root");
        assertNotNull(path.normalize().relativize(Paths.get("/lion")));
        assertThat(path.normalize().relativize(Paths.get("/lion")).toString(), equalTo("../lion"));
    }

    @Test
    public void callingResolveWithAnabsolutePathAsParameterReturnsTheAbsolutePath() {
        Path path = Paths.get("/user/.././root","../kodiacbear.txt");
        assertThat(path.toString(), equalTo("/user/.././root/../kodiacbear.txt"));
    }
}
