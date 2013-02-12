import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;


public class Raytracer extends PApplet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_PATH = "res/test.txt";
	
	private Camera camera;
	private RTInterpreter interpreter;
	
	public void setup() {
		noLoop();
		
		ArrayList<String> lines = null;
		try {
			FileParser parser = new FileParser( FILE_PATH );
			lines = parser.returnLines();
		} catch ( FileNotFoundException e ) {
			e.printStackTrace();
			exit();
		}
		if ( lines != null ) { 
			interpreter = new RTInterpreter( lines, this );
			if ( interpreter != null ) {
				size( interpreter.width, interpreter.height, OPENGL );
				background( interpreter.background.getRGB() );
			}
			// No interpreter exit app
			else exit();
		}
		// No lines read from file exit app
		else exit();
		
		int i = interpreter.cameraIndex;
		// Camera position and look position
		Vertex v = new Vertex( 0, 0, -10, 0, 0, 0 );
		if ( i >= 0 ) v = interpreter.getVertex( i );
		println("Camera vertex: " + v);
		camera = new Camera(v, this);
	}
	
	public void draw() {
		camera.active();
		
		// Before we deal with pixels
		loadPixels();
		
		float dist = camera.pos.dist(camera.center);
		float pdist = 2 * dist * tan( radians( 30 ) );
		PVector p0 = camera.pos;
		PVector p1 = PVector.sub( PVector.sub( PVector.add( p0, PVector.mult( camera.forward, dist ) ),
				PVector.mult( camera.right, pdist / 2 ) ),
				PVector.mult( camera.up, pdist / 2 ) );
		
		Primitive[] primitives = interpreter.getPrimitives();
		
		// Loop through every pixel
		float x, y;
		int i;
		for ( y = 0; y < height; y++ ) {
			for ( x = 0; x < width; x++ ) {
				i = (int) (y * width + x);
				
				PVector p = PVector.add( PVector.add( p1, 
						PVector.mult( camera.right, pdist * ( x / width + 0.5f )  ) ),
						PVector.mult( camera.up, pdist * ( y / height + 0.5f )  ) );
				//println(p);
				PVector v = PVector.div( PVector.sub(p, p0), PVector.sub(p, p0).mag() );

				Ray r = new Ray(camera.pos.get(), v);
				for ( Primitive prim : primitives ) {
					if ( prim.intersects( r ) )
						pixels[i] = Color.WHITE.getRGB();
					else
						pixels[i] = interpreter.background.getRGB();
				}
			}
		}
		// When we are finished dealing with pixels
		updatePixels();
		
		/*
		fill( 0, 255, 0 );
		Primitive[] primitives = interpreter.getPrimitives();
		for ( Primitive p : primitives ) {
			p.draw();
		}
		*/
	}
}
