package com.rdms.cli;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
abstract public class BasicTask implements Task {

    @NonNull
    private String name;
    @NonNull
    private String description;
    private String arg;
    private String longArg;

}
