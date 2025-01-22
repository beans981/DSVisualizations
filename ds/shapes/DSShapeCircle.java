package edu.usfca.ds.shapes;

import edu.usfca.xj.appkit.gview.object.GElementCircle;
import edu.usfca.xj.appkit.gview.shape.SLabel;
import edu.usfca.xj.foundation.XJXMLSerializable;

import java.awt.*;

public class DSShapeCircle extends GElementCircle implements XJXMLSerializable {

    protected boolean doublelabel;
    protected String secondLabel;
    protected Color secondLabelColor = Color.BLACK;


    public DSShapeCircle() {
        super();
        doublelabel = false;
    }

    public boolean getdoublelabel() {
        return doublelabel;

    }
    public void setdoublelabel(boolean newdouble) {
        doublelabel = newdouble;
    }

    public String getSecondLabel() {
        return secondLabel;
    }

    public Color getSecondLabelColor() {
        return secondLabelColor;
    }

    public void setSecondLabelColor(Color clr) {
        secondLabelColor = clr;
    }

    public void setSecondLabel(String newsecondLabel) {
        secondLabel = newsecondLabel;
    }
    public void draw(Graphics2D g) {
        if (doublelabel) {

        if(labelVisible) {
            g.setColor(secondLabelColor);
            SLabel.drawCenteredString(getSecondLabel(), (int)getPositionX(), (int) (getPositionY() -radius*.4), g);
            g.setColor(labelColor);
            SLabel.drawCenteredString(getLabel(), (int)getPositionX(), (int)(getPositionY()+radius*.4), g);
        }

        if(color != null)
            g.setColor(color);
        else
            g.setColor(Color.black);

        g.setStroke(strokeSize);

        drawShape(g);

        g.setStroke(strokeNormal);

        }   else {
            super.draw(g);
        }
    }
}
