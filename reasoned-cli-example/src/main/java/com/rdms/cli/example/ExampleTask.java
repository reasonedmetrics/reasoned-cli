package com.rdms.cli.example;

import com.rdms.cli.Arg;
import com.rdms.cli.BasicTask;
import com.rdms.cli.example.ExampleApp.Flags;
import java.util.Set;

public class ExampleTask extends BasicTask {

    public ExampleTask() {
        super("Example Task", "Does some example stuff");
    }

    @Override
    public void run(Set<? extends Arg> flags) {
        if (flags != null && flags.contains(Flags.DO_IT_DIFFERENT)) {
            System.out.println("Doing example things now - flag on");
        } else {
            System.out.println("Doing example things now");
        }
    }

}
