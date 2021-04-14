package it.polimi.ingsw.enumerations;

import it.polimi.ingsw.model.actions.Action;

/**
 * Enumerates all the possible action requests
 * a player can query
 */
public enum PossibleAction {

    BUY_PRODUCTION_CARD,
    PLACE_LEADER_CARD,
    DISCARD_LEADER_CARD,
    GET_RESOURCE_FROM_MARKET,
    ACTIVATE_PRODUCTION,
    REARRANGE_INVENTORY,
    END_TURN
}
