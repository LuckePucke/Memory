import java.io.File;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Memory extends JFrame implements ActionListener {
	
    File folder		= new File("mypictures");
    File[] pictures = folder.listFiles();
    Card[] allCards = new Card[pictures.length];
    Card[] cards;
    
    JPanel upp,ner,spelare,spelplan;
    JButton nytt,slut;
    
    int X, Y, p;
    Spelare[] players;
    int player = 0;
    int fas = 0; 
    Card[] visibleCards = new Card[2];
    Timer timer = new Timer(1500, this);
    
    public Memory() {
    	
    	// Skapa allCards
        for (int i = 0; i < pictures.length; i++) {
            allCards[i] = new Card(new ImageIcon(pictures[i].getPath()));
        }
        
        // JObjekt initiering
        spelare = new JPanel();
        spelplan = new JPanel();
        upp = new JPanel();
        ner = new JPanel();
        nytt = new JButton("Nytt");
        slut = new JButton("Avsluta");
        
        setLayout(new BorderLayout());
        
        ner.setLayout(new GridLayout(1,2));
        add(ner,BorderLayout.SOUTH);
        add(spelare,BorderLayout.WEST);
        add(spelplan,BorderLayout.CENTER);
        ner.add(nytt);      ner.add(slut);
        
        nytt.addActionListener(this);
        slut.addActionListener(this);
        
        newGame();
    }

    public void newGame() {
    	
    	// Antal spelare
        p = Integer.parseInt(JOptionPane.showInputDialog("Hur många spelare?"));
        players = new Spelare[p];
        
        // Skapa spelare och återställ fas
        for (int i = 0; i < p; i++) {
        	players[i] = new Spelare(i+1);
        }
    	spelare.removeAll();
    	spelare.setLayout(new GridLayout(p, 1));
    	for (int i = 0; i < p; i++) {
        	spelare.add(players[i]);
        }
        players[0].toggleTurn();
        player = 0;
    	fas = 0;
    	
    	// Bestäm planstorlek och skapa spelplan
    	do {
    		X = Integer.parseInt(JOptionPane.showInputDialog("Hur många rader?"));
            Y = Integer.parseInt(JOptionPane.showInputDialog("Hur många kolumner?"));
            
            if((X*Y % 2) != 0 ){
            	JOptionPane.showMessageDialog(null, "Måste vara ett jämnt antal kort, max 36 st!");
            }
            
    	} while (X*Y%2 != 0 && X*Y > 36);
    	
    	// Slumpa kort på planen
    	Tools.randomOrder(allCards);
    	cards = new Card[X*Y];
        for (int i = 0; i < X*Y/2; i++) {
            cards[i] = allCards[i].copy();
            cards[i].addActionListener(this);
            cards[X*Y/2 + i] = allCards[i].copy();
            cards[X*Y/2 + i].addActionListener(this);
        }
        Tools.randomOrder(cards);
        
        // Lägg kort på planen
    	spelplan.removeAll();
        spelplan.setLayout(new GridLayout(X, Y));
        for (int i = 0; i < X*Y; i++) {
            cards[i].setActionCommand(Integer.toString(i));
            spelplan.add(cards[i]);
        }
        
        pack();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nytt)){
            newGame();
        }
        else if (e.getSource().equals(slut)){
            System.exit(0);
        }
        else if (e.getSource().equals(timer)) {
            if (visibleCards[0].equalIcon(visibleCards[1])) {
                visibleCards[0].setStatus(Card.Status.MISSING);
                visibleCards[1].setStatus(Card.Status.MISSING);
            }
            else {
                visibleCards[0].setStatus(Card.Status.HIDDEN);
                visibleCards[1].setStatus(Card.Status.HIDDEN);
                players[player].toggleTurn();
                if (player < p-1) {
                	player++;
                }
                else {
                	player = 0;
                }
                players[player].toggleTurn();
            }
            fas = 0;
            timer.stop();
            int x = 0;
            for (int i = 0; i < p; i++) {
            	x += players[i].getPoints();
            }
            if (x == X*Y/2) {
            	if(JOptionPane.showConfirmDialog(null,
            			"Vill du spela igen?", "", JOptionPane.YES_NO_OPTION) == 0) {
            		newGame();
            	}
            }
        }
        else {
            String iString = e.getActionCommand();
            int i = Integer.parseInt(iString);
            if (fas == 0 && cards[i].getStatus() != Card.Status.MISSING) {
                cards[i].setStatus(Card.Status.VISIBLE); 
                visibleCards[0] = cards[i];
                fas = 1;
            }
            else if (fas == 1 && cards[i].getStatus() != Card.Status.MISSING) {
                cards[i].setStatus(Card.Status.VISIBLE);
                visibleCards[1] = cards[i];
                fas = 2;
                if (visibleCards[0].equalIcon(visibleCards[1])) {
                	players[player].addPoint();
                }
                timer.start();
            }
        }
        
    }
    
    public static void main(String[] args) {
        Memory game = new Memory();
    }      
}


