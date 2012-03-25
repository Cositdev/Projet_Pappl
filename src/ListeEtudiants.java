import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.filter.Filter;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

public class ListeEtudiants {
	
	
	/**
	 * L'attribut 'etudiants' contient la liste des �tudiants concern�s par le contr�le de pr�sence.
	 */
	protected static ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
	
	
	
	/**
	 * T�l�charge la liste des �tudiants depuis la base de donn�es AGAP, et
	 * sauvegarde les donn�es dans un fichier XML (etudiants.xml)
	 * @return
	 * boolean : true le fichier XML a �t� correctement cr��, false sinon
	 */
	public static boolean telechargerListeEtudiants() {
		
		File fichierXml = new File("etudiants.xml");
		
		// On supprime le fichier XML s'il existe
		if(fichierXml.exists()) {
			if(!fichierXml.delete()) {
				return false;
			}
		}
		
		// D�but de la structure XML
		Element racine = new Element("etudiants");
		org.jdom.Document document = new Document(racine);
		
		// Chargement du driver PostgreSQL
		try {
			Class.forName("org.postgresql.Driver");
		}
		catch(java.lang.ClassNotFoundException e) {
			System.out.println("Erreur : driver Postgresql non trouv�");
			System.err.print("ClassNotFoundException");
			System.err.println(e.getMessage());
		}
		
		
		try {
			// Connexion � la base de donn�es
			Properties prop = new Properties();
			prop.put("user", "testetudiant");
			prop.put("password", "ei3info");
			prop.put("useUnicode", "true");
			prop.put("characterEncoding", "utf-8");
			Connection con = DriverManager.getConnection("jdbc:postgresql://agapbd.ec-nantes.fr/AGAPTest", prop);
			
			// Ann�e scolaire
			int annee = Calendar.getInstance().get(Calendar.YEAR);
			if(Calendar.getInstance().get(Calendar.MONTH) > 9) {
				annee ++;
			}
			
			String libelleCycle = (annee - 1) + "-" + (annee);
			
			// Requ�te : liste des �tudiants
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM etudiants WHERE cycle_libelle='" + libelleCycle + "' ORDER BY personne_nom");
			
			// On ajoute chaque �tudiant au fichier XML
			while(rs.next()) {
				Etudiant etu = new Etudiant(rs.getString(1), rs.getString(2), "", rs.getString(5), rs.getString(4));
				
				ListeEtudiants.ajouterEtudiantXML(etu, racine);
			}
			
			try {
				XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
				sortie.output(document, new FileOutputStream("etudiants.xml"));
			}
			catch(java.io.IOException e) {
				JOptionPane jop = new JOptionPane();
				jop.showMessageDialog(null,
									  "Erreur lors de l'enregistrement du fichier XML. Veuillez recommencer.",
									  "Erreur",
									  JOptionPane.ERROR_MESSAGE);
				System.out.println(e.getMessage());
				return false;
			}
		}
		catch(SQLException ex) {
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null,
								  "Erreur lors de la connexion � la base de donn�es AGAP.",
								  "Erreur",
								  JOptionPane.ERROR_MESSAGE);
			System.err.println("SQLException :" + ex.getMessage());
		}
		
		return true;
	}
	
	
	
	
	/**
	 * Lit le fichier XML et stocke les informations dans l'attribut etudiants
	 * @param String groupe : groupe 
	 * @return Bool�en : true si tout se passe bien, false si le fichier XML n'existe pas
	 */
	public static boolean lireFichierXML(final String filtreGroupe) {
		
		org.jdom.Document document = null;
		Element racine;
		
		SAXBuilder sxb = new SAXBuilder();
		
		ListeEtudiants.etudiants = new ArrayList<Etudiant>();
		
		try {
			document = sxb.build((new File("etudiants.xml")));
		}
		catch (Exception e) {
			System.out.println("Erreur : " + e.getMessage());
		}
		
		racine = document.getRootElement();
		
		// Filtre en fonction du groupe
		Filter filtre = new Filter() {
			public boolean matches(Object objet) {
				if(!(objet instanceof Element)) {
					return false;
				}
				
				Element element = (Element) objet;
				
				List<Element> groupes = element.getChild("groupes").getChildren("groupe");
				
				for(Element e : groupes) {
					if(e.getTextTrim().equals(filtreGroupe)) {
						return true;
					}
				}
				
				return false;
			}
		};
		
		// On r�cup�re les r�sultats avec le filtre
		List resultats = racine.getContent(filtre);
		
		Iterator i = resultats.iterator();
		
		// On ajoute les �tudiants � l'attribut 'etudiants' (ArrayList d'Etudiant)
		while(i.hasNext()) {
			Element courant = (Element) i.next();
			Etudiant etu = new Etudiant(courant.getChildText("nom"),
										courant.getChildText("prenom"),
										courant.getChildText("groupe"),
										courant.getChildText("numeroMifare"),
										courant.getChildText("numeroEtudiant"));
			etudiants.add(etu);
		}
		
		return true;
	}
	
	
	
	
	/**
	 * Ajoute un �tudiant au fichier XML
	 * @param etu : �tudiant � ajouter au fichier XML
	 * @param listeGroupes : groupes auquels appartient l'�tudiant
	 * @param racine : racine du fichier XML
	 */
	public static void ajouterEtudiantXML(Etudiant etu, Element racine) {
	
		Element etudiant = new Element("etudiant");
		racine.addContent(etudiant);
		
		Element nom = new Element("nom");
		nom.setText(etu.getNom());
		Element prenom = new Element("prenom");
		prenom.setText(etu.getPrenom());
		Element numeroMifare = new Element("numeroMifare");
		numeroMifare.setText(etu.getNumeroMifare());
		Element numeroEtudiant = new Element("numeroEtudiant");
		numeroEtudiant.setText(etu.getNumeroEtudiant());
		Element groupes = new Element("groupes");
		
		etudiant.addContent(nom);
		etudiant.addContent(prenom);
		etudiant.addContent(groupes);
		etudiant.addContent(numeroMifare);
		etudiant.addContent(numeroEtudiant);
	}
	
	
	
	
	/**
	 * T�l�charger les photos des �tudiants concern�s par le contr�le de pr�sence (appartenant au groupe s�l�ctionn�)
	 * @return Nombre de photos qui n'ont pas pu �tre t�l�charg�es
	 */
	public static int telechargerPhotos() {
		
		int erreurs = 0;
		
		for(Etudiant e : etudiants) {
			if(! e.telechargerPhoto()) {
				erreurs ++;
			}
		}
		
		return erreurs;
	}
}
