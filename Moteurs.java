package robot;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;

/**
 * Classe moteurs
 * Classe qui permet d'initialiser les diff�rents attributs li�s aux moteurs et les m�thodes associ�s
 * @author Groupe 6
 */
public class Moteurs {

	private RegulatedMotor roueDroite ;
	private RegulatedMotor roueGauche;
	private int cumulAngle;

	/**
	 * Constructeur qui permet d'initialiser les moteurs et l'attribut cumulAngle
	 */
	public Moteurs(){
		cumulAngle = 0;
		roueDroite =  new  EV3LargeRegulatedMotor(MotorPort.D);
		roueGauche =  new  EV3LargeRegulatedMotor(MotorPort.A);

		roueDroite.setSpeed(Cedric.vitesse);
		roueDroite.setAcceleration(Cedric.acceleration);
		roueGauche.setSpeed(Cedric.vitesse);
		roueGauche.setAcceleration(Cedric.acceleration);
	}

	/**
	 * M�thode qui permet au robot d'avancer ou reculer droit d'une distance
	 * @param distance en mm postive pour avancer, n�gative pour reculer
	 */
	public void avancerDistance(int distance) { 
		roueDroite.rotate(distance*360/176, true);
		roueGauche.rotate(distance*360/176, false);
	}
	
	/**
	 * M�thode permettant au robot d'avancer
	 */
	public void avancer() {
		roueDroite.forward();
		roueGauche.forward();
	}

	/**
	 * M�thode qui permet de stopper les roues du robot
	 */
	public void arreter() { 
		roueDroite.stop(true);
		roueGauche.stop(false);
	}

	/**
	 * M�thode qui fait tourner le robot d'un certain angle 
	 * @param angle (en degr� ?)
	 */
	public void tourner (int angle) {
		if (angle >0)
			roueDroite.rotate(angle*343*4/360,true);
		else
			roueGauche.rotate(-angle*343*4/360,true);
		cumulAngle=cumulAngle+angle;
	}

	/**
	 * M�thode qui permet de savoir si une des roues est en mouvement
	 * @return
	 */
	public boolean isMoving() {
		return roueDroite.isMoving() || roueGauche.isMoving();
	}

	/**
	 * M�thode remettant le robot sur l'axe de d�part (face au but adverse)
	 * @param angle
	 */
	public void reorienter() {
		if (cumulAngle < 0) {
			if (cumulAngle%345<=-175)
				this.tourner(-1*(175-(-cumulAngle%175)));
			else
				this.tourner(-1*cumulAngle%345);
		}
		else {
			if (cumulAngle%345<=175)
				this.tourner(-1*(cumulAngle%345));
			else
				this.tourner(175-(cumulAngle%175));
		}
	}
	
	/**
	 * Getteur permettant de renvoyer la valeur de l'attribut cumulAngle
	 * @return un entier correspondant � la valeur de l'attribut cumulAngle 
	 * c'est � dire le nombre de degr�s qu'� fait le robot
	 */
	public int getCumulAngle() { 
		return cumulAngle;
	}	
}