package org.sobngwi.oca;

import org.junit.After;
import org.junit.Before;

import java.io.File;

import static org.sobngwi.oca.tools.Log4Test.log;

public class AbstractFileTest {

    final String canonicalName = this.getClass().getCanonicalName();
    protected File filename;
    public static final String FILENAME = "test.data";

    @Before
    public void init() {
        final String methodName="init";
        final String classAndMethod = "[" + canonicalName + "." + methodName + "]";
        filename = new File(FILENAME);
        log(classAndMethod,  " A File with filename have been created: " + filename);
    }


    @After
    public void reallyClean() {

        final String methodName="reallyClean";
        final String classAndMethod = "[" + canonicalName + "." + methodName + "]";
        boolean isDeleted = filename.delete();
        log(classAndMethod,  " Clean up: try to delete all artifacts after test ");
        log(classAndMethod,  " FILENAME + isDeleted ? :" + isDeleted);
        filename = null ;
    }

}
