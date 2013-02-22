import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Primitive extends Node {
    public Color ambientMaterial;
	public Color ambientLight;
	public Color diffuseMaterial;
	public Color specularMaterial;
	public float specularShininess;
	
	public Primitive(Vertex vertex_, PApplet parent_ ) {
		super( vertex_, parent_ );
	}
	
	public abstract Float tIntersect(Ray r);

    public abstract Color getColor(PVector intersectionPoint, PVector viewPoint, ArrayList<Light> lights);
	
	public abstract void debug();
}
