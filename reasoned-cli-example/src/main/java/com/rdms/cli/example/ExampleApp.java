package com.rdms.cli.example;

import com.rdms.cli.BasicCLI;
import java.util.ArrayList;
import java.util.Collection;
import com.rdms.cli.CLI;
import com.rdms.cli.Task;

public class ExampleApp extends BasicCLI {

    private ExampleApp(String[] args) {
        super("Example App", args, createTasks());
    }

    public static void main(String[] args) {
        CLI cli = new ExampleApp(args);
        cli.run();
    }

    private static Collection<Task> createTasks() {
        Collection<Task> tasks = new ArrayList<>();
        tasks.add(new ExampleTask());
        return tasks;
    }

}
