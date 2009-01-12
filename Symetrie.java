package jmyron;

import processing.core.PApplet;

public class Symetrie {

	boolean estHorizontale;

	int compteurVie;

	double origine;

	double vitesse;

	double acceleration;

	PApplet applet;

	public Symetrie(PApplet applet) {
		super();
		this.applet = applet;
		
		compteurVie = (int)(Math.random()*15) + 15;

		if (Math.random() > 0.5) {
			estHorizontale = true;
		} else {
			estHorizontale = false;
		}

		if (estHorizontale) {
			origine = Math.random() * applet.height;
		} else {
			origine = Math.random() * applet.width;
		}

		vitesse = Math.random() * 2. + 2;

		if (Math.random() > 0.5) {
			vitesse = -vitesse;
		}

		acceleration = Math.random() * 0.1 + 0.95;

	}

	public void passerTour() {
		compteurVie--;
		origine += vitesse;
		vitesse *= acceleration;

	}

	public void appliquerSymetrie() {
//		System.out.println("yep");
		
		int origineInt = (int) origine;

		if (estHorizontale && origineInt > 0 && origineInt <= applet.height / 2) {
			for (int y = 0; y < origineInt; y++) {

				int ny = 2 * origineInt - y;
				
				if(ny >= applet.height){
					ny = applet.height - 1;
				}

				for (int x = 0; x < applet.width-1; x++) {
					applet.pixels[y * applet.width + x] = applet.pixels[ny
							* applet.width + x ];
				}
			}

		} else if (estHorizontale && origineInt > applet.height / 2
				&& origineInt < applet.height-1) {
			for (int y = origineInt; y < applet.height-1; y++) {

				int ny = 2 * origineInt - y;

				for (int x = 0; x < applet.width-1; x++) {
					applet.pixels[y * applet.width + x] = applet.pixels[ny
							* applet.width + x];
				}
			}

		} else if (!estHorizontale && origineInt > 0
				&& origineInt <= applet.width / 2) {
			for (int x = 0; x < origineInt; x++) {

				int nx = 2 * origineInt - x;
				
				if(nx >= applet.width){
					nx = applet.width - 1;
				}

				for (int y = 0; y < applet.height; y++) {
					applet.pixels[y * applet.width + x] = applet.pixels[y
							* applet.width + nx];
				}
			}

		}else if (!estHorizontale && origineInt > applet.width / 2
				&& origineInt < applet.width) {
			for (int x = origineInt; x < applet.width; x++) {

				int nx = 2 * origineInt - x;

				for (int y = 0; y < applet.height; y++) {
					applet.pixels[y * applet.width + x] = applet.pixels[y
							* applet.width + nx];
				}
			}

		}
		
		
		

	}

}
