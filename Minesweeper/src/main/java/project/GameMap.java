package project;

import java.util.*;
import java.lang.Math;
import static project.Field.constructMineField;
import static project.Field.constructRegularField;

public class GameMap {

    private int dimension;
    private int numberOfMines;
    private List<Location> mineLocations;
    private Map<Location, Field> fields;

    public GameMap(int dimension, int numberOfMines) {
        this.dimension = dimension;
        this.numberOfMines = numberOfMines;
        this.mineLocations = generateMineLocations(dimension, numberOfMines);
        this.fields = generateFields(dimension, mineLocations);
    }

    public int getDimension() {
        return dimension;
    }
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }
    public int getNumberOfMines() {
        return numberOfMines;
    }
    public void setNumberOfMines(int numberOfMines) {
        this.numberOfMines = numberOfMines;
    }
    public List<Location> getMineLocations() {
        return mineLocations;
    }
    public void setMineLocations(List<Location> mineLocations) {
        this.mineLocations = mineLocations;
    }
    public Map<Location, Field> getFields() {
        return fields;
    }
    public void setFields(Map<Location, Field> fields) {
        this.fields = fields;
    }

    private List<Location> generateMineLocations(int dimension, int numberOfMines) {
        int min = 0;
        int max = dimension*dimension - 1;
        Set<Integer> mineLocationsIndices = new TreeSet<>();
        int mineCounter = 0;
        int index;
        boolean added;
        while (mineCounter < numberOfMines) {
            index = (int) (Math.random() * (max - min + 1) + min);
            added = mineLocationsIndices.add(index);
            if (added) mineCounter++;
        }

        List<Location> mineLocations = new ArrayList<>();
        int dimensionCounter = 0;
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (mineLocationsIndices.contains(dimensionCounter)) {
                    Location location = new Location(i, j);
                    mineLocations.add(location);
                }
                dimensionCounter++;
            }
        }
        return mineLocations;
    }

    private Map<Location, Field> generateFields(int dimension, List<Location> mineLocations) {
        Map<Location, Field> fields = new HashMap<>();
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                Location location = new Location(i, j);
                Field field;
                if (mineLocations.contains(location)) {
                    field = constructMineField();
                } else {
                    int numberOfNeighbourMines = countNeighbourMines(i, j, mineLocations);
                    field = constructRegularField(numberOfNeighbourMines);
                }
                fields.put(location, field);
            }
        }
        return fields;
    }

    private int countNeighbourMines(int row, int column, List<Location> mineLocations) {
        List<Location> neighbourLocations = List.of(
                                                new Location(row - 1, column - 1),
                                                new Location(row - 1, column),
                                                new Location(row - 1, column + 1),
                                                new Location(row, column - 1),
                                                new Location(row, column + 1),
                                                new Location(row + 1, column - 1),
                                                new Location(row + 1, column),
                                                new Location(row + 1, column + 1)
                                            );
        return (int) neighbourLocations
                        .stream()
                        .filter(location -> mineLocations.contains(location))
                        .count();
    }

    public Field getField(int row, int column) {
        return fields.get(new Location(row, column));
    }

    public Field getField(Location location) {
        return fields.get(location);
    }

    public boolean isVisible(Location location) {
        Field field = getField(location.getRow(), location.getColumn());
        return field.isVisible();
    }

    public boolean isMine(Location location) {
        Field field = getField(location.getRow(), location.getColumn());
        return field.isMine();
    }

    public void calculateGameMapChange(Location clickedLocation) {
        Field clickedField = this.getField(clickedLocation);
        clickedField.setVisible();
        if (clickedField.getNumberOfNeighbourMines() == 0) {
            setNeighboursVisible(clickedLocation);
        }
    }

    private void setNeighboursVisible(Location clickedLocation) {
        List<Location> neighbourLocations = getNeighbourLocations(clickedLocation);
        for (Location location : neighbourLocations) {
            Field field = this.getField(location);
            if (!field.isVisible()) {
                field.setVisible();
                if (field.getNumberOfNeighbourMines() == 0) {
                    setNeighboursVisible(location);
                }
            }
        }
    }

    private List<Location> getNeighbourLocations(Location clickedLocation) {
        int row = clickedLocation.getRow();
        int column = clickedLocation.getColumn();
        int dimension = this.getDimension();
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

}
