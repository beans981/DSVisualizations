package edu.usfca.ds.shapes;

import edu.usfca.xj.appkit.gview.shape.SLabel;
import edu.usfca.xj.foundation.XJXMLSerializable;

import java.awt.*;

public class DSShapeSingleLL2R extends DSShapeRect implements XJXMLSerializable {

    protected boolean isNull = false;
    protected double percentlink = 0.25;
    protected String label2 = null;
    protected Color label2Color = Color.BLACK;



    public DSShapeSingleLL2R() {
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

        g.drawLine((int) (r.x+ r.width*(1-percentlink)/2), r.y, (int) (r.x + r.width*(1-percentlink)/2),r.y+r.height);

        if (isNull) {
            g.drawLine((int) (r.x+r.width*(1-percentlink)),  r.y, r.x+r.width, r.y+r.height);
        } else {
            
        }



    }

    public Color getLabel2Color() {
        return label2Color;
    }

    public void setLabel2Color(Color newcolor) {
        label2Color = newcolor;
    }

    public String getLabel2() {
        return label2;
    }

    public void setLabel2(String newlab){
        label2 = newlab;
    }

    public void drawLabel(Graphics2D g) {
        Rectangle r = getFrame().rectangle();
        g.setColor(labelColor);

        SLabel.drawCenteredString(label, (int)(getPositionX()-r.width*percentlink/2 -
                                              r.width*(1-percentlink)/4), (int)getPositionY(), g);
        g.setColor(label2Color);

        SLabel.drawCenteredString(label2, (int)(getPositionX()-r.width*percentlink/2 +
                                              r.width*(1-percentlink)/4), (int)getPositionY(), g);
    }

     public void setPointerVoid(boolean isNull_) {
         isNull = isNull_;


    }

}
