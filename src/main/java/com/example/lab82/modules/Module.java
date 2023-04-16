package com.example.lab82.modules;

import java.util.List;

public interface Module {
    boolean isExtensionSuitable(String extension);

    String getName();

    List<String> getPermissibleExtension();

    List<String> getOption();
}
