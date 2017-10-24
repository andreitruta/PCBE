package com.pcbe.cell.manager;

import com.pcbe.cell.Cell;
import com.pcbe.draw.CellDrawManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CellManager {
    private volatile List<Cell> cells;
    private ReentrantLock lock = new ReentrantLock(true);
    private CellDrawManager cellManager;

    public CellManager() {
        this.cells = new ArrayList<>();
        this.cellManager = new CellDrawManager();
    }

    public void removeCell(Cell cell) {
        lock.lock();
        this.cells.remove(cell);
        this.cellManager.removeCell(cell);
        lock.unlock();
    }

    public void addCell(Cell cell) {
        lock.lock();
        this.cells.add(cell);
        this.cellManager.addCell(cell);
        lock.unlock();
    }

    public List<Cell> getCells() {
        return this.cells;
    }

    public ReentrantLock getCellsLock() {
        return this.lock;
    }
}