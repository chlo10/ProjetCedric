package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * Classe Pinces
 * Classe qui permet d'initialiser les diff�rents attributs li�s aux pinces et les m�thodes associ�es
 * @author Groupe 6
 */

public class Pinces {

	private RegulatedMotor pinces ;

	/**
	 * D�claration de l'attribut pinces
	 * Initialisation du moteur affect� aux pinces
	 */
	public Pinces() {
		pinces= new  EV3LargeRegulatedMotor (MotorPort.C);
	}

	/**
	 * M�thode qui permet d'ouvrir les pinces d'un certain ecart
	 * @param ecart
	 */
	public  void ouvrirPinces(int ecart) {
		pinces.rotate(ecart*360); 
	}

	/**
	 * M�thode qui permet de fermer les pinces d'un certain �cart
	 * @param ecart
	 */
	public void fermerPinces(int ecart ) {
		pinces.rotate(-ecart*360); 
	}
}
