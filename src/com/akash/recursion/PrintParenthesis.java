package com.akash.recursion;
public class PrintParenthesis {
 
    public static void main(String args[]) {
 
        int input = 4;
        printOutput(input);
    }
 
    private static void printOutput(int input) {
        String str = "{";
        //No of spaces filled till now
        int spacesCovered = 1;
        //No of open braces filled till now
        int noOfOpenBraces = 1;
        //noOfCloseBraces = spacesCovered - noOfOpenBraces
        printParenth(str, spacesCovered, noOfOpenBraces, input);
    }
 
    public static void printParenth(String str, int spacesCovered, int noOfOpenBraces, int input) {
 
        int noOfCloseBraces = spacesCovered - noOfOpenBraces;
        if (noOfOpenBraces == input) {
            for (int i = 1 ; i <= 2 * input - spacesCovered ; i++) {
                str = str + "}";
            }
            System.out.println(str);
        } else {
            if (noOfCloseBraces < noOfOpenBraces) {
                int tempSpaceCovered = spacesCovered;
                printParenth(str + "}", ++tempSpaceCovered, noOfOpenBraces, input);
            }
            printParenth(str + "{", ++spacesCovered, ++noOfOpenBraces, input);
        }
    }
}