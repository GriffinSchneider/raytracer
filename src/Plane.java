import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;


public class Plane extends Primitive {

	private PVector normal;		
	private float depth;
	public Plane(Vertex vertex_, PApplet parent_) {
		super(vertex_, parent_);
		this.normal = new PVector(vertex_.dx, vertex_.dy, vertex_.dz);
		this.normal.normalize();
		this.depth = vertex_.z;
	}

	@Override
	public Float tIntersect(Ray r) {
		
		float dot = this.normal.dot(r.d);
		if (dot == 0) return null;
		
		float dot2 = -(this.normal.dot(r.o) + this.depth);
		
		float t = dot2 / dot;
		if (t < 0) return null;
		
		return new Float(t);
	}

	@Override
	public Color getColor(PVector intersectionPoint, PVector viewPoint,	ArrayList<Light> lights) {
		return new Color(255, 255, 255);
	}

	@Override
	public void debug() {}

}
