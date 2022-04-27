package ifce.ppd.mom.ppdmom.models;

public class Client {
    private String name;
    private Sensor[] sensors;

    public Client(String name, Sensor[] sensors) {
        this.name = name;
        this.sensors = sensors;
    }
}
