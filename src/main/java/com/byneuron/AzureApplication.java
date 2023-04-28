package com.byneuron;

import org.slf4j.bridge.SLF4JBridgeHandler;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import io.micronaut.context.event.BeanCreatedEvent;
import io.micronaut.context.event.BeanCreatedEventListener;
import io.micronaut.runtime.Micronaut;
import jakarta.inject.Singleton;

public class AzureApplication {
	
	static {
		SLF4JBridgeHandler.install();
	}

    public static void main(String[] args) {
    	Micronaut.build(args)
    	.mainClass(AzureApplication.class)
    	.environmentPropertySource(true)
    	.start();
    }
    
    @Singleton
    static class ObjectMapperBeanEventListener implements BeanCreatedEventListener<ObjectMapper> {

        @Override
        public ObjectMapper onCreated(BeanCreatedEvent<ObjectMapper> event) {
            return configureObjectMapper(event.getBean());
            
        }
    }
    
    public static ObjectMapper configureObjectMapper(final ObjectMapper objectMapper) {
    	objectMapper.registerModule(new GuavaModule());
		objectMapper.registerModule(new Jdk8Module());
		objectMapper.registerModule(new JavaTimeModule());
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		objectMapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
        return objectMapper;
    }
}
