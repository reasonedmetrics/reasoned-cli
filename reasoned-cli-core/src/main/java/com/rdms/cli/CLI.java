package com.rdms.cli;

import java.util.Collection;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public interface CLI {

    @AllArgsConstructor
    public enum RuntimeErrors {
        TASKS_NULL("Tasks cannot be null"),
        TASKS_EMPTY("At least one task must be defined"),
        PROPOSED_ARG_NAME_IN_USE("The proposed argument name '%s' is already defined. Resolve the conflict or define an explicit argument name for '%s'."),
        ARG_NAME_IN_USE("The argument name '%s' is already defined. Resolve the conflict for '%s'."),
        PROPOSED_LONG_ARG_NAME_IN_USE("The proposed long argument name '%s' is already defined. Resolve the conflict or define an explicit long argument name for '%s'."),
        LONG_ARG_NAME_IN_USE("The long argument name '%s' is already defined. Resolve the conflict for '%s'."),
        ARG_NAME_BLANK("The argument name for '%s' is blank. Define argument name or set null to auto-create."),
        LONG_ARG_NAME_BLANK("The long argument name for '%s' is blank. Define long argument name or set null to auto-create."),
        TASK_NAME_BLANK("A task of type %s has a blank name. Define a name for this task that is more than whitespce."),
        FLAG_NAME_BLANK("A task of flag %s has a blank name. Define a name for this flag that is more than whitespce."),
        FAILED_TO_PARSE_COMMAND_LINE("Failed to parse command line");
        @Setter(AccessLevel.NONE)
        @Getter(AccessLevel.PUBLIC)
        private final String message;
    }

    public String getName();

    public Collection<Task> getTasks();
    
    public Set<? extends Arg> getFlags();

    public String[] getArgs();

    public void run();

    public void displayHelpMessage();

}
