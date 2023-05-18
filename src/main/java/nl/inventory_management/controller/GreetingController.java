package nl.inventory_management.controller;

import nl.inventory_management.configuration.security.isauthenticated.IsAuthenticated;
import nl.inventory_management.controller.dto.MessageReceive;
import nl.inventory_management.controller.dto.MessageSent;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import javax.annotation.security.RolesAllowed;
import java.time.LocalDateTime;

@Controller
public class GreetingController {
   @MessageMapping("/chat")
    @SendTo("/topic/message")
    public MessageSent greeting(MessageReceive message) {
        return new MessageSent(message.getUserName(), message.getMessage(), LocalDateTime.now());
    }
}
