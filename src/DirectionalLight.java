import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;

public class DirectionalLight extends Light {
    
    
    public DirectionalLight(Vertex vertex, Color color, PApplet parent) {
        super(vertex, color, parent);
    }

    @Override
    public Color getIntensity(PVector point) {
        return this.color;
    }

	@Override
	public PVector getDirection(PVector point) {
		return new PVector(this.vertex.dx, this.vertex.dy, this.vertex.dz);
	}
}
