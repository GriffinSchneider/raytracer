import processing.core.PApplet;
import processing.core.PVector;

public abstract class Node {
	protected PApplet parent;
	protected Vertex vertex;
	public PVector pos;
	
	public Node( Vertex vertex_, PApplet parent_ ) {
		this.parent = parent_;
		this.vertex = vertex_;
		this.pos = new PVector( this.vertex.x, this.vertex.y, this.vertex.z );
	}
}
