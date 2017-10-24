package com.pcbe.environment;

import com.pcbe.cell.Cell;
import com.pcbe.cell.manager.CellManager;
import com.pcbe.cell.setup.CellConfig;
import com.pcbe.resources.Resource;
import com.pcbe.resources.ResourceType;
import com.pcbe.resources.Resources;
import com.pcbe.resources.ResourcesLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class EnvironmentHolder {
    private static final Logger LOG = LoggerFactory.getLogger(EnvironmentHolder.class);

    private LinkedList<Resource> resources;
    private CellManager cellManager;
    private final ReentrantLock resourcesLock = new ReentrantLock(true);
    private Condition foodPresentCondition = resourcesLock.newCondition();
    private Resources resourceTypes;
    //TODO Move to config file
    public int MAX_RESOURCE = 15;
    private final AtomicInteger cellCount = new AtomicInteger(0);

    public EnvironmentHolder() {
        resources = new LinkedList<>();
        resourceTypes = ResourcesLoader.loadResourcesTypes();
        cellManager = new CellManager();
        generateResources();
        LOG.info("Instantitated EnvironmentHolder");
    }

    public int getResourcesLeft() {
        return this.resources.size();
    }

    public void requestFood(Cell cell) throws InterruptedException {
        LOG.debug("Cell " + cell + ": requested food. Total resources remaining: " + resources.size());
        resourcesLock.lock();
        LOG.debug("Cell " + cell + ": acquired res lock.");
        try {
            if(resources.isEmpty()) {
                LOG.debug("Cell " + cell + ": No food available, waiting...");
                int randomInt = new Random().nextInt(10);
                if(!foodPresentCondition.await((CellConfig.STARVE_TIME_SECONDS*1000)+randomInt, TimeUnit.MILLISECONDS)) {
                    LOG.debug("Cell " + cell + ": Ran out of time for food...");
                    cell.die();
                    return;
                }
                LOG.debug("Cell " + cell + ": Wait over");
            }
            //TODO Be more random???
            Resource resource = resources.removeFirst();
            cell.consume(resource);
        } finally {
            resourcesLock.unlock();
        }
        LOG.debug("Cell " + cell + ": released res lock. Total resources remaining: " + resources.size() + "\n");
    }

    public void generateFood(Cell cell) {
        //LOG.debug("generateFood: Cell " + cell + " trying to acquire res lock.");
        resourcesLock.lock();
        //LOG.debug("generateFood: Cell " + cell + " acquired res lock.");
        try {
            //LOG.debug("Generating food from cell " + cell);
            //TODO Use cell for determining
            int randomInt = new Random().nextInt(5) + 1;
            int randomResInt = new Random().nextInt(resourceTypes.getResourceList().size());

            for (int i = 0; i < randomInt; i++) {
                resources.add(new Resource(resourceTypes.getResourceList().get(randomResInt)));
                foodPresentCondition.signal();
            }
            LOG.info("Generated " + randomInt + " units of resources. Total: " + resources.size());
        } finally {
            resourcesLock.unlock();
        }
        //LOG.debug("generateFood: Cell " + cell + " released res lock.\n");
    }

    public void generateResources() {
        for(ResourceType resourceType : resourceTypes.getResourceList()) {
            for(int i = 0; i < MAX_RESOURCE; i++) {
                resources.add(new Resource(resourceType));
            }
        }
        Collections.shuffle(resources);
    }

    public void setResources(LinkedList<Resource> resources) {
        this.resources = resources;
    }

    public CellManager getCellManager() {
        return cellManager;
    }

    public void setCellManager(CellManager cellManager) {
        this.cellManager = cellManager;
    }

    public int getNextCellNumber() {
        return this.cellCount.getAndIncrement();
    }

    public void beginExecution() {
        for(Cell cell : cellManager.getCells()) {
            new Thread(cell, cell.getUniqueId()).start();
        }
    }

    public ReentrantLock getResourcesLock() {
        return this.resourcesLock;
    }

    public Condition getFoodPresentCondition() {
        return this.foodPresentCondition;
    }
}