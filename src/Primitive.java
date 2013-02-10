import processing.core.PApplet;
import processing.core.PVector;

public abstract class Primitive {
	protected PApplet parent;
	protected Vertex vertex;
	public PVector pos;
	
	public Primitive(Vertex vertex_, PApplet parent_ ) {
		this.parent = parent_;
		this.vertex = vertex_;
		this.pos = new PVector();
		this.pos.x = this.vertex.x;
		this.pos.y = this.vertex.y;
		this.pos.z = this.vertex.z;
	}
	
	public abstract boolean intersects( Ray r );
	
	public abstract void draw();
}
