package edu.usfca.ds.shapes;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElementRect;
import edu.usfca.xj.appkit.gview.shape.SLabel;
import edu.usfca.xj.foundation.XJXMLSerializable;

import java.awt.*;

public class DSShapeRectMultiLabel extends GElementRect implements XJXMLSerializable {

    protected String Labels[];
    protected Color LabelColors[];
    protected int numLabels;


    public void setLabelColor(int labelnum, Color clr) {
        LabelColors[labelnum] = clr;
    }

    public void setLabelColor(Color clr) {
        for (int i=0; i<numLabels;i++)
            setLabelColor(i,clr);
    }

    public DSShapeRectMultiLabel() {
        super();
        label = "dummy";
    }

    public Vector2D getLabelPosition(int labelnum) {
        double labelWidth = width / numLabels;
        double leftedge = getPositionX() - width/2;
        return new Vector2D(leftedge + labelWidth/2 + labelnum * labelWidth,  getPositionY());
    }


    public void setNumLabels(int newNumLabels) {
        int i;

        String newlabels[] = new String[newNumLabels];
        Color newLabelColors[] = new Color[newNumLabels];
        for (i=0; i<newNumLabels;i++)
            newLabelColors[i] = Color.BLACK;

        for (i=0; i<Math.min(numLabels,newNumLabels); i++) {
            newlabels[i] = Labels[i];
            newLabelColors[i] = LabelColors[i];

        }
        Labels = newlabels;
        numLabels = newNumLabels;
        LabelColors = newLabelColors;
    }

    public int getNumLabels() {
        return numLabels;
    }

    public void setLabel(String newlab) {
        setLabel(0,newlab);
    }

    public String getLabel() {
        return getLabel(0);
    }

    public void setLabel(int labelNum, String newlabel) {
        if (labelNum >= 0 && labelNum < numLabels)
            Labels[labelNum] = newlabel;
    }


    public void insertLabel(String newlabel) {
        int i;
        String newlabels[] = new String[numLabels+1];
        Color newLabelColors[] = new Color[numLabels+1];

        for (i=0; i<numLabels && newlabel.compareTo(Labels[i]) > 0; i++) {
            newlabels[i] = Labels[i];
            newLabelColors[i] = LabelColors[i];
        }
        newlabels[i] = newlabel;
        newLabelColors[i] = Color.BLACK;
        for (;i<numLabels;i++) {
            newlabels[i+1] = Labels[i];
            newLabelColors[i+1] = LabelColors[i];
        }
        Labels = newlabels;
        LabelColors = newLabelColors;
        numLabels++;
    }

    public void removeithLabel(int i) {
        int j;


        if (i >= 0 && i < numLabels) {
            for (j=0; j<i; j++);
            for (j=i+1; j<numLabels; j++) {
                Labels[j-1] = Labels[j];
                LabelColors[j-1] = LabelColors[j];
            }
            numLabels--;
        }
    }

    public String getLabel(int labelNum) {
        if (labelNum >= 0 && labelNum < numLabels)
            return Labels[labelNum];
        return null;

    }
    public void drawLabel(Graphics2D g) {
        int i;
        double labelWidth = width / numLabels;
        double leftedge = getPositionX() - width/2;


        for (i=0; i<numLabels;i++) {
            g.setColor(LabelColors[i]);
            SLabel.drawCenteredString(Labels[i], (int) (leftedge + labelWidth/2 + i * labelWidth), (int)getPositionY(), g);
        }

    }
}
