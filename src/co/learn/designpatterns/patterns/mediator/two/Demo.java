package co.learn.designpatterns.patterns.mediator.two;

import co.learn.log.Log;
import io.reactivex.Observable;
import io.reactivex.Observer;

import java.util.ArrayList;
import java.util.List;

class EventBroker extends Observable<Integer> {
    List<Observer<? extends Integer>> observers = new ArrayList<>();

    @Override
    protected void subscribeActual(Observer observer) {
        observers.add(observer);
    }

    public void publish(int number) {
        for (Observer observer : observers) {
            observer.onNext(number);
        }
    }
}

class FootballPlayer {
    public String name;
    private int goalsScored;
    private EventBroker broker;

    public FootballPlayer(String name, EventBroker broker) {
        this.name = name;
        this.broker = broker;
    }

    public void scores() {
        broker.publish(++goalsScored);
    }
}

class FootballCoach {
    public FootballCoach(EventBroker broker) {
        broker.subscribe(number -> {
            Log.logLine("Hey, you scored " + number + ". Keep it going.");
        });
    }
}

public class Demo {
    public static void main(String[] args) {
        EventBroker broker = new EventBroker();
        FootballPlayer player = new FootballPlayer("jones", broker);
        FootballCoach coach = new FootballCoach(broker);

        player.scores();
        player.scores();
        player.scores();
    }
}
