package com.rdms.cli;

import java.util.ArrayList;
import java.util.Collection;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import com.google.common.base.CaseFormat;
import java.util.AbstractSet;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

@Data
@RequiredArgsConstructor
abstract public class BasicCLI implements CLI {

    private static final Logger LOGGER = Logger.getLogger(BasicCLI.class.getName());

    @Setter(AccessLevel.PROTECTED)
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private String name;

    @Setter(AccessLevel.PROTECTED)
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private String[] args;

    @Setter(AccessLevel.PROTECTED)
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private Collection<Task> tasks;

    @Setter(AccessLevel.PROTECTED)
    @Getter(AccessLevel.PUBLIC)
    @NonNull
    private Set<? extends Arg> flags;

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private Options options;

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.PRIVATE)
    private CommandLine commandLine;

    @Override
    public void run() {
        preFlightChecks();
        buildOptions();
        processArgs();
        runTasks();
    }

    private void preFlightChecks() {
        validateConfiguration();
        resolveTaskArgs();
        validateTaskNames();
        validateTaskArgNames();
        validateTaskLongArgNames();
    }

    private void validateConfiguration() {
        if (getTasks() == null) {
            throw new CLIRuntimeException(RuntimeErrors.TASKS_NULL);
        }
        if (getTasks().size() < 1) {
            throw new CLIRuntimeException(RuntimeErrors.TASKS_EMPTY);
        }
    }

    private void resolveTaskArgs() {
        Collection<String> taskArgs = new ArrayList<>();
        Collection<String> taskLongArgs = new ArrayList<>();
        for (Task task : getTasks()) {
            LOGGER.log(Level.FINE, "Inspecting task {0}", task.toString());
            String arg = task.getArg();
            String longArg = task.getLongArg();
            if (arg == null) {
                String proposedArg = task.getName().toLowerCase().substring(0, 1);
                LOGGER.log(Level.INFO, "Task {0} does not define argument name, proposing -{1}", new Object[]{task.getName(), proposedArg});
                if (taskArgs.contains(proposedArg)) {
                    throw new CLIRuntimeException(RuntimeErrors.PROPOSED_ARG_NAME_IN_USE, proposedArg, task.getName());
                }
                arg = proposedArg;
            } else if (taskArgs.contains(arg)) {
                throw new CLIRuntimeException(RuntimeErrors.ARG_NAME_IN_USE, arg, task.getName());
            }
            if (longArg == null) {
                String proposedLongArg = task.getName().replaceAll("\\s+", "_").toUpperCase();
                proposedLongArg = CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, proposedLongArg);
                LOGGER.log(Level.INFO, "Task {0} does not define a long argument name, proposing --{1}", new Object[]{task.getName(), proposedLongArg});
                if (taskLongArgs.contains(proposedLongArg)) {
                    throw new CLIRuntimeException(RuntimeErrors.PROPOSED_LONG_ARG_NAME_IN_USE, proposedLongArg, task.getName());
                }
                longArg = proposedLongArg;
            } else if (taskLongArgs.contains(longArg)) {
                throw new CLIRuntimeException(RuntimeErrors.LONG_ARG_NAME_IN_USE, longArg, task.getName());
            }
            taskArgs.add(arg);
            taskLongArgs.add(longArg);
            task.setArg(arg);
            task.setLongArg(longArg);
        }
    }

    private void validateTaskNames() {
        for (Task task : getTasks()) {
            if (task.getName().matches("^\\s*$")) {
                throw new CLIRuntimeException(RuntimeErrors.TASK_NAME_BLANK, task.getClass().getName());
            }
        }
    }

    private void validateTaskArgNames() {
        for (Task task : getTasks()) {
            if (task.getArg().matches("^\\s*$")) {
                throw new CLIRuntimeException(RuntimeErrors.ARG_NAME_BLANK, task.getName());
            }
        }
    }

    private void validateTaskLongArgNames() {
        for (Task task : getTasks()) {
            if (task.getLongArg().matches("^\\s*$")) {
                throw new CLIRuntimeException(RuntimeErrors.LONG_ARG_NAME_BLANK, task.getName());
            }
        }
    }

    private void buildOptions() {
        if (getOptions() == null) {
            setOptions(new Options());
        }
        buildTaskOptions();
        buildFlagOptions();
    }

    private void buildTaskOptions() {
        OptionGroup taskOptions = new OptionGroup();
        for (Task task : getTasks()) {
            Option option = Option.builder(task.getArg()).longOpt(task.getLongArg()).desc(task.getDescription()).build();
            taskOptions.addOption(option);
        }
        getOptions().addOptionGroup(taskOptions);
    }

    private void buildFlagOptions() {
        if (getFlags() != null) {
            for (Arg arg : getFlags()) {
                Option option = Option.builder(arg.getArg()).longOpt(arg.getLongArg()).desc(arg.getDescription()).build();
                getOptions().addOption(option);
            }
        }
    }

    private void processArgs() {
        CommandLineParser parser = new DefaultParser();
        try {
            setCommandLine(parser.parse(getOptions(), getArgs()));
        } catch (ParseException ex) {
            throw new CLIRuntimeException(RuntimeErrors.FAILED_TO_PARSE_COMMAND_LINE, ex);
        }
    }
    
    private Set<Arg> getEnabledFlags() {
        Set<Arg> enabledFlags = new HashSet<>();
        for(Arg arg : getFlags()) {
            if(getCommandLine().hasOption(arg.getArg())) {
                enabledFlags.add(arg);
            }
        }
        return enabledFlags;
    }

    private void runTasks() {
        boolean ranATask = false;
        for (Task task : getTasks()) {
            if (getCommandLine().hasOption(task.getArg())) {
                LOGGER.log(Level.INFO, "Running task {0}", task.getName());
                task.run(getEnabledFlags());
                ranATask = true;
            }
        }
        if (!ranATask) {
            LOGGER.log(Level.SEVERE, "No task specified");
            displayHelpMessage();
            System.exit(1);
        }
    }

    @Override
    public void displayHelpMessage() {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp(getName().replaceAll("\\s+", ""), getOptions());
    }
}
