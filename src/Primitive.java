import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Primitive extends Node {
    public Color ambientMaterial;
	public Color ambientLight;
	public Color diffuseMaterial;
	
	public Primitive(Vertex vertex_, PApplet parent_ ) {
		super( vertex_, parent_ );
	}
	
	public abstract PVector intersectionPoint(Ray r);

    public abstract Color getColor(PVector intersectionPoint, ArrayList<Light> lights);
	
	public abstract void debug();
}
