package robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lejos.hardware.Button;
import lejos.utility.Delay;

/**
 * Classe Cedric
 * Classe qui permet d'initialiser les diff�rents attributs finaux et les m�thodes de bases
 * @author Groupe 6
 */
public class Cedric {

	public final static int vitesse=500;
	public final static int acceleration=1000;

	private Capteurs c;
	private Moteurs m;
	private Pinces p;
	private int nbBut;
	private int nbMur;

	/**
	 * Constructeur de la classe qui permet d'initialiser les attributs d'instances
	 */
	public Cedric() {
		c = new Capteurs();
		m= new Moteurs();
		p= new Pinces();
		nbBut=0;
		nbMur=0;
	}

	/**
	 * M�thode qui cr�e une liste des distances entre objets et robots sur un certain angle
	 * @return liste de float des distances des objets en (m ?)
	 */
	public  List<Float> distances(int angle){
		List<Float> res = new ArrayList<Float>();
		m.tourner(angle);
		while (m.isMoving()) {
			res.add(c.getDistance());
			Delay.msDelay(8);
		}
		return res ;
	}	

	/**
	 * M�thode qui r�cup�re la plus petite distance dans la liste et de s'orienter dans sa direction
	 * @param liste de float repr�sentant les distances autour du robot
	 * @return la distance entre le robot et l'objet le plus proche
	 */
	public float identifierPalet(List<Float> liste) {
		float dist = Collections.min(liste);
		int iFixe = liste.indexOf(dist);
		if (iFixe>=175) {
			m.tourner(-1*(360-(iFixe)));
			Delay.msDelay(3000);
		}
		else {
			m.tourner(iFixe);
			Delay.msDelay(3000);
		}
		return dist;
	}	

	/**
	 * M�thode qui permet au robot de r�cup�rer un palet situ� � une certaine distance 
	 * en s'assurant qu'il s'agit bien d'un palet
	 * Met � jour nbMur
	 * @param distance en m�tre
	 * @return false si le robot � identifier un mur et true sinon
	 */
	public boolean recupererPalet(float distance) { 
		int d = (int)(1000*distance);
		m.avancerDistance(d-150);
		int d2 = (int)(1000*c.getDistance());
		if (d2>300) { 
			p.ouvrirPinces(3);
			while(c.getTactile()!=1 && c.getDistance()>0.15) 
				m.avancer();
			if (c.getDistance()<=0.15) {
				m.arreter();
				p.fermerPinces(3);
				m.avancerDistance(-200-d);
				if(nbMur>2) {
					changementDeZone(distances(360));
					nbMur=0;
				}
				else
					m.avancerDistance(-200-d);
				nbMur++;
				return false;
			}
			else {
				m.arreter();
				p.fermerPinces(3);
				nbMur=0;
				return true;
			}
		}
		if (nbMur>2) {
			changementDeZone(distances(360));
			nbMur=0;
		}
		else 
			m.avancerDistance(-200-d);
		nbMur++;
		return false;
	}
	
	/**
	 * M�thode qui permet de s�orienter en direction de la plus longue distance mesur�e 
	 * et de parcourir la moiti� de cette distance
	 * @param liste
	 */
	public void changementDeZone(List<Float> liste) {
		float dist = Collections.max(liste);
		int iFixe = liste.indexOf(dist);
		if(iFixe>=175) {
			m.tourner(-1*(360-(iFixe)));
			Delay.msDelay(3000);
		}
		else 
			m.tourner(iFixe);
		m.avancerDistance((int)(dist/2));
	}

	/**
	 * Methode qui permet de d�poser un palet apr�s la ligne blanche de l'adversaire
	 */
	public void deposerPalet() { 
		m.reorienter();
		m.avancer();
		while (c.getCouleur()!=6) 
			m.avancer();
		m.arreter();
		p.ouvrirPinces(3);
		m.avancerDistance(-400);
		p.fermerPinces(3);
		this.verifierPalet();
	}

	/**
	 * M�thode qui permet de v�rifier si un palet a bien �t� d�pos� dans la zone
	 * @return true si il d�tecte un palet false sinon
	 */
	public boolean verifierPalet() {
		if((c.getDistance()>=0.35)&&(c.getDistance()<= 0.5)) {
			nbBut++;
			return true;
		}
		return false;
	}

	/**
	 * M�thode permettant de faire stopper le reobot en cas de temps-mort pendant le tournoi
	 */
	public void stopCedric() {
		if(Button.ENTER.isDown()) {
			m.arreter();
			System.exit(0);
			return;
		}
	}

	/**
	 * M�thode permettant d'homologuer le robot au d�but de la comp�tition :
	 * Prend le premier palet devant lui et le d�pose dans la zone d'en-but adverse
	 */
	public void homologation() {
		m.avancerDistance(300);
		p.ouvrirPinces(3);
		m.avancerDistance(200);
		p.fermerPinces(3);
		m.tourner(45);
		Delay.msDelay(3000);
		m.avancerDistance(300);
		m.reorienter();
		Delay.msDelay(3000);
		m.avancerDistance(1600);
		p.ouvrirPinces(3);
		nbBut++;
		m.avancerDistance(-200);
	}

	/**
	 * Positionne le robot apr�s l'homologation pour prendre le second palet et le d�poser dans la zone d'en-but adverse
	 */
	public void deuxiemePalet() {
		m.avancerDistance(-1400);
		m.tourner(-90);
		p.ouvrirPinces(3);
		recupererPalet(c.getDistance());
		deposerPalet();
	}

	/**
	 * Getteur permettant de renvoyer la valeur de l'attribut nbBut
	 * @return un entier correspondant � la valeur de l'attribut nbBut
	 */
	public int getNbBut() {
		return nbBut;
	}
}
