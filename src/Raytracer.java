import java.awt.Color;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import processing.core.PApplet;


public class Raytracer extends PApplet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_PATH = "res/test.txt";
	
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
				size( interpreter.width, interpreter.height, P3D );
				background( interpreter.background.getRGB() );
			}
			// No interpreter exit app
			else exit();
		}
		// No lines read from file exit app
		else exit();
	}
	
	private void createCamera() {
		float fov = radians(90);
		float cameraZ = ( height / 2 ) / tan( fov / 2 );
		float width = this.width;
		float height = this.height;
		
		int i = interpreter.cameraIndex;
		Vertex v = new Vertex(0, 0, -10, 0, 0, 0);
		if ( i >= 0 ) v = interpreter.getVertex( i );
		
		
		camera( v.x, v.y, v.z, v.x - v.dx, v.y - v.dy, v.z - v.dz, 0, 1, 0);
		perspective( fov, width / height, cameraZ / 10, cameraZ * 10 );
	}
	
	public void draw() {
		createCamera();
		
		// Before we deal with pixels
		loadPixels();  
		// Loop through every pixel
		interpreter.background = Color.RED;
		for (int i = 0; i < pixels.length; i++) {
		  pixels[i] = interpreter.background.getRGB();
		}
		// When we are finished dealing with pixels
		updatePixels();
		
		fill(0, 255, 0);
		Primitive[] primitives = interpreter.getPrimitives();
		
		
		
		for ( Primitive p : primitives ) {
			p.draw();
		}
	}
}
