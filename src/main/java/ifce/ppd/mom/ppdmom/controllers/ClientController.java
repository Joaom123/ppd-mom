package ifce.ppd.mom.ppdmom.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ClientController implements Initializable {
    Destination temperatureDestination;
    Destination speedDestination;
    Destination humidityDestination;

    @FXML
    public Text name;

    @FXML
    public CheckBox temperatureCheckBox;

    @FXML
    public CheckBox speedCheckBox;

    @FXML
    public CheckBox humidityCheckBox;

    @FXML
    public TextArea textArea;

    public void onSelectTemperatureCheckBox(ActionEvent actionEvent) {
        if (temperatureCheckBox.isSelected()) {
            // subscribe
        } else {
            // unsubscribe
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText("João Marcus");
        // Connecting to JMS Server
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            speedDestination = session.createTopic("velocidade");
            temperatureDestination = session.createTopic("temperatura");
            humidityDestination = session.createTopic("umidade");

            MessageConsumer speedConsumer = session.createConsumer(speedDestination);
            Message message = speedConsumer.receive();
            System.out.println(message);
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }
}
