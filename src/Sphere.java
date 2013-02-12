import processing.core.PApplet;
import processing.core.PVector;

public class Sphere extends Primitive {
	
	public float radius;
	public Sphere( Vertex vertex_, float radius_, PApplet parent_ ) {
		super( vertex_, parent_ );
		// If the radius is less than zero it was not set
		if ( radius_ < 0 ) 
			this.radius = PApplet.mag( vertex.dx, vertex.dy, vertex.dz );
		else
			this.radius = radius_;
	}
	
	@Override
	public void draw() {
		parent.noStroke();
		parent.translate( this.pos.x, this.pos.y, this.pos.z );
		parent.sphere( this.radius );
	}

	@Override
	public boolean intersects(Ray r) {
		PVector diff = PVector.sub( r.o, this.pos );
		
		float a = PVector.dot( r.d, r.d );
		float b = 2 * PVector.dot( r.d, diff );
		float c = PVector.dot( diff, diff ) - this.radius * this.radius;
		
		float d = b * b - 4 * a * c;
		// d is negative so there is no root
		if ( d < 0 )
			return false;
		
		float dSqrt = PApplet.sqrt( d );
		float s;
		if ( b < 0 )
			s = ( -b - dSqrt ) / 2;
		else
			s = ( -b + dSqrt ) / 2;
		
		float t0 = Math.min(s / a, c / s);
		float t1 = Math.max(s / a, c / s);
		
		// If t1 is less than zero the ray misses the sphere
		if ( t1 < 0 )
			return false;
		
		@SuppressWarnings("unused")
		float dist;
		if ( t0 < 0 )
			dist = t1;
		else
			dist = t0;
		
		return true;
	}
}
