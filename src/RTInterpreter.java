import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;

public class RTInterpreter {
	public ArrayList<Primitive> primitives;
    public ArrayList<Light> lights;

	public int width;
	public int height;
	public Color background;
	public Camera camera;
	
	private PApplet parent;

	public RTInterpreter( ArrayList<String> lines, PApplet parent_ ) {
		this.parent = parent_;
		this.primitives = new ArrayList<Primitive>();
		this.lights = new ArrayList<Light>();
		this.width = 256;
		this.height = 256;
		this.background = Color.WHITE;
	
		ArrayList<Vertex> vertices = new ArrayList<Vertex>();
		Vertex cameraVertex = new Vertex( 0, 0, -10, 0, 0, 0 );

		// Parse vertices first to make everything easier
		ArrayList<String> restOfLines = new ArrayList<String>();
		for ( String line : lines ) {
			String[] args = line.split( " " );
			String first = args[0];
			
			if ( first.equals( "vv" ) ) {
				float x = Float.parseFloat( args[1] );
				float y = Float.parseFloat( args[2] );
				float z = Float.parseFloat( args[3] );
				float dx = Float.parseFloat( args[4] );
				float dy = Float.parseFloat( args[5] );
				float dz = Float.parseFloat( args[6] );
			    vertices.add( new Vertex( x, y, z, dx, dy, dz ) );
			}
			else
				restOfLines.add(line);
		}
		
		Color ambientMaterial = new Color( 0.2f, 0.2f, 0.2f );
		Color ambientLight = new Color( 0.2f, 0.2f, 0.2f );
		Color diffuseMaterial = new Color( 1, 1, 1 );
		//Vector4f spec = new Vector4f( 1, 1, 1, 64 );
		//Vector4f trans = new Vector4f( 0, 0, 0, 1 );
		
		for ( String line : restOfLines ) {
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
            // Ambient light
            else if ( first.equals( "al" ) ) {
                float r = Float.parseFloat( args[1] );
				float g = Float.parseFloat( args[2] );
				float b = Float.parseFloat( args[3] );
				ambientLight = new Color(r, g, b);
            }
            // Directional light
            else if ( first.equals( "dl") ) {
                int index = Integer.parseInt( args[1] );
                float r = Float.parseFloat( args[2] );
				float g = Float.parseFloat( args[3] );
				float b = Float.parseFloat( args[4] );
				Color color = new Color(r, g, b);
                this.lights.add(new DirectionalLight(vertices.get(index), color, parent));
            }
            // Point light
            else if ( first.equals( "pl") ) {
                int index = Integer.parseInt( args[1] );
                float r = Float.parseFloat( args[2] );
				float g = Float.parseFloat( args[3] );
				float b = Float.parseFloat( args[4] );
				Color color = new Color(r, g, b);
                this.lights.add(new PointLight(vertices.get(index), color, parent));
            }
			// Sphere
			else if ( first.equals( "ss" ) ) {
				float radius = -1;
				int index = Integer.parseInt( args[1] );
				Vertex v = vertices.get( index );
				// Check if a radius was specified
				if ( args.length == 3 )
					radius = Float.parseFloat( args[2] );
				
				Sphere s = new Sphere( v, radius, parent );
				s.ambientMaterial = ambientMaterial;
                s.ambientLight = ambientLight;
				s.diffuseMaterial = diffuseMaterial;
				this.primitives.add( s );
			}
			// Camera
			else if ( first.equals( "cc" ) ) {
				int index = Integer.parseInt( args[1] );
				cameraVertex = vertices.get( index );
			}
			else if ( first.equals( "am" ) 
					|| first.equals( "dm" )
					|| first.equals( "sm" )
					|| first.equals( "al" )
					|| first.equals( "pl" )
					|| first.equals( "dl" )
					|| first.equals( "back" ) ) {
				int index = 1;
				if (first.equals( "pl" ) || first.equals( "dl" )) {
					index++;
				}
				float r = Float.parseFloat( args[index++] );
				float g = Float.parseFloat( args[index++] );
				float b = Float.parseFloat( args[index++] );
				Color color = new Color(r, g, b);
				
				if ( first.equals( "am" ) ) {
					ambientMaterial = color;
			    } if ( first.equals( "dm" ) ) {
			    	diffuseMaterial = color;
                }
				
				// Set the background color
				if (first.equals( "back" ))  {
					this.background = color;
                }
			}
			else {
				if (!first.equals("")) {
					System.out.println("Warning: Operation " + first + " is not supported.");
				}
			}
		}
		
		this.camera = new Camera( cameraVertex, parent );
	}
}
