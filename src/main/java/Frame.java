import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    private JLabel idLbl;
    public Frame(String id) {
        super();
        this.setSize(new Dimension(300, 100));
        this.setTitle("Fernwartung Client ID");
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout());
        idLbl = new JLabel(id, SwingConstants.CENTER);
        idLbl.setBounds(0,0,300, 50);
        idLbl.setFont(new Font("Avant Garde", 0, 25));
        this.add(idLbl);
        this.setVisible(true);
    }
    public void changeID(String id){
        idLbl.setText(id);
    }
}
