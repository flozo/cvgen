package de.flozo.latex.assembly;

import de.flozo.common.dto.appearance.Icon;
import de.flozo.latex.core.Command;
import de.flozo.latex.core.GenericCommand;

import java.util.List;

public class IconCommand implements Command {

    public static final String ICON_COMMAND = "faIcon";

    private final String iconName;
    private final Command command;

    private IconCommand(String iconName, Command command) {
        this.iconName = iconName;
        this.command = command;
    }

    public static IconCommand fromIcon(Icon iconName) {
        return new IconCommand(iconName.getName(),
                new GenericCommand.Builder("faIcon")
                        .body(iconName.getFontawesomeIcon().getSpecifier())
                        .build());
    }

    @Override
    public List<String> getInlineOptions() {
        return command.getInlineOptions();
    }

    @Override
    public List<String> getBlock() {
        return command.getBlock();
    }

    @Override
    public String getInline() {
        return command.getInline();
    }

    @Override
    public String toString() {
        return "IconCommand{" +
                "iconName='" + iconName + '\'' +
                ", command=" + command +
                '}';
    }
}
