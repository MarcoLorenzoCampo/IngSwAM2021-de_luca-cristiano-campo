package it.polimi.ingsw.enumerations;

import it.polimi.ingsw.network.messages.Message;

/**
 * Enum to list every possible message that can be sent by the player. Every message must have a message tag
 * from this enumeration.
 *
 * Checks are made in the model to assure no server messages can be sent by the player and
 * vice-versa.
 *
 * {@link Message}
 */
public enum PossibleMessages {

    //Messages sent by the players to the server.

    SETUP_LEADERS,
    SETUP_RESOURCES,
    PING_MESSAGE,
    GAME_SIZE,
    DEPOSIT,

    RESOURCE,
    END_TURN,
    DISCARD_LEADER,
    ACTIVATE_LEADER,
    GET_RESOURCES,
    SWAP,
    PEEK_ENEMY,
    ACTIVATE_PRODUCTION,
    ACTIVATE_EXTRA_PRODUCTION,
    ACTIVATE_BASE_PRODUCTION,
    BUY_PRODUCTION,
    SOURCE_WAREHOUSE,
    SOURCE_STRONGBOX,
    SEND_NICKNAME,
    EXCHANGE_RESOURCE,

    //Messages sent by the server to the players.

    GAME_STATUS,
    BOARD,
    LORENZO_TOKEN,
    LORENZO_FAITHTRACK,
    AVAILABLE_PRODUCTION_CARDS,
    LOGIN_OUTCOME,
    LOBBY_SIZE_REQUEST,
    GENERIC_SERVER_MESSAGE,
    FAITH_TRACK_MESSAGE,
    AVAILABLE_LEADERS,
    WIN_MESSAGE,
    YOUR_TURN,
    EXECUTE_PRODUCTION,
    BUFFER,
    STRONGBOX,
    WAREHOUSE,
    PRODUCTION_BOARD,
    FINAL_PRODUCTION,
    ERROR,
    PEEK_MESSAGE,
    POPE_FAVOR,

    //Notification message sent by the model to the controller
    VATICAN_REPORT_NOTIFICATION,
    DISCARDED_RESOURCE,
    END_GAME,
    NO_MORE_CARDS,
    BOUGHT_7_CARDS
}
