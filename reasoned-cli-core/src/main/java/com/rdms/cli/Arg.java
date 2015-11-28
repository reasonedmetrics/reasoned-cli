package com.rdms.cli;

public interface Arg {

    public String getName();

    public String getDescription();

    public String getArg();

    public String getLongArg();

    @Override
    public String toString();

}
