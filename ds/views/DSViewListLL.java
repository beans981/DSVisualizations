package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeSingleLLR;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;


public class DSViewListLL extends DSView {

    public final int LENGTH = 1;

    public final static int FIRST = 2;
    public final static int NEXT = 3;
    public final static int CURRENT = 4;
    public final static int INLIST = 5;
    public final static int INSERTBEFORE = 6;
    public final static int INSERTAFTER = 7;
    public final static int DELETE = 8;
    public final static int SETCURRENT = 9;
    public final static int ADDITERATOR = 10;
    public final static int ADD = 11;

    public final static Color iteratorColors[] = {Color.GREEN, Color.MAGENTA};


    protected int current[];
    protected GElementArrow currentarrow[];
    protected GElementLabel currentlabel[];
    public final int MAXLIST = 19;
    public final int NUMSTEPS = 25;
    protected int numiterator;
    protected int length;
    protected int Xpos[];
    protected int Ypos[];
    protected int LinkedListElemHeight = 50;
    protected int LinkedListElemWidth = 75;

    protected GElementArrow headarrow;
    protected GElementLabel headlabel;
    protected GElementArrow tailarrow;
    protected GElementLabel taillabel;
    protected GElementLabel lengthlabel;

    protected DSShapeSingleLLR list[];

    public DSViewListLL() {
        //   super(container);

        int i;

        current = new int[2];
        currentarrow = new GElementArrow[2];
        currentlabel = new GElementLabel[2];

        current[0] = -1;
        current[1] = -1;
        length = 0;

        waitscalefactor /= 2;
        list = new DSShapeSingleLLR[MAXLIST + 1];


        Xpos = new int[]{225, 350, 475, 600, 725, 850, 100, 225, 350, 475, 600, 725, 850,
                         100, 225, 350, 475, 600, 725, 850};
        Ypos = new int[]{100, 100, 100, 100, 100, 100, 223, 223, 223, 223, 223, 223, 223,
                         345, 345, 345, 345, 345, 345, 345};

        headlabel = createLabel("Head", Xpos[0] - 100, Ypos[0], false);
        headarrow = createArrow(Xpos[0] - 75, Ypos[0], Xpos[0] - 50, Ypos[0], 10);

        list[0] = createSingleLinkedListRecR("Dummy", Xpos[0], Ypos[0], LinkedListElemWidth, LinkedListElemHeight);
        list[0].setPointerVoid(true);
        list[0].setLabelColor(Color.BLUE);

        taillabel = createLabel("Tail", Xpos[0] + LinkedListElemWidth / 2, Ypos[0] + LinkedListElemHeight / 2 + 25);
        tailarrow = createArrow(Xpos[0] + LinkedListElemWidth / 2, Ypos[0] + LinkedListElemHeight / 2 + 20,
                                Xpos[0] + LinkedListElemWidth / 3, Ypos[0] + LinkedListElemHeight / 2, 10);

        createLabel("Length:", 50, Ypos[0]);
        lengthlabel = createLabel("0", 80, Ypos[0]);
    }


    int GetTailArrowX(int index) {
        return Xpos[index] + LinkedListElemWidth / 2;
    }

    int GetTailArrowY(int index) {
        return Ypos[index] + LinkedListElemHeight / 2 + 20;
    }

    int GetTailArrowHeadX(int index) {
        return Xpos[index] + LinkedListElemWidth / 3;
    }

    int GetTailArrowHeadY(int index) {
        return Ypos[index] + LinkedListElemHeight / 2 + 10;
    }

    int GetTailLabelX(int index) {
        return Xpos[index] + LinkedListElemWidth / 2;
    }

    int GetTailLabelY(int index) {
        return Ypos[index] + LinkedListElemHeight / 2 + 25;
    }

    Vector2D GetTailLabel(int index) {
        return new Vector2D(GetTailLabelX(index), GetTailLabelY(index));
    }

    Vector2D GetTailArrowHead(int index) {
        return new Vector2D(GetTailArrowHeadX(index), GetTailArrowHeadY(index));
    }

    Vector2D GetTailArrow(int index) {
        return new Vector2D(GetTailArrowX(index), GetTailArrowY(index));
    }


