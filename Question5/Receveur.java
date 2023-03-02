import jakarta.jms.*;
import javax.naming.*;
import java.io.*;

public class  Receveur {

    public static void main(String[] args) throws NamingException, JMSException, InterruptedException {

        InitialContext messaging = new InitialContext();
        QueueConnectionFactory connectionFactory = (QueueConnectionFactory) messaging.lookup("jms/ConnectionFactoryPtP");
        Queue queue = (Queue) messaging.lookup("jms/QueuePtP");
        QueueConnection connection = connectionFactory.createQueueConnection();
        QueueSession session = connection.createQueueSession(false,Session.AUTO_ACKNOWLEDGE);
        connection.start();

        String destinataire = "";

        QueueReceiver receiver;

        if (args.length == 0){
            receiver = session.createReceiver(queue);
        }
        else {
            for (int i = 0; i < args.length-1; i++){
                destinataire += "'" + args[i] + "', ";
            }
            destinataire += "'" + args[args.length-1] + "'";
            receiver = session.createReceiver(queue,"destinataire IN ("+destinataire+")");
        }
        while (true){
            TextMessage msg = (TextMessage) receiver.receive();
            System.out.println(msg.getText());
        }

        //connection.close();
    }
}