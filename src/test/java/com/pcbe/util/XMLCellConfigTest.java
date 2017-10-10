package com.pcbe.util;

import com.pcbe.cell.Cell;
import com.pcbe.cell.SCell;
import com.pcbe.cell.setup.CellConfig;
import com.pcbe.cell.setup.CellConfigException;
import com.pcbe.cell.setup.CellConfigLoader;
import com.pcbe.core.LifeGameBootstrap;
import com.pcbe.environment.EnvironmentHolder;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class XMLCellConfigTest {
    static LifeGameBootstrap lifeGameBootstrap;
    
    @BeforeClass
    public static void setUpCore() {
        lifeGameBootstrap = new LifeGameBootstrap();
        lifeGameBootstrap.bindResourceLocator(new ResourceXMLLoader());
    }
    
    @Before
    public void destroyCellConfig() {
        CellConfig.FULL_TIME_SECONDS = 0;
        CellConfig.STARVE_TIME_SECONDS = 0;
    }
    
    @Test
    public void testLoadConfigFromResource() {
        CellConfigLoader.loadConfig();
        assertTrue(CellConfig.FULL_TIME_SECONDS != 0);
        assertTrue(CellConfig.STARVE_TIME_SECONDS != 0);
    }
    
    @Test(expected = CellConfigException.class)
    public void testBadConfigCell() {
        //Should throw exception
        Cell cell = new SCell("test",new EnvironmentHolder());
    }
}
