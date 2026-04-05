package ru.mescat.message.event.listener;

import org.springframework.transaction.event.TransactionalEventListener;
import ru.mescat.message.dto.MessageForUser;

public class MessageEvent {

    @TransactionalEventListener
    public void newMessage(MessageForUser message){

    }
}
