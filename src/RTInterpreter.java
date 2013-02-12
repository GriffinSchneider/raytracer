import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

public class RTInterpreter {
	private ArrayList<Vertex> vertices;
	private ArrayList<SphereRef> spheres;
	
	
	public int width;
	public int height;
	public Color background;
	
	public int cameraIndex;
	
	private PApplet parent;
	
	public Vertex[] getVertices() {
		return this.vertices.toArray( new Vertex[this.vertices.size()] );
	}
	
	public Vertex getVertex(int i) {
		return this.vertices.get(i);
	}
	
	public Primitive[] getPrimitives() {
		int size = this.spheres.size();
		int i = 0;
		Primitive[] primitives = new Primitive[size];
		
		int j;
		for ( j = 0; j < this.spheres.size(); j++ ) {
			SphereRef sr = this.spheres.get( j );
			Vertex v = getVertex( sr.vertexIndex );
			primitives[i++] = new Sphere( v, sr.radius, parent );
		}
		
		return primitives;
	}
	
	public RTInterpreter( ArrayList<String> lines, PApplet parent_ ) {
		this.parent = parent_;
		this.vertices = new ArrayList<Vertex>();
		this.spheres = new ArrayList<SphereRef>();
		this.width = 256;
		this.height = 256;
		this.background = Color.WHITE;
		this.cameraIndex = -1;

		for ( String line : lines ) {
			String[] args = line.split( " " );
			String first = args[0];
			// Comment
			if ( first.equals( "##" ) ) {
				
			}
			// Size of scene
			else if ( first.equals( "ir" ) ) {
				this.width = Integer.parseInt( args[1] );
				this.height = Integer.parseInt( args[2] );
			}
			// Sphere
			else if ( first.equals( "ss" ) ) {
				float radius = -1;
				int index = Integer.parseInt( args[1] );
				// Check if a radius was specified
				if ( args.length == 3 )
					radius = Float.parseFloat( args[2] );
				this.spheres.add( new SphereRef(index, radius) );
			}
			// Camera
			else if ( first.equals( "cc" ) )
				this.cameraIndex = Integer.parseInt( args[1] );
			// Parse vertex
			else if ( first.equals( "vv" ) ) {
				int x = Integer.parseInt( args[1] );
				int y = Integer.parseInt( args[2] );
				int z = Integer.parseInt( args[3] );
				int dx = Integer.parseInt( args[4] );
				int dy = Integer.parseInt( args[5] );
				int dz = Integer.parseInt( args[6] );
				this.vertices.add( new Vertex( x, y, z, dx, dy, dz ) );
			}
			else if ( first.equals( "am" ) 
					|| first.equals( "dm" )
					|| first.equals( "sm" )
					|| first.equals( "al" )
					|| first.equals( "pl" )
					|| first.equals( "dl" )
					|| first.equals( "back" ) ) {
				int index = 1;
				if (first.equals( "pl" ) 
						|| first.equals( "dl" )) {
					index++;
				}
				float r = Float.parseFloat( args[index++] );
				float g = Float.parseFloat( args[index++] );
				float b = Float.parseFloat( args[index++] );
				Color color = new Color(r, g, b);
				
				// Set the background color
				if (first.equals( "back" )) 
					this.background = color;
			}
			else {
				System.out.println("Warning: Operation " + first + " is not supported.");
			}
		}
	}
}

class SphereRef {
	int vertexIndex;
	float radius;
	
	public SphereRef(int i, float r) {
		this.vertexIndex = i;
		this.radius = r;
	}
}
