package com.ideas.akka.com.ideas.runner;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.ideas.akka.actors.DatabaseUpdater;
import com.ideas.akka.messages.UpdateDatabase;
import akka.routing.RoundRobinPool;
import java.sql.Connection;
import java.sql.DriverManager;

public class Run {

    private static final long NUMBER_OF_INSERTIONS = 100000;
    public static void main(String... args)throws Exception{
        final ActorSystem actorSystem = ActorSystem.create("actor-system");
        //final ActorRef actorRef = actorSystem.actorOf(Props.create(DatabaseUpdater.class), "database-updater");
        ActorRef actorRef = actorSystem.actorOf(new RoundRobinPool(10).props(Props.create(DatabaseUpdater.class)));
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/akka", "root", "root");
        connection.createStatement().executeUpdate("truncate akka_table");
        for(long i=0;i<NUMBER_OF_INSERTIONS;i++){
            actorRef.tell(new UpdateDatabase("INSERT INTO akka_table VALUES ('random','random','random','random','random',6,7,8,9,10)",connection), null);
        }
        Thread.sleep(5000);
        actorSystem.shutdown();
        System.out.println("Actor system closed.");
    }
}
