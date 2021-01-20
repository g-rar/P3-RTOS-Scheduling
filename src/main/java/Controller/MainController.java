package Controller;

import Algorithms.EDF_ProcessScheduler;
import Algorithms.ProcessScheduler;
import Algorithms.RateMonotonicProcessScheduler;
import Model.Config;
import Model.RTProcess;
import Model.Timeline;

import java.util.ArrayList;
import java.util.List;

/**
 *  Esta clase representa el controlador principal de la aplicacion. Es quien contiene la totalidad de
 *  funcionalidades a las que acceden las interfaces.
 */
public class MainController {
    private Config config;
    private Timeline timeline;
    private List<RTProcess> processes;
    private ProcessScheduler processScheduler;

    public MainController(){
        this.processes = new ArrayList<>();
    }

    public void setConfig(Config config) {
        this.config = config;
        //extensibilidad
        switch (config.algorithm){
            case RMS:
                this.processScheduler = new RateMonotonicProcessScheduler();
                break;
            case EDF:
                this.processScheduler = new EDF_ProcessScheduler();
                break;
        }
    }

    /** Updates the processes deadlines and cycles to timeline,
     * but deletes assignments in the process */
    public void updateTimeline() throws Exception {
        if(this.config == null) throw new Exception("Config required for updating timeline");
        this.timeline = new Timeline(this.config.timeLimit);
        for (RTProcess p : processes) {
            this.timeline.addProcessMarks(p);
        }
    }

    public void addProcess(RTProcess process){
        this.processes.add(process);
    }

    public void schedule() throws Exception {
        if(this.config == null) throw new Exception("Config required for updating timeline");
        if(this.processes.isEmpty()) throw new Exception("There are no processes to schedule");
        this.processScheduler.schedule(timeline, processes, config.timeLimit);
        //todo execute the scheduling algorithms, such that the resulting timeline and process statistics are in this object
    }

    //GETTERS

    public Config getConfig() {
        return config;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public List<RTProcess> getProcesses() {
        return processes;
    }

    public ProcessScheduler getProcessScheduler() {
        return processScheduler;
    }
}
