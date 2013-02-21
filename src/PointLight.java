import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;


public class PointLight extends Light {

	public PointLight(Vertex vertex, Color color, PApplet parent) {
		super(vertex, color, parent);
	}

	@Override
	public Color getIntensity(PVector point) {
		float distanceSquared = (float) Math.pow(point.dist(this.pos), 2);
		Color c = new Color(
				(float)this.color.getRed() / distanceSquared,
				(float)this.color.getGreen() / distanceSquared,
				(float)this.color.getBlue()  / distanceSquared);
//		System.out.println("d2: " + distanceSquared + " c: " + c + " point: " + point);
		return c;
	}
	
	@Override
	public PVector getDirection(PVector point) {
		return PVector.sub(point, this.pos);
	}

}
