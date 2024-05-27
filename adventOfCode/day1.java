//https://adventofcode.com/2023/day/1

package adventOfCode;
import java.io.*;
import java.util.ArrayList;

public class day1 {
    public static void main(String[] args) throws Exception{
        BufferedReader file = new BufferedReader(new FileReader(
            "C:\\Users\\danny\\OneDrive\\Desktop\\Algo\\School-repo\\adventOfCode\\day1.txt"));

        part1(file);
        //part2(file);
    }

    public static void part1 (BufferedReader file) throws Exception {
        int i;
        int line = 0;
        int totalSum = 0;
        String str = "";

        while ((i = file.read()) != -1) {
            if (i > 47 && i < 58) {
                str += (char)i;
            }
            if (i == 13) {
                line ++;
                if (str.length() > 1)
                    str = "" + str.charAt(0) + str.charAt(str.length() - 1);
                else if (str.length() == 1)
                    str = "" + str.charAt(0) + str.charAt(0);
                System.out.println("row " + line + " number is " + str);
                totalSum += Integer.parseInt(str);
                str = "";
            }
        }

        System.out.println("total sum is: " + totalSum);
    }
    
    public static void part2 (BufferedReader file) throws Exception {
        String line = null;
        String[] dict = {"zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine"};
        String numString = "";
        ArrayList<String> fileList = new ArrayList<String>();
        ArrayList<String> numList = new ArrayList<String>();
        while ((line = file.readLine()) != null) { //create a list of strings where each string is a row in the file
                fileList.add(line);
        }

        for (int rows = 0; rows < fileList.size(); rows++) { // loop through each row
            for (int chars = 0; chars < fileList.get(rows).length(); chars++) { //loop through each char in the string
                if (Character.isDigit(fileList.get(rows).charAt(chars))) { //add numbers into numList
                    numString += fileList.get(rows).charAt(chars);
                    continue;
                }
                boolean wordFound = false;
                for (int dictIndex = 0; dictIndex < dict.length; dictIndex ++) { //check the char against dict, if it matches check more following chars
                    if (wordFound)
                        break;
                    int wordLength = dict[dictIndex].length();
                    int matchCounter = 0;
                    for (int extraLetters = 0; extraLetters < wordLength; extraLetters++) {
                        if (chars + extraLetters >= fileList.get(rows).length()) // stop checking for matches when you get to the end of a row
                            break;
                        if (fileList.get(rows).charAt(chars + extraLetters) != dict[dictIndex].charAt(extraLetters)) {//letter doesn't match so check against next dict item
                            break;
                        }
                        else if (fileList.get(rows).charAt(chars + extraLetters) == dict[dictIndex].charAt(extraLetters)) {//letters match a dictionary element
                            matchCounter ++;
                            if (matchCounter == wordLength) {//word matches
                                // add number to numString
                                numString += dictIndex;
                                wordFound = true;
                                //advance cursor forward l places to be efficient maybe later
                                chars += wordLength - 2; // this is really wordLength - 1 because each iteration is +1 as well
                                break;
                            }
                        }
                        else {
                            break;
                        }

                    }
                }

            }
            numList.add(numString);
            numString = "";
        }

        int total = 0;
        int rowCounter = 0;

        for (String value : numList) {
            String strTotal = "" + value.charAt(0) + value.charAt(value.length() - 1);
            int rowTotal = Integer.parseInt(strTotal);
            total += rowTotal;
            rowCounter ++; 
            System.out.println("row: " + rowCounter + " number: " + rowTotal + " (" + value + ")");
        }

        System.out.println("TOTAL: " + total);

    }
}
