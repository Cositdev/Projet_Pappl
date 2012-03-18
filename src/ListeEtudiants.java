import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

import org.jdom.*;
import org.jdom.input.*;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.jdom.filter.*;

public class ListeEtudiants {
	
	/**
	 * L'attribut 'etudiants' contient la liste des étudiants concernés par le contrôle de présence.
	 */
	protected static ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
	
	
	/**
	 * Télécharge la liste des étudiants depuis la base de données AGAP, et
	 * sauvegarde les données dans un fichier XML (etudiants.xml)
	 * @return
	 * boolean : true le fichier XML a été correctement créé, false sinon
	 */
	public static boolean telechargerListeEtudiants() {
		
		String message;
		File fichierXml = new File("etudiants.xml");
		
		if(fichierXml.exists()) {
			// Il y a déjà un fichier XML, on le supprime
			if(!fichierXml.delete()) {
				return false;
			}
		}
		
		// Connexion au serveur
		// ...
		
		// Création du fichier XML
		Element racine = new Element("etudiants");
		org.jdom.Document document = new Document(racine);
		
		
		Element etudiant = new Element("etudiant");
		racine.addContent(etudiant);
		
		Element nom = new Element("nom");
		nom.setText("Bugnet");
		etudiant.addContent(nom);
		
		Element prenom = new Element("prenom");
		prenom.setText("Guillaume");
		etudiant.addContent(prenom);
		
		try {
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream("etudiants.xml"));
		}
		catch(java.io.IOException e) {
			System.out.println("Erreur lors de l'enregistrement du fichier etudiants.xml");
			System.out.println(e.getMessage());
		}
		
		
		return true;
	}
	
	
	/**
	 * Lit le fichier XML et stocke les informations dans l'attribut etudiants
	 * @param String groupe : groupe 
	 * @return Booléen : true si tout se passe bien, false si le fichier XML n'existe pas
	 */
	public static boolean lireFichierXML(final String filtreGroupe) {
		
		org.jdom.Document document = null;
		Element racine;
		
		SAXBuilder sxb = new SAXBuilder();
		
		try {
			document = sxb.build((new File("etudiants.xml")));
		}
		catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		racine = document.getRootElement();
		
		Filter filtre = new Filter() {
			public boolean matches(Object objet) {
				if(!(objet instanceof Element)) {
					return false;
				}
				
				Element element = (Element) objet;
				
				if(element.getChild("groupe").getTextTrim().equals(filtreGroupe)) {
					return true;
				}
				
				return false;
			}
		};
		
		List resultats = racine.getContent(filtre);
		
		Iterator i = resultats.iterator();
		
		// On ajoute les étudiants à l'attribut 'etudiants' (ArrayList d'Etudiant)
		while(i.hasNext()) {
			Element courant = (Element) i.next();
			Etudiant etu = new Etudiant(courant.getChildText("nom"),
										courant.getChildText("prenom"),
										courant.getChildText("groupe"),
										courant.getChildText("lienPhoto"),
										courant.getChildText("numeroMifare"),
										courant.getChildText("numeroEtudiant"));
			etudiants.add(etu);
		}
		
		return true;
	}
}
