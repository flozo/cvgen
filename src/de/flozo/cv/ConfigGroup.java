package de.flozo.cv;

import de.flozo.io.File;
import de.flozo.letter.data.*;

import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public enum ConfigGroup {

    MASTER("master", "master"),
    LETTER_GENERAL("letter.general", "letter_general"),
    LETTER_GEOMETRY("letter.geometry", "letter_geometry"),
    LETTER_COLORS("letter.colors", "letter_colors"),
    LETTER_FONTS("letter.fonts", "letter_fonts"),
    LETTER_ENCLOSURES("letter.enclosures", "letter_enclosures"),
    LETTER_ICONS("letter.icons", "icons"),
    SENDER_DATA("letter.sender", "sender_data"),
    RECEIVER_DATA("letter.receiver", "receiver_data"),

    CV_GENERAL("cv.general", "cv_general"),
    CV_GEOMETRY("cv.geometry", "cv_geometry"),
    CV_COLORS("cv.colors", "cv_colors"),
    CV_FONTS("cv.fonts", "cv_fonts"),
    CV_EDUCATION("cv.education", "cv_education"),
    CV_CAREER("cv.career", "cv_career"),
    CV_SKILLS("cv.skills", "cv_skills");


    private final String configGroup;
    private final String defaultFileName;
    private final String resourceFileName;

    ConfigGroup(String configGroup, String defaultFileName) {
        this.configGroup = configGroup;
        this.defaultFileName = defaultFileName + ".config";
        this.resourceFileName = "/resource/config_file_comments/" + defaultFileName + ".txt";
    }

    public String getPropertyKey() {
        return configGroup;
    }

    public String getDefaultFileName() {
        return defaultFileName;
    }


    public Map<String, String> getDefaultPropertyMap() {
        Map<String, String> propertiesRawMap = new HashMap<>();
        if (Objects.equals(configGroup, ConfigGroup.MASTER.configGroup)) {
            propertiesRawMap.putAll(Arrays.stream(de.flozo.letter.data.ConfigGroup.values())
                    .filter(e -> !e.equals(de.flozo.letter.data.ConfigGroup.MASTER))
                    .collect(Collectors.toMap(de.flozo.letter.data.ConfigGroup::getPropertyKey, de.flozo.letter.data.ConfigGroup::getDefaultFileName)));
        }
        if (Objects.equals(configGroup, ConfigGroup.LETTER_GENERAL.configGroup)) {
            propertiesRawMap.putAll(Arrays.stream(LetterGeneralProperty.values())
                    .collect(Collectors.toMap(LetterGeneralProperty::getPropertyKey, LetterGeneralProperty::getGenericStringValue)));
        }
        if (Objects.equals(configGroup, ConfigGroup.LETTER_GEOMETRY.configGroup)) {
            propertiesRawMap.putAll(Arrays.stream(LetterGeometryProperty.values())
                    .collect(Collectors.toMap(LetterGeometryProperty::getPropertyKey, LetterGeometryProperty::getGenericStringValue)));
        }
        if (Objects.equals(configGroup, ConfigGroup.LETTER_COLORS.configGroup)) {
            propertiesRawMap.putAll(Arrays.stream(LetterColorProperty.values())
                    .collect(Collectors.toMap(LetterColorProperty::getPropertyKey, LetterColorProperty::getGenericStringValue)));
        }
        if (Objects.equals(configGroup, ConfigGroup.LETTER_FONTS.configGroup)) {
            propertiesRawMap.putAll(Arrays.stream(LetterFontProperty.values())
                    .collect(Collectors.toMap(LetterFontProperty::getPropertyKey, LetterFontProperty::getGenericStringValue)));
        }
        if (Objects.equals(configGroup, ConfigGroup.SENDER_DATA.configGroup) || Objects.equals(configGroup, ConfigGroup.RECEIVER_DATA.configGroup)) {
            propertiesRawMap.putAll(Arrays.stream(AddressProperty.values())
                    .collect(Collectors.toMap(AddressProperty::getPropertyKey, AddressProperty::getGenericStringValue)));
        }
        return propertiesRawMap;
    }

    public void writeToFile(ConfigDirectory configDirectory, String versionInfo) {
        File configFile = File.fromPath(Paths.get(configDirectory.getString(), defaultFileName));
        configFile.writeLines(assembleDefaultConfigLines(versionInfo));
    }

    private List<String> getDefaultPropertyLines() {
        return getDefaultPropertyMap().entrySet()
                .stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .sorted()
                .collect(Collectors.toList());
    }

    private List<String> getCommentLines() {
        return File.fromString(Objects.requireNonNull(getClass().getResource(resourceFileName)).getPath())
                .getLines()
                .stream()
                .map(e -> "# " + e)
                .collect(Collectors.toList());
    }

    private List<String> assembleDefaultConfigLines(String versioninfo) {
        List<String> lines = new ArrayList<>();
        lines.add("# Program info: " + versioninfo);
        lines.add("#");
        lines.addAll(getCommentLines());
        lines.add("");
        lines.addAll(getDefaultPropertyLines());
        return lines;
    }


    @Override
    public String toString() {
        return "ConfigGroup{" +
                "configGroup='" + configGroup + '\'' +
                ", defaultFileName='" + defaultFileName + '\'' +
                ", resourceFileName='" + resourceFileName + '\'' +
                '}';
    }

}
