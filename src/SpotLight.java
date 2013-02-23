import java.awt.Color;

import processing.core.PApplet;
import processing.core.PVector;


public class SpotLight extends Light {
	//private static final float ATTENUATION = 0.01f;
	
	private float beamAngle = PApplet.radians(90);
	private float fallOffAngle = PApplet.radians(180);
	
	public SpotLight(Vertex vertex, Color color, PApplet parent) {
		super(vertex, color, parent);
	}

	@Override
	public Color getIntensity(PVector point) {
		PVector toSurface = PVector.sub(point, this.pos);
		toSurface.normalize();
		
		float angle = PApplet.acos(toSurface.dot(this.getDirection(point)));
		if (angle > this.beamAngle + this.fallOffAngle) {
			return new Color(0);
		}
		if (angle > this.beamAngle) {
			
			float portion = 1 - ((angle - this.beamAngle) / this.fallOffAngle);
			return new Color(
					Math.max(0, (this.color.getRed() / 255f) * portion),
					Math.max(0, (this.color.getGreen() / 255f) * portion),
					Math.max(0, (this.color.getBlue() / 255f) * portion));
		}
		else
			return this.color;
	}
	
	@Override
	public PVector getDirection(PVector point) {
		return this.vertex.getDirection();
	}

}
