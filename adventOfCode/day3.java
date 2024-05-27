//https://adventofcode.com/2023/day/3

package adventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class day3 {
    public static void main(String[] args) throws Exception {
        BufferedReader file = new BufferedReader(new FileReader(
            "C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day3.txt"));
        part1(file);
        //part2(file);
    }

    public static void part1 (BufferedReader f) throws Exception {
        ArrayList<String> fList = new ArrayList<String>();
        String line;

        while ((line = f.readLine()) != null) {
            fList.add(line);
        }
        int strLength = fList.get(0).length();
        int totalSum = 0;

        for (int i = 0; i < fList.size(); i++) {
            String str = fList.get(i);
            int[] numCoord = new int[2]; //[number start index][number end index]
            numCoord[0] = -1;

            for (int j = 0; j < strLength; j++) {
                if (Character.isDigit(str.charAt(j))) {
                    numCoord[1] = j;
                    if (numCoord[0] == -1) {
                        numCoord[0] = j;
                    }
                    else {
                        numCoord[1] = j;
                    }
                }
                boolean symbolNear = false;
                boolean finishedNumber = false;
                if (j == numCoord[1] + 1 || numCoord[1] == strLength - 1) { // number ended on prev character
                    finishedNumber = true;
                    //check all coords around the number
                    //System.out.println("past the number now " + numCoord[1]);
                    for (int k = -1; k < 2; k++) {
                        if (symbolNear)
                            break;
                        for (int l = 0; l < (numCoord[1] - numCoord[0] + 3); l++) {
                            if (symbolNear)
                                break;
                            try {
                                if (isSymbol(fList.get(i + k).charAt(numCoord[0] + l - 1))) {//check all the characters in a box around the number coords
                                    symbolNear = true;
                                }
                            }
                            catch (IndexOutOfBoundsException e) {
                            }
                        }
                    }
                }
                if (symbolNear) {
                    int coordValue = Integer.parseInt(str.substring(numCoord[0], numCoord[1] + 1));
                    System.out.println(coordValue);
                    totalSum += coordValue;
                }
                if (finishedNumber) {
                    numCoord[0] = -1;
                }
            }
        }
        System.out.println("TOTAL: " + totalSum);
    }
    public static void part2 (BufferedReader f) throws Exception {
        ArrayList<String> fList = new ArrayList<String>();
        String line;
        while ((line = f.readLine()) != null)
            fList.add(line);
        int totalRatio = 0;
        for (int i = 0; i < fList.size(); i++) {
            String str = fList.get(i);
            int strLength = str.length();
            for (int j = 0; j < strLength; j++) {
                if (str.charAt(j) == '*') {
                    int numFound = 0;
                    int[][] coordStore = new int[2][3]; //{number counter}{coords}
                    for (int k = -1; k < 2; k++) {
                        for(int l = -1; l < 2; l++) {
                            try {
                                if (Character.isDigit(fList.get(i + k).charAt(j + l))) {
                                    //get number
                                    int[] coords = {i + k, j + l, j + l}; //{row #}{first number coordinate}{last number coordinate}
                                    try {
                                        for (int back = -1; Character.isDigit(fList.get(i + k).charAt(j + l + back)); back--) {
                                            coords[1] = j + l + back;
                                        }
                                    }
                                    catch (IndexOutOfBoundsException e) {}   
                                    int forw = 1;
                                    try {
                                        for (forw = 1; Character.isDigit(fList.get(i + k).charAt(j + l + forw)); forw++) {
                                            coords[2] = j + l + forw;
                                        }
                                    }
                                    catch (IndexOutOfBoundsException e) {} 
                                    l += forw - 1;
                                    coordStore[numFound] = coords;
                                    numFound ++;
                                }
                            }
                            catch (IndexOutOfBoundsException e) {}
                        }
                    }
                    if (numFound == 2) {
                        String s1 = fList.get(coordStore[0][0]);
                        String s2 = fList.get(coordStore[1][0]);
                        int num1 = Integer.parseInt(s1.substring(coordStore[0][1], coordStore[0][2] + 1));
                        int num2 = Integer.parseInt(s2.substring(coordStore[1][1], coordStore[1][2] + 1));
                        int ratio = num1 * num2;
                        System.out.println("found * at i, j: (" + i + ", " + j + "), 2 nearby numbers are: (" + num1 + ", " + num2 + ") gear ratio: " + ratio);
                        totalRatio += ratio;
                    }
                }
            }
        }
        System.out.println("total ratio: " + totalRatio);
    }

    public static boolean isSymbol (char c) {
        if (!Character.isDigit(c) && c != '.')
            return true;
        return false;
    }
}
