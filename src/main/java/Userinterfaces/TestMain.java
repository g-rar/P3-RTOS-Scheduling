package Userinterfaces;

import Controller.MainController;
import Model.Config;
import Model.RTProcess;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class TestMain {
    public static void main(String[] args) {
        System.out.println("Hello world");
        try {
//            ProcessFileParser.createFileTemplate();
            MainController controller = new MainController();
            Config config = new Config();
            config.algorithm = Config.ScheduleAlgorithm.EDF;
            config.timeLimit = 150;
            controller.setConfig(config);
            List<RTProcess> processList =  ProcessFileParser.parsefile("newFile.csv");
            for (RTProcess process : processList) {
                controller.addProcess(process);
            }
            controller.updateTimeline();

            controller.schedule();
            controller.getTimeline();
            List<Integer> assignments =
                    controller.getTimeline().getPeriods().stream()
                    .map(e -> {return e.getAssignedTo() != null ? e.getAssignedTo().getId() : null ;})
                    .collect(Collectors.toList());
            System.out.println("En teoria termino...");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
