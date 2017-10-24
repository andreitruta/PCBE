package com.pcbe;

import com.pcbe.cell.ASCell;
import com.pcbe.cell.Cell;
import com.pcbe.cell.SCell;
import com.pcbe.cell.setup.CellConfigLoader;
import com.pcbe.core.LifeGameBootstrap;
import com.pcbe.environment.EnvironmentHolder;
import com.pcbe.util.ResourceXMLLoader;

import java.util.Random;

public class EntryPoint {
    static LifeGameBootstrap lifeGameBootstrap;
    static EnvironmentHolder environmentHolder;
    
    public static void main(String args[]) {
        lifeGameBootstrap = new LifeGameBootstrap();
        lifeGameBootstrap.bindResourceLocator(new ResourceXMLLoader());
        CellConfigLoader.loadConfig();
        environmentHolder = new EnvironmentHolder();
        
        for(int i = 0; i < 20; i++) {
            Cell newCell = null;
            
            Random random = new Random();
            
            if(random.nextInt(2) == 1) {
                newCell = new ASCell("c" + i, environmentHolder);
            } else {
                newCell = new SCell("c" + i, environmentHolder);
            }
            
            environmentHolder.getCellManager().addCell(newCell);
        }
        
        environmentHolder.beginExecution();
    }
}
