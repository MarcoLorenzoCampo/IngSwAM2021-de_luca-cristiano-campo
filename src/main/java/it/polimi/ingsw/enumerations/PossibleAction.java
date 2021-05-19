package it.polimi.ingsw.enumerations;

/**
 * Enumerates all the possible action requests
 * a player can query
 */
public enum PossibleAction {

    GET_RESOURCE_FROM_MARKET,
    CHANGE_COLOR,
    REARRANGE_INVENTORY,
    DEPOSIT,

    BUY_PRODUCTION_CARD,

    ACTIVATE_PRODUCTION,
    REMOVE_RESOURCE,
    EXECUTE_PRODUCTION,

    PLACE_LEADER_CARD,
    DISCARD_LEADER_CARD,

    END_TURN,

    LORENZO_ACTION,
}
