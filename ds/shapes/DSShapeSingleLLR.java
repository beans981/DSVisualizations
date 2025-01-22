package edu.usfca.ds.shapes;

import edu.usfca.xj.appkit.gview.shape.SLabel;
import edu.usfca.xj.foundation.XJXMLSerializable;

import java.awt.*;

public class DSShapeSingleLLR extends DSShapeRect implements XJXMLSerializable {

    protected boolean isNull = false;
    protected double percentlink = 0.25;



    public DSShapeSingleLLR() {
        super();
    }

    public void setpercentLink(double newpercent) {
        if (newpercent > 0 && newpercent < 1)
            percentlink = newpercent;
    }

    public void drawShape(Graphics2D g) {
        super.drawShape(g);

        Rectangle r = getFrame().rectangle();
        g.drawLine((int)(r.x+r.width*(1-percentlink)), r.y, (int)(r.x+r.width*(1-percentlink)), r.y+r.height);


        if (isNull) {
            g.drawLine((int) (r.x+r.width*(1-percentlink)),  r.y, r.x+r.width, r.y+r.height);
        } else
            g.drawLine((int) (r.x+r.width*(1-percentlink/2)),  r.y+(r.height/2), r.x+r.width, r.y+r.height/2);



    }


    public void drawLabel(Graphics2D g) {
        Rectangle r = getFrame().rectangle();
        SLabel.drawCenteredString(label, (int)(getPositionX()-r.width*percentlink/2), (int)getPositionY(), g);
    }

     public void setPointerVoid(boolean isNull_) {
         isNull = isNull_;


    }

}
