package Userinterfaces;

import java.io.IOException;

public class ConsoleInterface {

    public static void main(String[] args) {

        System.out.println("Hello world");
        try {
//            ProcessFileParser.createFileTemplate();
            System.out.println(ProcessFileParser.parsefile("newFile.csv").toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
