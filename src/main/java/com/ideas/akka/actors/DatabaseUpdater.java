package com.ideas.akka.actors;

import akka.actor.UntypedActor;
import com.ideas.akka.messages.UpdateDatabase;

public class DatabaseUpdater extends UntypedActor {

    @Override
    public void onReceive(Object message) throws Exception {
        if (message instanceof UpdateDatabase){
            UpdateDatabase updateDatabase= (UpdateDatabase) message;
            updateDatabase.getConnection().createStatement()
                    .execute(updateDatabase.getQuery());
        }
        else {
            unhandled(message);
        }

    }
}
