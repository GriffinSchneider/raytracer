import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;

public class DirectionalLight extends Light {
    private Color color;
    
    public DirectionalLight(Vertex vertex, Color color, PApplet parent) {
        super(vertex, parent);
        this.color = color;
    }

    @Override
    public Color getIntensity(PVector point) {
        return this.color;
    }
}
