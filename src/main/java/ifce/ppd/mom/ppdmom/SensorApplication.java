package ifce.ppd.mom.ppdmom;

import javax.jms.*;

import ifce.ppd.mom.ppdmom.models.Sensor;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SensorApplication {
    static Sensor sensor;
    static Connection connection;
    static Session session;

    public static void main(String[] args) throws JMSException {
        String sensorType = args[0];
        String sensorName = args[1];
        //TODO: Adicionar erro dos argumentos

        sensor = new Sensor(sensorName, sensorType, 0, 100);
        //if(sensorType.equals("TEMPERATURE"))

        // Init connection
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
        connection = connectionFactory.createConnection();
        connection.start();

        // Create a Session
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Create the destination (Topic)
        Destination destination = session.createTopic(sensorType);

        // Create a MessageProducer from the Session to the Topic
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        ScheduledExecutorService exec = Executors.newSingleThreadScheduledExecutor();
        exec.scheduleAtFixedRate(() -> {
            Random rand = new Random();
            int sensorValue = rand.nextInt(200) - 100;
            System.out.println("Valor atual do sensor: " + sensorValue);
            sensor.setValue(sensorValue);

            if (sensor.valueIsLessThanMinumum() || sensor.valueIsGreaterThanMaximum()) {
                try {
                    ObjectMessage objectMessage = session.createObjectMessage(sensor);
                    producer.send(objectMessage);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 1, 1, TimeUnit.SECONDS);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Encerrando conexão!");
            try {
                session.close();
                connection.close();
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }));
    }
}

