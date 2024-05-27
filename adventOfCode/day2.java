//https://adventofcode.com/2023/day/2

package adventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class day2 {
    public static void main (String[] args) throws Exception {
        BufferedReader file = new BufferedReader(new FileReader(
            "C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day2.txt"));
        //part1(file);
        part2(file);
    }


    public static void part1 (BufferedReader f) throws Exception {
        ArrayList<String> fList = new ArrayList<String>();
        String line;

        while ((line = f.readLine()) != null) {
            fList.add(line);
        }

        int IdsSum = 0;
        for (int i = 0; i < fList.size(); i++) { // iterate through each row in the file
            String str = fList.get(i);
            int strLength = str.length();
            int gameNum = i + 1;
            //System.out.println("game " + gameNum + ": ids sum: " + IdsSum);
            boolean rowPasses = true;

            for (int j = 0; j < strLength - 2; j ++) {
                if (Character.isDigit(str.charAt(j)) && Character.isDigit(str.charAt(j + 1)) && str.charAt(j + 2) != ':') {
                    //System.out.println("checking number");
                    if (Integer.parseInt(str.substring(j, j + 2)) > 14) {
                        //fails
                        IdsSum -= gameNum;
                        //System.out.println("fail1");
                        rowPasses = false;
                        break;
                    }
                    else if ((Integer.parseInt(str.substring(j, j + 2)) == 14) && (!str.substring(j + 3, j + 7).equals("blue"))) {
                        //fails
                        IdsSum -= gameNum;
                        //System.out.println("fail2");
                        rowPasses = false;
                        break;
                    }
                    else if ((Integer.parseInt(str.substring(j, j + 2)) == 13) && 
                    (!str.substring(j + 3, j + 7).equals("blue")) &&
                    (!str.substring(j + 3, j + 8).equals("green"))) {
                        //fails
                        IdsSum -= gameNum;
                        //System.out.println("fail3");
                        rowPasses = false;
                        break;
                    }
                    else if ((Integer.parseInt(str.substring(j, j + 2)) == 12) && 
                    (!str.substring(j + 3, j + 7).equals("blue")) &&
                    (!str.substring(j + 3, j + 8).equals("green")) &&
                    (!str.substring(j + 3, j + 6).equals("red"))) {
                        //fails
                        IdsSum -= gameNum;
                        //System.out.println("fail4");
                        rowPasses = false;
                        break;
                    }
                }
            }
            IdsSum += gameNum;
            if (rowPasses)
                System.out.println("game " + gameNum + ": legal row, ids sum: " + IdsSum);
            else   
                System.out.println("game " + gameNum + ": not legal row, ids sum: " + IdsSum);
        }
        System.out.println("IDs sum: " + IdsSum);
    }

    public static void part2 (BufferedReader f) throws Exception {
        ArrayList<String> fList = new ArrayList<String>();
        String line;

        while ((line = f.readLine()) != null) {
            fList.add(line);
        }
        int totalSum = 0;
        for (int i = 0; i < fList.size(); i++) {
            String str = fList.get(i);
            int strLength = str.length();
            boolean pastColon = false;
            int[] minValues = new int[3];
            for (int j = 0; j < strLength; j++) {
                if (str.charAt(j) == ':')
                    pastColon = true;
                if (pastColon) {
                    if (Character.isDigit(str.charAt(j)) && !Character.isDigit(str.charAt(j + 1))) {
                        if (str.charAt(j + 2) == 'r') {
                            if (minValues[0] < Integer.parseInt(String.valueOf(str.charAt(j)))) {
                                minValues[0] = Integer.parseInt(String.valueOf(str.charAt(j)));
                            }
                        }
                        else if (str.charAt(j + 2) == 'g') {
                            if (minValues[1] < Integer.parseInt(String.valueOf(str.charAt(j)))) {
                                minValues[1] = Integer.parseInt(String.valueOf(str.charAt(j)));
                            }
                        }
                        else if (str.charAt(j + 2) == 'b') {
                            if (minValues[2] < Integer.parseInt(String.valueOf(str.charAt(j)))) {
                                minValues[2] = Integer.parseInt(String.valueOf(str.charAt(j)));
                            }
                        }
                    }
                    if (Character.isDigit(str.charAt(j)) && Character.isDigit(str.charAt(j + 1))) {
                        if (str.charAt(j + 3) == 'r') {
                            if (minValues[0] < Integer.parseInt(str.substring(j, j + 2))) {
                                minValues[0] = Integer.parseInt(str.substring(j, j + 2));
                            }
                        }
                        else if (str.charAt(j + 3) == 'g') {
                            if (minValues[1] < Integer.parseInt(str.substring(j, j + 2))) {
                                minValues[1] = Integer.parseInt(str.substring(j, j + 2));
                            }
                        }
                        else if (str.charAt(j + 3) == 'b') {
                            if (minValues[2] < Integer.parseInt(str.substring(j, j + 2))) {
                                minValues[2] = Integer.parseInt(str.substring(j, j + 2));
                            }
                        }
                    }
                }
            }
            totalSum += (minValues[0] * minValues[1] * minValues[2]);
            System.out.println("red: " + minValues[0] + " green: " +  minValues[1] + " blue: " + minValues[2] + " sum: " + (minValues[0] * minValues[1] * minValues[2]));
        }
        System.out.println("total sum: " + totalSum);
    }
}
