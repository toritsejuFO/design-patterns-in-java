package co.learn.designpatterns.patterns.mediator.exercise;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

class ParticipantEvent {
    public UUID participantId;
    public int value;

    public ParticipantEvent(UUID participantId, int value) {
        this.participantId = participantId;
        this.value = value;
    }
}

class Participant {
    public UUID id;
    public int value;
    private Mediator mediator;

    public Participant(Mediator mediator) {
        // todo
        this.mediator = mediator;
        this.id = UUID.randomUUID();
        mediator.subscribe(this);
    }

    public void say(int n) {
        // todo
        mediator.publish(new ParticipantEvent(this.id, n));
    }

    public void notify(ParticipantEvent event) {
        this.value = event.value;
    }
}

class Mediator {
    // todo
    List<Participant> participants = new ArrayList<>();

    public Mediator() {
    }

    public void subscribe(Participant participant) {
        participants.add(participant);
    }

    public void publish(ParticipantEvent event) {
        for (Participant participant : participants) {
            if (participant.id != event.participantId) {
                participant.notify(event);
            }
        }
    }
}

public class Demo {
    public static void main(String[] args) {

        Mediator mediator = new Mediator();
        Participant p1 = new Participant(mediator);
        Participant p2 = new Participant(mediator);

        assertEquals(0, p1.value);
        assertEquals(0, p2.value);

        p1.say(2);

        assertEquals(0, p1.value);
        assertEquals(2, p2.value);

        p2.say(4);

        assertEquals(4, p1.value);
        assertEquals(2, p2.value);
    }
}
