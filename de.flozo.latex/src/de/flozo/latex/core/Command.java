package de.flozo.latex.core;

import java.util.List;

public interface Command extends ExpressionList {

    List<String> getInlineOptions();

}
