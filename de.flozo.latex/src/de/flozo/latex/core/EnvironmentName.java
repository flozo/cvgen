package de.flozo.latex.core;

public enum EnvironmentName {

    DOCUMENT,
    SCOPE,
    TIKZPICTURE,
    PGFONLAYER;

    public String getString() {
        return name().toLowerCase();
    }
}
