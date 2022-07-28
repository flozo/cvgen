package de.flozo.latex.assembly;

import de.flozo.latex.core.Delimiter;
import de.flozo.latex.core.GenericCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LayerList {

    List<String> layers;

    public LayerList(Builder builder) {
        this.layers = builder.layers;
    }


    private List<String> getDeclarationBlock() {
        List<String> codeLines = new ArrayList<>();
        for (String layer : layers) {
            if (!Objects.equals(layer, "main")) {
                codeLines.add(new GenericCommand.Builder("pgfdeclarelayer")
                        .body(layer)
                        .build().getInline());
            }
        }
        return codeLines;
    }

    private String getSetLayers() {
        return new GenericCommand.Builder("pgfsetlayers")
                .body(layers)
                .bodyDelimiter(Delimiter.COMMA)
                .build().getInline();
    }
    public List<String> getLayerCode() {
        List<String> codeLines = new ArrayList<>(getDeclarationBlock());
        codeLines.add(getSetLayers());
        return codeLines;
    }


    @Override
    public String toString() {
        return "LayerList{" +
                "layers=" + layers +
                '}';
    }



    public static class Builder {

        List<String> layers;

        public Builder(String... layers) {
            this(new ArrayList<>(List.of(layers)));
        }

        public Builder(List<String> layers) {
            this.layers = layers;
        }

        public Builder addLayer(String layer) {
            layers.add(layer);
            return this;
        }


        public LayerList build() {
            return new LayerList(this);
        }
    }
}
