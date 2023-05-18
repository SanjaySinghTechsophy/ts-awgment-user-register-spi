package com.techsophy.awgment.provider;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventListenerProviderFactory;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.KeycloakSessionFactory;



public class SampleEventListenerProviderFactory implements EventListenerProviderFactory {
    private static final String LISTENER_ID = "awgment_register_event_listener";

    @Override
    public EventListenerProvider create(KeycloakSession keycloakSession) {

        return new SampleEventListenerProvider(keycloakSession);
    }

    @Override
    public void init(Config.Scope scope) {
        //Initiate the Listener
    }

    @Override
    public void postInit(KeycloakSessionFactory keycloakSessionFactory) {
            //Post Initiate the Listener
    }

    @Override
    public void close() {
        //Close the Listener
    }

    @Override
    public String getId() {
        return LISTENER_ID;
    }
}