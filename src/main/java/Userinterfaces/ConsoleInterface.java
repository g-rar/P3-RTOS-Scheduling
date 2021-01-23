package Userinterfaces;

import Model.Config;

import java.util.Scanner;

public class ConsoleInterface {

public static Config config = new Config();4


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
                    elegirTiempoSimulacion();
                    break;
                case 3:
                    System.out.println("Menu 3- Elegir comando para calendarizar");
                    elegirComandoCalendarizar();
                    break;
                case 4:
                    System.out.println("Menu 4- Iniciar emulación");
                    runScheduling();
                    break;
                case 5:
                    System.out.println("Menu HELP");
                    HELPMENU();
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
        System.out.println("Seleccione su opción:");
        System.out.println("/------------------/");
        System.out.println("1- Elegir tipo de carga datos");
        System.out.println("2- Elegir tiempo de emulación");
        System.out.println("3- Elegir comando para calendarizar");
        System.out.println("4- Iniciar emulación");
        System.out.println("5- Ayuda");
        System.out.println("6- Exit");
        selection = opMenuScnnr.nextInt();
        return selection;
    }

    public static void eligeTipoCarga(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor presione 1 para introducción manual ó presione 2 para lectura desde CSV:");
        Integer entradaTeclado = 1;
        entradaTeclado = entradaEscaner.nextInt ();
        if (entradaTeclado == 1) {
            System.out.println("Seleccion recibida es: introducción Manual");
            //llamado al seteo manual
            MenuData();
        }
        if (entradaTeclado == 2){
            System.out.println ("Seleccion recibida es: lectura desde archivo CSV");
            //correr emulacion de 1
            MenuData();
        }
    }

    public static void elegirTiempoSimulacion(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor introduzca el tiempo en segundos");
        Integer entradaTeclado = 1; //DEFAULT
        entradaTeclado = entradaEscaner.nextInt ();
        System.out.println ("El tiempo del deadline es de: " + entradaTeclado +" seg");
        config.timeLimit = entradaTeclado;
        MenuData();
    }

    public static void elegirComandoCalendarizar(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor presione 1 para Algoritmo RMS ó presione 2 para Algoritmo EDF");
        Integer entradaTeclado = entradaEscaner.nextInt();
        if (entradaTeclado == 1) {
            config.algorithm = Config.ScheduleAlgorithm.RMS;
            System.out.println("Seleccion recibida es: Algoritmo RMS");
            MenuData();
        }
        if (entradaTeclado == 2){
            config.algorithm = Config.ScheduleAlgorithm.EDF;
            System.out.println("Seleccion recibida es: Algoritmo EDF");

            MenuData();
        }
        MenuData();
    }

    public static void runScheduling(){
        System.out.println ("START...");
    }

    public static void HELPMENU(){
        System.out.println ("Sistema de Emulación de Scheduling");
        MenuData();
    }

}

