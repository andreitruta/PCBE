package com.pcbe.cell;

import com.pcbe.cell.manager.CellManager;
import com.pcbe.cell.setup.CellConfig;
import com.pcbe.cell.setup.CellConfigException;
import com.pcbe.resources.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.ReentrantLock;

public abstract class Cell implements Runnable{
    private static final Logger LOG = LoggerFactory.getLogger(Cell.class);

    private long fullTime;
    private long starvationTime;

    protected boolean isAlive = true;
    protected boolean isStarving = false;
    protected ReentrantLock aliveLock = new ReentrantLock(true);

    protected String uniqueId;
    protected Timer fullTimer;
    protected Timer starvationTimer;
    protected TimerTask requestFoodTask;
    protected CellManager cellManager;

    private int timesAte;

    public Cell(String uniqueId) {
        this.uniqueId = uniqueId;
        this.fullTime = CellConfig.FULL_TIME_SECONDS * 1000;
        this.starvationTime = CellConfig.STARVE_TIME_SECONDS * 1000;
        verifyConfig();
    }

    public void run() {
        LOG.info("Instantiated cell with id " + this.uniqueId);
        this.fullTimer = new Timer();
        this.requestFoodTask = new RequestFoodTask();
        fullTimer.schedule(requestFoodTask, fullTime);
    }

    public String getUniqueId() {
        return  this.uniqueId;
    }

    public void consume(Resource resource) {
        //Make sure it doesn't die while consuming resources
        LOG.info(uniqueId + "->consume() @ " + Thread.currentThread().getName());
        if(aliveLock.tryLock()) {
            try {
                this.isStarving = false;
                this.starvationTimer.cancel();
                this.timesAte++;
                if(timesAte == 10) {
                    this.timesAte = 0;
                    reproduce();
                }
                this.fullTime = (int) (resource.getNutritionValue() * CellConfig.FULL_TIME_SECONDS) * 1000;
                this.fullTimer = new Timer();
                this.requestFoodTask = new RequestFoodTask();
                this.fullTimer.schedule(requestFoodTask, fullTime);
                LOG.info(uniqueId + " consumed " + resource.getName() + ". Full for " + fullTime / 1000 + " seconds.");
            } finally {
                aliveLock.unlock();
            }
        }
    }

    public void die() {
        if(aliveLock.tryLock()) {
            try {
                //Lock wasn't acquired while consuming, so cell will be removed
                LOG.info(uniqueId + " died.");
                this.isAlive = false;
                this.fullTimer.cancel();
                this.requestFoodTask.cancel();
                this.cellManager.removeCell(this);
            } finally {
                aliveLock.unlock();
            }
        }
    }

    public abstract void reproduce();

    public void verifyConfig() {
        if(fullTime == 0)
            throw new CellConfigException("FullTime cannot be zero!");
        if(starvationTime == 0)
            throw new CellConfigException("StarvationTime cannot be zero!");
    }

    class RequestFoodTask extends TimerTask {
        @Override
        public void run() {
            /**
             * Request food after fullTimer expires
             * If not received after starvationTime, die
             */
            isStarving = true;
            fullTimer.cancel();
            starvationTimer = new Timer();
            starvationTimer.schedule(new DieTask(), starvationTime);
        }
    }

    class DieTask extends TimerTask {
        /**
         * Timer ran out, sorry
         */
        @Override
        public void run() {
            //Nobody is dying, eh?
            synchronized (cellManager.getDeathLock()) {
                //If it hasn't eaten while another cell was dying
                //This is to prevent situations where one cell dies and then another dies
                //being unable to eat the dead one
                if(isStarving) {
                    LOG.info(uniqueId + "->DIE() @ " + Thread.currentThread().getName());
                    die();
                }
            }
        }
    }

    public String toString() {
        return this.uniqueId;
    }
    
}
