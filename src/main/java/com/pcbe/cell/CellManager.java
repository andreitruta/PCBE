package com.pcbe.cell;

import com.pcbe.cell.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CellManager {
    private volatile List<Cell> cells;
    private ReentrantLock lock = new ReentrantLock(true);

    public CellManager() {
        this.cells = new ArrayList<>();
    }

    public void removeCell(Cell cell) {
        lock.lock();
        this.cells.remove(cell);
        lock.unlock();
    }

    public void addCell(Cell cell) {
        this.cells.add(cell);
    }

    public List<Cell> getCells() {
        return this.cells;
    }

    public ReentrantLock getDeathLock() {
        return this.lock;
    }
}
