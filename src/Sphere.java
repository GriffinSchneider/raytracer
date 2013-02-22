import java.awt.Color;
import java.util.ArrayList;

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
	public Color getColor(PVector intersectionPoint, PVector viewPoint, ArrayList<Light> lights) {
		Color ambientComponent = new Color(
				this.ambientLight.getRed() * this.ambientMaterial.getRed() / (255.0f*255.0f),		
				this.ambientLight.getGreen() * this.ambientMaterial.getGreen() / (255.0f*255.0f), 
				this.ambientLight.getBlue()	* this.ambientMaterial.getBlue() / (255.0f*255.0f));

		// Get surface normal
		PVector normal = PVector.sub(intersectionPoint, this.pos);
		normal.normalize();
		
		// Get direction to viewer
		PVector viewerDir = PVector.sub(intersectionPoint, viewPoint);
		viewerDir.normalize();

		// Calculate the diffuse and specular components
		float diffuseR = 0f;
		float diffuseG = 0f;
		float diffuseB = 0f;
		float specularR = 0f;
		float specularG = 0f;
		float specularB = 0f;
		for (Light light : lights) {
			// Get direction to light 
			PVector lightDir = light.getDirection(intersectionPoint);
			lightDir.normalize();
			
			float dot = PVector.dot(normal, lightDir);
			if (dot > 0) {
				
				Color lightIntensity = light.getIntensity(intersectionPoint);
				
				// Diffuse
				diffuseR += lightIntensity.getRed() * this.diffuseMaterial.getRed() * dot / (255.0f * 255.0f);
				diffuseG += lightIntensity.getGreen() * this.diffuseMaterial.getGreen() * dot / (255.0f * 255.0f);
				diffuseB += lightIntensity.getBlue() * this.diffuseMaterial.getBlue() * dot / (255.0f * 255.0f);
				
				//Specular
				PVector reflection = PVector.sub(PVector.mult(normal, 2 * dot), lightDir);
				reflection.normalize();
				float specDot = PVector.dot(viewerDir, reflection);
				float specCoefficient = (float) Math.pow(specDot, this.specularShininess);
				specularR += lightIntensity.getRed() * this.specularMaterial.getRed() * specCoefficient / (255.0f * 255.0f);
				specularG += lightIntensity.getGreen() * this.specularMaterial.getRed() * specCoefficient / (255.0f * 255.0f);
				specularB += lightIntensity.getBlue() * this.specularMaterial.getRed() * specCoefficient / (255.0f * 255.0f);
			}
		}
		Color diffuseComponent = new Color(
				Math.max(Math.min(diffuseR,1.0f), 0), 
				Math.max(Math.min(diffuseG,1.0f), 0), 
				Math.max(Math.min(diffuseB,1.0f), 0));
		Color specularComponent = new Color(
				Math.max(Math.min(specularR,1.0f), 0), 
				Math.max(Math.min(specularG,1.0f), 0), 
				Math.max(Math.min(specularB,1.0f), 0));
		Color ret = new Color(
				Math.min(ambientComponent.getRed() + diffuseComponent.getRed() + specularComponent.getRed(), 255),
				Math.min(ambientComponent.getGreen() + diffuseComponent.getGreen() + specularComponent.getGreen(), 255),
				Math.min(ambientComponent.getBlue() + diffuseComponent.getBlue() + specularComponent.getBlue(), 255));
		return ret;
	}
		
	@Override
	public void debug() {
		parent.noStroke();
		parent.translate( this.pos.x, this.pos.y, this.pos.z );
		parent.sphere( this.radius );
	}
		
	@Override
	public Float tIntersect(Ray r) {
		// Transform the ray into object space
		Ray transformedRay = new Ray(PVector.sub(r.o, this.pos), r.d);
		
		float a = PVector.dot(transformedRay.d, transformedRay.d);
		float b = 2 * PVector.dot(transformedRay.d, transformedRay.o);
		float c = PVector.dot(transformedRay.o, transformedRay.o) - this.radius*this.radius;
		
		float d = b * b - 4 * a * c;
		// d is negative so there is no root
		if (d < 0) {
			return null;
		}
		
		float dSqrt = PApplet.sqrt(d);
		float s;
		if ( b < 0 )
			s = ( -b - dSqrt ) / 2;
		else
			s = ( -b + dSqrt ) / 2;
		
		float t0 = Math.min(s / a, c / s);
		float t1 = Math.max(s / a, c / s);

		// If t1 is less than zero the ray misses the sphere
		if (t1 < 0) {
			return null;
		}

		float dist;
		if ( t0 < 0 )
			dist = t1;
		else
			dist = t0;
		
		return new Float(dist);
	}
}
