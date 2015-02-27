import java.io.File;

import javax.swing.*;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Memory extends JFrame implements ActionListener {
    File folder;
    File[] pictures;
    Card[] allCards;
    Card[] cards;
    int X, Y;
    JPanel upp,ner,spelare,spelplan;
    JButton nytt,slut;
    int player = 0;
    int fas = 0; 
    Card[] visibleCards = new Card[2];
    Timer timer = new Timer(1500, this);
    
    public Memory() {
        folder =  new File("mypictures");
        System.out.println(folder.getAbsolutePath());
        pictures  = folder.listFiles();
        
        allCards= new Card[pictures.length];
        spelare = new JPanel();
        spelplan = new JPanel();
        upp = new JPanel();
        ner = new JPanel();
        nytt = new JButton("Nytt");
        slut = new JButton("Avsluta");
        
        for (int i = 0; i < pictures.length; i++) {
            allCards[i] = new Card(new ImageIcon(pictures[i].getPath()));
        }
        
        setLayout(new BorderLayout());
        
        ner.setLayout(new GridLayout(1,2));
        spelare.setLayout(new GridLayout(2, 1));
        add(ner,BorderLayout.SOUTH);
        add(spelare,BorderLayout.WEST);
        add(spelplan,BorderLayout.CENTER);
        ner.add(nytt);      ner.add(slut);
        
        newGame();
    }

    public void newGame() {
        X = Integer.parseInt(JOptionPane.showInputDialog("Hur många rader?"));
        Y = Integer.parseInt(JOptionPane.showInputDialog("Hur många kolumner?"));
        spelplan.removeAll();
        Tools.randomOrder(allCards);
        cards = new Card[X*Y];
        for (int i = 0; i < X*Y/2; i++) {
            cards[i] = allCards[i].copy();
            cards[i].addActionListener(this);
            cards[X*Y/2 + i] = allCards[i].copy();
            cards[X*Y/2 + i].addActionListener(this);
        }
        
        Tools.randomOrder(cards);
        
        spelplan.setLayout(new GridLayout(X, Y));
        for (int i = 0; i < X*Y; i++) {
            cards[i].setActionCommand(Integer.toString(i));
            spelplan.add(cards[i]);
        }
        nytt.addActionListener(this);
        slut.addActionListener(this);
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
            }
            fas = 0;
            if (player == 0) {player = 1;} else if (player == 1) {player = 0;}
            timer.stop();
        }
        else {
            String iString = e.getActionCommand();
            int i = Integer.parseInt(iString);
            if (fas == 0) {
                cards[i].setStatus(Card.Status.VISIBLE); 
                visibleCards[0] = cards[i];
                fas = 1;
            }
            else if (fas == 1) {
                cards[i].setStatus(Card.Status.VISIBLE);
                visibleCards[1] = cards[i];
                fas = 2;
                timer.start();
                
            }
        }
        
    }
    
    public static void main(String[] args) {
        Memory game = new Memory();
    }      
}


