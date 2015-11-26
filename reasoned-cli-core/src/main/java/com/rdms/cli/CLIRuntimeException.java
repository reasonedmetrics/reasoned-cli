package com.rdms.cli;

public class CLIRuntimeException extends RuntimeException {

    public CLIRuntimeException(CLI.RuntimeErrors err) {
        super(err.getMessage());
    }

    public CLIRuntimeException(CLI.RuntimeErrors err, String... vars) {
        super(String.format(err.getMessage(), (Object[]) vars));
    }

    public CLIRuntimeException(CLI.RuntimeErrors err, Exception ex) {
        super(err.getMessage(), ex);
    }

}
