import java.awt.EventQueue;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
		
		// Chargement du driver Mysql
		try {
			Class.forName("com.mysql.jdbc.Driver");
		}
		catch(java.lang.ClassNotFoundException e) {
			System.err.print("ClassNotFoundException");
			System.err.println(e.getMessage());
		}
		
		// URL de la base de donn�e
		String url = "jdbc:mysql://localhost:3306/forumprepa";
		
		try {
			// Connexion � la base de donn�es
			Connection con = DriverManager.getConnection(url, "root", "");
			
			// Requ�te : liste des �tudiants
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT nom, prenom, groupe, numeroMifare, numeroEtudiant, lienPhoto FROM etudiants");
			
			ArrayList<String> listeGroupes = new ArrayList<String>();
			
			rs.next();
			
			Etudiant etu = new Etudiant(rs.getString(1), rs.getString(2), "", rs.getString(6), rs.getString(4), rs.getString(5));
			listeGroupes.add(rs.getString(3));
			
			
			/* Un �tudiant appara�t autant de fois que de groupes auquel il appartient.
			 * Les lignes se suivent. Pour chaque r�sultat, on compare avec le r�sultat
			 * pr�c�dent pour voir s'il s'agit du m�me �tudiant.
			 * On stocke les groupes dans l'ArrayList groupes */
			while(rs.next()) {
				if(rs.getString(4).equals(etu.getNumeroMifare())) {
					// C'est le m�me �tudiant, on ajoute le groupe
					listeGroupes.add(rs.getString(3));
				}
				else {
					// Ce n'est pas le m�me �tudiant. On ajoute le pr�c�dent �tudiant dans le fichier XML.
					ListeEtudiants.ajouterEtudiantXML(etu, listeGroupes, racine);
					
					listeGroupes.clear();
					
					etu.setNom(rs.getString(1)); etu.setPrenom(rs.getString(2));
					etu.setNumeroMifare(rs.getString(4)); etu.setNumeroEtudiant(rs.getString(5));
					etu.setLienPhoto(rs.getString(6));
					listeGroupes.add(rs.getString(3));
				}
			}
			
			// On ajoute le dernier �tudiant au fichier XML
			ListeEtudiants.ajouterEtudiantXML(etu, listeGroupes, racine);
		}
		catch(SQLException ex) {
			System.err.println("SQLException :" + ex.getMessage());
		}
		
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
										courant.getChildText("lienPhoto"),
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
	public static void ajouterEtudiantXML(Etudiant etu, ArrayList<String> listeGroupes, Element racine) {
	
		Element etudiant = new Element("etudiant");
		racine.addContent(etudiant);
		
		Element nom = new Element("nom");
		nom.setText(etu.getNom());
		Element prenom = new Element("prenom");
		prenom.setText(etu.getPrenom());
		Element lienPhoto = new Element("lienPhoto");
		lienPhoto.setText(etu.getLienPhoto());
		Element numeroMifare = new Element("numeroMifare");
		numeroMifare.setText(etu.getNumeroMifare());
		Element numeroEtudiant = new Element("numeroEtudiant");
		numeroEtudiant.setText(etu.getNumeroEtudiant());
		
		Element groupes = new Element("groupes");
		for(String s : listeGroupes) {
			Element groupe = new Element("groupe");
			groupe.setText(s);
			groupes.addContent(groupe);
		}
		
		etudiant.addContent(nom);
		etudiant.addContent(prenom);
		etudiant.addContent(groupes);
		etudiant.addContent(lienPhoto);
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
