package project;

import java.util.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) {
        GameMap gameMap = new GameMap(10, 10);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            gameMap = play(gameMap, reader);
        }
    }

    private static GameMap play(GameMap gameMap, BufferedReader reader) {
        printGameMap(gameMap);
        Location clickedLocation = clickOnField(gameMap, reader);
        gameMap = calculateGameMapChange(gameMap, clickedLocation);
        return gameMap;
    }

    private static void printGameMap(GameMap gameMap) {
        int dimension = gameMap.getDimension();
        System.out.println();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Field field = gameMap.getField(i, j);
                if (field.isVisible()) {
                    if (field.isMine()) {
                        System.out.print(" X ");
                    } else {
                        System.out.print(" " + field.getNumberOfNeighbourMines() + " ");
                    }
                } else {
                    System.out.print(" ? ");
                }
            }
            System.out.println();
        }
    }

    private static Location clickOnField(GameMap gameMap, BufferedReader reader) {
        String input = null;
        try {
            System.out.print("Which field do you want to click? ");
            input = reader.readLine();
        } catch (IOException e) {
            System.out.println("Unable to read line. ");
            System.exit(0);
        }

        Location location = null;
        try {
            String[] inputParts = input.split(" ");
            if (inputParts.length != 2) {
                System.out.println("Incorrect input. Enter two numbers divided by a space.");
                clickOnField(gameMap, reader);
            }
            int row = Integer.parseInt(inputParts[0]) - 1;
            int column = Integer.parseInt(inputParts[1]) - 1;
            int dimension = gameMap.getDimension();
            if (row + 1 < 1 || column + 1 < 1 || row + 1 > dimension || column + 1 > dimension) {
                System.out.println("Incorrect input. Row and column must be whole numbers bigger that 0, and smaller than " + dimension + ".");
                clickOnField(gameMap, reader);
            }
            location = new Location(row, column);
        } catch (Exception ex) {
            System.out.println("Incorrect input. Row and column must be whole numbers.");
            clickOnField(gameMap, reader);
        }

        if (gameMap.isVisible(location)) {
            System.out.println("Field already clicked.");
            clickOnField(gameMap, reader);
        }

        if (gameMap.isMine(location)) {
            System.out.println("You clicked the mine and died.");
            printEndGameMap(gameMap);
            System.exit(0);
        }

        return location;
    }

    private static GameMap calculateGameMapChange(GameMap gameMap, Location clickedLocation) {
        Field clickedField = gameMap.getField(clickedLocation);
        clickedField.setVisible();
        if (clickedField.getNumberOfNeighbourMines() == 0) {
            gameMap = setNeighboursVisible(gameMap, clickedLocation);
        }
        return gameMap;
    }

    private static GameMap setNeighboursVisible(GameMap gameMap, Location clickedLocation) {
        List<Location> neighbourLocations = getNeighbourLocations(gameMap, clickedLocation);
        for (Location location : neighbourLocations) {
            Field field = gameMap.getField(location);
            if (!field.isVisible()) {
                field.setVisible();
                if (field.getNumberOfNeighbourMines() == 0) {
                    gameMap = setNeighboursVisible(gameMap, location);
                }
            }
        }
        return gameMap;
    }

    private static List<Location> getNeighbourLocations(GameMap gameMap, Location clickedLocation) {
        int row = clickedLocation.getRow();
        int column = clickedLocation.getColumn();
        int dimension = gameMap.getDimension();
        List<Location> neighbourLocations = new ArrayList<>();
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                if (i >= 0 && j >= 0 && i <= dimension - 1 && j <= dimension - 1) {
                    if (i != clickedLocation.getRow() || j != clickedLocation.getColumn()) {
                        neighbourLocations.add(new Location(i, j));
                    }
                }
            }
        }
        return neighbourLocations;
    }

    private static void printEndGameMap(GameMap gameMap) {
        int dimension = gameMap.getDimension();
        System.out.println();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Field field = gameMap.getField(i, j);
                if (field.isMine()) {
                    System.out.print(" X ");
                } else {
                    System.out.print(" " + field.getNumberOfNeighbourMines() + " ");
                }
            }
            System.out.println();
        }
    }

}