package org.sobngwi.oca.generics;

import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class Generics {


    @Test
    public void typeInference() {

        assertThat((new Hello<String>("hi")).toString().concat( new Hello("there").toString()), equalTo("hithere"));

    }
}
