package Algorithms;

import Model.RTProcess;
import Model.Timeline;

import java.util.List;

public abstract class ProcessScheduler {

    public abstract void schedule(Timeline timeline, List<RTProcess> processes, int maxLimt);

}
