package mrvladimort.pet.sbmjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mrvladimort.pet.sbmjms.config.JmsConfig;
import mrvladimort.pet.sbmjms.model.BaseMessage;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class BaseSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedRate = 10000)
    public void sendMessage() {
        log.info("Sending message");
        BaseMessage message = BaseMessage.builder()
                .id(UUID.randomUUID())
                .name("base")
                .build();

        jmsTemplate.convertAndSend(JmsConfig.BASE_QUEUE, message);
        log.info("Message was sent");
    }

    @Scheduled(fixedRate = 5000)
    public void sendAndReceiveMessage() throws JMSException {

        BaseMessage message = BaseMessage
                .builder()
                .id(UUID.randomUUID())
                .name("Hello")
                .build();

        Message receviedMsg = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, session -> {
            Message baseMessage;

            try {
                baseMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
                baseMessage.setStringProperty("_type", "mrvladimort.pet.sbmjms.model.BaseMessage");

                System.out.println("Sending Hello");
                return baseMessage;

            } catch (JsonProcessingException e) {
                throw new JMSException("boom");
            }
        });

        log.info(receviedMsg.getBody(String.class));

    }
}