    int GetItrArrowX(int index, int itr) {
        if (itr == 0)
            return Xpos[index];
        else
            return Xpos[index] - LinkedListElemWidth / 2;
    }

    int GetItrArrowY(int index, int itr) {
        if (itr == 0)
            return Ypos[index] - LinkedListElemHeight / 2 - 20;
        else
            return Ypos[index] + LinkedListElemHeight / 2 + 20;
    }

    int GetItrArrowHeadX(int index, int itr) {
        if (itr == 0)
            return Xpos[index];
        else
            return Xpos[index] - LinkedListElemWidth / 3;
    }

    int GetItrArrowHeadY(int index, int itr) {
        if (itr == 0)
            return Ypos[index] - LinkedListElemHeight / 2;
        else
            return Ypos[index] + LinkedListElemHeight / 2;
    }

    int GetItrLabelX(int index, int itr) {
        if (itr == 0)
            return Xpos[index];
        else
            return Xpos[index] - LinkedListElemWidth / 2;
    }

    int GetItrLabelY(int index, int itr) {
        if (itr == 0)
            return Ypos[index] - LinkedListElemHeight / 2 - 30;
        else
            return Ypos[index] + LinkedListElemHeight / 2 + 25;
    }

    Vector2D GetItrLabel(int index, int itr) {
        return new Vector2D(GetItrLabelX(index, itr), GetItrLabelY(index, itr));
    }

    Vector2D GetItrArrowHead(int index, int itr) {
        return new Vector2D(GetItrArrowHeadX(index, itr), GetItrArrowHeadY(index, itr));
    }

    Vector2D GetItrArrow(int index, int itr) {
        return new Vector2D(GetItrArrowX(index, itr), GetItrArrowY(index, itr));
    }


    protected void CallFunction(int function) {
        switch (function) {
            case LENGTH:
                length();
                break;
            case ADDITERATOR:
                addIterator();
                break;
        }

    }


    protected void CallFunction(int function, Object param1) {
        switch (function) {

            case FIRST:
                first(((Integer)param1).intValue());
                break;
            case NEXT:
                next(((Integer)param1).intValue());
                break;
            case CURRENT:
                current(((Integer)param1).intValue());
                break;
            case INLIST:
                inlist(((Integer)param1).intValue());
                break;
            case DELETE:
                delete(((Integer)param1).intValue());
                break;
            case ADD:
                addtoend((String) param1);
                break;

        }
    }

    protected void CallFunction(int function, Object param1, Object param2) {
        Integer p1, p2i;
        String p2s;
        switch (function) {

            case INSERTBEFORE:

                p1 = (Integer) param1;
                p2s = (String) param2;
                insertBefore(p1.intValue(), p2s);
                break;

            case INSERTAFTER:

                p1 = (Integer) param1;
                p2s = (String) param2;
                insertAfter(p1.intValue(), p2s);
                break;

            case SETCURRENT:

                p1 = (Integer) param1;
                p2i = (Integer) param2;
                setCurrent(p1.intValue(), p2i.intValue());
                break;

        }
    }


    private void addtoend(String elem) {
        int j;
        Vector2D[] path, path2;

        if (length < MAXLIST) {
            length++;
            list[length] = createSingleLinkedListRecR(elem, LinkedListElemWidth, LinkedListElemHeight,
                                                      LinkedListElemWidth, LinkedListElemHeight);
            list[length].setPointerVoid(true);
            repaintwait();
            list[length - 1].setPointerVoid(false);
            createLink(list[length - 1], list[length], GLink.SHAPE_ELBOW, GElement.ANCHOR_RIGHT, GElement.ANCHOR_LEFT, "", 0);
            repaintwait();


            path = createPath(list[length].getPosition(), new Vector2D(Xpos[length], Ypos[length]), NUMSTEPS);
            for (j = 0; j < path.length; j++) {
                list[length].moveToPosition(path[j]);
                repaintwaitmin();
            }
            path = createPath(tailarrow.getPosition(), GetTailArrow(length), NUMSTEPS);
            path2 = createPath(taillabel.getPosition(), GetTailLabel(length), path.length);
            for (j = 0; j < path.length; j++) {
                tailarrow.moveToPosition(path[j]);
                taillabel.moveToPosition(path2[j]);
                repaintwaitmin();
            }
            lengthlabel.setLabel(String.valueOf(length));
        } else {
            GElementLabel error = createLabel("Graphic display of List is full (linked lists, of course, do not get full in this way)", 400, 20);
            HoldoverGraphics.add(error);

        }

    }

