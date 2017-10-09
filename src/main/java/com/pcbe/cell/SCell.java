package com.pcbe.cell;

import com.pcbe.environment.EnvironmentHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Random;

public class SCell extends Cell {
    private static final Logger LOG = LoggerFactory.getLogger(SCell.class);

    public SCell(String uniqueId, EnvironmentHolder environmentHolder) {
        super(uniqueId, environmentHolder);
    }

    @Override
    public boolean reproduce() {
        environmentHolder.getCellManager().getCellsLock().lock();
        try {
            Cell cell = getCellWaitingToReproduce();
            if (cell != null) {
                reproduce(cell);
                return true;
            }
        } finally {
            environmentHolder.getCellManager().getCellsLock().unlock();
        }
        return false;
    }

    private void reproduce(Cell cell) {
        this.timesAte = 0;
        cell.timesAte = 0;

        int cellTypeInt = new Random().nextInt(1);
        Cell newCell;
        String uniqueId = "pc" + environmentHolder.getNextCellNumber();
        switch (cellTypeInt) {
            case 0: {
                newCell = new ASCell(uniqueId, environmentHolder);
                break;
            }
            default: {
                newCell = new SCell(uniqueId, environmentHolder);
                break;
            }
        }
        cellManager.addCell(newCell);
        new Thread(newCell, newCell.getUniqueId()).start();
        LOG.info("Cell " + this + " produced cell " + newCell + " with " + cell);
    }

    private Cell getCellWaitingToReproduce() {
        Iterator<Cell> cellIterator = environmentHolder.getCellManager().getCells().iterator();
        while(cellIterator.hasNext()) {
            Cell cell = cellIterator.next();
            if(cell != this && cell instanceof SCell && ((SCell)cell).isWaitingToReproduce()) {
                return cell;
            }
        }
        return null;
    }

    public boolean isWaitingToReproduce() {
        return (timesAte >= 10);
    }
}