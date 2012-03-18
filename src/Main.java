import java.awt.EventQueue;


public class Main {
	
	/**
	 * Chaque fen�tre utilis�e dans l'application est un attribut,
	 * qui pourront �tre utilis�s par toutes les classes
	 */
	protected static PageAccueil fenetreAccueil;
	protected static SelectionCours fenetreSelectionCours;
	protected static ControlePresence fenetreControle;
	
	
	/**
	 * Lancement de l'application
	 */
	public static void main(String[] args) {
		
		// On cr�� une instance de chaque fen�tre
		fenetreAccueil = new PageAccueil();
		fenetreSelectionCours = new SelectionCours();
		fenetreControle = new ControlePresence();
		
		// On masque toutes les fen�tre sauf la fen�tre d'accueil
		fenetreSelectionCours.setVisible(false);
		fenetreControle.setVisible(false);
		fenetreAccueil.setVisible(true);
	}

}
