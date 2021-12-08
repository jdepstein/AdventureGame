package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import model.enums.CaveObject;
import model.enums.Direction;

import org.junit.Test;



public class PlayerTestP5 {

  @Test
  public void resetPlayer() {
    Cave cave1 =  new CaveImpl(new Location(0,0));
    Cave cave2 =  new CaveImpl(new Location(0,1));
    cave1.addConnection(Direction.NORTH, new Location(0,1) );
    cave2.addConnection(Direction.SOUTH, new Location(0,0) );
    cave1.addTreasure(CaveObject.DIAMOND);
    cave1.addTreasure(CaveObject.SAPPHIRE);
    cave2.addMonster(new Otyugh());
    Player player = new PlayerImpl("John", cave1);

    player.shootArrow();
    assertEquals(2, player.getItems().get(CaveObject.CROOKEDARROW).intValue());
    player.search();
    assertEquals(1, player.getItems().get(CaveObject.DIAMOND).intValue());
    assertEquals(1, player.getItems().get(CaveObject.SAPPHIRE).intValue());
    player.updateLocation(cave2);
    assertFalse(player.isAlive());

    player.reset(cave1);
    assertTrue(player.isAlive());
    assertEquals(3, player.getItems().get(CaveObject.CROOKEDARROW).intValue());
    assertEquals(0, player.getItems().get(CaveObject.DIAMOND).intValue());
    assertEquals(0, player.getItems().get(CaveObject.SAPPHIRE).intValue());

  }
}
