package delta.dkt.logic;

import ClientUIHandling.Config;
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
    void setup() {
        testProperty1 = new Property(12, 10, 10, PropertyLevel.NORMAL, 10, 10);
        testProperty2 = new Property(14, 10, 10, PropertyLevel.NORMAL, 10, 10);

        setMockRequirements_PropertyAquisition();
        player = new Player("Mike");

    }

    //? Getters

    /**
     * Checks whether the initialized nickname, via the constructor, has been set and is accesible.
     */
    @Test
    void checkNickname() {
        assertEquals("Mike", player.getNickname());
    }

    /**
     * Checks whether the default position of a player is set correclty and accessible.
     */
    @Test
    void checkDefaultPosition() {
        assertEquals(testProperty1, player.getPosition());
    }

    /**
     * Check whether a player is not suspened by default.
     */
    @Test
    void checkDefaultSuspension() {
        assertEquals(0, player.getSuspention());
    }

    /**
     * Checks whether the amount of players of a Game can be retrieved correctly.
     */
    @Test
    void checkGameGetPlayers() {
        assertEquals(0, Game.getPlayers().size());

        Game.getPlayers().put(1, null);
        assertEquals(1, Game.getPlayers().size());

        Game.getPlayers().clear();
        assertEquals(0, Game.getPlayers().size());
    }

    /**
     * Checks whether the default nickname is set.
     */
    @Test
    void checkDefaultPlayerNickname() {
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
    void checkSuccessfulPropertyAcquisition(boolean onTop) {
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
     *
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_LowCash(boolean onTop) {
        player = new Player();
        player.setCash(0);

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
     *
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_Owned(boolean onTop) {
        Property buying = onTop ? testProperty1 : testProperty2;
        buying.setOwner(new Player("Maxi"));

        if (onTop) {
            assertFalse(player.buyProperty());
        } else {
            assertFalse(player.buyProperty(testProperty2.getLocation()));
        }

        verifyMockCalls(buying);
    }


    /**
     * Checks whether the acquisition process handles the amtept to buy a field, thus no property, correclty.
     *
     * @param onTop parameterization for whether the player is on top of the property or not.
     */
    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void checkPropertyAcqusition_InvalidField(boolean onTop) {
        Game.setMap(mockMapHandling);

        Field invalidField = new SomeTestField(16);

        when(mockMapHandling.getField(1)).thenReturn(invalidField);
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
    void checkPropertyRefund_Successful() {
        player.buyProperty();

        assertTrue(player.sellProperty(testProperty1.getLocation()));
        assertEquals(Config.INITIAL_CASH - testProperty1.getPrice() / 2, player.getCash());
        assertEquals(0, player.getProperties().size());
        assertNull(testProperty1.getOwner());
        assertEquals(0, testProperty1.getAccessories().size());
    }

    /**
     * Checks whether a player can sell a property that he does not own.
     */
    @Test
    void checkPropertyRefund_NotOwned() {
        assertFalse(player.sellProperty(testProperty1.getLocation()));

        testProperty1.setOwner(new Player("Herbert"));
        assertFalse(player.sellProperty(testProperty1.getLocation()));
    }

    /**
     * Check whether the attempt of selling a field that is no property is handled correctly.
     */
    @Test
    void checkPropertyRefund_InvalidField() {
        Game.setMap(mockMapHandling);

        Field invalidField = new SomeTestField(16);

        when(mockMapHandling.getField(1)).thenReturn(invalidField);
        when(mockMapHandling.getField(16)).thenReturn(invalidField);

        player = new Player("Josef");

        assertFalse(player.sellProperty(invalidField.getLocation()));
    }

    //? Player Timeouts

    /**
     * Checks whether a player can be suspended for a given amount of rounds.
     */
    @Test
    void checkSettingPlayerTimeout() {
        player.setSuspension(10);
        assertEquals(10, player.getSuspention());
    }

    /**
     * Checks whether the suspension state can be accessed.
     */
    @Test
    void checkIsPlayerSuspended() {
        checkSettingPlayerTimeout();
        assertTrue(player.isSuspended());
    }

    /**
     * Check whether the suspension of a player can be lifted.
     */
    @Test
    void checkResetSuspension() {
        checkSettingPlayerTimeout();
        player.resetSuspension();
        assertFalse(player.isSuspended());
        assertEquals(0, player.getSuspention());
    }

    /**
     * Check whether reducing a player's suspension works properly.
     */
    @Test
    void checkReducingSuspension() {
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
    void setMockRequirements_PropertyAquisition() {
        Game.setMap(mockMapHandling);
        when(mockMapHandling.getField(1)).thenReturn(testProperty1);
        when(mockMapHandling.getField(testProperty1.getLocation())).thenReturn(testProperty1);
        when(mockMapHandling.getField(testProperty2.getLocation())).thenReturn(testProperty2);

        player = new Player("Patrick"); // => usage mock in instance variables
    }

    /**
     * This method will verify the calls of the previously mocked methods.
     */
    void verifyMockCalls(Field bought) {
        verify(mockMapHandling, atLeastOnce()).getField(1);
        verify(mockMapHandling, atLeastOnce()).getField(bought.getLocation());
    }

    /**
     * This method will check whether the property has been added to the players collection,
     * it will check if the owner property has been set correctly,
     * it will check whether the player cash has been modified correctly.
     */
    void testSuccessfulPropertyAcquisition(int exProperties, int propertyPrice) {
        assertEquals(exProperties, player.getProperties().size());
        assertEquals(player, player.getProperties().get(0).getOwner());
        assertEquals((Config.INITIAL_CASH - propertyPrice), player.getCash());
    }


    //! Testing the player movements

    /**
     * Checks whether moving the player with a given amount of steps is successful
     *
     * @param steps Steps that the player will move from its current position.
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10})
    void checkPlayerMovement_bySteps(int steps) {
        //? sets the return value for getField method with its given arguments to a valid property.
        int location = player.getPosition().getLocation() + steps;
        if (steps % 2 == 0) {
            when(mockMapHandling.getField(location)).thenReturn(generateDummyProperty(location));
        } else {
            //Test for movement on a special location
            Field mockField = new SpecialField(location);
            if(steps == 1) {
                mockField.setName("VermögensAbgabe");
            } else {
                mockField.setName("Steuerabgabe");
            }
            when(mockMapHandling.getField(location)).thenReturn(mockField);
        }
        when(mockMapHandling.getFields()).thenReturn(generateDummyList());

        player.moveSteps(steps);
        assertEquals(location, player.getPosition().getLocation());

        verify(mockMapHandling).getField(location);
        verify(mockMapHandling).getFields();
    }

    /**
     * Checks whether a players position can be set by moving the player to a given position.
     *
     * @param location The location to which the player should be moved.
     */
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 10})
    void checkPlayerMovement_moveTo(int location) {

        //? sets the return value for getField method with its given arguments to a valid property.
        when(mockMapHandling.getField(location == 0 ? location + 1 : location)).thenReturn(generateDummyProperty(location == 0 ? location + 1 : location));

        player.moveTo(location);
        assertEquals(location == 0 ? location + 1 : location, player.getPosition().getLocation());

        verify(mockMapHandling, atLeastOnce()).getField(location == 0 ? location + 1 : location);
    }


    /**
     * Checks whether a player is prohibited from moving when suspended.
     */
    @Test
    void checkPlayerMovement_Suspended() {
        when(mockMapHandling.getFields()).thenReturn(generateDummyList());

        player.setSuspension(10);
        assertTrue(player.isSuspended());

        int previous = player.getPosition().getLocation();
        player.moveTo(12);

        assertEquals(previous, player.getPosition().getLocation());
    }

    //! Preliminary tests for the Game Map

    /**
     * Checks whether the preliminary implementation of the Game map does not throw any errors.
     * For now, this test will stay in this class since it tests all the code written in this branch.
     */
    @Test
    void checkGameMap_getFields() {
        assertDoesNotThrow(() -> {
            assertEquals(40, new GameMap().getFields().size());
        });
    }

    /**
     * Checks whether the preliminary implementation of the getField method returns null.
     * For now, this test will stay in this class since it tests all the code written in this branch.
     */
    @Test
    void checkGameMap_getField() {
        assertNotNull(new GameMap().getField(10));
    }


    @Test
    void checkPlayer_payRent() {

        Player testPlayer = new Player();
        Player testOwner = new Player();

        Property p = new Property(1, 10, 10, PropertyLevel.CHEAP, 10, 10);
        p.setOwner(testOwner);
        int amount = p.calculateRent();
        int cashBeforePlayer = testPlayer.getCash();
        int cashBeforeOwner = testOwner.getCash();
        testPlayer.payRentTo(testOwner, amount);

        assertEquals(cashBeforePlayer - amount, testPlayer.getCash());
        assertEquals(cashBeforeOwner + amount, testOwner.getCash());

    }

    @Test
    void testSetNickName(){
        Player player = new Player();
        player.setNickname("testNickName");
        assertEquals("testNickName", player.getNickname());
    }

    @Test
    void testSetID(){
        Player player = new Player();
        player.setId(5);
        assertEquals(5, player.getId());
    }

    /**
     * This method will create a property object that is being used as a valid field.
     *
     * @param location The location of the property-field.
     */
    Field generateDummyProperty(int location) {
        return new Property(location, 0, 0, PropertyLevel.NORMAL, 0, 0);
    }

    ArrayList<Field> generateDummyList() {
        ArrayList<Field> dummy = new ArrayList<>();
        for (int i = 0; i < 40; i++) dummy.add(null);

        return dummy;
    }
}


class SomeTestField extends Field {

    public SomeTestField(int location) {
        super(location);
    }
}