package week8.lab8;

import java.io.*;
import java.util.Scanner;

public class Lab8 {
    public static void main(String[] args) {
        // input files
        String inputFile = "src/week8/lab8/words.txt";
        String outputFile = "output.txt";

        boolean outputExist = false;
        boolean printLetters = false;
//        args = new String[]{"-i","src/week8/lab8/words.txt", "-o","output.txt","-letters"};

        if(args.length <= 1){
            System.out.println("Error 1 >>>");
            printHelp();
            System.exit(1);
        }
        for(int i = 0; i<args.length;i ++){
            if(args[i].equals("-i")){
                inputFile = args[i+1];
                i++;
            }else if (args[i].equals("-o")){
                outputFile = args[i+1];
                outputExist = true;
                i++;
            }else if (args[i].equals("-help")){
                System.out.println("Error 2 >>>");
                printHelp();
            }else if (args[i].equals("-letters")){
                printLetters = true;
            }else{
                System.out.println("Error 3 >>>");
                printHelp();
                System.exit(1);
            }
        }

        int letterCount = 0;
        int wordCount = 0;
        try {
            Scanner in = new Scanner(new File(inputFile));
//            BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
            PrintWriter writer = new PrintWriter(outputFile);
            String line;
            while (in.hasNextLine()){
                line = in.nextLine();

                wordCount += line.split(" ").length;

                if(printLetters){
                    letterCount += countLetters(line);
                }

                if(outputExist){
                    writer.println(line);
//                    writer.write("\n");
                }else {
                    System.out.println(line);
                }
            }

            System.out.println("total words: " + wordCount + "\n");

            if(printLetters) {
                System.out.println("total letters: " + letterCount + "\n");
            }

            writer.close();
            in.close();

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error 4 >>>");
            printHelp();
        }

    }

    private static int countLetters(String str){
        int count = 0;
        for(int i = 0; i<str.length(); i++){
            if(Character.isAlphabetic(str.charAt(i))){
                count ++;
            }
        }
        return count;
    }

    private static void printHelp(){
        System.out.println("java Lab8 -help \n" +
                           " Usage: java Lab8 -help\n" +
                           " Usage: java Lab8 -i inputFile [-o outputFile] [-letters]");
//        System.out.println( "-help: as for help \n" +
//                            "-i: followed by input file\n" +
//                            "-o: followed by output file\n" +
//                            "-letters: print letters");
    }

}
