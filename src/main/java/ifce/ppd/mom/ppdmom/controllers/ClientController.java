package ifce.ppd.mom.ppdmom.controllers;

import ifce.ppd.mom.ppdmom.models.Sensor;
import javafx.application.Platform;
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
import java.util.UUID;

public class ClientController implements Initializable {
    Destination temperatureDestination;
    Destination speedDestination;
    Destination humidityDestination;
    MessageConsumer temperatureMessageConsumer;
    MessageConsumer speedMessageConsumer;
    MessageConsumer humidityMessageConsumer;
    Session session;

    @FXML
    public Text name;

    @FXML
    public CheckBox temperatureCheckBox;

    @FXML
    public CheckBox speedCheckBox;

    @FXML
    public CheckBox humidityCheckBox;

    @FXML
    private TextArea textArea;

    public void onSelectTemperatureCheckBox(ActionEvent actionEvent) throws JMSException {
        if (temperatureCheckBox.isSelected()) {
            // subscribe
            temperatureMessageConsumer = session.createConsumer(temperatureDestination);
            temperatureMessageConsumer.setMessageListener(new QueueMessageListener());
            Platform.runLater(() -> {
                textArea.appendText("Tópico de temperatura foi assinado!\n");
                textArea.appendText("----------------------------------------\n");
            });
        } else {
            // unsubscribe
            if (temperatureMessageConsumer != null) {
                temperatureMessageConsumer.close();
                Platform.runLater(() -> {
                    textArea.appendText("Incrição no tópico de temperatura foi cancelado!\n");
                    textArea.appendText("----------------------------------------\n");
                });
            }
        }
    }

    public void onSelectHumidityCheckBox(ActionEvent actionEvent) throws JMSException {
        if (humidityCheckBox.isSelected()) {
            // subscribe
            humidityMessageConsumer = session.createConsumer(humidityDestination);
            humidityMessageConsumer.setMessageListener(new QueueMessageListener());
            Platform.runLater(() -> {
                textArea.appendText("Tópico de umidade foi assinado!\n");
                textArea.appendText("----------------------------------------\n");
            });
        } else {
            // unsubscribe
            if (humidityMessageConsumer != null) {
                humidityMessageConsumer.close();
                Platform.runLater(() -> {
                    textArea.appendText("Incrição no tópico de umidade foi cancelado!\n");
                    textArea.appendText("----------------------------------------\n");
                });
            }
        }
    }

    public void onSelectSpeedCheckBox(ActionEvent actionEvent) throws JMSException {
        if (speedCheckBox.isSelected()) {
            // subscribe
            speedMessageConsumer = session.createConsumer(speedDestination);
            speedMessageConsumer.setMessageListener(new QueueMessageListener());
            Platform.runLater(() -> {
                textArea.appendText("Tópico de velocidade foi assinado!\n");
                textArea.appendText("----------------------------------------\n");
            });
        } else {
            // unsubscribe
            if (speedMessageConsumer != null) {
                speedMessageConsumer.close();
                Platform.runLater(() -> {
                    textArea.appendText("Incrição no tópico de velocidade foi cancelado!\n");
                    textArea.appendText("----------------------------------------\n");
                });
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        name.setText(UUID.randomUUID().toString().substring(0, 5));
        // Connecting to JMS Server
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_BROKER_URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the topics
            speedDestination = session.createTopic("SPEED");
            temperatureDestination = session.createTopic("TEMPERATURE");
            humidityDestination = session.createTopic("HUMIDITY");
        } catch (JMSException e) {
            throw new RuntimeException(e);
        }
    }

    private class QueueMessageListener implements MessageListener {
        private String translate(String type) {
            if (type.equals("TEMPERATURE"))
                return "Temperatura";

            if (type.equals("HUMIDITY"))
                return "Umidade";

            if (type.equals("SPEED"))
                return "Velocidade";
            return "";
        }

        @Override
        public void onMessage(Message message) {
            try {
                if (message instanceof ObjectMessage objectMessage) {
                    Sensor sensor = (Sensor) objectMessage.getObject();
                    if (sensor.valueIsLessThanMinimum()) {
                        Platform.runLater (() -> {
                            textArea.appendText("Dados do sensor:\n");
                            textArea.appendText("Tipo: " + translate(sensor.getType()) + " | Nome: " + sensor.getName() + "\n");
                            textArea.appendText("Valor mínimo: " + sensor.getMinValue() + " | Valor atual: " + sensor.getValue() + "\n");
                            textArea.appendText("----------------------------------------\n");
                        });

                    } else {
                        Platform.runLater (() -> {
                            textArea.appendText("Dados do sensor:\n");
                            textArea.appendText("Tipo: " + translate(sensor.getType()) + " | Nome: " + sensor.getName() + "\n");
                            textArea.appendText("Valor máximo: " + sensor.getMaxValue() + " | Valor atual: " + sensor.getValue() + "\n");
                            textArea.appendText("----------------------------------------\n");
                        });

                    }
                }
            } catch (JMSException e) {
                throw new RuntimeException(e);
            }
        }
    }

}
