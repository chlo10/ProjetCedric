package robot;

import lejos.hardware.Button;
import lejos.utility.Delay;

public class Strategie {

	/**
	 * Classe principale 
	 * Si le bouton du haut est pressé :
	 * Exécute l'homologation puis le second palet puis cherche les palets
	 * Si le bouton du bas est pressé :
	 * Exécute le programme qui permet au robot de trouver des palets
	 * @param args
	 * @author Groupe 6
	 */
	public static void main(String[] args) {
		Cedric c = new Cedric();
		Button.waitForAnyPress();
		if(Button.UP.isDown()) {
			c.stopCedric();
			c.homologation();
			c.stopCedric();
			c.deuxiemePalet();
			c.stopCedric();
			for(;c.getNbBut()<10;c.deposerPalet()) {
				c.stopCedric();
				while(c.recupererPalet(c.identifierPalet(c.distances(360)))!=true)
					;
			}

		}
		else if(Button.DOWN.isDown()) {
			for(;c.getNbBut()<10;c.deposerPalet()) {
				c.stopCedric();
				while(c.recupererPalet(c.identifierPalet(c.distances(360)))!=true)
					;
			}
		}
		if (c.getNbBut() >= 5) {
			System.out.println("Victoire!!");
			Delay.msDelay(15000);
		}
		else {
			System.out.println("Defaite :(");
			Delay.msDelay(5000);
		}
	}
}
