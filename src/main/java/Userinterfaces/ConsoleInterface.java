package Userinterfaces;

import Controller.MainController;
import Model.Config;
import Model.RTProcess;

import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {

    public Config config = new Config();
    public MainController controller = new MainController();

    public void execMenu(){
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
                    System.out.println("Menu 3- Elegir algoritmo para calendarizar");
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
                case 6:
                    displayProcesses();
                    break;
                default:
                    break;
            }
        }
        while (userSelected < 6);
    }

    private void displayProcesses() {
        List<RTProcess> processList = controller.getProcesses();
    }

    public static void main(String[] args) {
        //System.out.println("Hello world");
        new ConsoleInterface().execMenu();
    }

    public int MenuData(){
        int selection;
        Scanner opMenuScnnr = new Scanner(System.in);
        System.out.println("Seleccione su opción:");
        System.out.println("/------------------/");
        System.out.println("1- Elegir tipo de carga datos");
        System.out.println("2- Elegir tiempo de emulación");
        System.out.println("3- Elegir comando para calendarizar");
        System.out.println("4- Iniciar emulación");
        System.out.println("5- Ayuda");
        System.out.println("6- Ayuda");
        System.out.println("7- Exit");
        selection = opMenuScnnr.nextInt();
        return selection;
    }

    public void eligeTipoCarga(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor presione 1 para introducción manual ó presione 2 para lectura desde CSV:");
        Integer entradaTeclado = entradaEscaner.nextInt ();
        if (entradaTeclado == 1) {
            System.out.println("Introduzca el id del proceso: ");
            int id = entradaEscaner.nextInt();
            System.out.println("Introduzca el tiempo que dura un ciclo del proceso:");
            int cycle = entradaEscaner.nextInt();
            System.out.println("Introduzca el deadline del proceso:");
            int deadLine = entradaEscaner.nextInt();
            System.out.println("Introduzca el tiempo de ejecucion del proceso:");
            int execTime = entradaEscaner.nextInt();

        }
        if (entradaTeclado == 2){
            System.out.println ("Por favor ingrese la direccion del archivo: ");
            String filepath = entradaEscaner.next();
            try {
                List<RTProcess> processes = ProcessFileParser.parsefile(filepath);
                for (int i = 0; i < processes.size(); i++) {
                    RTProcess p = processes.get(i);
                    this.controller.addProcess(p);
                }
                controller.updateTimeline();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }
    }

    public  void elegirTiempoSimulacion(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor introduzca la cantidad de unidades de tiempo a emular:");
        Integer entradaTeclado = 1; //DEFAULT
        entradaTeclado = entradaEscaner.nextInt ();
        System.out.println ("El tiempo del deadline es de: " + entradaTeclado +" seg");
        config.timeLimit = entradaTeclado;
    }

    public  void elegirComandoCalendarizar(){
        Scanner entradaEscaner = new Scanner (System.in);
        System.out.println ("Por favor presione 1 para Algoritmo RMS ó presione 2 para Algoritmo EDF");
        Integer entradaTeclado = entradaEscaner.nextInt();
        if (entradaTeclado == 1) {
            config.algorithm = Config.ScheduleAlgorithm.RMS;
            System.out.println("Seleccion recibida es: Algoritmo RMS");
        }
        if (entradaTeclado == 2){
            config.algorithm = Config.ScheduleAlgorithm.EDF;
            System.out.println("Seleccion recibida es: Algoritmo EDF");
        }
        controller.setConfig(config);
    }

    public  void runScheduling(){
        System.out.println ("START...");
    }

    public  void HELPMENU(){
        System.out.println ("Sistema de Emulación de Scheduling");
    }

}

