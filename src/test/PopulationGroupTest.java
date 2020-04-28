package test;

import org.junit.Test;
import org.junit.Assert;

import simulationobjects.location.Location;
import simulationobjects.populations.PopulationGroup;
import simulationobjects.resources.FoodGroup;
import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.ResourceType;

import java.util.HashMap;

public class PopulationGroupTest {

    private class TestPopGroup extends PopulationGroup {

        public TestPopGroup(float size, Location location) {
            super(size, location);

        }

        public void addNeed(ResourceType need, float quantity) {
            super.setNeed(need, quantity);
        }
    }

    @Test
    public void testIfNeedIsAdded() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.addNeed(ResourceType.FRUIT, 1.2f);

        Assert.assertEquals(1.2f, popGroup.getNeeds().get(ResourceType.FRUIT), 0.0f);
    }

    @Test
    public void testIfNonExistentNeedReturnsNull() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.addNeed(ResourceType.FRUIT, 1.2f);

        Assert.assertNull(popGroup.getNeeds().get(ResourceType.GRAIN));
    }

    @Test
    public void addMethodAddsToQuantity() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.add(200);


        Assert.assertEquals(300, popGroup.getQuantity(), 0.0f);
    }

    @Test
    public void addMethodRemovesFromQuantity() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.add(-50);


        Assert.assertEquals(50, popGroup.getQuantity(), 0.0f);
    }

    @Test
    public void addMethodSetsPopulationAsZeroIfWiped() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.add(-200);

        Assert.assertEquals(0, popGroup.getQuantity(), 0.0f);
    }

    @Test
    public void multiplyMethodMultipliesPop() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.multiply(2);


        Assert.assertEquals(200, popGroup.getQuantity(), 0.0f);
    }

    @Test
    public void multiplyMethodWipesPopulationIfLessThanZero() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.multiply(-2f);


        Assert.assertEquals(0, popGroup.getQuantity(), 0.0f);
    }

    @Test
    public void setQuantitySetsQuantity() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.setQuantity(50);


        Assert.assertEquals(50, popGroup.getQuantity(), 0.0f);
    }

    @Test
    public void setQuantityWipesPopIfSetToNegativeQuantity() {
        Location location = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, location);

        popGroup.setQuantity(-50);


        Assert.assertEquals(0, popGroup.getQuantity(), 0.0f);
    }

    @Test
    public void popShouldConsumeIfResourceIsAtSameLocation() {
        Location popL = new Location(0,0);

        Location resL = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, popL);

        popGroup.addNeed(ResourceType.GRAIN, 0.1f);

        FoodGroup resources = new FoodGroup(100,ResourceType.GRAIN, resL);
        Resource resKey = new Resource(ResourceType.GRAIN, resL);
        HashMap<Resource, ResourceGroup> map = new HashMap<>();
        map.put(resKey, resources);

        popGroup.consume(map);


        Assert.assertEquals(90, map.get(resKey).getQuantity(), 0.0f);
    }

    @Test
    public void popShouldNotConsumeIfResourceIsAtDifferentLocation() {
        Location popL = new Location(0,0);

        Location resL = new Location(1,0);

        TestPopGroup popGroup = new TestPopGroup(100, popL);

        popGroup.addNeed(ResourceType.GRAIN, 0.1f);

        FoodGroup resources = new FoodGroup(100,ResourceType.GRAIN, resL);
        Resource resKey = new Resource(ResourceType.GRAIN, resL);
        HashMap<Resource, ResourceGroup> map = new HashMap<>();
        map.put(resKey, resources);

        popGroup.consume(map);


        Assert.assertEquals(100, map.get(resKey).getQuantity(), 0.0f);
    }

    @Test
    public void popShouldHaveTheirNeedsSatisfiedIfSuccessfullyConsuming() {
        Location popL = new Location(0,0);

        Location resL = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, popL);

        popGroup.addNeed(ResourceType.GRAIN, 0.1f);

        FoodGroup resources = new FoodGroup(100,ResourceType.GRAIN, resL);
        Resource resKey = new Resource(ResourceType.GRAIN, resL);
        HashMap<Resource, ResourceGroup> map = new HashMap<>();
        map.put(resKey, resources);


        float satisfaction = popGroup.consume(map).get(ResourceType.GRAIN);

        Assert.assertEquals(1, satisfaction, 0.0f);
    }

    @Test
    public void popShouldHaveTheirNeedsPartlySatisfiedIfResourcesArePartlyAdequate() {
        Location popL = new Location(0,0);

        Location resL = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, popL);

        popGroup.addNeed(ResourceType.GRAIN, 1f);

        FoodGroup resources = new FoodGroup(50,ResourceType.GRAIN, resL);
        Resource resKey = new Resource(ResourceType.GRAIN, resL);
        HashMap<Resource, ResourceGroup> map = new HashMap<>();
        map.put(resKey, resources);


        float satisfaction = popGroup.consume(map).get(ResourceType.GRAIN);

        Assert.assertEquals(0.5f, satisfaction, 0.0f);
    }

    @Test
    public void popShouldHaveTheirNeedsUnsatisfiedIfResourcesAreNonExistent() {
        Location popL = new Location(0,0);

        Location resL = new Location(0,0);

        TestPopGroup popGroup = new TestPopGroup(100, popL);

        popGroup.addNeed(ResourceType.GRAIN, 1f);

        FoodGroup resources = new FoodGroup(0,ResourceType.GRAIN, resL);
        Resource resKey = new Resource(ResourceType.GRAIN, resL);
        HashMap<Resource, ResourceGroup> map = new HashMap<>();
        map.put(resKey, resources);


        float satisfaction = popGroup.consume(map).get(ResourceType.GRAIN);

        Assert.assertEquals(0f, satisfaction, 0.0f);
    }
}
