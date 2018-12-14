package org.sobngwi.oca.nio2;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

public class Nio2Test {

    private String workingDir;

    @Before
    public void setUp()  {

         workingDir = "/Users/sobngwi/intelliJ/codebase/certification/oca/";
    }

    @Test
    public void q4() {

        Path path = Paths.get("/zoo/animals/bear/koala");
        assertThat(path.subpath(1,3).toString(), equalTo("animals/bear"));
        assertThat(path.subpath(1,3).getName(1).toString(), equalTo("bear"));
        assertThat(path.subpath(1,3).getName(1).toAbsolutePath().toString(), equalTo(workingDir + "bear"));
    }

    @Test (expected = NoSuchFileException.class)
    public void q7() throws IOException {
        Path path = Paths.get("turkey");

        if(Files.isSameFile(path, Paths.get(workingDir +"turkey"))){
            Files.createDirectory(path.resolve("info"));
        }
    }

    @Test
    public void q8() {
        Path path1 = Paths.get("/pets/../cat.txt");
        Path path2 = Paths.get("./dog.txt");

        assertThat(path1.resolve(path2).toString(), equalTo("/pets/../cat.txt/./dog.txt"));
        assertThat(path2.resolve(path1), equalTo(path1));
    }

    @Test
    public void q11() throws IOException {
        Path path1 = Paths.get("pom.xml");
        Path path2 = Paths.get("pom.xml.saved");

        Files.copy(path1, path2, StandardCopyOption.COPY_ATTRIBUTES);

        assertFalse(Files.isSameFile(path1, path2));
        assertTrue(Files.isSameFile(path1, path1));
        assertTrue(Files.isSameFile(path2, path2));

        Files.delete(path2);
    }

    @Test
    public void filesListNio2() throws IOException {

       List<String> result =  Files.list(Paths.get(workingDir))
                .map(Path::toString) // instead of : p -> p.toString()
                .collect(Collectors.toList());

        assertThat( result.toString(), allOf(containsString("pom.xml"),
                containsString("src"),
                containsString(".git")));
    }

    @Test
    public void walkingTheFileTreeNiO2() throws IOException {

      List<String> result =   Files.walk(Paths.get(workingDir + "src/main/java/org/sobngwi/oca/exercices"))
                .map(Path::toAbsolutePath)
                .map(Path::toString)
                .collect(Collectors.toList());
      assertThat(result.toString(), containsString(".java"));
    }

    @Test
    public void equalsNormalzeOnNIO2() throws IOException {

        Path path1 = Paths.get(workingDir + "/pom.xml");
        Path path2 = Paths.get(workingDir + ".." + "/." + "/oca" + "/pom.xml");

        assertTrue(Files.isSameFile(path1, path2));
        assertThat(path1, not(equalTo(path2)));
        assertThat(path1.normalize(), equalTo(path2.normalize()));
    }

    @Test
    public void pathGetNameCount() {
        Path path= Paths.get(".").normalize();

        int countPoint =0 ;
        for ( int i = 0 ; i < path.getNameCount(); i++){
            countPoint++;
        }

        int countAbsolte=0;
        path = Paths.get(workingDir);
        for ( int i = 0 ; i < path.getNameCount(); i++){
            countAbsolte++;
        }

        assertThat(countPoint, not(countAbsolte));
        assertThat(countPoint, equalTo(1));
        assertThat(countAbsolte, equalTo(6));
    }

    @Test
    public void fileAlreadyExisException() throws  IOException{
        Path path1 = Paths.get("pom.xml");
        Path path2 = Paths.get("pom.xml.saved");

        Files.copy(path1, path2, StandardCopyOption.COPY_ATTRIBUTES);
        try{
        Files.copy(path1, path2, StandardCopyOption.COPY_ATTRIBUTES);}
        catch (FileAlreadyExistsException e)
        {
            Files.delete(path2);
        }
    }
}
