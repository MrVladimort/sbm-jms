package mrvladimort.pet.sbmjms.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mrvladimort.pet.sbmjms.config.JmsConfig;
import mrvladimort.pet.sbmjms.model.BaseMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class BaseListener {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.BASE_QUEUE)
    public void listen(@Payload BaseMessage baseMessage, @Headers MessageHeaders headers, Message message) {
        log.info("I got a message");
        log.info(baseMessage.toString());
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void listenForHello(@Payload BaseMessage baseMessage,
                               @Headers MessageHeaders headers, Message message) throws JMSException {

        BaseMessage payloadMsg = BaseMessage
                .builder()
                .id(UUID.randomUUID())
                .name("World!!")
                .build();

        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
    }
}
