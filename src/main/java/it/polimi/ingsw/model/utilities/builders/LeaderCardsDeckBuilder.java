package it.polimi.ingsw.model.utilities.builders;

import it.polimi.ingsw.enumerations.Color;
import it.polimi.ingsw.enumerations.EffectType;
import it.polimi.ingsw.enumerations.Level;
import it.polimi.ingsw.enumerations.ResourceType;
import it.polimi.ingsw.model.market.leaderCards.*;
import it.polimi.ingsw.model.utilities.DevelopmentTag;
import it.polimi.ingsw.model.utilities.ResourceTag;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Utility class to build the leader cards leaderCardsDeck. Methods are declared static since they serve
 * validation purposes. They don't have side effects and can be called from different parts of the code.
 */
public final class LeaderCardsDeckBuilder {

    public static List<LeaderCard> deckBuilder() {

        List<LeaderCard> leaderCardsDeck = new LinkedList<>();

        leaderCardsDeck.add(new MarbleExchangeLeaderCard(
                5,
                EffectType.MARBLE_EXCHANGE,
                new DevelopmentTag[] {new DevelopmentTag(2, Color.GREEN, Level.ANY),
                        new DevelopmentTag(1, Color.PURPLE, Level.ANY)},
                /*new DevelopmentTag[] {new DevelopmentTag(0, Color.GREEN, Level.ANY),
                        new DevelopmentTag(0, Color.PURPLE, Level.ANY)},*/
                ResourceType.SHIELD
        ));
        leaderCardsDeck.add(new MarbleExchangeLeaderCard(
                5,
                EffectType.MARBLE_EXCHANGE,
                new DevelopmentTag[] {new DevelopmentTag(2, Color.PURPLE, Level.ANY),
                        new DevelopmentTag(1, Color.GREEN, Level.ANY)},
                /*new DevelopmentTag[] {new DevelopmentTag(0, Color.PURPLE, Level.ANY),
                        new DevelopmentTag(0, Color.GREEN, Level.ANY)},*/
                ResourceType.COIN
        ));
        leaderCardsDeck.add(new MarbleExchangeLeaderCard(
                5,
                EffectType.MARBLE_EXCHANGE,
                new DevelopmentTag[] {new DevelopmentTag(2, Color.YELLOW, Level.ANY),
                        new DevelopmentTag(1, Color.BLUE, Level.ANY)},
                ResourceType.SERVANT
        ));
        leaderCardsDeck.add(new MarbleExchangeLeaderCard(
                5,
                EffectType.MARBLE_EXCHANGE,
                new DevelopmentTag[] {new DevelopmentTag(2, Color.BLUE, Level.ANY),
                        new DevelopmentTag(1, Color.YELLOW, Level.ANY)},
                ResourceType.STONE
        ));

        leaderCardsDeck.add(new DiscountLeaderCard(
                2,
                EffectType.DISCOUNT,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.YELLOW, Level.ANY),
                        new DevelopmentTag(1, Color.PURPLE, Level.ANY)},
                /*new DevelopmentTag[] {new DevelopmentTag(0, Color.YELLOW, Level.ANY),
                        new DevelopmentTag(0, Color.PURPLE, Level.ANY)},*/
                ResourceType.COIN
        ));
        leaderCardsDeck.add(new DiscountLeaderCard(
                2,
                EffectType.DISCOUNT,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.GREEN, Level.ANY),
                        new DevelopmentTag(1, Color.BLUE, Level.ANY)},
                /*new DevelopmentTag[] {new DevelopmentTag(0, Color.GREEN, Level.ANY),
                        new DevelopmentTag(0, Color.BLUE, Level.ANY)},*/
                ResourceType.STONE
        ));
        leaderCardsDeck.add(new DiscountLeaderCard(
                2,
                EffectType.DISCOUNT,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.BLUE, Level.ANY),
                        new DevelopmentTag(1, Color.PURPLE, Level.ANY)},
                ResourceType.SHIELD
        ));
        leaderCardsDeck.add(new DiscountLeaderCard(
                2,
                EffectType.DISCOUNT,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.YELLOW, Level.ANY),
                        new DevelopmentTag(1, Color.GREEN, Level.ANY)},
                ResourceType.SERVANT
        ));

        leaderCardsDeck.add(new ExtraProductionLeaderCard(
                ResourceType.COIN,
                4,
                EffectType.EXTRA_PRODUCTION,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.GREEN, Level.TWO)},
                new ResourceTag[] {new ResourceTag(ResourceType.COIN, 1)},
                new ResourceTag[] {new ResourceTag(ResourceType.UNDEFINED, 1),
                        new ResourceTag(ResourceType.FAITH, 1),}
        ));
        leaderCardsDeck.add(new ExtraProductionLeaderCard(
                ResourceType.STONE,
                4,
                EffectType.EXTRA_PRODUCTION,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.PURPLE, Level.TWO)},
                new ResourceTag[] {new ResourceTag(ResourceType.STONE, 1)},
                new ResourceTag[] {new ResourceTag(ResourceType.UNDEFINED, 1),
                        new ResourceTag(ResourceType.FAITH, 1)}
        ));
        leaderCardsDeck.add(new ExtraProductionLeaderCard(
                ResourceType.SERVANT,
                4,
                EffectType.EXTRA_PRODUCTION,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.BLUE, Level.TWO)},
                new ResourceTag[] {new ResourceTag(ResourceType.SERVANT, 1)},
                new ResourceTag[] {new ResourceTag(ResourceType.UNDEFINED, 1),
                        new ResourceTag(ResourceType.FAITH, 1)}
        ));
        leaderCardsDeck.add(new ExtraProductionLeaderCard(
                ResourceType.SHIELD,
                4,
                EffectType.EXTRA_PRODUCTION,
                new DevelopmentTag[] {new DevelopmentTag(1, Color.YELLOW, Level.TWO)},
                new ResourceTag[] {new ResourceTag(ResourceType.SHIELD, 1)},
                new ResourceTag[] {new ResourceTag(ResourceType.UNDEFINED, 1),
                        new ResourceTag(ResourceType.FAITH, 1)}
        ));

                leaderCardsDeck.add(new ExtraInventoryLeaderCard(
                3,
                EffectType.EXTRA_INVENTORY,
                new ResourceTag[] {new ResourceTag(ResourceType.STONE, 5)},
                null,
                ResourceType.SERVANT
        ));
        leaderCardsDeck.add(new ExtraInventoryLeaderCard(
                3,
                EffectType.EXTRA_INVENTORY,
                new ResourceTag[] {new ResourceTag(ResourceType.SHIELD, 5)},
                null,
                ResourceType.COIN
        ));
        leaderCardsDeck.add(new ExtraInventoryLeaderCard(
                3,
                EffectType.EXTRA_INVENTORY,
                new ResourceTag[] {new ResourceTag(ResourceType.SERVANT, 5)},
                null,
                ResourceType.SHIELD
        ));
        leaderCardsDeck.add(new ExtraInventoryLeaderCard(
                3,
                EffectType.EXTRA_INVENTORY,
                new ResourceTag[] {new ResourceTag(ResourceType.COIN, 5)},
                null,
                ResourceType.STONE
        ));

        Collections.shuffle(leaderCardsDeck);
        return leaderCardsDeck;
    }
}
