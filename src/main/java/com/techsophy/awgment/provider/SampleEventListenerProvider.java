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

import java.math.BigInteger;


public class SampleEventListenerProvider implements EventListenerProvider {
    private final KeycloakSession session;
    private final RealmProvider model;
    public SampleEventListenerProvider(KeycloakSession session) {
        this.session = session;
        this.model = session.realms();
    }

    @Override
    public void onEvent(Event event) {
        if (EventType.REGISTER.equals(event.getType())) {
            RealmModel realm = this.model.getRealm(event.getRealmId());
            UserModel newRegisteredUser = this.session.users().getUserById(event.getUserId(), realm);
            registerNewUser(realm, event, newRegisteredUser);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        //Admin Event Occurred
    }

    @Override
    public void close() {
        // Close the Listener
    }

    private String registerNewUser(RealmModel realm, Event event,UserModel newRegisteredUser) {
        RealmModel realmModel = session.realms().getRealm(event.getRealmId());
        UserModel user = session.users().getUserById(event.getUserId(), realmModel);


        BigInteger userId = AwgmentRestCall.addUserToAwgment(
                newRegisteredUser.getUsername(),
                newRegisteredUser.getFirstName(),
                newRegisteredUser.getLastName(), 
                "",
                newRegisteredUser.getEmail(),
                realm.getName());
        user.setSingleAttribute("userId", userId.toString());
        return "SUCCESS";
    }
}