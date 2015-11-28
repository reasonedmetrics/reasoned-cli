package com.rdms.cli;

public interface MutableArg extends Arg {

    public void setName(String name);

    public void setDescription(String description);

    public void setArg(String arg);

    public void setLongArg(String longArg);

}
