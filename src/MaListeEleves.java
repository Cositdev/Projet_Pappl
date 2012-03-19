import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;

import javax.swing.Box;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;

public class MaListeEleves extends DefaultListCellRenderer {

	private String cheminCheck = "./img/check.png";
	JLabel labelNom ;
	JLabel labelImage;
	Etudiant etudiant;

	public MaListeEleves() {
		labelImage = new JLabel();
		labelNom = new JLabel();
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		// Get the renderer component from parent class
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

		if (etudiant.getPresent()) {
			System.out.println(etudiant.getNom() + " est present(e)");

						
			
			ImageIcon icon = ControlePresence.createImageListe(cheminCheck);
			labelImage.setText("Present");
			labelImage.setIcon(icon);
			labelImage.setPreferredSize(new Dimension(100, 20));

			boiteHorizontale.add(labelImage);

		}

		boiteHorizontale.add(Box.createGlue());

		panel.add(boiteHorizontale, BorderLayout.CENTER);
		
		return panel;
	}
	
	
	public void majDeLaPhoto(Etudiant etu){
		Main.fenetreControle.setDernierEtudiant(etu);
		Main.fenetreControle.majPhoto();
	}
}