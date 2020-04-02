package com.company;

import java.io.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        System.out.println("Read file");
        if(args.length < 3){
            System.out.println("Please give me 3 parameters first with your file and other 2 with the file names you want me to generate! ");
            System.out.println("Example: java main person-keywords.html fixed.html per-keywords.html");
            System.exit(0);
        }

        try {

            String filename = args[0];
            String filenameFixed = args[1];
            String filenameKeywords = args[2];
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String currentLine = "";
            String lineBreak = "\n";
            String result = "";
            while(myReader.hasNextLine()){
                currentLine = myReader.nextLine();
                String fixedLine = lineBreak; // + "<tr><td>";
                for (int i = 0; i < currentLine.length()-1; i++) {

                    if (currentLine.charAt(i) ==  '<' && currentLine.charAt(i+1)=='o'){
                        fixedLine += lineBreak + currentLine.charAt(i);
                    } else {
                        fixedLine += currentLine.charAt(i);
                    }
                }

                result += fixedLine + lineBreak;
            }
            myReader.close();

            System.out.println(result);

            String outputTextStart = "<html><head></head><body><table>"+lineBreak;
            String outputTextEnd = lineBreak+"</table></body></html>";
            String outHtml = outputTextStart + result + outputTextEnd;

            writeToHtmlFile(filenameFixed, outHtml);
            extractKeywords(filenameFixed, filenameKeywords);

        }catch(FileNotFoundException ex){
            System.out.println("Exception: " + ex.getMessage());
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    private static void extractKeywords(String filename, String filenameKeywords){
        try {
            File myObj = new File(filename);
            Scanner myReader = new Scanner(myObj);
            String currentLine = "";
            String lineBreak = "\n";
            String result = "";
            while (myReader.hasNextLine()) {
                currentLine = myReader.nextLine();
                String fixedLine = lineBreak;
                int keywordStarts = currentLine.indexOf("value=\"");
                if(keywordStarts == -1) {
                    continue;
                }
                int keywordEnds = currentLine.indexOf("\">");
                try {
                    String keyword = currentLine.substring(keywordStarts+"value=\"".length(), keywordEnds);
                    String out1 = "" + keyword + ";";
                    System.out.println(out1);

                    String out2 = " ;";

                    String description = currentLine.substring(keywordEnds+"\">".length(), currentLine.indexOf("</op"));
                    String out3 = "" + description + ";";

                    String out4 = " ;";
                    String out5 = " ;";
                    String out6 = " ;";
                    String out7 = " ;";

                    result += lineBreak +  out1 + out2 + out3 + out4 + out5 + out6 + out7 ;

                }catch(Exception ex){
                    System.out.println("extractKeywords: " + ex.getMessage());
                }
            }
            myReader.close();

            System.out.println(result);

            writeToHtmlFile(filenameKeywords, result);


        }catch(FileNotFoundException ex){
            System.out.println("Exception: " + ex.getMessage());
        }catch(Exception ex){
            System.out.println("Exception: " + ex.getMessage());
        }
    }


    private static void writeToHtmlFile(String fileName, String result) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        BufferedWriter bw = new BufferedWriter(fw);
        String outHtml = result;
        bw.write(outHtml);
        bw.flush();
        fw.close();
    }
}
