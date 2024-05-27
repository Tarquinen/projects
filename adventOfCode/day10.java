// https://adventofcode.com/2023/day/10

package adventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;

public class day10 {
    public static void main (String[] args) throws Exception {
        BufferedReader file = new BufferedReader(new FileReader(
            "C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day10.txt"));
        part1(file);
    }

    public static void part1 (BufferedReader f) throws Exception {
        ArrayList<String> fList = new ArrayList<String>();
        String line;
        while ((line = f.readLine()) != null)
            fList.add(line);
        int[] mouseLoc = new int[2]; //mouse starting location {row #, col #}
        for (int rows = 0; rows < fList.size(); rows++) { // find the mouse
            String row = fList.get(rows);
            int rowLen = row.length();
            for (int cols = 0; cols < rowLen; cols ++) {
                if (row.charAt(cols) == 'S') {
                    mouseLoc[0] = rows;
                    mouseLoc[1] = cols;
                }
            }
        }
        boolean startingPoint = false;
        char lastMovedDir = ' ';
        int rowLen = fList.get(0).length();
        int colLen = fList.size();
        int[] searchStart = Arrays.copyOf(mouseLoc, 2); //{row #, Col #}
        char[][] pipeRoute = new char[colLen][rowLen]; //chart of all connected pipes with trash pipes pieces removed

        for (int rows = 0; rows < fList.size(); rows++) { // initial pipe chart population
            for (int cols = 0; cols < rowLen; cols ++) {
                pipeRoute[rows][cols] = '.';
            }
        }

        pipeRoute[mouseLoc[0]][mouseLoc[1]] = 'S'; // place mouse in pipe chart

        int steps = 0;
        while (!startingPoint) {
            steps ++;
            boolean canGoLeft = searchStart[1] != 0;
            boolean canGoRight = searchStart[1] != rowLen - 1;
            boolean canGoDown = searchStart[0] != colLen - 1;
            boolean CanGoUp = searchStart[0] != 0;
            if (canGoLeft && lastMovedDir != 'R' && legalPipe(searchStart, 'L', fList)) {
                searchStart[1] --;
                lastMovedDir = 'L';
                pipeRoute[searchStart[0]][searchStart[1]] = fList.get(searchStart[0]).charAt(searchStart[1]);
            }
            else if (canGoRight && lastMovedDir != 'L' && legalPipe(searchStart, 'R', fList)) {
                searchStart[1] ++;
                lastMovedDir = 'R';
                pipeRoute[searchStart[0]][searchStart[1]] = fList.get(searchStart[0]).charAt(searchStart[1]);
            }
            else if (CanGoUp && lastMovedDir != 'D' && legalPipe(searchStart, 'U', fList)) {
                searchStart[0] --;
                lastMovedDir = 'U';
                pipeRoute[searchStart[0]][searchStart[1]] = fList.get(searchStart[0]).charAt(searchStart[1]);
            }
            else if (canGoDown && lastMovedDir != 'U' && legalPipe(searchStart, 'D', fList)) {
                searchStart[0] ++;
                lastMovedDir = 'D';
                pipeRoute[searchStart[0]][searchStart[1]] = fList.get(searchStart[0]).charAt(searchStart[1]);
            }
            else {
                startingPoint = true;
            }
            // System.out.println("Step #: " + steps + ", checked pipe in the " + lastMovedDir + " direction, current pipe ("
            //     + searchStart[0] + ", " + searchStart[1] + "): " + fList.get(searchStart[0]).charAt(searchStart[1]));
        }

        // part2
        int pipeOutsideLoop = 0;
        for (int rows = 0; rows < fList.size(); rows++) {
            for (int cols = 0; cols < rowLen; cols ++) {
                if (mouseLoc[0] == rows) { //dont check junk pipe if mouse is on same row/col
                    // search down
                    int crossingPipes = 0;
                    if (pipeRoute[rows][cols] == '.') { // junk pipe found
                        char corner = ' ';
                        for (int down = 0; rows + down <  fList.size(); down ++) {
                            if (pipeRoute[rows + down][cols] == '.') {
                                continue;
                            } 
                            else if (pipeRoute[rows + down][cols] == '|') {
                                continue;
                            } 
                            else if (pipeRoute[rows + down][cols] == '-') {
                                crossingPipes ++;
                            }
                            else {
                                if (pipeRoute[rows + down][cols] == 'J' && corner == 'F') {
                                    crossingPipes ++;
                                }
                                if (pipeRoute[rows + down][cols] == 'L' && corner == '7') {
                                    crossingPipes ++;
                                }
                                corner = pipeRoute[rows + down][cols];
                            }
                        }
                        if (crossingPipes % 2 == 1) { // if theres an odd amount of crossing pipes, nest/junk pipe is within the loop
                            pipeOutsideLoop++;
                            pipeRoute[rows][cols] = 'X';
                        }
                        else {
                            pipeRoute[rows][cols] = 'O';
                        }
                    }
                }
                else {
                    // search right
                    int crossingPipes = 0;
                    if (pipeRoute[rows][cols] == '.') {
                        char corner = ' ';
                        for (int right = 0; cols + right < fList.get(rows).length(); right ++) {
                            if (pipeRoute[rows][cols + right] == '.') {
                                continue;
                            } 
                            else if (pipeRoute[rows][cols + right] == '-') {
                                continue;
                            } 
                            else if (pipeRoute[rows][cols + right] == '|') {
                                crossingPipes ++;
                            }
                            else {
                                if (pipeRoute[rows][cols + right] == 'J' && corner == 'F') {
                                    crossingPipes ++;
                                }
                                if (pipeRoute[rows][cols + right] == '7' && corner == 'L') {
                                    crossingPipes ++;
                                }
                                corner = pipeRoute[rows][cols + right];
                            }
                        }
                        if (crossingPipes % 2 == 1) {
                            pipeOutsideLoop++;
                            pipeRoute[rows][cols] = 'X';
                        }
                        else {
                            pipeRoute[rows][cols] = 'O';
                        }
                    }
                }
            }
        } 
        System.out.println("pieces outslide loop: " + pipeOutsideLoop);
        
        System.out.print("resulting pipe loop with mouse starting at " + Arrays.toString(mouseLoc) + ": ");        
        for (int rows = 0; rows < fList.size(); rows++) {
            System.out.println("");
            for (int cols = 0; cols < rowLen; cols ++) {
                System.out.print(pipeRoute[rows][cols] + " ");
            }
        }
        
        System.out.print("\nThe furthest point a mouse can be is " + steps/2 + " steps away.");
    }

