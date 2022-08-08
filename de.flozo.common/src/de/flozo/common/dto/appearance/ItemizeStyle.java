package de.flozo.common.dto.appearance;

public class ItemizeStyle {

    private int id;
    private String name;
    private Length topSep;
    private Length leftMargin;
    private Length labelSep;
    private Length itemIndent;
    private Length itemSep;
    private ItemizeLabel label;

    public ItemizeStyle(int id, String name, Length topSep, Length leftMargin, Length labelSep, Length itemIndent, Length itemSep, ItemizeLabel label) {
        this.id = id;
        this.name = name;
        this.topSep = topSep;
        this.leftMargin = leftMargin;
        this.labelSep = labelSep;
        this.itemIndent = itemIndent;
        this.itemSep = itemSep;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Length getTopSep() {
        return topSep;
    }

    public void setTopSep(Length topSep) {
        this.topSep = topSep;
    }

    public Length getLeftMargin() {
        return leftMargin;
    }

    public void setLeftMargin(Length leftMargin) {
        this.leftMargin = leftMargin;
    }

    public Length getLabelSep() {
        return labelSep;
    }

    public void setLabelSep(Length labelSep) {
        this.labelSep = labelSep;
    }

    public Length getItemIndent() {
        return itemIndent;
    }

    public void setItemIndent(Length itemIndent) {
        this.itemIndent = itemIndent;
    }

    public Length getItemSep() {
        return itemSep;
    }

    public void setItemSep(Length itemSep) {
        this.itemSep = itemSep;
    }

    public ItemizeLabel getLabel() {
        return label;
    }

    public void setLabel(ItemizeLabel label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "ItemizeStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", topSep=" + topSep +
                ", leftMargin=" + leftMargin +
                ", labelSep=" + labelSep +
                ", itemIndent=" + itemIndent +
                ", itemSep=" + itemSep +
                ", label=" + label +
                '}';
    }
}
