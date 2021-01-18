package Model;

public class RTProcess {

    private int id;
    private int cycle;
    private int deadline;
    private int executiontTime;
    private int missedDealines = 0;
    private int executedPeriods = 0;
    private int missedPeriods = 0;


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
}
