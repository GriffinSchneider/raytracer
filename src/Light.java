import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Light extends Node{
    public Light(Vertex vertex, PApplet parent) {
        super(vertex, parent);
    }

    public abstract Color getIntensity(PVector point);
}
