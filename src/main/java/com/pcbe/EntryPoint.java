package com.pcbe;

import com.pcbe.cell.ASCell;
import com.pcbe.cell.Cell;
import com.pcbe.cell.SCell;
import com.pcbe.cell.setup.CellConfigLoader;
import com.pcbe.core.LifeGameBootstrap;
import com.pcbe.environment.EnvironmentHolder;
import com.pcbe.util.ResourceXMLLoader;

public class EntryPoint {
    static LifeGameBootstrap lifeGameBootstrap;
    static EnvironmentHolder environmentHolder;
    
    public static void main(String args[]) {
        lifeGameBootstrap = new LifeGameBootstrap();
        lifeGameBootstrap.bindResourceLocator(new ResourceXMLLoader());
        CellConfigLoader.loadConfig();
        environmentHolder = new EnvironmentHolder();
        Cell cell = new ASCell("c1", environmentHolder);
        Cell cell1 = new SCell("c2", environmentHolder);
        Cell cell2 = new SCell("c3", environmentHolder);
        environmentHolder.getCellManager().addCell(cell);
        environmentHolder.getCellManager().addCell(cell1);
        environmentHolder.getCellManager().addCell(cell2);
        environmentHolder.beginExecution();
    }
}
