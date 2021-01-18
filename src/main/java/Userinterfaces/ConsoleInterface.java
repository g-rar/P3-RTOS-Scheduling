package Userinterfaces;

import java.util.Scanner;

public class ConsoleInterface {

    public static void main(String[] args) {
        //System.out.println("Hello world");

        int userSelected;
        do{
            userSelected = MenuData();
            switch (userSelected){
                case 1:
                    System.out.println("Menu 1- Elegir tipo de carga datos");
                    eligeTipoCarga();
                    break;
                case 2:
                    System.out.println("Menu 2- Elegir tiempo deadline");
                    eligeTiempoDeadLine();
                    break;
                case 3:
                    System.out.println("Menu 3- Elegir comando para calendarizar");
                    break;
                case 4:
                    System.out.println("Menu 4- Iniciar emulación");
                    runScheduling();
                    break;
                case 5:
                    System.out.println("Menu HELP");
                    break;
                default:
                    break;
            }
        }
        while (userSelected > 6);
    }

    public static int MenuData(){
        int selection;
        Scanner opMenuScnnr = new Scanner(System.in);
        System.out.println("Console Menu");
        System.out.println("Select your option:");
        System.out.println("/------------------/");
        System.out.println("1- Elegir tipo de carga datos");
        System.out.println("2- Elegir tiempo deadline");
        System.out.println("3- Elegir comando para calendarizar");
        System.out.println("4- Iniciar emulación");
        System.out.println("5- HELP");

        System.out.println("Your selected option is:");
        selection = opMenuScnnr.nextInt();
        return selection;
    }

    public static void eligeTipoCarga(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor eliga 1 para introducción manual 2 para lectura desde TXT:");
        Integer entradaTeclado = 1; //DEFAULT
        entradaTeclado = entradaEscaner.nextInt ();
        if (entradaTeclado == 1) {
            System.out.println("Seleccion recibida es: introducción Manual");
            //llamado a un seteo manual
        }
        if (entradaTeclado == 2){
            System.out.println ("Seleccion recibida es: lectura desde archivo TXT");
        }
    }

    public static void eligeTiempoDeadLine(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor introduzca el tiempo del deadline en segundos");
        Integer entradaTeclado = 1; //DEFAULT
        entradaTeclado = entradaEscaner.nextInt ();
        System.out.println ("El tiempo del deadline es de: " + entradaTeclado +" seg");
    }

    public static void runScheduling(){
        System.out.println ("RUN...");
    }

}

