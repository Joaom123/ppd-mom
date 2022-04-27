package ifce.ppd.mom.ppdmom.models;

public class Client {
    private final String name;
    private final Sensor[] sensors;

    public Client(String name, Sensor[] sensors) {
        this.name = name;
        this.sensors = sensors;
    }
}
