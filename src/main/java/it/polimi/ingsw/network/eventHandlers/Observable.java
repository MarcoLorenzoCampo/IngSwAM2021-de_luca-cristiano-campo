package it.polimi.ingsw.network.eventHandlers;

import it.polimi.ingsw.network.messages.Message;

import java.util.ArrayList;

/**
 * Observable class to work as "listener", registers Observers and notifies them when
 * events occur.
 * Class that want to become observable must extend this class.
 */
public abstract class Observable {

    /**
     * Collection of registered Observers {@link Observer}
     */
    private final ArrayList<Observer> observers = new ArrayList<>();

    /**
     * Collections of registered ControllerObservers {@link ControllerObserver}
     */
    private final ArrayList<ControllerObserver> controllerObservers = new ArrayList<>();

    /**
     * Method to add an observer to the registered ones.
     * @param observer: observer to be added.
     */
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Overloaded method to add one of the controller package as an observer.
     * @param observer: controller observer to add.
     */
    public void addControllerObserver(ControllerObserver observer) {
        controllerObservers.add(observer);
    }
    /**
     * Removes an observer from the registered ones.
     * @param observer: observer to be removed.
     */
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Method to remove a registered controller observer.
     * @param observer: controller observer to remove.
     */
    public void removeObserver(ControllerObserver observer) {
        controllerObservers.remove(observer);
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

    /**
     * Notifies all the controller by invoking the update() method to send them a Message
     * {@link Message}.
     *
     * @param message: message passed to the controller.
     */
    protected void notifyControllerObserver(Message message) {
        for(ControllerObserver observer : controllerObservers) {
            observer.controllerUpdate(message);
        }
    }
}
