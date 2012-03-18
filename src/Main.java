import java.awt.EventQueue;


public class Main {
	
	/**
	 * Chaque fenêtre utilisée dans l'application est un attribut,
	 * qui pourront être utilisés par toutes les classes
	 */
	protected static PageAccueil fenetreAccueil;
	protected static SelectionCours fenetreSelectionCours;
	protected static ControlePresence fenetreControle;
	
	
	/**
	 * Lancement de l'application
	 */
	public static void main(String[] args) {
		
		// On créé une instance de chaque fenêtre
		fenetreAccueil = new PageAccueil();
		fenetreSelectionCours = new SelectionCours();
		fenetreControle = new ControlePresence();
		
		// On masque toutes les fenêtre sauf la fenêtre d'accueil
		fenetreSelectionCours.setVisible(false);
		fenetreControle.setVisible(false);
		fenetreAccueil.setVisible(true);
	}

}
