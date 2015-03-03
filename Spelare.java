import javax.swing.*;
import java.awt.*;

public class Spelare extends JPanel {
	
	int poäng = 0;
	int nr;
	boolean tur = false;
	
	public Spelare (int i){
		nr = i;
		setBackground(Color.GRAY);
		setLayout(new GridLayout(2, 1));
		update();
	}
	
	public void update() {
		removeAll();
		add(new JLabel("Spelare " + nr, SwingConstants.CENTER));
		add(new JLabel(Integer.toString(poäng), SwingConstants.CENTER));
	}
	
	public void addPoint() {
		poäng++;
		update();
	}
	
	public int getPoints() {
		return poäng;
	}
	
	public void toggleTurn() {
		if (tur) {
			setBackground(Color.GRAY);
			tur = false;
		}
		else {
			setBackground(Color.YELLOW);
			tur = true;
		}
	}
}
