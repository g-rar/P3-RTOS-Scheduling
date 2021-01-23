package Model;

public class RTProcess {

    private int id;
    private int cycle;
    private int deadline;
    private int executiontTime;
    private int missedDealines = 0;
    private int executedPeriods = 0;
    private int missedPeriods = 0;
    private boolean hasExecutedActualCycle = false;

    public RTProcess(int cycle, int deadline, int executiontTime, int id){
        this.cycle = cycle;
        this.deadline=deadline;
        this.executiontTime=executiontTime;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getCycle() {
        return cycle;
    }

    public int getDeadline() {
        return deadline;
    }

    public int getExecutiontTime() {
        return executiontTime;
    }

    public float getUssage(){
        return executiontTime / cycle;
    }

    public int getMissedDealines() {
        return missedDealines;
    }

    public int getExecutedPeriods() {
        return executedPeriods;
    }

    public int getMissedPeriods() {
        return missedPeriods;
    }

    public boolean hasExecutedActualCycle() {
        return hasExecutedActualCycle;
    }

    public void execute(){
        this.executedPeriods++;
        this.hasExecutedActualCycle = true;
    }

    public void startCycle(){
        this.hasExecutedActualCycle = false;
    }

    public void missDeadline(){
        this.missedDealines++;
    }

    public void missPeriod(){
        this.missedPeriods++;
    }

    public void setHasExecutedActualCycle(boolean hasExecutedActualCycle) {
        this.hasExecutedActualCycle = hasExecutedActualCycle;
    }
}
