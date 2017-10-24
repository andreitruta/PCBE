package com.pcbe.draw;

import com.pcbe.cell.Cell;
import org.piccolo2d.PCamera;
import org.piccolo2d.PCanvas;
import org.piccolo2d.PLayer;
import org.piccolo2d.event.PMouseWheelZoomEventHandler;
import org.piccolo2d.extras.PFrame;
import org.piccolo2d.nodes.PPath;
import org.piccolo2d.nodes.PText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Random;

public class CellDrawManager extends PFrame {
    private static final Logger LOG = LoggerFactory.getLogger(CellDrawManager.class);

    private PLayer nodeLayer;
    private PCamera camera;
    private HashMap<String, PPath> cells;

    public CellDrawManager() {
        this(null);
        cells = new HashMap<>();
    }

    public CellDrawManager(final PCanvas aCanvas) {
        super("Life Game", false, aCanvas);
    }

    public void initialize() {
        Dimension dimension = new Dimension(1600, 1200);
        setPreferredSize(dimension);
        Dimension minDimension = new Dimension(800, 600);
        setMinimumSize(minDimension);
        setResizable(false);
        this.nodeLayer = new PLayer();

        camera = new PCamera();
        camera.setBounds(0, 0, getWidth(), getHeight());
        camera.addLayer(nodeLayer);
        camera.setPaint(Color.darkGray);
        getCanvas().getLayer().addChild(camera);

        getCanvas().removeInputEventListener(getCanvas().getZoomEventHandler());
        PMouseWheelZoomEventHandler mouseWheelZoomEventHandler = new PMouseWheelZoomEventHandler();
        mouseWheelZoomEventHandler.setScaleFactor(-0.1d);
        mouseWheelZoomEventHandler.zoomAboutMouse();
        getCanvas().getLayer().addInputEventListener(mouseWheelZoomEventHandler);
    }


    public void addCell(Cell cell) {
        final Random rnd = new Random();
        final float x = (float) (300. * rnd.nextDouble());
        final float y = (float) (400. * rnd.nextDouble());
        LOG.debug("x = " + x + ", y = " + y);
        final PPath cellEllipse = PPath.createEllipse(x, y, 20, 20);
        cellEllipse.setPaint(Color.red);
        PText pText = new PText(cell.getUniqueId());
        pText.setBounds(x, y, 20, 20);
        pText.setConstrainHeightToTextHeight(false);
        pText.setConstrainWidthToTextWidth(false);
        pText.centerFullBoundsOnPoint(cellEllipse.getBounds().getCenterX(), cellEllipse.getBounds().getCenterY());
        cellEllipse.addChild(pText);
        cells.put(cell.getUniqueId(), cellEllipse);
        nodeLayer.addChild(cellEllipse);
        nodeLayer.repaint();
    }

    public void removeCell(Cell cell) {
        PPath removeCell = cells.remove(cell.getUniqueId());
        nodeLayer.removeChild(removeCell);
        nodeLayer.repaint();
    }
}
