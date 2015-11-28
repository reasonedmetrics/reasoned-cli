package com.rdms.cli;

import java.util.Set;

public interface Task extends MutableArg {

    public void run(Set<? extends Arg> flags);

}
