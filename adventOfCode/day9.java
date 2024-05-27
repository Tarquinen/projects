//https://adventofcode.com/2023/day/9

package adventOfCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class day9 {
    public static void main (String[] args) throws Exception {
        BufferedReader file = new BufferedReader(new FileReader(
            "C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day9.txt"));
        part1(file);
        //part2(file);
    }
    
    public static void part1 (BufferedReader f) throws Exception {
        ArrayList<String> fList = new ArrayList<String>();
        String line;
        while ((line = f.readLine()) != null)
            fList.add(line);
        int totalSum = 0;
        for (int i = 0; i < fList.size(); i++) {
            String s = fList.get(i);
            int sLen = s.length();
            int numCounter = 0;
            int[] coord = new int[2];
            ArrayList<Integer> in = new ArrayList<Integer>();
            for (int j = 0; j < sLen; j++) {
                if (Character.isDigit(s.charAt(j))) {
                    coord[1] = j;
                }
                if (s.charAt(j) == ' ') { //when reaching a space
                    in.add(Integer.parseInt(s.substring(coord[0], coord[1] + 1))); //add number to list
                    coord[0] = j + 1;
                    numCounter++;
                }
                if (j == sLen - 1) {
                    in.add(Integer.parseInt(s.substring(coord[0], coord[1] + 1)));
                    numCounter++;
                }
            }
            ArrayList<ArrayList<Integer>> bigList = new ArrayList<ArrayList<Integer>>();
            bigList.add(new ArrayList<>(in));
            boolean zeros = false;
            while (!zeros) {
                int zeroCounter = 0;
                in.clear();
                for (int k = 0; k < numCounter - 1; k ++) { // populate next array down
                    in.add(bigList.get(bigList.size() - 1).get(k + 1) - bigList.get(bigList.size() - 1).get(k));
                    if (in.get(k) == 0) {
                        zeroCounter ++;
                    }
                }
                if (zeroCounter == numCounter - 1) {
                    zeros = true;
                }
                bigList.add(new ArrayList<>(in));
                numCounter--;
            }
            bigList.get(bigList.size() - 1).add(0); // add another 0 to last row in tree
            for (int l = bigList.size() - 1; l > 0; l--) {
                int lastVal = bigList.get(l).get(bigList.get(l).size() - 1);
                int lastValRowUp = bigList.get(l - 1).get(bigList.get(l - 1).size() - 1);
                bigList.get(l - 1).add(lastVal + lastValRowUp);
            }
            System.out.println("row " + (i + 1) + " last value: " + bigList.get(0).get(bigList.get(0).size() - 1));
            totalSum += bigList.get(0).get(bigList.get(0).size() - 1);
        }
        System.out.println("total sum: " + totalSum);
    }
    
    public static void part2 (BufferedReader f) throws Exception {
        ArrayList<String> fList = new ArrayList<String>();
        String line;
        while ((line = f.readLine()) != null)
            fList.add(line);
        int totalSum = 0;
        for (int i = 0; i < fList.size(); i++) {
            String s = fList.get(i);
            int sLen = s.length();
            int numCounter = 0;
            int[] coord = new int[2];
            ArrayList<Integer> in = new ArrayList<Integer>();
            for (int j = 0; j < sLen; j++) {
                if (Character.isDigit(s.charAt(j))) {
                    coord[1] = j;
                }
                if (s.charAt(j) == ' ') { //when reaching a space
                    in.add(Integer.parseInt(s.substring(coord[0], coord[1] + 1))); //add number to list
                    coord[0] = j + 1;
                    numCounter++;
                }
                if (j == sLen - 1) {
                    in.add(Integer.parseInt(s.substring(coord[0], coord[1] + 1)));
                    numCounter++;
                }
            }
            ArrayList<ArrayList<Integer>> bigList = new ArrayList<ArrayList<Integer>>();
            bigList.add(new ArrayList<>(in));
            boolean zeros = false;
            while (!zeros) {
                int zeroCounter = 0;
                in.clear();
                for (int k = 0; k < numCounter - 1; k ++) { // populate next array down
                    in.add(bigList.get(bigList.size() - 1).get(k + 1) - bigList.get(bigList.size() - 1).get(k));
                    if (in.get(k) == 0) {
                        zeroCounter ++;
                    }
                }
                if (zeroCounter == numCounter - 1) {
                    zeros = true;
                }
                bigList.add(new ArrayList<>(in));
                numCounter--;
            }
            bigList.get(bigList.size() - 1).add(0, 0); // add another 0 to last row in tree at start of row
            for (int l = bigList.size() - 1; l > 0; l--) {
                int firstVal = bigList.get(l).get(0);
                int firstValRowUp = bigList.get(l - 1).get(0);
                bigList.get(l - 1).add(0, firstValRowUp - firstVal);
            }
            System.out.println("row " + (i + 1) + " first value: " + bigList.get(0).get(0));
            totalSum += bigList.get(0).get(0);
        }
        System.out.println("total sum: " + totalSum);
    }
}
