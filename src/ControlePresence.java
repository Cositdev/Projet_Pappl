import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.plaf.metal.MetalIconFactory;

public class ControlePresence extends JFrame {

	private JPanel contentPane, panelDroite, panelGauche;

	private String cheminSmiley = "./img/smiley.jpg";
	private String cheminCheck = "./img/check.png";

	private ArrayList<Etudiant> listeEtudiants;
	private JTextField textFieldInput;
	private JSplitPane splitPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ControlePresence frame = new ControlePresence();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ControlePresence() {

		listeEtudiants = new ArrayList<Etudiant>();
		listeEtudiants.add(new Etudiant("Guillaume Bugnet", "001"));
		listeEtudiants.add(new Etudiant("Bertrand Magnien", "002"));
		Etudiant antoine = new Etudiant("Antoine NashPatate", "003");
		antoine.setPresent(true);
		listeEtudiants.add(antoine);
		majFenetre();
	}

	public void majFenetre() {
		
		System.out.println("MISE A JOUR DE L\'affichage de la fenêtre");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 550, 300);
		setResizable(true);
		contentPane = new JPanel();
		// contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		
		
		Map<Object, ImageIcon> icons = new HashMap<Object, ImageIcon>();
		icons.put("present", createImageListe(cheminCheck));


		
		
		
		
		JLabel titreFenetre = new JLabel("Contrôle en cours");

		// Gestion de la droite, ou se trouve l'image et le champ de texte
		panelDroite = new JPanel();
		panelDroite.setLayout(new BorderLayout());
		
		Box boiteVerticale = Box.createVerticalBox();
		boiteVerticale.add(Box.createGlue());
			Box boiteHorizontale1= Box.createHorizontalBox();
			boiteHorizontale1.add(Box.createGlue());

			ImageIcon icone = createImageIcon(cheminSmiley);
			
			JLabel jlbLabel1 = new JLabel(icone, JLabel.CENTER);
			jlbLabel1.setPreferredSize(new Dimension(100, 100));
			boiteHorizontale1.add(jlbLabel1);
			boiteHorizontale1.add(Box.createGlue());
			
		boiteVerticale.add(boiteHorizontale1);
		
			Box boiteHorizontale2= Box.createHorizontalBox();
			boiteHorizontale2.add(Box.createGlue());
				textFieldInput = new JTextField();
				textFieldInput.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						rechercheETUpdate();
					}
				});
				textFieldInput.setPreferredSize(new Dimension(100, 20));
				textFieldInput.setMaximumSize(new Dimension(100, 20));
			boiteHorizontale2.add(textFieldInput);
			boiteHorizontale2.add(Box.createGlue());
			
		boiteVerticale.add(boiteHorizontale2);
		
			Box boiteHorizontale3= Box.createHorizontalBox();
			boiteHorizontale3.add(Box.createGlue());
				JButton boutonFinControle = new JButton("Fin du contrôle");
				boutonFinControle.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent arg0) {
						setVisible(false); 
						System.exit(0);
						
					}
				});
				boiteHorizontale3.add(boutonFinControle);
				boiteHorizontale3.add(Box.createGlue());
			
		boiteVerticale.add(boiteHorizontale3);
		
		
		boiteVerticale.add(Box.createGlue());
		panelDroite.add(boiteVerticale,BorderLayout.CENTER);

		
		
		// Gestion de la gauche, liste des élèves
		panelGauche = new JPanel();
		panelGauche.setLayout(new BorderLayout());

		jlbLabel1.setIcon(createImageListe(cheminCheck));
		
		JList listEleves= majListeEleves();

		// create a cell renderer to add the appropriate icon

		listEleves.setCellRenderer(new MaListeEleves(icons));
		

		panelGauche.add(listEleves, BorderLayout.CENTER);

		JSplitPane splitPane= new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
				panelGauche, panelDroite);
		splitPane.setDividerLocation(300);

		contentPane.add(splitPane, BorderLayout.CENTER);
		contentPane.add(titreFenetre, BorderLayout.NORTH);
		// contentPane.add(panelDroite, BorderLayout.EAST);
		//
	}

	public JList majListeEleves() {
		int nombreEtudiants = listeEtudiants.size();
		int position = 0;
		Etudiant[] vecteurEtudiants = new Etudiant[nombreEtudiants];
		for (Etudiant etu : listeEtudiants) {
			vecteurEtudiants[position]=etu;
			position++;
		}
		JList listEleves= new JList(vecteurEtudiants);
		return listEleves;
	}

	public void rechercheETUpdate() {
		String myfareTrouve = textFieldInput.getText();
		for (Etudiant etu : listeEtudiants) {
			if (etu.getNumeroMifare().equals(myfareTrouve)) {
				etu.setPresent(true);
				System.out.println(myfareTrouve
						+ " a ete considere comme present : c\'est "
						+ etu.getNom());
			}
		}
		textFieldInput.setText("");
		majListeEleves();
		this.repaint();

	}

	public static ImageIcon createImageIcon(String path) {
			ImageIcon retour = new ImageIcon() ;
	
			
            try {
				Image img = ImageIO.read(new File(path));
            	Image mieux = img.getScaledInstance(80, 100, 1);
            	retour.setImage(mieux);

			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			return retour;
	}
	
	public static ImageIcon createImageListe(String path){
		ImageIcon retour = new ImageIcon() ;
		System.out.println("Appel de la fonction createImageListe");
		try {
			Image img = ImageIO.read(new File(path));
        	Image mieux = img.getScaledInstance(30, 30, 1);
        	retour.setImage(mieux);
    		System.out.println("on renvoie bien l'image");

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		return retour;

	}
}
