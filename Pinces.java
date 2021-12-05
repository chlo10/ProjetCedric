package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * Classe Pinces
 * Classe qui permet d'initialiser les différents attributs liés aux pinces et les méthodes associées
 * @author Groupe 6
 */

public class Pinces {

	private RegulatedMotor pinces ;

	/**
	 * Déclaration de l'attribut pinces
	 * Initialisation du moteur affecté aux pinces
	 */
	public Pinces() {
		pinces= new  EV3LargeRegulatedMotor (MotorPort.C);
	}

	/**
	 * Méthode qui permet d'ouvrir les pinces d'un certain ecart
	 * @param ecart
	 */
	public  void ouvrirPinces(int ecart) {
		pinces.rotate(ecart*360); 
	}

	/**
	 * Méthode qui permet de fermer les pinces d'un certain écart
	 * @param ecart
	 */
	public void fermerPinces(int ecart ) {
		pinces.rotate(-ecart*360); 
	}
}
