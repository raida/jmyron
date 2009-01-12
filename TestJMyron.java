package jmyron;

import java.io.File;

import JMyron.JMyron;
import processing.core.PApplet;

public class TestJMyron extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1521683394141185533L;

	JMyron m;

	int buffer[][];

	int fond[];

	int tailleBuffer = 15;

	int tolerance = 30;

	boolean rechercheSihouette;

	int test = 0;

	@Override
	public void setup() {
		// TODO Auto-generated method stub
		int w = 320;
		int h = 240;
		frameRate(15);
		size(w, h);
		m = new JMyron();
		m.start(w, h);
		println("Myron " + m.version());
		buffer = new int[tailleBuffer][width * height];
		fond = new int[width * height];
		for (int i = 0; i < width * height; i++) {
			fond[i] = 0;
			for (int j = 0; j < tailleBuffer; j++) {
				buffer[j][i] = 0;
			}
		}
		frame.setUndecorated(true);
	}

	@Override
	public void draw() {
		frame.setLocation(50, 50);
		m.update();

		int[] img = m.image();

		if (!rechercheSihouette) {
			for (int i = 0; i < buffer.length - 1; i++) {
				buffer[i] = buffer[i + 1];
			}

			buffer[buffer.length - 1] = img;

			loadPixels();

			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					pixels[j * width + i] = img[j * width + i];
				}
			}

			updatePixels();
		} else {
			// Pour tous les points de la frame courante
			loadPixels();
			for (int i = 0; i < width * height; i++) {
				fond[i] = 0;
				if (appartientAuFond(img[i], i)) {
					for (int j = 0; j < buffer.length - 1; j++) {
						buffer[j][i] = buffer[j + 1][i];
					}
					buffer[buffer.length - 1][i] = img[i];
				} else {
					fond[i] = 1;
				}
			}
			// Silhouette en rouge, reste de l'extraction en noir, le fond en
			// blanc + algo de nettoyage du bruit
			for (int i = 0; i < width; i++) {
				for (int j = 0; j < height; j++) {
					if (fond[j * width + i] == 1) {
						int compteurVoisins = calculerVoisins(i, j);
						switch (compteurVoisins) {
						case 0:
							pixels[j * width + i] = color(255, 255, 255);
							fond[j * width + i] = 0;
							break;
						case 1:
							pixels[j * width + i] = color(255, 255, 255);
							fond[j * width + i] = 0;
							break;
						case 2:
							pixels[j * width + i] = color(255, 255, 255);
							fond[j * width + i] = 0;
							break;
						// case 8 : pixels[j*width+i] = color(random(0,255),
						// random(0,255), random(0,255));
						case 8:
							pixels[j * width + i] = img[j * width + i];
							break;
						default:
							pixels[j * width + i] = color(255, 0, 0);
							break;
						}
					} else {
						pixels[j * width + i] = color(255, 255, 255);
					}
				}
			}
			updatePixels();
		}
	}

	public void mousePressed() {
		rechercheSihouette = true;
	}

	public void stop() {
		m.stop();
		super.stop();
	}

	public boolean appartientAuFond(int couleurCourante, int numPixel) {
		float rouge = red(couleurCourante);
		float vert = green(couleurCourante);
		float bleu = blue(couleurCourante);

		float rougeMoyen = 0;
		float vertMoyen = 0;
		float bleuMoyen = 0;
		float compteurMoyenne = 0;
		for (int i = 0; i < tailleBuffer; i++) {
			compteurMoyenne = compteurMoyenne + (i + 1);

			float rougeCourant = red(buffer[i][numPixel]);
			rougeMoyen = rougeMoyen + rougeCourant * (i + 1);

			float vertCourant = green(buffer[i][numPixel]);
			vertMoyen = vertMoyen + vertCourant * (i + 1);

			float bleuCourant = blue(buffer[i][numPixel]);
			bleuMoyen = bleuMoyen + bleuCourant * (i + 1);
		}
		rougeMoyen = rougeMoyen / compteurMoyenne;
		vertMoyen = vertMoyen / compteurMoyenne;
		bleuMoyen = bleuMoyen / compteurMoyenne;

		float differenceTotale = 0;
		differenceTotale = abs(rougeMoyen - rouge) + abs(vertMoyen - vert)
				+ abs(bleuMoyen - bleu);
		if (differenceTotale < tolerance) {
			return true;
		}
		return false;
	}

	public int calculerVoisins(int i, int j) {
		int retour = 0;
		// i-1, j-1
		if (i - 1 >= 0 && j - 1 >= 0)
			if (fond[(j - 1) * width + i - 1] == 1)
				retour++;
		// i, j-1
		if (j - 1 >= 0)
			if (fond[(j - 1) * width + i] == 1)
				retour++;
		// i+1, j-1
		if (i + 1 <= width - 1 && j - 1 >= 0)
			if (fond[(j - 1) * width + i + 1] == 1)
				retour++;

		// i-1, j
		if (i - 1 >= 0)
			if (fond[j * width + i - 1] == 1)
				retour++;
		// i+1, j
		if (i + 1 <= width - 1)
			if (fond[j * width + i + 1] == 1)
				retour++;

		// i-1, j+1
		if (i - 1 >= 0 && j + 1 <= height - 1)
			if (fond[(j + 1) * width + i - 1] == 1)
				retour++;
		// i+1, j+1
		if (i + 1 <= width - 1 && j + 1 <= height - 1)
			if (fond[(j + 1) * width + i + 1] == 1)
				retour++;

		// i, j+1
		if (j + 1 <= height - 1)
			if (fond[(j + 1) * width + i] == 1)
				retour++;

		return retour;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		appendToJavaLibraryPath("/home/nico/outil/processing-0135/libraries/JMyron/library");
		PApplet.main(new String[] { "jmyron.TestJMyron" });
	}

	public static void appendToJavaLibraryPath(String newPath) {
		String javaLibraryPath = System.getProperty("java.library.path");
		System.setProperty("java.library.path", javaLibraryPath
				+ File.pathSeparatorChar + newPath);
		System.out.println(System.getProperty("java.library.path"));
	}

}
