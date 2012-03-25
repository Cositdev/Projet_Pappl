


public class Main {
	
	/**
	 * Chaque fenêtre utilisée dans l'application est un attribut,
	 * qui pourra être utilisé par toutes les classes
	 */
	protected static PageAccueil fenetreAccueil;
	protected static SelectionCours fenetreSelectionCours;
	protected static ControlePresence fenetreControle;
	protected static FenetreListeAbsents fenetreListeAbsents;
	
	
	
	/**
	 * Lancement de l'application
	 */
	public static void main(String[] args) {
		
		// On créé une instance de chaque fenêtre
		fenetreAccueil = new PageAccueil();
		fenetreSelectionCours = new SelectionCours();
		fenetreControle = new ControlePresence();
		fenetreListeAbsents = new FenetreListeAbsents();
		
		// On masque toutes les fenêtre sauf la fenêtre d'accueil
		fenetreSelectionCours.setVisible(false);
		fenetreControle.setVisible(false);
		fenetreListeAbsents.setVisible(false);
		fenetreAccueil.setVisible(true);
	}

}
