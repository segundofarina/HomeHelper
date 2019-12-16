package ar.edu.itba.paw.homehelper.config;

import ar.edu.itba.paw.homehelper.auth.JwtAuthentication;
import ar.edu.itba.paw.homehelper.auth.TokenAuthenticationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.simp.user.DefaultUserDestinationResolver;
import org.springframework.messaging.simp.user.SimpUserRegistry;
import org.springframework.messaging.simp.user.UserDestinationResolver;
import org.springframework.messaging.support.ChannelInterceptorAdapter;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.messaging.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@Configuration
@EnableWebSocketMessageBroker
@Order(HIGHEST_PRECEDENCE + 50)
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    // Taken from https://stackoverflow.com/questions/30887788/json-web-token-jwt-with-spring-based-sockjs-stomp-web-socket

    private DefaultSimpUserRegistry userRegistry = new DefaultSimpUserRegistry();
    private DefaultUserDestinationResolver userDestinationResolver = new DefaultUserDestinationResolver(userRegistry);

    @Autowired
    TokenAuthenticationManager tokenAuthenticationManager;

    @Bean
    @Primary
    public SimpUserRegistry userRegistry() {
        return userRegistry;
    }

    @Bean
    @Primary
    public UserDestinationResolver userDestinationResolver() {
        return userDestinationResolver;
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS().setSessionCookieNeeded(false);
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue", "/topic", "/user");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.setInterceptors(new ChannelInterceptorAdapter() {
            @Override
            public Message<?> preSend(Message<?> message, MessageChannel channel) {
                StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

                List<String> tokenList = accessor.getNativeHeader("X-Authorization");
                accessor.removeNativeHeader("X-Authorization");

                String token = null;
                if(tokenList != null && tokenList.size() > 0) {
                    token = tokenList.get(0);
                }

                /* Generate principal */
                Principal auth = null;
                if(token != null) {
                    auth = tokenAuthenticationManager.authenticate(new JwtAuthentication(token));
                }

                if (accessor.getMessageType() == SimpMessageType.CONNECT) {
                    userRegistry.onApplicationEvent(new SessionConnectedEvent(this, (Message<byte[]>) message, auth));
                } else if (accessor.getMessageType() == SimpMessageType.SUBSCRIBE) {
                    userRegistry.onApplicationEvent(new SessionSubscribeEvent(this, (Message<byte[]>) message, auth));
                } else if (accessor.getMessageType() == SimpMessageType.UNSUBSCRIBE) {
                    userRegistry.onApplicationEvent(new SessionUnsubscribeEvent(this, (Message<byte[]>) message, auth));
                } else if (accessor.getMessageType() == SimpMessageType.DISCONNECT) {
                    userRegistry.onApplicationEvent(new SessionDisconnectEvent(this, (Message<byte[]>) message, accessor.getSessionId(), CloseStatus.NORMAL));
                }

                accessor.setUser(auth);

                accessor.setLeaveMutable(true);

                return MessageBuilder.createMessage(message.getPayload(), accessor.getMessageHeaders());
            }
        });
    }
}
