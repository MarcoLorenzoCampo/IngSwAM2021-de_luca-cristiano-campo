package it.polimi.ingsw.network.eventHandlers;

import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

/**
 * Observable class to work as "listener", registers Observers and notifies them when
 * events occur.
 */
public class Observable {

    /**
     * Collection of registered Observers {@link IObserver}
     */
    private final ArrayList<IObserver> observers = new ArrayList<>();

    /**
     * Method to add an observer to the registered ones.
     * @param observer: observer to be added.
     */
    public void addObserver(IObserver observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the registered ones.
     * @param observer: observer to be removed.
     */
    public void removeObserver(IObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all the observers by invoking the update() method to send them a Message
     * {@link Message}.
     *
     * @param message the message to be passed to the observers.
     */
    protected void notifyObserver(Message message) {
        for (IObserver observer : observers) {
            observer.update(message);
        }
    }
}
