package com.rdms.cli.example;

import com.rdms.cli.Arg;
import com.rdms.cli.example.ExampleApp.Flags;
import java.util.EnumSet;
import java.util.Set;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class ExampleTaskTest {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    public ExampleTaskTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testRun_nullFlags() {
        ExampleTask task = new ExampleTask();
        task.run(null);
        assertEquals("Doing example things now\n", systemOutRule.getLog());
    }

    @Test
    public void testRun_withFlags() {
        ExampleTask task = new ExampleTask();
        Set<? extends Arg> flags = EnumSet.of(Flags.DO_IT_DIFFERENT);
        task.run(flags);
        assertEquals("Doing example things now - flag on\n", systemOutRule.getLog());
    }

}