    public static boolean legalPipe (int[] coord, char direction, ArrayList<String> f) {
        if (direction == 'L' && ((f.get(coord[0]).charAt(coord[1]) == 'J') || (f.get(coord[0]).charAt(coord[1]) == '7') || (f.get(coord[0]).charAt(coord[1]) == '-') || (f.get(coord[0]).charAt(coord[1]) == 'S'))) {
            if ((f.get(coord[0]).charAt(coord[1] - 1) == 'L') || (f.get(coord[0]).charAt(coord[1] - 1) == 'F') || (f.get(coord[0]).charAt(coord[1] - 1) == '-')) {
                return true;
            }
        }
        else if (direction == 'R' && ((f.get(coord[0]).charAt(coord[1]) == 'F') || (f.get(coord[0]).charAt(coord[1]) == 'L') || (f.get(coord[0]).charAt(coord[1]) == '-') || (f.get(coord[0]).charAt(coord[1]) == 'S'))) {
            if ((f.get(coord[0]).charAt(coord[1] + 1) == 'J') || (f.get(coord[0]).charAt(coord[1] + 1) == '7') || (f.get(coord[0]).charAt(coord[1] + 1) == '-')) {
                return true;
            }
        }
        else if (direction == 'U' && ((f.get(coord[0]).charAt(coord[1]) == 'J') || (f.get(coord[0]).charAt(coord[1]) == 'L') || (f.get(coord[0]).charAt(coord[1]) == '|') || (f.get(coord[0]).charAt(coord[1]) == 'S'))) {
            if ((f.get(coord[0] - 1).charAt(coord[1]) == 'F') || (f.get(coord[0] - 1).charAt(coord[1]) == '7') || (f.get(coord[0] - 1).charAt(coord[1]) == '|')) {
                return true;
            }
        }
        else if (direction == 'D' && ((f.get(coord[0]).charAt(coord[1]) == 'F') || (f.get(coord[0]).charAt(coord[1]) == '7') || (f.get(coord[0]).charAt(coord[1]) == '|') || (f.get(coord[0]).charAt(coord[1]) == 'S'))) {
            if ((f.get(coord[0] + 1).charAt(coord[1]) == 'J') || (f.get(coord[0] + 1).charAt(coord[1]) == 'L') || (f.get(coord[0] + 1).charAt(coord[1]) == '|')) {
                return true;
            }
        }
        return false;
    }
}
