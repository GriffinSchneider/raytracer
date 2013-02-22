import java.awt.Color;
import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public abstract class Primitive extends Node {
    public Color ambientMaterial;
	public Color ambientLight;
	public Color diffuseMaterial;
	public Color specularMaterial;
	public float specularShininess;
	
	public Primitive(Vertex vertex_, PApplet parent_ ) {
		super( vertex_, parent_ );
	}
	
	public abstract Float tIntersect(Ray r);

    public abstract Color getColor(PVector intersectionPoint, PVector viewPoint, ArrayList<Light> lights, ArrayList<Primitive> primitives);
    
    public Color getColor(PVector intersectionPoint, PVector viewPoint, PVector surfaceNormal, ArrayList<Light> lights, ArrayList<Primitive> primitives) {
		Color ambientComponent = new Color(
				(this.ambientLight.getRed() * this.ambientMaterial.getRed()) / ( 255.0f * 255.0f ),		
				(this.ambientLight.getGreen() * this.ambientMaterial.getGreen()) / ( 255.0f * 255.0f ), 
				(this.ambientLight.getBlue() * this.ambientMaterial.getBlue()) / ( 255.0f * 255.0f ));
		
		// Get direction to viewer
		PVector viewerDir = PVector.sub(intersectionPoint, viewPoint );
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

			float dot = PVector.dot(surfaceNormal, lightDir);
			if (dot > 0) {
				// 1 = no shadow
				float shadow = 1f;
				// Check for shadows
				for (Primitive p : primitives) {
					if (p != this) {
						Ray r = new Ray(intersectionPoint, lightDir);
						if (p.tIntersect(r) != null) {
							shadow = 0.5f;
						}
					}
				}
				
				Color lightIntensity = light.getIntensity(intersectionPoint);
				// Diffuse
				diffuseR += (shadow * lightIntensity.getRed() * this.diffuseMaterial.getRed() * dot) / (255.0f * 255.0f);
				diffuseG += (shadow * lightIntensity.getGreen() * this.diffuseMaterial.getGreen() * dot) / (255.0f * 255.0f);
				diffuseB += (shadow * lightIntensity.getBlue() * this.diffuseMaterial.getBlue() * dot) / (255.0f * 255.0f);

				if (shadow == 1f) {
					//Specular
					PVector reflection = PVector.sub(PVector.mult(surfaceNormal, 2 * dot), lightDir);
					reflection.normalize();
					float specDot = PVector.dot(viewerDir, reflection);
					float specCoefficient = PApplet.pow(specDot, this.specularShininess);
					specularR += (lightIntensity.getRed() * this.specularMaterial.getRed() * specCoefficient) / (255.0f * 255.0f);
					specularG += (lightIntensity.getGreen() * this.specularMaterial.getRed() * specCoefficient) / (255.0f * 255.0f);
					specularB += (lightIntensity.getBlue() * this.specularMaterial.getRed() * specCoefficient) / (255.0f * 255.0f);
				}
			}
		}
		Color diffuseComponent = new Color(
				Math.max(Math.min(diffuseR, 1.0f), 0), 
				Math.max(Math.min(diffuseG, 1.0f), 0), 
				Math.max(Math.min(diffuseB, 1.0f), 0));
		Color specularComponent = new Color(
				Math.max(Math.min(specularR, 1.0f), 0), 
				Math.max(Math.min(specularG, 1.0f), 0), 
				Math.max(Math.min(specularB, 1.0f), 0));
		Color ret = new Color(
				Math.min(ambientComponent.getRed() + diffuseComponent.getRed() + specularComponent.getRed(), 255),
				Math.min(ambientComponent.getGreen() + diffuseComponent.getGreen() + specularComponent.getGreen(), 255),
				Math.min(ambientComponent.getBlue() + diffuseComponent.getBlue() + specularComponent.getBlue(), 255));
		
		return ret;
    }
	
	public abstract void debug();
}
