package org.cheetah.core;

import vo.TestVar;

import org.junit.jupiter.api.Test;

/**
 * @author Wooyea
 */
class VarsMapTest {

    @Test
    void bind() {

        VarsMap  map = new VarsMap();
        TestVar var = new TestVar();
        var.intField = 0;
        var.longField = 1000000;
        var.nexLevel = new TestVar();
        var.nexLevel.intField = 11;
        var.nexLevel.stringField = "levle2stringField";
        map.bind("testVar", var);

        map.getByPath("testVar");

    }

    @Test
    void getVars() {
    }

}