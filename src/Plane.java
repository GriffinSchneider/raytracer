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
	public Color getColor(PVector intersectionPoint, PVector viewPoint,	ArrayList<Light> lights, ArrayList<Primitive> primitives) {
		// Inefficient hack: re-calculate this dot product to determine if we need to 
		// reverse the normal (because the ray from the camera is intersecting the
		// "back" of the plane).
		float dot = this.normal.dot(PVector.sub(intersectionPoint, viewPoint));
		
		if (dot > 0) {
			return this.getColor(intersectionPoint, viewPoint, PVector.mult(this.normal, -1), lights, primitives);
		} else {
			return this.getColor(intersectionPoint, viewPoint, this.normal, lights, primitives);
		}
		
	}

	@Override
	public void debug() {}

}
