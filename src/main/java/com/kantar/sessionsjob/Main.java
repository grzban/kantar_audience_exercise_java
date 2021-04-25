package com.kantar.sessionsjob;


import com.kantar.sessionsjob.facade.Facade;

public class Main {

    // See README.txt for usage example

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Missing arguments: <input-statements-file> <output-sessions-file>");
            System.exit(1);
        } else {
            String inputStatementsFile = args[0];
            String outputSessionsFile = args[1];
            Facade facade = Facade
                    .builder()
                    .inputFilePath(inputStatementsFile)
                    .outputFilePath(outputSessionsFile)
                    .build();
            facade.prepareSessionReport();
        }
    }
}
