package test;

import org.junit.Test;
import org.junit.Assert;
import simulationobjects.location.Location;
import simulationobjects.populations.AverageGroup;
import simulationobjects.resources.Resource;
import simulationobjects.resources.ResourceGroup;
import simulationobjects.resources.resourcetypes.ResourceTypes;

import java.util.HashMap;
import java.util.Map;

public class AverageGroupTest {

    @Test
    public void averageGroupShouldConsumeResources() {
        Location location = new Location(0,0);
        AverageGroup group = new AverageGroup(100, location, ResourceTypes.CLOTHING);

        Resource resourceMeat = new Resource(ResourceTypes.MEAT, location);
        Resource resourceFruit = new Resource(ResourceTypes.FRUIT, location);
        Resource resourceGrain = new Resource(ResourceTypes.GRAIN, location);

        HashMap<Resource, ResourceGroup> resources = new HashMap<>();
        resources.put(resourceMeat, new ResourceGroup(200));
        resources.put(resourceFruit, new ResourceGroup(200));
        resources.put(resourceGrain, new ResourceGroup(200));

        group.consume(resources);

        for (ResourceGroup entry : resources.values()) {
            Assert.assertNotEquals(200, entry.getQuantity(), 0.0f);
        }
    }

    @Test
    public void averageGroupShouldGetSatisfactionFromConsuming() {
        Location location = new Location(0,0);
        AverageGroup group = new AverageGroup(100, location, ResourceTypes.CLOTHING);

        Resource resourceMeat = new Resource(ResourceTypes.MEAT, location);
        Resource resourceFruit = new Resource(ResourceTypes.FRUIT, location);
        Resource resourceGrain = new Resource(ResourceTypes.GRAIN, location);

        HashMap<Resource, ResourceGroup> resources = new HashMap<>();
        resources.put(resourceMeat, new ResourceGroup(200));
        resources.put(resourceFruit, new ResourceGroup(200));
        resources.put(resourceGrain, new ResourceGroup(200));

        HashMap<ResourceTypes, Float> satisfaction = group.consume(resources);

        for (float entry : satisfaction.values()) {
            Assert.assertEquals(1, entry, 0.0f);
        }
    }

    @Test
    public void averageGroupShouldLackSatisfactionWithoutResource() {
        Location location = new Location(0,0);
        AverageGroup group = new AverageGroup(100, location, ResourceTypes.CLOTHING);

        Resource resourceMeat = new Resource(ResourceTypes.MEAT, location);
        Resource resourceGrain = new Resource(ResourceTypes.GRAIN, location);

        HashMap<Resource, ResourceGroup> resources = new HashMap<>();
        resources.put(resourceMeat, new ResourceGroup(200));
        resources.put(resourceGrain, new ResourceGroup(200));

        HashMap<ResourceTypes, Float> satisfaction = group.consume(resources);


        for (Map.Entry<ResourceTypes, Float> entry : satisfaction.entrySet()) {
            if(entry.getValue() == 0.0f && entry.getKey() == ResourceTypes.FRUIT) {
                Assert.assertTrue(true);
                return;
            }
        }
        Assert.fail();
    }

    @Test
    public void averageGroupShouldProduceResources() {
        Location location = new Location(0,0);
        AverageGroup group = new AverageGroup(100, location, ResourceTypes.CLOTHING);

        Resource resourceWool = new Resource(ResourceTypes.WOOL, location);
        Resource resourceClothes = new Resource(ResourceTypes.CLOTHING, location);

        HashMap<Resource, ResourceGroup> resources = new HashMap<>();
        resources.put(resourceWool, new ResourceGroup(1000));
        resources.put(resourceClothes, new ResourceGroup(0));

        HashMap<ResourceTypes, Float> result = group.produce(resources);


        for (Map.Entry<ResourceTypes, Float> entry : result.entrySet()) {
            if(entry.getKey() == ResourceTypes.CLOTHING) {
                Assert.assertEquals(100, entry.getValue(), 0.0f);
                return;
            }
        }
        Assert.fail();
    }
}
