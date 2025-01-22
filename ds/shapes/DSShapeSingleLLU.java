package edu.usfca.ds.shapes;

import edu.usfca.xj.appkit.gview.shape.SLabel;
import edu.usfca.xj.foundation.XJXMLSerializable;

import java.awt.*;

public class DSShapeSingleLLU extends DSShapeRect implements XJXMLSerializable {

    protected boolean isNull = false;
    protected double percentlink = 0.25;



    public DSShapeSingleLLU() {
        super();
    }

    public void setpercentLink(double newpercent) {
        if (newpercent > 0 && newpercent < 1)
            percentlink = newpercent;
    }

    public double getpercentLink() {
        return percentlink;
    }

    public void drawShape(Graphics2D g) {
        super.drawShape(g);

        Rectangle r = getFrame().rectangle();
        g.drawLine(r.x, (int)(r.y + r.height*percentlink), r.x+r.width, (int) (r.y+r.height*percentlink));


        if (isNull) {
            g.drawLine(r.x,r.y, r.x+r.width, (int) (r.y+r.height*percentlink));
        } else  {
         //   g.drawLine((int) (r.x+r.width/2),  r.y, r.x+r.width/2, (int) (r.y+r.height*percentlink/2));
        }


    }


    public void drawLabel(Graphics2D g) {
        double strwidth,strheight;

        FontMetrics fm = g.getFontMetrics();
        strwidth = fm.getStringBounds(label,g).getWidth();
        strheight = fm.getStringBounds(label,g).getHeight();
        if (strwidth > width) {
            Rectangle r = getFrame().rectangle();
            SLabel.drawCenteredString(label.substring(0,label.length()/2), getPositionX(),
                                      (int)(getPositionY()+r.height*percentlink/2)  -strheight/2-1, g);
            SLabel.drawCenteredString(label.substring(label.length()/2,label.length()), getPositionX(),
                                      (int)(getPositionY()+r.height*percentlink/2) + strheight/2, g);

        }   else {

            Rectangle r = getFrame().rectangle();
            SLabel.drawCenteredString(label, getPositionX(), (int)(getPositionY()+r.height*percentlink/2), g);
        }
    }

     public void setPointerVoid(boolean isNull_) {
         isNull = isNull_;


    }

}
