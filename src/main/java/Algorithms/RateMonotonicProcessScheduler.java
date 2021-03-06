package Algorithms;

import Model.RTProcess;
import Model.Timeline;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RateMonotonicProcessScheduler extends ProcessScheduler {

    public void schedule(Timeline timeline, List<RTProcess> processes, int maxLimt) {
        List<RTProcess> sortedProcesses = new ArrayList<>();
        sortedProcesses.addAll(processes);
        sortedProcesses.sort(Comparator.comparingInt(RTProcess::getCycle));
        int actualtime = 0;
        int processExecIndex = 0;
        while(actualtime < timeline.getMaxLength()){
            int nextProc = -1;
            if(timeline.executeToCompletion(actualtime, sortedProcesses.get(processExecIndex))){
                actualtime += sortedProcesses.get(processExecIndex).getExecutiontTime();
                sortedProcesses = timeline.advanceTo(actualtime, sortedProcesses);
                nextProc = getNextProc(sortedProcesses);
            }
            if(nextProc == -1){
                actualtime = timeline.nextCycle(actualtime)+1;
                sortedProcesses = timeline.advanceTo(actualtime, sortedProcesses);
                nextProc = getNextProc(sortedProcesses);
            }
            processExecIndex = nextProc;
        }
    }

    private int getNextProc(List<RTProcess> processes){
        for (int i = 0; i < processes.size(); i++) {
            if(!processes.get(i).hasExecutedActualCycle()){
                return i;
            }
        }
        return -1;
    }


}
