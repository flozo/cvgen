package de.flozo.common.dto.appearance;

public class LineStyle {

    private int id;
    private String name;
    private LineWidth lineWidth;
    private LineCap lineCap;
    private LineJoin lineJoin;
    private DashPattern dashPattern;
    private Color color;
    private PredefinedOpacity opacity;

    public LineStyle(int id, String name, LineWidth lineWidth, LineCap lineCap, LineJoin lineJoin, DashPattern dashPattern, Color color, PredefinedOpacity opacity) {
        this.id = id;
        this.name = name;
        this.lineWidth = lineWidth;
        this.lineCap = lineCap;
        this.lineJoin = lineJoin;
        this.dashPattern = dashPattern;
        this.color = color;
        this.opacity = opacity;
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

    public LineWidth getLineWidth() {
        return lineWidth;
    }

    public void setLineWidth(LineWidth lineWidth) {
        this.lineWidth = lineWidth;
    }

    public LineCap getLineCap() {
        return lineCap;
    }

    public void setLineCap(LineCap lineCap) {
        this.lineCap = lineCap;
    }

    public LineJoin getLineJoin() {
        return lineJoin;
    }

    public void setLineJoin(LineJoin lineJoin) {
        this.lineJoin = lineJoin;
    }

    public DashPattern getDashPattern() {
        return dashPattern;
    }

    public void setDashPattern(DashPattern dashPattern) {
        this.dashPattern = dashPattern;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public PredefinedOpacity getOpacity() {
        return opacity;
    }

    public void setOpacity(PredefinedOpacity opacity) {
        this.opacity = opacity;
    }

    @Override
    public String toString() {
        return "LineStyle{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lineWidth=" + lineWidth +
                ", lineCap=" + lineCap +
                ", lineJoin=" + lineJoin +
                ", dashPattern=" + dashPattern +
                ", color=" + color +
                ", opacity=" + opacity +
                '}';
    }
}
