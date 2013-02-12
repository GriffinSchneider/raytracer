import processing.core.PApplet;
import processing.core.PVector;

public abstract class Primitive extends Node {
	public Primitive(Vertex vertex_, PApplet parent_ ) {
		super( vertex_, parent_ );
	}
	
	public abstract boolean intersects( Ray r );
	
	public abstract void draw();
}
