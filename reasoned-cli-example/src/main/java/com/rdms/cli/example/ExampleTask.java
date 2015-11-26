package com.rdms.cli.example;

import com.rdms.cli.BasicTask;

public class ExampleTask extends BasicTask {

    public ExampleTask() {
        super("Example Task", "Does some example stuff");
    }

    @Override
    public void run() {
        System.out.println("Doing example things now");
    }

}
