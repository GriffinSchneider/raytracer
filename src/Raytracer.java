import java.io.FileNotFoundException;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;


public class Raytracer extends PApplet {
	private static final long serialVersionUID = 1L;
	private static final String FILE_PATH = "res/test.txt";

	private Camera camera;
	private RTInterpreter interpreter;

    public static void main(String[] args) {
        PApplet.main(new String[] { Raytracer.class.getName() });
    }
	
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
				camera = interpreter.camera;
			}
			// No interpreter exit app
			else exit();
		}
		// No lines read from file exit app
		else exit();
	}

	public void draw() {
		camera.active();

		// Before we deal with pixels
		loadPixels();

		float dist = 0.5f / tan( camera.fov / 2 );
		PVector d = PVector.mult( camera.forward, dist );
		// Loop through every pixel
		float x, y;
		int i;
		for ( y = 0; y < height; y++ ) {
			for ( x = 0; x < width; x++ ) {
				i = (int) ( y * width + x );

				PVector dir = PVector.add( PVector.add( d,
								PVector.mult( camera.up, ( 0.5f - y / ( height - 1 ) ) ) ),
								PVector.mult( camera.right, ( 0.5f -  x / ( width - 1 ) ) ) );
				
				Ray r = new Ray( camera.pos.get(), dir );
				for (Primitive p : interpreter.primitives) {
					PVector intersect = p.intersectionPoint(r);
					if (intersect == null) {
						pixels[i] = interpreter.background.getRGB();
					} else {
						pixels[i] = p.getColor(intersect, camera.pos, interpreter.lights).getRGB();
						break;
					}
				}
			}
		}
		// When we are finished dealing with pixels
		updatePixels();

		/*
		fill( 0, 255, 0 );

		for ( Primitive p : primitives ) {
			p.deug();
		}
		 */
	}
}