    private void UpdateList(int otheritr) {
        int i, j;
        Vector2D[] path[];
        Vector2D[] pathtailLabel, pathtailArrow;
        Vector2D[] pathitrLabel = null;
        Vector2D[] pathitrArrow = null;
        pathtailArrow = createPath(tailarrow.getPosition(), GetTailArrow(length), NUMSTEPS);
        pathtailLabel = createPath(taillabel.getPosition(), GetTailLabel(length), pathtailArrow.length);
        if (numiterator == 2) {
            pathitrArrow = createPath(currentarrow[otheritr].getPosition(), GetItrArrow(current[otheritr], otheritr), pathtailArrow.length);
            pathitrLabel = createPath(currentlabel[otheritr].getPosition(), GetItrLabel(current[otheritr], otheritr), pathtailArrow.length);
        }
        path = new Vector2D[length + 1][];
        for (i = 0; i <= length; i++) {
            path[i] = createPath(list[i].getPosition(), new Vector2D(Xpos[i], Ypos[i]), pathtailArrow.length);
        }

        for (j = 0; j < pathtailArrow.length; j++) {
            tailarrow.moveToPosition(pathtailArrow[j]);
            taillabel.moveToPosition(pathtailLabel[j]);
            if (numiterator == 2) {
                currentarrow[otheritr].moveToPosition(pathitrArrow[j]);
                currentlabel[otheritr].moveToPosition(pathitrLabel[j]);
            }
            for (i = 0; i <= length; i++) {
                list[i].setPosition(path[i][j]);
            }
            repaintwaitmin();
        }
    }

