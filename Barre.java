package jmyron;

import processing.core.PApplet;

public class Barre {
	
	int compteurVie;
	
	double origine;
	
	double fin;
	
	double vitesse;
	
	double acceleration;
	
	PApplet applet;

	public Barre(PApplet applet) {
		super();
		this.applet = applet;
		compteurVie = (int)(Math.random()*15) + 15;
		origine = Math.random() * applet.height;
		fin = origine + Math.random()*100.;
		vitesse = Math.random()*2. + 2;
		
		if((int)(Math.random()*10) % 2 == 0)
			vitesse = -vitesse;
		
		acceleration = Math.random()*0.1 + 0.95;
	}
	
	public void passerTour(){
		compteurVie--;
		origine += vitesse;
		fin += vitesse;
		vitesse *= acceleration;
	}

	public int getCompteurVie() {
		return compteurVie;
	}

	public void setCompteurVie(int compteurVie) {
		this.compteurVie = compteurVie;
	}

	public double getFin() {
		return fin;
	}

	public void setFin(double fin) {
		this.fin = fin;
	}

	public double getOrigine() {
		return origine;
	}

	public void setOrigine(double origine) {
		this.origine = origine;
	}

	public double getVitesse() {
		return vitesse;
	}

	public void setVitesse(double vitesse) {
		this.vitesse = vitesse;
	}

	public double getAcceleration() {
		return acceleration;
	}

	public void setAcceleration(double acceleration) {
		this.acceleration = acceleration;
	}

	public PApplet getApplet() {
		return applet;
	}

	public void setApplet(PApplet applet) {
		this.applet = applet;
	}
	

}
