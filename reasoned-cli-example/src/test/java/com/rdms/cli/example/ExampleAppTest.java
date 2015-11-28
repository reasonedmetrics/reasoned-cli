package com.rdms.cli.example;

import com.rdms.cli.CLIRuntimeException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class ExampleAppTest {

    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

    public ExampleAppTest() {
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

    @Test(expected = NullPointerException.class)
    public void testMain_nullArgs() {
        String[] args = null;
        ExampleApp.main(args);
    }

    @Test
    public void testMain_emptyArgs() {
        String[] args = new String[]{};
        exit.expectSystemExitWithStatus(1);
        ExampleApp.main(args);
    }

    @Test
    public void testMain_badArgs() {
        String[] args = new String[]{"wtf"};
        exit.expectSystemExitWithStatus(1);
        ExampleApp.main(args);
    }

    @Test(expected = CLIRuntimeException.class)
    public void testMain_shortArgWrong() {
        String[] args = new String[]{"-f"};
        ExampleApp.main(args);
    }

    @Test
    public void testMain_shortArg() {
        String[] args = new String[]{"-e"};
        ExampleApp.main(args);
        assertEquals("Doing example things now\n", systemOutRule.getLog());
    }

    @Test
    public void testMain_shortArgWithFlag() {
        String[] args = new String[]{"-e", "-d"};
        ExampleApp.main(args);
        assertEquals("Doing example things now - flag on\n", systemOutRule.getLog());
    }

    @Test(expected = CLIRuntimeException.class)
    public void testMain_longArgWrong() {
        String[] args = new String[]{"--fake"};
        ExampleApp.main(args);
    }

    @Test
    public void testMain_longArg() {
        String[] args = new String[]{"--example"};
        ExampleApp.main(args);
        assertEquals("Doing example things now\n", systemOutRule.getLog());
    }

    @Test
    public void testMain_longArgWithFlag() {
        String[] args = new String[]{"--example", "-d"};
        ExampleApp.main(args);
        assertEquals("Doing example things now - flag on\n", systemOutRule.getLog());
    }

}
