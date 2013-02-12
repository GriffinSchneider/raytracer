import processing.core.PVector;


public class Vertex {
	public int x, y, z, dx, dy, dz;
	public Vertex( int x_, int y_, int z_, int dx_, int dy_, int dz_ ) {
		this.x = x_;
		this.y = y_;
		this.z = z_;
		this.dx = dx_;
		this.dy = dy_;
		this.dz = dz_;
	}
	
	public PVector getD() {
		return new PVector( this.dx, this.dy, this.dz );
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append(this.x);
		sb.append(", ");
		sb.append(this.y);
		sb.append(", ");
		sb.append(this.z);
		sb.append(", ");
		sb.append(this.dx);
		sb.append(", ");
		sb.append(this.dy);
		sb.append(", ");
		sb.append(this.dz);
		sb.append(')');
		return sb.toString();
	}
}
