package com.rdms.cli;

public interface Task {

    public String getName();

    public void setName(String name);

    public String getDescription();

    public void setDescription(String description);

    public String getArg();

    public void setArg(String arg);

    public String getLongArg();

    public void setLongArg(String longArg);

    public void run();

    @Override
    public String toString();

}
