package project;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {

    private static GameMap gameMap;
    private static int dimension = 10;
    private static int numberOfMines = 10;

    public Window() {

        super();
        this.gameMap = new GameMap(dimension, numberOfMines);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Minesweeper");
        setLocation(20, 20);
        setSize(500, 200);
        initGUI();

        pack();

    }

    private void initGUI() {

        Container cp = getContentPane();
        cp.setLayout(new GridLayout(dimension, dimension));

        for (int i = 0; i < dimension * dimension; i++) {
            generateAndAddNumberButton(cp);
        }

    }

    private JButton generateButton() {
        JButton jButton = new JButton();
        jButton.setOpaque(true);
        jButton.setBackground(Color.LIGHT_GRAY);
        jButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        jButton.setHorizontalAlignment(JLabel.CENTER);
        jButton.setPreferredSize(new Dimension(70, 70));
        jButton.setFont(jButton.getFont().deriveFont(15f));
        return jButton;
    }

    private void generateAndAddNumberButton(Container cp) {
        JButton jButton = generateButton();
        // todo
        //NumberCalcValueListener numberCalcValueListener = new NumberCalcValueListener(number);
        //jButton.addActionListener(e -> { numberCalcValueListener.valueChanged(this.calcModel); });
        cp.add(jButton);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window window = new Window();
                window.setVisible(true);
            }
        });
    }

}
