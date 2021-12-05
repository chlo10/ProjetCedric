package test;

import lejos.utility.Delay;
import robot.Capteurs;
import robot.Cedric;
import robot.Moteurs;

public class TestFonctionnel {
	public static void main(String[] args){
		Cedric cedric = new Cedric();
		Moteurs moteur = new Moteurs();
		Capteurs capteur = new Capteurs();
		cedric.homologation(); //test de l�homologation
		System.out.println("homologation ok"); //confirme que l�homologation est pass�e
		Delay.msDelay(5000);
		cedric.stopCedric();
		if (!moteur.isMoving())
			System.out.println("arr�t sur commande ok"); //confirme que le robot s�est arr�t�
		Delay.msDelay(5000);
		float dist = cedric.identifierPalet(cedric.distances(360));
		cedric.recupererPalet (cedric.identifierPalet(cedric.distances(360))); //test de la recherche de palet en situation �mur�
		if (dist > capteur.getDistance())
			System.out.println("detection du mur ok"); //confirme que le robot a bien d�tect� le mur
		Delay.msDelay(5000);
		cedric.recupererPalet (cedric.identifierPalet(cedric.distances(360))); //test de la recherche de palet en situation �palet�
		System.out.println("aller chercher le palet ok"); //confirme que le palet est r�cup�r�
		Delay.msDelay(5000);
		cedric.stopCedric();
		if (!moteur.isMoving())
			System.out.println("arr�t sur commande ok"); //confirme que le robot s�est arr�t�
			Delay.msDelay(5000);
			cedric.deposerPalet(); //test que le robot depose le palet
			System.out.println("deposer le palet ok"); //confirme que le robot a d�pos� le palet
			Delay.msDelay(5000);
	}
}
