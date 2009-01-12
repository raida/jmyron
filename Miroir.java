package jmyron;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import JMyron.JMyron;
import processing.core.PApplet;

public class Miroir extends PApplet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3438302230680279991L;
	
	JMyron m;
	
	List<Symetrie> symetrList = new ArrayList<Symetrie>();
	
	@Override
	public void setup() {
		int w = 320;
		int h = 240;
		frameRate(15);
		size(w, h);
		m = new JMyron();
		
		m.start(w, h);
		
		m.findGlobs(0);
		println("Myron " + m.version());		
		
		frame.setUndecorated(true);
		
		loadPixels();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[j * width + i] = 0;
			}
		}

	}

	@Override
	public void draw() {
		
		frame.setLocation(50, 50);
		
		if(Math.random() > 0.80){
			symetrList.add(new Symetrie(this));
		}
		
		Iterator<Symetrie> iterator = symetrList.iterator();
		while(iterator.hasNext()){
			Symetrie symetrie = iterator.next();
			symetrie.passerTour();
			if(symetrie.compteurVie <= 0)
				iterator.remove();
		}
		
		if(mousePressed){
		
		m.update();
		
		int[] img = m.image();
		
		loadPixels();
		
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				pixels[j * width + i] = img[j * width + i];
			}
		}
		
		}
		
		Iterator<Symetrie> iterator2 = symetrList.iterator();
		
		while(iterator2.hasNext()){
			Symetrie symetrie = iterator2.next();
			symetrie.appliquerSymetrie();
		}
		
		updatePixels();

	}

	@Override
	public void stop() {
		m.stop();
		super.stop();
	}
	
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PApplet.main(new String[] { "jmyron.Miroir" });
	}
	
	

}
