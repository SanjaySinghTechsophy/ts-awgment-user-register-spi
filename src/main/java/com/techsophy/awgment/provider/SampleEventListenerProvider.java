package com.techsophy.awgment.provider;

import com.techsophy.awgment.utils.AwgmentRestCall;
import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;
import org.keycloak.models.RealmProvider;
import org.keycloak.models.UserModel;

import java.util.Map;
import java.util.logging.Logger;


public class SampleEventListenerProvider implements EventListenerProvider {
    private static final Logger log = Logger.getLogger("SampleEventListenerProvider");
    private final KeycloakSession session;
    private final RealmProvider model;
    public SampleEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
        log.info(String.format("## NEW %s EVENT", event.getType().name()));
        log.info("-----------------------------------------------------------");
        if (EventType.REGISTER.equals(event.getType())) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel newRegisteredUser = this.session.users().getUserById(event.getUserId(), realm);
            registerNewUser(realm, event, newRegisteredUser);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        log.info("Admin Event Occurred");
    }

    @Override
    public void close() {

    }

    private String registerNewUser(RealmModel realm, Event event,UserModel newRegisteredUser) {
        log.info("Event Type: "+event.getType().name());
        log.info("First Name: "+newRegisteredUser.getFirstName());
        log.info("Last Name: "+newRegisteredUser.getLastName());
        log.info("Email: "+newRegisteredUser.getEmail());
        log.info("Username: "+newRegisteredUser.getUsername());
        log.info("Realm Name: "+realm.getName());
        log.info("User Id: "+event.getUserId());
        log.info("Client Id: "+event.getClientId());
        log.info("Identity Provider: "+event.getDetails().get("identity_provider"));
        RealmModel realmModel = session.realms().getRealm(event.getRealmId());
        UserModel user = session.users().getUserById(event.getUserId(), realmModel);

        user.setSingleAttribute("userId", event.getUserId());
        AwgmentRestCall.addUserToAwgment(
                event.getType().name(),
                newRegisteredUser.getUsername(),
                newRegisteredUser.getFirstName(),
                newRegisteredUser.getLastName(), 
                "",
                event.getUserId(),
                newRegisteredUser.getEmail(),
                realm.getName(),
                event.getClientId(),
                event.getDetails().get("identity_provider"));
        
        return "SUCCESS";
    }
}