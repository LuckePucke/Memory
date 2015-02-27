import javax.swing.*;
import java.awt.*;

public class Card extends JButton {
	
	enum Status {HIDDEN, VISIBLE, MISSING};
	Status state;
	Icon pic;
	
	public Card(Icon a) {
		pic = a; setStatus(Status.HIDDEN);}
	public Card(Icon a, Status b) {
		pic = a; setStatus(b);}
	
	public Status getStatus() {
		return state;}
	public Card copy() {
		return new Card(pic, state);}
	public boolean equalIcon(Card a) {
		return a.pic == pic;}
	public void setStatus(Status a) {
		state = a;
		switch(a) {
			case HIDDEN:	setBackground(Color.BLUE);
							setIcon(null);
							break;
			
			case MISSING:	setBackground(Color.WHITE);
							setIcon(null);
							break;
			
			case VISIBLE:	setIcon(pic);
							break;
		}
	}
	
}
