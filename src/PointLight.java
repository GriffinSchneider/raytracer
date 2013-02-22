import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;


public class PointLight extends Light {

	public PointLight(Vertex vertex, Color color, PApplet parent) {
		super(vertex, color, parent);
	}

	@Override
	public Color getIntensity(PVector point) {
		// Use c=0.001 for the attenuation coefficient, since c=1 makes point lights
		// extremely dim.
		float distanceSquared = PApplet.pow(point.dist(this.pos), 2) * 0.001f;
		
		float r = this.color.getRed() / 255.0f;
		float g = this.color.getGreen() / 255.0f;
		float b = this.color.getBlue() / 255.0f;
		Color c = new Color(
				Math.min(r / distanceSquared, 1.0f),
				Math.min(g / distanceSquared, 1.0f),
				Math.min(b / distanceSquared, 1.0f));
		return c;
	}
	
	@Override
	public PVector getDirection(PVector point) {
		return PVector.sub(this.pos, point);
	}

}
