package Model;

public class Config {

    public enum ScheduleAlgorithm {RMS, EDF}

    public int timeLimit;
    public ScheduleAlgorithm algorithm;
}
