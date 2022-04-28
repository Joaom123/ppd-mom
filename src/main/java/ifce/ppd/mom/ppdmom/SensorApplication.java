package ifce.ppd.mom.ppdmom;

import ifce.ppd.mom.ppdmom.models.Sensor;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class SensorApplication {
    static Sensor sensor;
    static Connection connection;
    static Session session;

    public static void main(String[] args) throws JMSException {
        if (args.length != 2) {
            System.err.println("Entre com os argumentos corretamente!");
            System.err.println("1º - Tipo do sensor 2º - Nome do sensor");
            System.exit(0);
        }

        String sensorType = args[0];
        String sensorName = args[1];

        // Check if the arg is correct
        if (!sensorType.equals("TEMPERATURE") && !sensorType.equals("HUMIDITY") && !sensorType.equals("SPEED")) {
            System.err.println("Tipo de sensor inválido!");
            System.err.println("Tipos de sensores: TEMPERATURE | HUMIDITY | SPEED");
            System.exit(0);
        }

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
            int sensorValue = rand.nextInt(300) - 100;
            System.out.println("Valor atual do sensor: " + sensorValue);
            sensor.setValue(sensorValue);

            if (sensor.valueIsLessThanMinimum() || sensor.valueIsGreaterThanMaximum()) {
                try {
                    ObjectMessage objectMessage = session.createObjectMessage(sensor);
                    producer.send(objectMessage);
                } catch (JMSException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 1, 5, TimeUnit.SECONDS);

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

