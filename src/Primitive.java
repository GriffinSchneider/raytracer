import java.awt.Color;

import processing.core.PApplet;

public abstract class Primitive extends Node {
	public Color amb;
	public Color diff;
	
	public Primitive(Vertex vertex_, PApplet parent_ ) {
		super( vertex_, parent_ );
	}
	
	public abstract boolean intersects( Ray r );
	
	public abstract void debug();
}
