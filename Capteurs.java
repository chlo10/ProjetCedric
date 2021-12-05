package robot;

import  lejos.hardware.BrickFinder;
import  lejos.hardware.sensor.EV3ColorSensor ;
import  lejos.hardware.sensor.EV3TouchSensor ;
import  lejos.hardware.sensor.EV3UltrasonicSensor ;
import  lejos.robotics.SampleProvider ;

/**
 * Classe Capteur
 * Classe qui permet d'initialier les capteurs du robot
 * @author Groupe 6
 *
 */
public class Capteurs  {
	
	final private static String PORT_CAPTEUR_TOUCHER = "S1";
	final private static String PORT_CAPTEUR_ULTRASONS ="S2";
	final private static String PORT_CAPTEUR_COULEUR = "S3";

	private EV3TouchSensor capteurToucher ;	
	private EV3UltrasonicSensor capteurUltrasons ;
	private EV3ColorSensor capteurCouleur ;

	private float distance ;
	private int tactile ;
	private int idCouleur ;

	/**
	 * Constructeur de la classe qui permet d'initialiser les attributs d'instances
	 */
	public Capteurs() {
		capteurToucher= new EV3TouchSensor(BrickFinder.getDefault().getPort(Capteurs.PORT_CAPTEUR_TOUCHER));
		capteurUltrasons = new EV3UltrasonicSensor(BrickFinder.getDefault().getPort(Capteurs.PORT_CAPTEUR_ULTRASONS));
		capteurCouleur = new EV3ColorSensor(BrickFinder.getDefault().getPort(Capteurs.PORT_CAPTEUR_COULEUR));
		capteurCouleur.setFloodlight(true);
		capteurCouleur.setFloodlight(lejos.robotics.Color.WHITE);
	}

	/**
	 * Méthode qui permet d'allumer le capteur d'ultasons
	 */
	public void allumerCapteurUltrasons() { 
		capteurUltrasons.enable();
	}

	/**
	 * Méthode qui permet d'éteindre le capteur d'ultasons
	 */
	public void eteindreCapteurUltrasons() { 
		capteurUltrasons.disable();
	}
	
	/**
	 * Méthode qui retourne la distance entre le capteur d'ultasons du robot et un objet
	 * @return la distance en float (en cm?) 
	 */
	public float getDistance () {
		final SampleProvider sp = capteurUltrasons.getDistanceMode();
		distance = 0;
		float [] sample = new float[sp.sampleSize()]; 
		sp.fetchSample(sample, 0);
		distance = sample[0];	
		return distance;
	}

	/**
	 * Méthode qui permet de savoir si le capteur tactile est enfoncé
	 * @return 1 si le capteur touché est enfoncé et 0 sinon
	 */
	public int getTactile() { 
		final SampleProvider sp = capteurToucher.getTouchMode();
		float [] sample = new float[sp.sampleSize()];
		sp.fetchSample(sample, 0);
		tactile = (int) sample[0];
		return tactile;
	}
	
	/**
	 * Méthode qui retourne l'indice de la couleur captée par le capteur couleur
	 * @return l'indice 6 si la couleur captée  par le capteur couleur est le blanc
	 */
	public int getCouleur() { 
		idCouleur= capteurCouleur.getColorID();
		return idCouleur;
	}
}