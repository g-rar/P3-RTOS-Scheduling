package Algorithms;

import Model.RTProcess;
import Model.Timeline;

import java.util.List;

public abstract class ProcessScheduler {

    /** Ejecuta el algoritmo de calendarizacion sobre el timeline y procesos indicados como parametro */
    public abstract void schedule(Timeline timeline, List<RTProcess> processes, int maxLimt);

}
