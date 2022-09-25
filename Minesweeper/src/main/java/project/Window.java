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
        cp.setLayout(new BorderLayout());

        JLabel jLabel = generateLabel();

        JPanel p = new JPanel();
        p.setLayout(new GridLayout(dimension, dimension));

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Location location = new Location(i, j);
                JButton jButton = generateButtonAndAddListener(location);
                p.add(jButton);
            }
        }

        cp.add(jLabel, BorderLayout.PAGE_START);
        cp.add(p, BorderLayout.CENTER);

    }

    private JLabel generateLabel() {
        JLabel jLabel = new JLabel();
        jLabel.setOpaque(true);
        jLabel.setBackground(Color.LIGHT_GRAY);
        jLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        jLabel.setHorizontalAlignment(JLabel.CENTER);
        jLabel.setPreferredSize(new Dimension(70 * dimension, 70));
        jLabel.setFont(jLabel.getFont().deriveFont(30f));
        return jLabel;
    }

    private JButton generateButtonAndAddListener(Location location) {
        JButton jButton = new JButton();
        jButton.setOpaque(true);
        jButton.setBackground(Color.LIGHT_GRAY);
        jButton.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        jButton.setHorizontalAlignment(JLabel.CENTER);
        jButton.setPreferredSize(new Dimension(70, 70));
        jButton.setFont(jButton.getFont().deriveFont(30f));
        LocationListener locationListener = new LocationListener(location);
        jButton.addActionListener(e -> { locationListener.locationClicked(this.gameMap); });
        return jButton;
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
        Component[] contentPaneComponents = cp.getComponents();
        Component[] panelComponents = ((JPanel) contentPaneComponents[1]).getComponents();

        if (gameMap.getGameStatus() == GameStatus.lost) {
            ((JLabel) contentPaneComponents[0]).setText("You lost! :(");
        } else if (gameMap.getGameStatus() == GameStatus.won) {
            ((JLabel) contentPaneComponents[0]).setText("You won! :)");
        }

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                int index = j + i * dimension;
                Field field = gameMap.getField(i, j);
                if (field.isVisible()) {
                    JButton button = (JButton) panelComponents[index];
                    if (field.isMine()) {
                        button.setText("X");
                        button.setBackground(Color.WHITE);
                    } else {
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
