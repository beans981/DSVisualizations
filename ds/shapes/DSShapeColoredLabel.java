package edu.usfca.ds.shapes;

import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.shape.SLabel;
import edu.usfca.xj.foundation.XJXMLSerializable;

import java.awt.*;
import java.awt.geom.Rectangle2D;


public class DSShapeColoredLabel extends GElementLabel implements XJXMLSerializable {

    protected Color[] ColorIndex;
    protected double offsets[] = null;
    protected final Object lock = new Object();

    public DSShapeColoredLabel() {
        super();
    }


    public void setLabel(String label) {
        synchronized (lock) {
            int i;
            super.setLabel(label);
            ColorIndex = new Color[label.length()];
            for (i = 0; i < label.length(); i++)
                ColorIndex[i] = Color.BLACK;
            offsets = null;
        }
    }

    public void setLabelColor(Color c) {
        int i;

        for (i = 0; i < ColorIndex.length; i++)
            ColorIndex[i] = c;
        super.setLabelColor(c);
    }

    public void setLabelColorIndex(Color c, int index) {
        if (index >= 0 && index < ColorIndex.length)
            ColorIndex[index] = c;
    }

    public void draw(Graphics2D g) {
        int i,j;
        Rectangle2D bounds1;

        synchronized (lock) {

            if (offsets == null) {
                FontMetrics fm = g.getFontMetrics();
                offsets = new double[ColorIndex.length];
                double offset = -fm.getStringBounds(getLabel(), g).getWidth() / 2;
                for (i = 0; i < ColorIndex.length; i++) {
                    bounds1 = fm.getStringBounds(getLabel(), i, i + 1, g);
                    offset += bounds1.getWidth() / 2;
                    offsets[i] = offset;
                    offset += bounds1.getWidth() / 2;
                }
            }

            this.g = g;
            if (labelVisible) {
                i = 0;
                while (i < ColorIndex.length) {
                    g.setColor(ColorIndex[i]);
                    for (j=i+1; j<ColorIndex.length && ColorIndex[j] == ColorIndex[i]; j++);
                    SLabel.drawCenteredString(getLabel().substring(i,j), (int) (getPositionX() + (offsets[i]+offsets[j-1])/2), (int) getPositionY(), g);
                    i = j;
                }
                g.setColor(labelColor);
            }

        }
    }

}
