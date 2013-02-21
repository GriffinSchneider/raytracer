import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Light extends Node{
	Color color;
	
    public Light(Vertex vertex, Color color, PApplet parent) {
        super(vertex, parent);
        this.color = color;
    }

    public abstract Color getIntensity(PVector point);
    public abstract PVector getDirection(PVector point);
}
