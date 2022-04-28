package ifce.ppd.mom.ppdmom;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class SensorApplication {
    public static void main(String[] args) throws JMSException {
        thread(new SensorProducer(), false);
    }

    public static void thread(Runnable runnable, boolean daemon) {
        Thread brokerThread = new Thread(runnable);
        brokerThread.setDaemon(daemon);
        brokerThread.start();
    }

    public static class SensorProducer implements Runnable {
        public void run() {
            try {
                ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
                Connection connection = connectionFactory.createConnection();
                connection.start();

                // Create a Session
                Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                System.out.println("conexão iniciada");

                // Create the destination (Topic or Queue)
                Destination destination = session.createTopic("velocidade");

                // Create a MessageProducer from the Session to the Topic or Queue
                MessageProducer producer = session.createProducer(destination);
                producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

                // Create a messages
                String text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
                TextMessage message = session.createTextMessage(text);

                // Tell the producer to send the message
                System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
                producer.send(message);

                // Clean up
                session.close();
                connection.close();
            }
            catch (JMSException e) {
                System.out.println("Caught: " + e);
                e.printStackTrace();
            }
        }
    }
}

