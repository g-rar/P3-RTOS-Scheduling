package Model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Timeline {

    public class Unit{
        private RTProcess assignedTo = null;
        private List<RTProcess> deadLineOf = new ArrayList<RTProcess>();
        private List<RTProcess> cycleOf = new ArrayList<RTProcess>();

        boolean isFree(){
            return this.assignedTo != null;
        }

        void addDeadLine(RTProcess process){
            this.deadLineOf.add(process);
            this.sortDeadLines();
        }

        void addCycle(RTProcess process){
            this.cycleOf.add(process);
            this.sortCycles();
        }

        private void sortCycles(){
            this.cycleOf.sort(new Comparator<RTProcess>() {
                @Override
                public int compare(RTProcess o1, RTProcess o2) {
                    return o1.getCycle() - o2.getCycle();
                }
            });
        }

        private void sortDeadLines(){
            //If more than one process have the same deadline, the one with shortest period is prioritized
            this.deadLineOf.sort(Comparator.comparingInt(RTProcess::getCycle));
        }
    }

    private int maxLength;
    private List<Unit> periods;

    public Timeline(int length){
        this.maxLength = length;
        this.periods = new ArrayList<Unit>(maxLength);
        for (int i = 0; i < maxLength; i++) {
            this.periods.add(new Unit());
        }
    }

    public int size(){
        return maxLength;
    }

    public int nextDeadline(int position){
        // esto es que el siguiente deadline se sobrepaso
        if(position > maxLength) return -1;

        for (Unit u : this.periods.subList(position, this.maxLength-1)) {
            // la siguiente unidad que sea deadline de alguien
            if(!u.deadLineOf.isEmpty())
                return u.deadLineOf.get(0).getId();
        }
        // al haber revisado todos, el tiempo restante el sistema va a permanecer idle
        return -1;
    }

    public int nextDeadlineFor(RTProcess proc,int position){
        if(position > maxLength) return -1;

        for (int i = position; i<this.maxLength-1;i++) {
            // la siguiente unidad que sea deadline de proc
            Unit u = this.periods.get(i);
            if(!u.deadLineOf.stream().anyMatch(p -> p.getId() == proc.getId()))
                return i;
        }
        return -1;
    }

    public boolean executeToCompletion(int startTime,RTProcess process){
        // si el deadline está antes de que se pueda completar la tarea no se ejecuta
        if(this.periods.subList(startTime, startTime + process.getExecutiontTime())
            .stream().anyMatch(u -> u.deadLineOf.contains(process))){
            return false;
        }
        for (int i = 0; i < process.getExecutiontTime(); i++) {
            this.periods.get(startTime+i).assignedTo = process;
            process.execute();
        }
        return true;
    }

    public int nextCycle(int position){
        if(position > maxLength) return -1;

        for (int i = position; i<this.maxLength-1;i++) {
            // la siguiente unidad que sea ciclo de alguien
            Unit u = this.periods.get(i);
            if(!u.cycleOf.isEmpty())
                return i;
        }
        return -1;
    }

    public List<RTProcess> advanceTo(int t, List<RTProcess> processes){
        return processes.stream().map(p -> {
            int prevCycle = Math.floorDiv(t, p.getCycle());
            int nextCycle = prevCycle + p.getCycle();
            // si entre el ciclo anterior y el siguiente no se ha ejecutado un proceso, reflejarlo
            if(this.periods.subList(prevCycle,nextCycle).stream().anyMatch(
                    u -> u.assignedTo.getId() == p.getId()
            )){
                p.setHasExecutedActualCycle(true);
            } else {
                p.setHasExecutedActualCycle(false);
            }
            return p;
        }).collect(Collectors.toList());
    }

    public boolean addProcessMarks(RTProcess process){
        //todo por cada cyclo añadir el marcador y deadline correspondientes
        return true;
    }

    public int getMaxLength() {
        return maxLength;
    }
}
