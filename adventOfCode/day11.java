package adventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class day11 {
    public static void main (String[] args) throws Exception {
        BufferedReader file = new BufferedReader(new FileReader(
            "C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day11.txt"));
        //part1(file);
        part2(file);
    }

    public static void part1 (BufferedReader f) throws Exception {
        long startTime = System.nanoTime();
        int galaxyIncreaseRate = 10;
        ArrayList<String> fList = new ArrayList<String>();
        String line;
        while ((line = f.readLine()) != null)
            fList.add(line);
        
        //find rows and columns with no galaxies
        ArrayList<Integer> rowsWithoutGalaxies = new ArrayList<Integer>();
        ArrayList<Integer> colsWithoutGalaxies = new ArrayList<Integer>();

        for (int rows = 0; rows < fList.size(); rows++) {
            String row = fList.get(rows);
            if (fList.get(rows).indexOf('#') == -1)
                rowsWithoutGalaxies.add(rows);
            for (int cols = 0; cols < row.length(); cols++) {
                for (int searchDown = 0; searchDown + rows < fList.size(); searchDown++) {
                    if (fList.get(searchDown + rows).charAt(cols) == '#')
                        break;
                    if (searchDown == fList.size() - 1)
                        colsWithoutGalaxies.add(cols);
                }
            }
        }

        System.out.println("There are " + colsWithoutGalaxies.size() + " column and " + rowsWithoutGalaxies.size() + " row expansions planned");

        String manyPeriods = "";
        for (int increase = 0; increase < galaxyIncreaseRate - 1; increase ++) {
            manyPeriods += ".";
        }

        System.out.println("Expanding galaxy columns...");
        int incCount = 0;
        for (int cols = 0; cols < fList.get(0).length(); cols++) {
            for (int galaxies : colsWithoutGalaxies) {
                if (cols == galaxies + incCount) {
                    for (int rows = 0; rows < fList.size(); rows++) {
                        fList.set(rows, fList.get(rows).substring(0, cols) + manyPeriods  + fList.get(rows).substring(cols));
                        System.out.print("\rExpanding galaxy coordinates: (" + rows + ", " + cols + ")");
                    }
                    incCount += galaxyIncreaseRate - 1;
                    cols += galaxyIncreaseRate - 1;
                }
            }
            //System.out.print("\rExpanding galaxy coordinates: " + cols);
        }
        //System.out.println("rows without galaxies: " + rowsWithoutGalaxies);
        //System.out.println("galaxy with doubled size rows where there are no galaxies: \n");
        System.out.println("\nExpanding galaxy rows...");

        incCount = 0;
        for (int rows = 0; rows < fList.size(); rows++) {
            for (int galaxies : rowsWithoutGalaxies) {
                if (rows == galaxies + incCount) {
                    for (int increase = 0; increase < galaxyIncreaseRate - 1; increase ++) {
                        fList.add(rows, fList.get(rows));
                    }
                    incCount += galaxyIncreaseRate - 1;
                    rows += galaxyIncreaseRate - 1;
                    
                    //incCount += 10;
                    //rows ++;
                }
            }
            System.out.print("\rExpanding galaxy row: " + rows);
        }

        // for (int rows = 0; rows < fList.size(); rows++) {
        //     System.out.println(String.format("%-2d", rows) + " " + fList.get(rows));
        // }

        System.out.println("\nadding galaxy coordinates to list...");
        ArrayList<int[]> galaxyCoords = new ArrayList<int[]>();
        for (int rows = 0; rows < fList.size(); rows++) {
            for (int cols = 0; cols < fList.get(rows).length(); cols++) {
                if (fList.get(rows).charAt(cols) == '#') {
                    galaxyCoords.add(new int[]{rows, cols});
                }
            }
            System.out.print("\rSearch progress: row " + rows);
        }
        // for (int[] coords : galaxyCoords) {
        //     System.out.println("(" + coords[0] + ", " + coords[1] + ")");
        // }
        
        System.out.println("\ngalaxy created...");

        int totalDistance = 0;
        for (int i = 0; i < galaxyCoords.size(); i++) {
            for (int j = i + 1; j < galaxyCoords.size(); j++) {
                int rowDistance = Math.abs(galaxyCoords.get(i)[0] - galaxyCoords.get(j)[0]);
                int colDistance = Math.abs(galaxyCoords.get(i)[1] - galaxyCoords.get(j)[1]);
                totalDistance += rowDistance + colDistance;
                //System.out.println("total distance between galaxies " + i + ", " + j + ": " + (rowDistance + colDistance));
            }
        }
        System.out.println("total distance between all pairs of galaxies: " + totalDistance);
        long endTime = System.nanoTime();
        System.out.println("Time taken to calculate: " + (endTime - startTime) / 1000000 + " ms");

    }

    public static void part2 (BufferedReader f) throws Exception {
        int galaxyIncreaseAmount = 1000000;
        ArrayList<String> fList = new ArrayList<String>();
        String line;
        while ((line = f.readLine()) != null)
            fList.add(line);
        
        //find rows and columns with no galaxies and store galaxy coordinates
        ArrayList<Integer> rowsWithoutGalaxies = new ArrayList<Integer>();
        ArrayList<Integer> colsWithoutGalaxies = new ArrayList<Integer>();
        ArrayList<int[]> galaxyCoordinates = new ArrayList<int[]>();

        for (int rows = 0; rows < fList.size(); rows++) {
            String row = fList.get(rows);
            if (fList.get(rows).indexOf('#') == -1)
                rowsWithoutGalaxies.add(rows);
            for (int cols = 0; cols < row.length(); cols++) {
                if (fList.get(rows).charAt(cols) == '#') {
                    galaxyCoordinates.add(new int[]{rows, cols});
                }
                for (int searchDown = 0; searchDown + rows < fList.size(); searchDown++) {
                    if (fList.get(searchDown + rows).charAt(cols) == '#') {
                        break;
                    }
                    if (searchDown == fList.size() - 1)
                        colsWithoutGalaxies.add(cols);
                }
            }
        }
        System.out.println("Rows without galaxies: " + rowsWithoutGalaxies + "\nColumns without galaxies: " + colsWithoutGalaxies);

        long totalDistance = 0;
        for (int i = 0; i < galaxyCoordinates.size(); i ++) {
            for (int j = i + 1; j < galaxyCoordinates.size(); j ++) {
                int rowDistance = Math.abs(galaxyCoordinates.get(i)[0] - galaxyCoordinates.get(j)[0]);
                int colDistance = Math.abs(galaxyCoordinates.get(i)[1] - galaxyCoordinates.get(j)[1]);
                for (int blankrow : rowsWithoutGalaxies) {
                    if ((galaxyCoordinates.get(i)[0] < blankrow && galaxyCoordinates.get(j)[0] > blankrow) ||
                    (galaxyCoordinates.get(j)[0] < blankrow && galaxyCoordinates.get(i)[0] > blankrow)) {
                        colDistance += galaxyIncreaseAmount - 1;
                    }
                }
                for (int blankcol : colsWithoutGalaxies) {
                    if ((galaxyCoordinates.get(i)[1] < blankcol && galaxyCoordinates.get(j)[1] > blankcol) || 
                    (galaxyCoordinates.get(j)[1] < blankcol && galaxyCoordinates.get(i)[1] > blankcol)) {
                        rowDistance += galaxyIncreaseAmount - 1;
                    }
                }
                totalDistance += rowDistance + colDistance;
            }
        }
        System.out.println("total distance: " + totalDistance);
    }
}