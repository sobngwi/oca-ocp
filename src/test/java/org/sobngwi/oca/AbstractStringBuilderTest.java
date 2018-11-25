package org.sobngwi.oca;

import org.junit.After;
import org.junit.Before;

import java.io.File;

import static org.sobngwi.oca.tools.Log4Test.log;

public class AbstractStringBuilderTest {

    private final String canonicalName = this.getClass().getCanonicalName();
    protected StringBuilder aStringBuilder;
    protected String immutableString;
    protected File filename;

    @Before
    public void setUp() {
        final String methodName="setUp";
        final String classAndMethod = "[" + canonicalName + "." + methodName + "]";

        aStringBuilder = new StringBuilder();
        immutableString = new String();

        log(classAndMethod, " An empty stringBuilder have been initialised " + aStringBuilder.toString());
        log(classAndMethod,  " an empty immutableString have been initialised " + immutableString);
    }

    @After
    public void cleanUp() {
        final String methodName="cleanUp";
        final String classAndMethod = "[" + canonicalName + "." + methodName + "]";
        aStringBuilder = null;
        immutableString = null ;
        log(classAndMethod,   " aStringBuilder have been set to null " + aStringBuilder);
        log(classAndMethod,   " immutableString have been set to null " + immutableString);
    }

}
