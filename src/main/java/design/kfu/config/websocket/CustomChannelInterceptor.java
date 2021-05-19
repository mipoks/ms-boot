package design.kfu.config.websocket;

import design.kfu.config.security.details.UserDetailsImpl;
import design.kfu.entity.music.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.security.Principal;

@Slf4j
public class CustomChannelInterceptor implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        //ToDo сделать проверку, что id пользователя совпадает с адресом, куда он хочет подписаться
        //ToDo и будет ли это работать?

//        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);
//        Principal userPrincipal = headerAccessor.getUser();
//        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
//                (UsernamePasswordAuthenticationToken) userPrincipal;
//        UserDetailsImpl temp = (UserDetailsImpl) usernamePasswordAuthenticationToken.getPrincipal();
//        Person person = (temp).getUser();
//        System.out.println(message);
//        System.out.println(channel);
        return message;
    }
}
