package edu.usfca.ds.views;

import edu.usfca.ds.utils.DSAction;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;

public class DSViewTree extends DSView {

    GLink nl;

    public DSViewTree() {
        // Create a tree (the root element will be the first node added into this view)
        createCircle("a", 200, 100);
        createCircle("b", 250, 180);
        createCircle("c", 150, 180);

        // Create two kinds of link between node 0-1 and 0-2
        createLink(0, 1, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "r1", 30);
        createLink(0, 2, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "r2", -30);
    }

    public void addLeaf() {
        GElement element = (GElement)shapes.get(shapes.size()-1);
        createCircle("newLeaf", element.getPositionX()+50, element.getPositionY()+50);
        nl = createLink(shapes.size()-2, shapes.size()-1, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 30);
        repaint();
    }

    public void removeLeaf() {
        if(shapes.size()>1) {
            removeLink(shapes.size()-2, shapes.size()-1);
            removeLastShape();
            repaint();            
        }
    }

    public void renameLabels() {
        performActionOnAllElements(new DSAction() {
            public void perform(GElement element, int index) {
                element.setLabel(String.valueOf((int)(Math.random()*Short.MAX_VALUE)));
            }
        });
        repaint();
    }

    public void colorizeLabels() {
        performActionOnAllElements(new DSAction() {
            public void perform(GElement element, int index) {
                element.setLabelColor(new Color((int) (Math.random()*Integer.MAX_VALUE)));
            }
        });
        repaint();
    }

    public void toggleLabelsVisibility() {
        performActionOnAllElements(new DSAction() {
            public void perform(GElement element, int index) {
                element.setLabelVisible(!element.isLabelVisible());
            }
        });

        repaint();
    }
}
