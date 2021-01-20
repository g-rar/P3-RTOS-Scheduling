package Algorithms;

import Model.RTProcess;
import Model.Timeline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class EDF_ProcessScheduler extends ProcessScheduler {


    public void schedule(Timeline timeline, List<RTProcess> processes, int maxLimt) {
        int actualTime = 0;
        List<RTProcess> relevantProcesses = new ArrayList<>(processes);
        RTProcess toExecute;
        while(actualTime < maxLimt){
            final int ac = actualTime;
            toExecute = relevantProcesses.stream().filter(p -> !p.hasExecutedActualCycle()).min(Comparator.comparingInt(n -> timeline.nextDeadlineFor(n, ac+1)))
                    .orElse(null);
            if(toExecute == null){
                actualTime = timeline.nextCycle(actualTime);
                timeline.advanceTo(actualTime, relevantProcesses);
            } else {
                timeline.executeUnits(actualTime, 1, toExecute);
                actualTime++;
                timeline.advanceTo(actualTime, relevantProcesses);
            }
        }

    }
//    public void schedule(Timeline timeline, List<RTProcess> processes, int maxLimt) {
//        int actualTime = 0;
//        int execId;
//        List<RTProcess> relevantProcesses = new ArrayList<>(processes);
//        while(actualTime < maxLimt){
//            execId = timeline.nextDeadlineId(actualTime);
//            RTProcess executeProcess = getProcessWithId(execId, relevantProcesses);
//            if(execId == -1 || executeProcess.hasExecutedActualCycle()){
//                final int a = actualTime;
//                int nextDeadline = relevantProcesses.stream()
//                        .filter(p -> !p.hasExecutedActualCycle()).map(p -> timeline.nextDeadlineFor(p, a))
//                        .min(Comparator.comparingInt(n -> n)).orElse(-1);
//                if(nextDeadline == -1){
//                    actualTime = timeline.nextCycle(actualTime);
//                    execId = timeline.nextDeadlineId(actualTime);
//                    executeProcess = getProcessWithId(execId, relevantProcesses);
//                }
//            }
//            timeline.executeUnits(actualTime,1, executeProcess);
//            actualTime++;
//            if(timeline.nextDeadlineId(actualTime) == -1 || getProcessWithId(timeline.nextDeadlineId(actualTime), relevantProcesses).hasExecutedActualCycle()){
//                // Si ya no se puede ejecutar más nada pues se va
//                if(timeline.nextCycle(actualTime) == timeline.size()){
//                    break;
//                }
//                actualTime = timeline.nextCycle(actualTime) +1;
//            }
//            timeline.advanceTo(actualTime, relevantProcesses);
//        }
//    }

    private RTProcess getProcessWithId(int id, List<RTProcess> processes){
        return processes.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }
}
