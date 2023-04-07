package delta.dkt.logic;

import delta.dkt.logic.structure.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerTests {

    GameMap mockMapHandling = mock(GameMap.class);
    Player player = null;

    Property testProperty1 = null;
    Property testProperty2 = null;

    @BeforeEach
    void setup () {
        player = new Player("Mike");
        testProperty1 = new Property(12, 10, 10, PropertyLevel.NORMAL, 10, 10);
        testProperty2 = new Property(14, 10, 10, PropertyLevel.NORMAL, 10, 10);
    }

    //? Getters

    /**
     * Checks whether the initialized nickname, via the constructor, has been set and is accesible.
     */
    @Test
    void checkNickname () {
        assertEquals("Mike", player.getNickname());
    }

    /**
     * Checks whether the player actually starts with the default cash.
     */
    @Test
    void checkCash () {
        assertEquals(Player.getStartCash(), player.getCash());
    }

    /**
     * Checks whether the default position of a player is set correclty and accessible.
     */
    @Test
    void checkDefaultPosition () {
        setMockRequirements_PropertyAquisition();
        assertEquals(testProperty1, player.getPosition());
    }

    /**
     * Check whether a player is not suspened by default.
     */
    @Test
    void checkDefaultSuspension () {
        assertEquals(0, player.getSuspention());
    }


    /**
     * Checks whether the static variable startCash can be accessed and modified
     */
    @Test
    void checkStartCash_GetterSetter () {
        Player.setStartCash(1000);
        assertEquals(1000, Player.getStartCash());
    }

    /**
     * Checks whether the static variable playerCount can be accessed and modified
     */
    @Test
    void checkGamePlayerCount_GetterSetter () {
        Game.setPlayerCount(10);
        assertEquals(10, Game.getPlayerCount());
    }

    /**
     * Checks whether the default nickname is set.
     */
    @Test
    void checkDefaultPlayerNickname () {
        player = new Player();
        assertTrue(player.getNickname().startsWith("Player-"));
    }

    //? Property Acquisitions

    /**
     * Checks whether a property can be bought when standing on top of it and when a player would choose to buy any (free) property.
     * Including reference checks included in this payment cycle.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkSuccessfulPropertyAcquisition (boolean onTop) {
        setMockRequirements_PropertyAquisition();

        Property buying = onTop ? testProperty1 : testProperty2;

        if (onTop) {
            assertTrue(player.buyProperty());
        } else {
            assertTrue(player.buyProperty(testProperty2.getLocation()));
        }

        testSuccessfulPropertyAcquisition(1, buying.getPrice());

        verifyMockCalls(buying);
    }


    /**
     * Checks whether the proceeding of a player attempting to buy a property when he does not have enough cash.
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_LowCash (boolean onTop) {
        Player.setStartCash(0);
        setMockRequirements_PropertyAquisition();

        Property buying = onTop ? testProperty1 : testProperty2;

        if (onTop) {
            assertFalse(player.buyProperty());
        } else {
            assertFalse(player.buyProperty(testProperty2.getLocation()));
        }

        verifyMockCalls(buying);
    }


    /**
     * Checks whether a property cannot be bought when it is already owned by someone else.
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_Owned (boolean onTop) {
        Property buying = onTop ? testProperty1 : testProperty2;
        buying.setOwner(new Player("Maxi"));

        setMockRequirements_PropertyAquisition();

        if (onTop) {
            assertFalse(player.buyProperty());
        } else {
            assertFalse(player.buyProperty(testProperty2.getLocation()));
        }

        verifyMockCalls(buying);
    }


    /**
     * Checks whether the acquisition process handles the amtept to buy a field, thus no property, correclty.
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_InvalidField (boolean onTop) {
        Game.setMap(mockMapHandling);

        Field invalidField = new SomeTestField(16);

        when(mockMapHandling.getField(0)).thenReturn(invalidField);
        when(mockMapHandling.getField(16)).thenReturn(invalidField);

        player = new Player("Josef");

        if (onTop) {
            assertFalse(player.buyProperty());
        } else {
            assertFalse(player.buyProperty(invalidField.getLocation()));
        }

        verifyMockCalls(invalidField);
    }


    //? Property Refunds

    /**
     * Checks whether a property owned by a player can be refuned successfully
     */
    @Test
    void checkPropertyRefund_Successful () {
        setMockRequirements_PropertyAquisition();
        player.buyProperty();

        assertTrue(player.sellProperty(testProperty1.getLocation()));
        assertEquals(Player.getStartCash() - testProperty1.getPrice() / 2, player.getCash());
        assertEquals(0, player.getProperties().size());
        assertNull(testProperty1.getOwner());
        assertEquals(0, testProperty1.getAccessories().size());
    }

    /**
     * Checks whether a player can sell a property that he does not own.
     */
    @Test
    void checkPropertyRefund_NotOwned () {
        setMockRequirements_PropertyAquisition();

        assertFalse(player.sellProperty(testProperty1.getLocation()));

        testProperty1.setOwner(new Player("Herbert"));
        assertFalse(player.sellProperty(testProperty1.getLocation()));
    }

    /**
     * Check whether the attempt of selling a field that is no property is handled correctly.
     */
    @Test
    void checkPropertyRefund_InvalidField () {
        Game.setMap(mockMapHandling);

        Field invalidField = new SomeTestField(16);

        when(mockMapHandling.getField(0)).thenReturn(invalidField);
        when(mockMapHandling.getField(16)).thenReturn(invalidField);

        player = new Player("Josef");

        assertFalse(player.sellProperty(invalidField.getLocation()));
    }

    //? Player Timeouts

    /**
     * Checks whether a player can be suspended for a given amount of rounds.
     */
    @Test
    void checkSettingPlayerTimeout () {
        player.setSuspension(10);
        assertEquals(10, player.getSuspention());
    }

    /**
     * Checks whether the suspension state can be accessed.
     */
    @Test
    void checkIsPlayerSuspended () {
        checkSettingPlayerTimeout();
        assertTrue(player.isSuspended());
    }

    /**
     * Check whether the suspension of a player can be lifted.
     */
    @Test
    void checkResetSuspension () {
        checkSettingPlayerTimeout();
        player.resetSuspension();
        assertFalse(player.isSuspended());
        assertEquals(0, player.getSuspention());
    }

    /**
     * Check whether reducing a player's suspension works properly.
     */
    @Test
    void checkReducingSuspension () {
        checkSettingPlayerTimeout();

        for (int i = 10; i > 0; i--) {
            assertEquals(i, player.getSuspention());
            player.reduceSuspension();
            assertEquals(i - 1, player.getSuspention());

            // there is still a suspension
            if (i - 1 > 0) assertTrue(player.isSuspended());
        }

        assertEquals(0, player.getSuspention());
        assertFalse(player.isSuspended());

        player.reduceSuspension();
        assertEquals(0, player.getSuspention());
    }


    /**
     * This method will set up the mock-requirements for testing the property-payment-cycle
     */
    void setMockRequirements_PropertyAquisition () {
        Game.setMap(mockMapHandling);
        when(mockMapHandling.getField(0)).thenReturn(testProperty1);
        when(mockMapHandling.getField(testProperty1.getLocation())).thenReturn(testProperty1);
        when(mockMapHandling.getField(testProperty2.getLocation())).thenReturn(testProperty2);

        player = new Player("Patrick"); // => usage mock in instance variables
    }

    /**
     * This method will verify the calls of the previously mocked methods.
     */
    void verifyMockCalls (Field bought) {
        verify(mockMapHandling).getField(0);
        verify(mockMapHandling).getField(bought.getLocation());
    }

    /**
     * This method will check whether the property has been added to the players collection,
     * it will check if the owner property has been set correctly,
     * it will check whether the player cash has been modified correctly.
     */
    void testSuccessfulPropertyAcquisition (int exProperties, int propertyPrice) {
        assertEquals(exProperties, player.getProperties().size());
        assertEquals(player, player.getProperties().get(0).getOwner());
        assertEquals((Player.getStartCash() - propertyPrice), player.getCash());
    }


    //! Testing the player movements

    /**
     * Checks whether moving the player with a given amount of steps is successful
     * @param steps Steps that the player will move from its current position.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 10})
    void checkPlayerMovement_bySteps (int steps) {
        setMockRequirements_PropertyAquisition();

        //? sets the return value for getField method with its given arguments to a valid property.
        int location = player.getPosition().getLocation() + steps;
        when(mockMapHandling.getField(location)).thenReturn(generateDummyProperty(location));
        when(mockMapHandling.getFields()).thenReturn(generateDummyList());

        player.moveSteps(steps);
        assertEquals(location, player.getPosition().getLocation());

        verify(mockMapHandling).getField(location);
        verify(mockMapHandling).getFields();
    }

    /**
     * Checks whether a players position can be set by moving the player to a given position.
     * @param location The location to which the player should be moved.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 10})
    void checkPlayerMovement_moveTo (int location) {
        setMockRequirements_PropertyAquisition();

        //? sets the return value for getField method with its given arguments to a valid property.
        when(mockMapHandling.getField(location)).thenReturn(generateDummyProperty(location));
        when(mockMapHandling.getFields()).thenReturn(generateDummyList());

        player.moveTo(location);
        assertEquals(location, player.getPosition().getLocation());

        verify(mockMapHandling).getField(location);
        verify(mockMapHandling).getFields();
    }

    
    /**
     * Checks whether a player is prohibited from moving when suspended.
     */
    @Test
    void checkPlayerMovement_Suspended () {
        setMockRequirements_PropertyAquisition();
        when(mockMapHandling.getFields()).thenReturn(generateDummyList());

        player.setSuspension(10);
        assertTrue(player.isSuspended());

        int previous = player.getPosition().getLocation();
        player.moveTo(12);

        assertEquals(previous, player.getPosition().getLocation());
    }

    /**
     * This method will create a property object that is being used as a valid field.
     * @param location The location of the property-field.
     */
    Field generateDummyProperty (int location) {
        return new Property(location, 0, 0, PropertyLevel.NORMAL, 0, 0);
    }

    ArrayList<Field> generateDummyList () {
        ArrayList<Field> dummy = new ArrayList<>();
        for (int i = 0; i < 40; i++) dummy.add(null);

        return dummy;
    }
}


class SomeTestField extends Field {

    public SomeTestField (int location) {
        super(location);
    }
}