package com.pcbe.cell;

import com.pcbe.cell.manager.CellManager;
import com.pcbe.cell.setup.CellConfig;
import com.pcbe.cell.setup.CellConfigException;
import com.pcbe.environment.EnvironmentHolder;
import com.pcbe.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Cell implements Runnable {
    private static final Logger LOG = LoggerFactory.getLogger(Cell.class);

    private long fullTime;
    private long starvationTime;

    protected boolean isAlive = true;
    protected boolean isStarving = false;
    protected ReentrantLock aliveLock = new ReentrantLock(true);

    protected String uniqueId;
    protected TimerTask requestFoodTask;
    protected CellManager cellManager;
    protected EnvironmentHolder environmentHolder;

    protected int timesAte;

    public Cell(String uniqueId, EnvironmentHolder environmentHolder) {
        this.uniqueId = uniqueId;
        this.cellManager = environmentHolder.getCellManager();
        this.environmentHolder = environmentHolder;
        this.starvationTime = CellConfig.STARVE_TIME_SECONDS * 1000;
        this.fullTime = CellConfig.FULL_TIME_SECONDS * 1000;
        verifyConfig();
        LOG.info("Instantiated cell with id " + this.uniqueId);
    }

    public void run() {
        try {
            while (isAlive) {
                Thread.sleep(fullTime);
                /*
                 * Request food after fullTimer expires
                 * If not received after starvationTime, die
                 */
                isStarving = true;
                environmentHolder.requestFood(Cell.this);
            }
        } catch(InterruptedException e) {
            LOG.info(e.getMessage());
        }
    }

    public String getUniqueId() {
        return  this.uniqueId;
    }

    public void consume(Resource resource) {
        //Make sure it doesn't die while consuming resources
        LOG.debug(uniqueId + "->consume() @ " + Thread.currentThread().getName());
        if(aliveLock.tryLock()) {
            try {
                this.isStarving = false;
                this.timesAte++;
                if(timesAte >= 10) {
                    if(reproduce()) {
                        timesAte = 0;
                    }
                }
                this.fullTime = (int) (resource.getNutritionValue() * CellConfig.FULL_TIME_SECONDS) * 1000;
                LOG.info(uniqueId + " consumed " + resource.getName() + "Total left: " + environmentHolder.getResourcesLeft() + ". Full for " + fullTime / 1000 + " seconds.");
            } finally {
                aliveLock.unlock();
            }
        }
    }

    public void die() {
        if (aliveLock.tryLock()) {
            try {
                //Lock wasn't acquired while consuming, so cell will be removed
                this.isAlive = false;
                this.environmentHolder.generateFood(this);
                this.cellManager.removeCell(this);
                LOG.info(uniqueId + " died.");
            } finally {
                aliveLock.unlock();
            }
        }
    }

    public abstract boolean reproduce();

    public void verifyConfig() {
        if(fullTime == 0)
            throw new CellConfigException("FullTime cannot be zero!");
        if(starvationTime == 0)
            throw new CellConfigException("StarvationTime cannot be zero!");
    }

    public String toString() {
        return this.uniqueId;
    }
}