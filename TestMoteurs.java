package test;

import lejos.utility.Delay;
import robot.Moteurs;

/**
 * Classe TestMoteurs
 * Classe qui permet de tester les méthodes de la classe Moteurs
 * @author Groupe 6
 */
public class TestMoteurs {
	public static void main(String[] args) {
		Moteurs m = new Moteurs();

		//m.avancerDistance(200);
		//m.avancerDistance(-200);

		//m.avancer();

		//m.arreter();

		//m.tourner(90);
		//m.tourner(-90);

		//System.out.println(m.isMoving());

		//m.reorienter();

		//System.out.println(m.getCumulAngle());

		Delay.msDelay(5000);
	}
}