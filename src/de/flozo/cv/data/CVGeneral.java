package de.flozo.cv.data;

import de.flozo.letter.data.PropertyMap;

import java.util.Map;

public class CVGeneral {

    private final boolean draftModeOn;
    private final boolean imagePlaceholderOn;
    private final boolean hyperlinksOn;

    private final String cvImageFile;
    private final String signatureImageFile;

    public CVGeneral(PropertyMap propertyMap) {
        Map<String, String> stringSubMap = propertyMap.stringSubMap();
        Map<String, Boolean> booleanSubMap = propertyMap.booleanSubMap();
        this.draftModeOn = booleanSubMap.get(CVGeneralProperty.DRAFT_MODE_ON.getPropertyKey());
        this.imagePlaceholderOn = booleanSubMap.get(CVGeneralProperty.IMAGE_PLACEHOLDER_ON.getPropertyKey());
        this.hyperlinksOn = booleanSubMap.get(CVGeneralProperty.HYPERLINKS_ON.getPropertyKey());
        this.cvImageFile = stringSubMap.get(CVGeneralProperty.CV_IMAGE_FILE.getPropertyKey());
        this.signatureImageFile = stringSubMap.get(CVGeneralProperty.CV_SIGNATURE_FILE.getPropertyKey());
    }

    public boolean isDraftModeOn() {
        return draftModeOn;
    }

    public boolean isImagePlaceholderOn() {
        return imagePlaceholderOn;
    }

    public boolean isHyperlinksOn() {
        return hyperlinksOn;
    }

    public String getCvImageFile() {
        return cvImageFile;
    }

    public String getSignatureImageFile() {
        return signatureImageFile;
    }

    @Override
    public String toString() {
        return "CVGeneral{" +
                "draftModeOn=" + draftModeOn +
                ", imagePlaceholderOn=" + imagePlaceholderOn +
                ", hyperlinksOn=" + hyperlinksOn +
                ", cvImageFile='" + cvImageFile + '\'' +
                ", signatureImageFile='" + signatureImageFile + '\'' +
                '}';
    }
}
