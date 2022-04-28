package ifce.ppd.mom.ppdmom.server;

import ifce.ppd.mom.ppdmom.server.legado.SubscriberProf;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;


public class Subscriber implements MessageListener {
    /*
     * URL do servidor JMS. DEFAULT_BROKER_URL indica que o servidor está em localhost
     */

    private static final String url = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static final String topicName = "TOPICO_EXEMPLO";


    public static void main(String[] args) {
        new SubscriberProf().Go();
    }

    public void Go() {

        try {

            /*
             * Estabelecendo conexão com o Servidor JMS
             */
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            /*
             * Criando Session
             */
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            /*
             * Criando Topic
             */
            Destination dest = session.createTopic(topicName);

            /*
             * Criando ConsumidorProf
             */
            MessageConsumer subscriber = session.createConsumer(dest);

            /*
             * Setando Listener
             */
            subscriber.setMessageListener(this);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                System.out.println(((TextMessage) message).getText());
            } catch (Exception e) {
            }
        }
    }
}
