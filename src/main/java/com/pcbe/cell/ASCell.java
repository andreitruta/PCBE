package com.pcbe.cell;

import com.pcbe.environment.EnvironmentHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class ASCell extends Cell {
    private static final Logger LOG = LoggerFactory.getLogger(ASCell.class);

    public ASCell(String uniqueId, EnvironmentHolder environmentHolder) {
        super(uniqueId, environmentHolder);
    }

    @Override
    public boolean reproduce() {
        int cellTypeInt = new Random().nextInt(1);
        Cell cell;
        String uniqueId = "pc" + environmentHolder.getNextCellNumber();
        switch(cellTypeInt) {
            case 0: {
                cell = new ASCell(uniqueId, environmentHolder);
                break;
            }
            default: {
                cell = new SCell(uniqueId, environmentHolder);
                break;
            }
        }
        cellManager.addCell(cell);
        new Thread(cell, cell.getUniqueId()).start();
        LOG.info("Cell " + this + " produced cell " + cell);
        return true;
    }
}