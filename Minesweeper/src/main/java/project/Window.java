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

        ModelListener modelListener = new ModelListener();
        gameMap.addListener(modelListener);

    }

    private void initGUI() {

        Container cp = getContentPane();
        cp.setLayout(new GridLayout(dimension, dimension));

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Location location = new Location(i, j);
                generateAndAddNumberButton(cp, location);
            }
        }

    }

    private JButton generateButton() {
        JButton jButton = new JButton();
        jButton.setOpaque(true);
        jButton.setBackground(Color.LIGHT_GRAY);
        jButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        jButton.setHorizontalAlignment(JLabel.CENTER);
        jButton.setPreferredSize(new Dimension(70, 70));
        jButton.setFont(jButton.getFont().deriveFont(30f));
        return jButton;
    }

    private void generateAndAddNumberButton(Container cp, Location location) {
        JButton jButton = generateButton();
        LocationListener locationListener = new LocationListener(location);
        jButton.addActionListener(e -> { locationListener.locationClicked(this.gameMap); });
        cp.add(jButton);
    }

    private class ModelListener implements Listener {

        private ModelListener() {
            gameMap.addListener(this);
        }

        @Override
        public void locationClicked(GameMap gameMap) {
            Update();
        }

    }

    public void Update() {
        Container cp = getContentPane();
        Component[] components = cp.getComponents();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int index = j + i * dimension;
                Field field = gameMap.getField(i, j);
                if (field.isVisible()) {
                    if (field.isMine()) {
                        JButton button = (JButton) components[index];
                        button.setText("X");
                        button.setBackground(Color.WHITE);
                    } else {
                        JButton button = (JButton) components[index];
                        Integer numberOfNeighbourMines = field.getNumberOfNeighbourMines();
                        if (numberOfNeighbourMines != 0) {
                            button.setText(numberOfNeighbourMines.toString());
                        }
                        button.setBackground(Color.WHITE);
                    }
                }
            }
        }
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
