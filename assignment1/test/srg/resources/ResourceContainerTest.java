package srg.test.srg.resources;

import static org.junit.Assert.*;

import org.junit.Test;
import srg.resources.FuelContainer;
import srg.resources.FuelGrade;
import srg.resources.ResourceContainer;
import srg.resources.ResourceType;

public class ResourceContainerTest {

  /**
   * Test Constructor of ResourceContainer
   */
  @Test
  public void resourceContainerTest() {
    ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT, 9);
    assertEquals(ResourceType.REPAIR_KIT, container.getType());
    assertEquals(9, container.getAmount());

    try {
      new ResourceContainer(ResourceType.FUEL, 5);
      assertFalse("Assertion failed", 0 == 0);
    } catch (IllegalArgumentException ex) {
      assertTrue("Assertion thrown", 0 == 0);
    }

    ResourceContainer container1 = new FuelContainer(FuelGrade.TRITIUM, 8);
    assertEquals(ResourceType.FUEL, container1.getType());
    assertEquals(8, container1.getAmount());

  }

  /**
   * Test the function canStore()
   */
  @Test
  public void canStoreTest() {
    ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT, 5);
    assertTrue(container.canStore(ResourceType.REPAIR_KIT));
    assertFalse(container.canStore(ResourceType.FUEL));
  }

  /**
   * Test the function getAmount()
   */
  @Test
  public void getAmountTest() {
    ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT, 5);
    assertEquals(5, container.getAmount());
    ResourceContainer container1 = new FuelContainer(FuelGrade.TRITIUM, 1);
    assertEquals(1, container1.getAmount());
  }

  @Test
  public void setAmountTest() {
    ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT, 5);
    container.setAmount(7);
    assertEquals(7, container.getAmount());

    ResourceContainer container1 = new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 5);
    container1.setAmount(3);
    assertEquals(3, container1.getAmount());
  }

  @Test
  public void getShortNameTest() {
    ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT, 5);
    assertEquals("REPAIR_KIT", container.getShortName());

    ResourceContainer container1 = new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 5);
    assertEquals("HYPERDRIVE_CORE", container1.getShortName());

    ResourceContainer container2 = new FuelContainer(FuelGrade.TRITIUM, 5);
    assertEquals("TRITIUM", container2.getShortName());
  }

  @Test
  public void toStringTest() {
    ResourceContainer container = new ResourceContainer(ResourceType.REPAIR_KIT, 5);
    assertEquals("REPAIR_KIT: 5", container.toString());

    ResourceContainer container1 = new FuelContainer(FuelGrade.HYPERDRIVE_CORE, 1);
    assertEquals("FUEL: 1 - HYPERDRIVE_CORE", container1.toString());

  }
}
