package it.polimi.ingsw.enumerations;

/**
 * Enum to list every possible message that can be sent by the player. Every message must have a message tag
 * from this enumeration.
 *
 * {@link it.polimi.ingsw.network.messages.Message}
 */
public enum PossiblePlayerMessages {
    SETUP_LEADERS,
    PING_MESSAGE,
    GAME_SIZE,
    RESOURCE,
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
    SEND_NICKNAME
}
