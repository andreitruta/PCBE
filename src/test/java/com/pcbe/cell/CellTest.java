package com.pcbe.cell;

import com.pcbe.cell.setup.CellConfigLoader;
import com.pcbe.core.LifeGameBootstrap;
import com.pcbe.environment.EnvironmentHolder;
import com.pcbe.util.ResourceXMLLoader;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class CellTest {
    static LifeGameBootstrap lifeGameBootstrap;
    EnvironmentHolder environmentHolder;

    @BeforeClass
    public static void setUpCore() {
        lifeGameBootstrap = new LifeGameBootstrap();
        lifeGameBootstrap.bindResourceLocator(new ResourceXMLLoader());
        CellConfigLoader.loadConfig();
    }
    
    @AfterClass
    public static void afterClass() {
    }
    
    @Before
    public void setUpCells() {
        environmentHolder = new EnvironmentHolder();
        Cell cell = new SCell("c1", environmentHolder);
        Cell cell1 = new SCell("c2", environmentHolder);
        Cell cell2 = new SCell("c3", environmentHolder);
        environmentHolder.getCellManager().addCell(cell);
        //environmentHolder.getCellManager().addCell(cell1);
        //environmentHolder.getCellManager().addCell(cell2);
    }
    
    @Test
    public void testCellFullTimer() {
        environmentHolder.beginExecution();
        System.out.println("Test");
    }
}
