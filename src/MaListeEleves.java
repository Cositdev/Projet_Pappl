import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MaListeEleves extends DefaultListCellRenderer {
	
	private JLabel labelNom ;
	private JLabel labelImage;
	private Etudiant etudiant;
	
	
	
	public MaListeEleves() {
		labelImage = new JLabel();
		labelNom = new JLabel();
	}
	
	
	
	public Component getListCellRendererComponent(JList list, Object value,	int index, boolean isSelected, boolean cellHasFocus) {
		
		etudiant = (Etudiant) value;
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		Box boiteHorizontale = Box.createHorizontalBox();
		labelNom= (JLabel) super.getListCellRendererComponent(list,
															  etudiant.getNom() + " " + etudiant.getPrenom(),
															  index,
															  isSelected,
															  cellHasFocus);
		labelNom.setPreferredSize(new Dimension(180, 20));
		boiteHorizontale.add(labelNom);
		
		// On affiche "présent" si l'étudiant est présent
		if (etudiant.getPresent()) {
			
			labelImage.setText("Present");
			labelImage.setPreferredSize(new Dimension(100, 20));

			boiteHorizontale.add(labelImage);
		}

		boiteHorizontale.add(Box.createGlue());

		panel.add(boiteHorizontale, BorderLayout.CENTER);
		
		return panel;
	}
	
	
	public void majDeLaPhoto(Etudiant etu) {
		Main.fenetreControle.setDernierEtudiant(etu);
		Main.fenetreControle.majPhoto();
	}
}