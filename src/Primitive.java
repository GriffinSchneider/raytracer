import processing.core.PApplet;

public abstract class Primitive extends Node {
	public Primitive(Vertex vertex_, PApplet parent_ ) {
		super( vertex_, parent_ );
	}
	
	public abstract boolean intersects( Ray r );
	
	public abstract void draw();
}
