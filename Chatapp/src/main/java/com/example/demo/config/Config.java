package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class Config implements WebSocketMessageBrokerConfigurer {

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		
		registry.addEndpoint("/server1").withSockJS();// Client yaha connect karega
		
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		
	   registry.enableSimpleBroker("/topic"); // Broadcast ke liye use hoga
	   registry.setApplicationDestinationPrefixes("/app");// Koi bhi message ye prefix pe bhejega
	   
	}
    
	
	
}
