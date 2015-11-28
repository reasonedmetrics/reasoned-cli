package com.rdms.cli.example;

import com.rdms.cli.Arg;
import com.rdms.cli.BasicCLI;
import java.util.ArrayList;
import java.util.Collection;
import com.rdms.cli.CLI;
import com.rdms.cli.Task;
import java.util.EnumSet;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

public class ExampleApp extends BasicCLI {

    @RequiredArgsConstructor
    @Getter
    public enum Flags implements Arg {
        DO_IT_DIFFERENT("different", "Do things differently", "d", "different");
        @NonNull
        private final String name;
        @NonNull
        private final String description;
        @NonNull
        private final String arg;
        @NonNull
        private final String longArg;

    }

    private ExampleApp(String[] args) {
        super("Example App", args, createTasks(), EnumSet.allOf(Flags.class));
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