    private void delete(int itr) {
        int i, j;
        Vector2D[] path[];
        Vector2D[] pathtailLabel, pathtailArrow;
        Vector2D[] pathitrLabel = null;
        Vector2D[] pathitrArrow = null;
        int otheritr = 1 - itr;

        if ((current[itr] < 0) || (current[itr] >= length)) {
            GElementLabel l = createLabel("Iterator not in the list:  Delete not valid", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            removeLink(list[current[itr]], list[current[itr] + 1]);

            if (current[itr] == length - 1) {
                list[current[itr]].setPointerVoid(true);
            } else {
                createLink(list[current[itr]], list[current[itr] + 2], GLink.SHAPE_ELBOW, GElement.ANCHOR_RIGHT, GElement.ANCHOR_LEFT, "", 0);
            }
            repaintwait();
            if (current[itr] != length - 1) {
                removeLink(list[current[itr] + 1], list[current[itr] + 2]);
            }
            removeAny(list[current[itr] + 1]);
            if (((current[otheritr] > current[itr]) && (current[otheritr] != current[itr] + 1)) ||
                current[otheritr] == length)
                current[otheritr]--;
            for (i = current[itr] + 1; i < length; i++) {
                list[i] = list[i + 1];
            }

            length--;
            lengthlabel.setLabel(String.valueOf(length));


            UpdateList(otheritr);


        }


    }

    private void inlist(int itr) {
        GElementLabel l;
        if (current[itr] >= 0 && current[itr] < length)
            l = createLabel("Iterator is in the list", 200, 20);
        else
            l = createLabel("Iterator is not in the list", 200, 20);

        l.setLabelColor(iteratorColors[itr]);
        HoldoverGraphics.add(l);
    }

    private void current(int itr) {
        int j;
        if ((current[itr] < 0) || (current[itr] >= length)) {
            GElementLabel l = createLabel("Iterator not in the list:  Current not valid", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            GElementLabel l = createLabel("Current Element: ", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            GElementLabel eleml = createLabel(list[current[itr] + 1].getLabel(),
                                              list[current[itr] + 1].getPositionX(),
                                              list[current[itr] + 1].getPositionY());
            Vector2D[] path = createPath(eleml.getPosition(),
                                         new Vector2D(285, 20), NUMSTEPS);
            for (j = 0; j < NUMSTEPS; j++) {
                eleml.setPosition(path[j]);
                repaintwaitmin();
            }
            GElement lst[] = {l,eleml};
            LineupHorizontal(lst);
            HoldoverGraphics.add(l);
            HoldoverGraphics.add(eleml);
        }
    }

    private void next(int itr) {

        Vector2D[] path, path2;

        int j;
        if ((current[itr] < 0) || (current[itr] >= length)) {
            GElement l = createLabel("Iterator is not in the list, cannot advance", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            path = createPath(currentarrow[itr].getPosition(), GetItrArrow(current[itr] + 1, itr), NUMSTEPS);
            path2 = createPath(currentlabel[itr].getPosition(), GetItrLabel(current[itr] + 1, itr), path.length);

            for (j = 0; j < path.length; j++) {
                currentlabel[itr].moveToPosition(path2[j]);
                currentarrow[itr].moveToPosition(path[j]);
                repaintwaitmin();
            }
            current[itr]++;

        }
        repaint();
    }

    private void first(int itr) {

        Vector2D[] path, path2;
        int j;

        path = createPath(currentarrow[itr].getPosition(), GetItrArrow(0, itr), NUMSTEPS);
        path2 = createPath(currentlabel[itr].getPosition(), GetItrLabel(0, itr), path.length);

        for (j = 0; j < path.length; j++) {
            currentarrow[itr].moveToPosition(path[j]);
            currentlabel[itr].moveToPosition(path2[j]);
            repaintwaitmin();
        }
        current[itr] = 0;
        repaint();
    }


    private void addIterator() {
        currentlabel[numiterator] = createLabel("previous", GetItrLabelX(0, numiterator), GetItrLabelY(0, numiterator));
        currentlabel[numiterator].setLabelColor(iteratorColors[numiterator]);
        current[numiterator] = 0;
        currentarrow[numiterator] = createArrow(GetItrArrowX(0, numiterator), GetItrArrowY(0, numiterator),
                                                GetItrArrowHeadX(0, numiterator), GetItrArrowHeadY(0, numiterator), 10);
        currentarrow[numiterator].setColor(iteratorColors[numiterator]);
        numiterator++;
        /*   if (numiterator == 0) {
               currentlabel[0] = createLabel("previous", Xpos[0], Ypos[0]-LinkedListElemHeight/2-30);
               currentlabel[0].setLabelColor(iteratorColors[0]);
               numiterator = 1;
               current[0] = 0;
               currentarrow[0] = createArrow(Xpos[0], Ypos[0]-LinkedListElemHeight/2-20, Xpos[0],Ypos[0] - LinkedListElemHeight/2, 10);
               currentarrow[0].setColor(iteratorColors[0]);
           } else if (numiterator == 1) {
               currentlabel[1] = createLabel("previous", Xpos[0]-LinkedListElemWidth/2, Ypos[0]+LinkedListElemHeight/2+25);
               currentlabel[1].setLabelColor(iteratorColors[1]);
               numiterator = 2;
               current[1] = 0;
               currentarrow[1] = createArrow(Xpos[0]-LinkedListElemWidth/2, Ypos[0]+LinkedListElemHeight/2+20,
                                             Xpos[0]-LinkedListElemWidth/3, Ypos[0]+LinkedListElemHeight/2,10);
               currentarrow[1].setColor(iteratorColors[1]);
           }
           */
    }

    private void length() {
        int j;
        Vector2D[] path;
        GElementLabel l2 = createLabel(lengthlabel.getLabel(), lengthlabel.getPositionX(), lengthlabel.getPositionY());
        GElementLabel l = createLabel("Length = ", 100, 10);

        path = createPath(l2.getPosition(), new Vector2D(135, 10), NUMSTEPS);
        for (j = 0; j < path.length; j++) {
            l2.setPosition(path[j]);
            repaintwaitmin();
        }
        HoldoverGraphics.add(l);
        HoldoverGraphics.add(l2);
    }


    private void setCurrent(int itr, int index) {
        Vector2D[] path, path2;
        int j;


        if (index < 0 || index >= length) {
            GElementLabel l = createLabel("Can't set Current to " + index, 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);


        } else {
            GElementLabel l = createLabel("Setting Current to " + index, 200, 20);
            l.setLabelColor(iteratorColors[itr]);

            for (current[itr] = 0; current[itr] <= index; current[itr]++) {

                path = createPath(currentarrow[itr].getPosition(), GetItrArrow(current[itr], itr), NUMSTEPS);
                path2 = createPath(currentlabel[itr].getPosition(), GetItrLabel(current[itr], itr), path.length);

                for (j = 0; j < path.length; j++) {
                    currentarrow[itr].moveToPosition(path[j]);
                    currentlabel[itr].moveToPosition(path2[j]);
                    repaintwaitmin();
                }
                repaintwait();
            }
            current[itr]--;
            removeAny(l);
        }
    }


    private void shiftElementsRight(int firstmove) {
        boolean otherfix = false;
        int i, j;

        for (i = length - 1; i >= firstmove; i--) {
            GElement l = createLabel(list[i].getLabel(), 50 + i * 50, 250, false);
            list[i].setLabel("");
            for (j = 0; j < NUMSTEPS; j++) {
                l.move(50 / NUMSTEPS, 0);
                if (i == current[0]) {
                    currentarrow[0].move(50 / NUMSTEPS, 0);
                    currentlabel[0].move(50 / NUMSTEPS, 0);
                }
                if ((numiterator == 2) && (i == current[1])) {
                    currentarrow[1].move(50 / NUMSTEPS, 0);
                    currentlabel[1].move(50 / NUMSTEPS, 0);
                }
                repaintwaitmin();
            }
            if (i == current[0])
                current[0]++;
            if (i == current[1])
                current[1]++;
            list[i + 1].setLabel(l.getLabel());
            removeAny(l);
            repaint();

        }
    }


    private void insertAfter(int itr, String elem) {
/* not implemented !! */


    }

    private void insertBefore(int itr, String elem) {
        int i, j;
        Vector2D[] path, path2;


        if (current[itr] >= 0 && current[itr] < length && length < MAXLIST) {

            length++;
            for (i = length + 1; i > current[itr]; i--) {
                list[i + 1] = list[i];
            }
            list[current[itr] + 1] = createSingleLinkedListRecR(elem, LinkedListElemWidth, LinkedListElemHeight,
                                                                LinkedListElemWidth, LinkedListElemHeight);
            list[current[itr] + 1].setPointerVoid(true);
            repaintwait();
            list[current[itr] + 1].setPointerVoid(false);
            createLink(list[current[itr] + 1], list[current[itr] + 2], GLink.SHAPE_ELBOW, GElement.ANCHOR_RIGHT, GElement.ANCHOR_LEFT, "", 0);
            repaintwait();
            removeLink(list[current[itr]], list[current[itr] + 2]);
            createLink(list[current[itr]], list[current[itr] + 1], GLink.SHAPE_ELBOW, GElement.ANCHOR_RIGHT, GElement.ANCHOR_LEFT, "", 0);
            repaintwait();
            UpdateList(1 - itr);
            current[itr]++;
            path = createPath(currentarrow[itr].getPosition(), GetItrArrow(current[itr], itr), NUMSTEPS);
            path2 = createPath(currentlabel[itr].getPosition(), GetItrLabel(current[itr], itr), path.length);
            for (j = 0; j < path.length; j++) {
                currentarrow[itr].moveToPosition(path[j]);
                currentlabel[itr].moveToPosition(path2[j]);
                repaintwaitmin();
            }

            lengthlabel.setLabel(String.valueOf(length));


        } else if (length == MAXLIST) {
            GElementLabel error = createLabel("Graphic display of List is full (linked lists, of course, do not get full in this way)", 400, 20);
            error.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(error);
        } else {
            GElementLabel l = createLabel("Iterator not in the list, cannot insert", 200, 10);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        }
        repaint();


    }

}
