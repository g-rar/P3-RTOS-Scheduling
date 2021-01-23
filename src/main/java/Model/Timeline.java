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

        public RTProcess getAssignedTo() {
            return assignedTo;
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

    /** Retorna el id del proceso que tiene el próximo deadline */
    public int nextDeadlineId(int position){
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

    public int nextDeadlineTime(int position){
        // esto es que el siguiente deadline se sobrepaso
        if(position > maxLength) return -1;

        for (int i = position; i < maxLength; i++) {
            // la siguiente unidad que sea deadline de alguien
            Unit u = this.periods.get(i);
            if(!u.deadLineOf.isEmpty())
                return i;
        }
        // al haber revisado todos, el tiempo restante el sistema va a permanecer idle
        return -1;
    }

    public int nextDeadlineFor(RTProcess proc,int position){
        if(position > maxLength) return -1;

        for (int i = position; i<this.maxLength-1;i++) {
            // la siguiente unidad que sea deadline de proc
            Unit u = this.periods.get(i);
            if(u.deadLineOf.stream().anyMatch(p -> p.getId() == proc.getId()))
                return i;
        }
        return -1;
    }

    public boolean executeToCompletion(int startTime,RTProcess process){
        //si se excede el limite ni vrga
        if(startTime + process.getExecutiontTime() > maxLength){
            return false;
        }
        // si el deadline está antes de que se pueda completar la tarea no se ejecuta
        if(this.periods.subList(startTime, startTime + process.getExecutiontTime()-1)
            .stream().anyMatch(u -> u.deadLineOf.stream().filter(un -> un == process).findFirst().orElse(null) != null)){
            process.missDeadline();
            // si en el periodo aun da tiempo de hacerla se hace? de momento asumamos que no
            process.missPeriod();
            return false;
        }
        for (int i = 0; i < process.getExecutiontTime(); i++) {
            this.periods.get(startTime+i).assignedTo = process;
        }
        process.execute();
        return true;
    }

    public boolean executeUnits(int startTime, int units, RTProcess process){
        if(startTime + units > maxLength || units > process.getExecutiontTime()){
            return false;
        }
        if(this.periods.subList(startTime, startTime + units)
                .stream().anyMatch(u -> periods.get(startTime) != u && u.deadLineOf.stream().filter(un -> un == process).findFirst().orElse(null) != null)) {
            process.missDeadline();
            // si en el periodo aun da tiempo de hacerla se hace? de momento asumamos que no
            process.missPeriod();
            return false;
        }
        for (int i = 0; i < units; i++) {
            this.periods.get(startTime+i).assignedTo = process;
        }
        int prevCycle = Math.floorDiv(startTime, process.getCycle()) * process.getCycle();
        int nextCycle = prevCycle + process.getCycle();
        //Si las unidades ejecutadas fueron las que hacian falta
        if(this.periods.subList(prevCycle, Math.min(nextCycle, maxLength)).stream().filter(
                u -> u.assignedTo != null && u.assignedTo.getId() == process.getId()
        ).count() >= process.getExecutiontTime()){
            process.execute();
            process.setHasExecutedActualCycle(true);
        }
        return true;
    }

    /** Retorna el tiempo en que se da el siguiente ciclo */
    public int nextCycle(int position){
        if(position > maxLength) return -1;

        for (int i = position; i<this.maxLength-1;i++) {
            // la siguiente unidad que sea ciclo de alguien
            Unit u = this.periods.get(i);
            if(!u.cycleOf.isEmpty())
                return i;
        }
        return maxLength;
    }

    public List<RTProcess> advanceTo(int t, List<RTProcess> processes){
        //nothing to do
        if(t >= maxLength) return processes;
        return processes.stream().map(p -> {
            int prevCycle = Math.floorDiv(t, p.getCycle()) * p.getCycle();
            int nextCycle = prevCycle + p.getCycle();
            // si entre el ciclo anterior y el siguiente no se ha ejecutado un proceso, reflejarlo
            if(this.periods.subList(prevCycle, Math.min(nextCycle, maxLength)).stream().filter(
                    u -> u.assignedTo != null && u.assignedTo.getId() == p.getId()
            ).count() >= p.getExecutiontTime()){
                p.setHasExecutedActualCycle(true);
            } else {
                p.setHasExecutedActualCycle(false);
            }
            return p;
        }).collect(Collectors.toList());
    }

    public boolean addProcessMarks(RTProcess process){
        for (int i = process.getCycle(); i < maxLength; i += process.getCycle()) {
            periods.get(i).addCycle(process);
        }
        for (int i = process.getDeadline(); i < maxLength; i += process.getDeadline()) {
            periods.get(i).addDeadLine(process);
        }
        return true;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public List<Unit> getPeriods() {
        return periods;
    }
}
