package de.flozo.cv.data;

import java.util.Objects;

public enum CVGeneralProperty {

    DRAFT_MODE_ON("draft_mode.on", false),
    IMAGE_PLACEHOLDER_ON("image_placeholder.on", false),
    HYPERLINKS_ON("hyperlinks.on", true),
    CV_IMAGE_FILE("cv.image_file", ""),
    CV_SIGNATURE_FILE("cv.signature_file", "");

    private final String property;
    private final String stringValue;
    private final boolean booleanValue;

    CVGeneralProperty(String property, String stringValue) {
        this.property = property;
        this.stringValue = stringValue;
        this.booleanValue = false;
    }

    CVGeneralProperty(String property, boolean booleanValue) {
        this.property = property;
        this.stringValue = "";
        this.booleanValue = booleanValue;
    }



    public String getPropertyKey() {
        return property;
    }

    public String getStringValue() {
        return stringValue;
    }

    public boolean getBooleanValue() {
        return booleanValue;
    }

    public String getGenericStringValue() {
        return (Objects.equals(stringValue, "") ? String.valueOf(booleanValue) : stringValue);
    }


    public String getEntry() {
        return property + " = " + getGenericStringValue();
    }


}
