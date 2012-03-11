import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
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

	private Map<Object, ImageIcon> icons = null;

	public MaListeEleves(Map<Object, ImageIcon> icons) {
		this.icons = icons;
	}

	@Override
	public Component getListCellRendererComponent(JList list, Object value,
			int index, boolean isSelected, boolean cellHasFocus) {

		// Get the renderer component from parent class
		Etudiant etudiant = (Etudiant) value;

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());

		Box boiteHorizontale = Box.createHorizontalBox();
		JLabel labelNom = (JLabel) super.getListCellRendererComponent(list,
				etudiant.getNom(), index, isSelected, cellHasFocus);
		labelNom.setPreferredSize(new Dimension(180, 20));
		boiteHorizontale.add(labelNom);


		if (etudiant.getPresent()) {
			JLabel labelImage = new JLabel();
			System.out.println(etudiant.getNom() + " est present(e)");

			// ImageIcon icon = new
			// ImageIcon(getClass().getResource("/img/check.png"));
			ImageIcon icon = ControlePresence.createImageListe(cheminCheck);
			labelImage.setIcon(icon);
			labelImage.setText("Present");
			labelImage.setPreferredSize(new Dimension(100, 20));

			boiteHorizontale.add(labelImage);

		}

		boiteHorizontale.add(Box.createGlue());

		panel.add(boiteHorizontale, BorderLayout.CENTER);

		return panel;
	}

}