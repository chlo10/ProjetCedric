package robot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lejos.hardware.Button;
import lejos.utility.Delay;

/**
 * Classe Cedric
 * Classe qui permet d'initialiser les différents attributs finaux et les méthodes de bases
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
	 * Méthode qui crée une liste des distances entre objets et robots sur un certain angle
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
	 * Méthode qui récupére la plus petite distance dans la liste et de s'orienter dans sa direction
	 * @param liste de float représentant les distances autour du robot
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
	 * Méthode qui permet au robot de récupérer un palet situé à une certaine distance 
	 * en s'assurant qu'il s'agit bien d'un palet
	 * Met à jour nbMur
	 * @param distance en mètre
	 * @return false si le robot à identifier un mur et true sinon
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
	 * Méthode qui permet de s’orienter en direction de la plus longue distance mesurée 
	 * et de parcourir la moitié de cette distance
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
	 * Methode qui permet de déposer un palet après la ligne blanche de l'adversaire
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
	 * Méthode qui permet de vérifier si un palet a bien été déposé dans la zone
	 * @return true si il détecte un palet false sinon
	 */
	public boolean verifierPalet() {
		if((c.getDistance()>=0.35)&&(c.getDistance()<= 0.5)) {
			nbBut++;
			return true;
		}
		return false;
	}

	/**
	 * Méthode permettant de faire stopper le reobot en cas de temps-mort pendant le tournoi
	 */
	public void stopCedric() {
		if(Button.ENTER.isDown()) {
			m.arreter();
			System.exit(0);
			return;
		}
	}

	/**
	 * Méthode permettant d'homologuer le robot au début de la compétition :
	 * Prend le premier palet devant lui et le dépose dans la zone d'en-but adverse
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
	 * Positionne le robot après l'homologation pour prendre le second palet et le déposer dans la zone d'en-but adverse
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
	 * @return un entier correspondant à la valeur de l'attribut nbBut
	 */
	public int getNbBut() {
		return nbBut;
	}
}
