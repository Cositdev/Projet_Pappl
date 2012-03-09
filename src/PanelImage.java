import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 

//352 x 460
public class PanelImage extends JPanel {
	private String CheminImage;
		
	public PanelImage(String chemin){
		CheminImage=chemin;
		
	}
	
   public String getCheminImage() {
		return CheminImage;
	}

	public void setCheminImage(String cheminImage) {
		CheminImage = cheminImage;
	}

		public void paintComponent(Graphics g){
        	System.out.println("PaintComponent : On dessine l'apercu : "+CheminImage);
                try {
                        Image img = ImageIO.read(new File(CheminImage));
                        
                        g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

                } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                }
                
        }               
}
