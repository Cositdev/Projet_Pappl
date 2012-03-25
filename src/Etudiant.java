import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.net.URLConnection;


public class Etudiant {
	
	protected String nom;
	protected String prenom;
	protected String groupe;
	protected String numeroMifare;
	protected String numeroEtudiant;
	protected static String urlPhoto = "http://agap.ec-nantes.fr/AGAP/Photo/";
	protected Boolean present = false;
	
	
	
	public Etudiant(String nom, String prenom, String groupe, String numeroMifare, String numeroEtudiant) {
		this.nom = nom;
		this.prenom = prenom;
		this.groupe = groupe;
		this.numeroMifare = numeroMifare;
		this.numeroEtudiant = numeroEtudiant;
	}
	
	
	
	/**
	 * Télécharge la photo d'un étudiant
	 * @return Booléen : true si la photo a été téléchargée ou existe déjà, false sinon
	 */
	public boolean telechargerPhoto() {
		try {
			URL adresse = new URL(Etudiant.urlPhoto + this.numeroEtudiant + ".jpg");
			File photo = new File("img/" + this.numeroEtudiant + ".jpg");
			
			if(!photo.exists()) {
				URLConnection con = adresse.openConnection();
				BufferedInputStream in = new BufferedInputStream(con.getInputStream());
				BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("img/" + this.numeroEtudiant + ".jpg"));
				byte[] read = new byte[1];
				int len = 1;
				
				while ((len = in.read(read)) > 0) {
					out.write(read, 0, len);
				}
				
				out.flush();
				out.close();
				in.close();
			}
			
			return true;
		}
		catch (Exception e) {
			System.out.println("Erreur lors du téléchargement de la photo de " + this.nom + " " + this.prenom);
			return false;
		}
	}
	
	
	
	public String getLienPhotoDisque(){
		return  "./img/" + this.numeroEtudiant + ".jpg";
	}
	
	
	
	/*
	 * Getters et Setters
	 */
	public String getNom(){
		return this.nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getGroupe() {
		return groupe;
	}
	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}
	public String getNumeroMifare() {
		return numeroMifare;
	}
	public void setNumeroMifare(String numeroMifare) {
		this.numeroMifare = numeroMifare;
	}
	public String getNumeroEtudiant() {
		return numeroEtudiant;
	}
	public void setNumeroEtudiant(String numeroEtudiant) {
		this.numeroEtudiant = numeroEtudiant;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Boolean getPresent() {
		return present;
	}
	public void setPresent(Boolean present) {
		this.present = present;
	}
}
