package it.polimi.ingsw.network.eventHandlers.observers;

import it.polimi.ingsw.network.eventHandlers.observers.Observer;
import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

/**
 * Observable class to work as "listener", registers Observers and notifies them when
 * events occur.
 */
public class Observable {

    /**
     * Collection of registered Observers {@link Observer}
     */
    private final ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Method to add an observer to the registered ones.
     * @param observer: observer to be added.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the registered ones.
     * @param observer: observer to be removed.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all the observers by invoking the update() method to send them a Message
     * {@link Message}.
     *
     * @param message the message to be passed to the observers.
     */
    protected void notifyObserver(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
