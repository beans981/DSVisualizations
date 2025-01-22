package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;

import java.awt.*;


public class DSViewListArray extends DSView {

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


    protected GElement index[];
    protected int current[];
    protected GElement list[];
    protected GElementArrow currentarrow[];
    protected GElementLabel currentlabel[];
    public final int MAXLIST = 18;
    public final int NUMSTEPS = 25;
    protected int numiterator;
    protected int length;
    protected GElementLabel lengthlabel;


    public DSViewListArray() {
        //   super(container);

        int i;

        current = new int[2];
        currentarrow = new GElementArrow[2];
        currentlabel = new GElementLabel[2];
        GElementLabel indexlabel;
        current[0] = -1;
        current[1] = -1;
        length = 0;

        waitscalefactor /= 2;
        list = new GElement[MAXLIST];
        index = new GElementLabel[MAXLIST];
        for (i = 0; i < MAXLIST; i++) {
            list[i] = createRectangle("", i * 50 + 50, 250, 50, 50, false);
            index[i] = createLabel(String.valueOf(i), i * 50 + 50, 287, false);
            index[i].setLabelColor(Color.BLUE);

        }
        createLabel("Length:", 50, 120);
        lengthlabel = createLabel("0", 80, 120);


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
                first(((Integer) param1).intValue());
                break;
            case NEXT:
                next(((Integer) param1).intValue());
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
        Vector2D path[];

        if (length < MAXLIST) {
            GElementLabel l = createLabel("Adding: ", 100, 10);
            GElementLabel l2 = createLabel(elem, 140, 10);

            lengthlabel.setLabelColor(Color.RED);
            index[length].setLabelColor(Color.RED);
            repaintwait();
            path = createPath(l2.getPosition(), list[length].getPosition(), NUMSTEPS);

            for (j = 0; j < path.length; j++) {
                l2.setPosition(path[j]);
                repaintwaitmin();
            }
            list[length].setLabel(elem);
            removeAny(l);
            removeAny(l2);
            lengthlabel.setLabelColor(Color.BLACK);
            index[length].setLabelColor(Color.BLUE);

            length++;
            if (length == 1) {
                if (current[0] == 0) {
                    currentarrow[0] = createArrow(50, 170, 50, 225, 20);
                    currentarrow[0].setColor(iteratorColors[0]);
                }
                if (current[1] == 0) {
                    currentarrow[1] = createArrow(50, 345, 50, 300, 20);
                    currentarrow[1].setColor(iteratorColors[1]);

                }
            }
            lengthlabel.setLabel(String.valueOf(length));
        } else {
            GElementLabel error = createLabel("List is full", 200, 20);
            HoldoverGraphics.add(error);

        }

    }

    private void delete(int itr) {
        int i, j;
        if ((current[itr] < 0) || (current[itr] >= length)) {
            GElementLabel l = createLabel("Iterator not in the list:  Delete not valid", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            list[current[itr]].setLabel("");
            for (i = current[itr] + 1; i < length; i++) {
                GElement l = createLabel(list[i].getLabel(), 50 + i * 50, 250, false);
                list[i].setLabel("");
                for (j = 0; j < NUMSTEPS; j++) {
                    l.move(-50 / NUMSTEPS, 0);
                    if (i == current[0]) {
                        currentarrow[0].move(-50 / NUMSTEPS, 0);
                        currentlabel[0].move(-50 / NUMSTEPS, 0);
                    }
                    if ((numiterator == 2) && (i == current[1])) {
                        currentarrow[1].move(-50 / NUMSTEPS, 0);
                        currentlabel[1].move(-50 / NUMSTEPS, 0);
                    }
                    repaintwaitmin();
                }
                if (i == current[0])
                    current[0]--;
                if (i == current[1])
                    current[1]--;
                list[i - 1].setLabel(l.getLabel());
                removeAny(l);

            }
            length--;
            for (j = 0; j < 2; j++) {
                if (current[j] == length) {
                    current[j] = -1;
                    removeAny(currentarrow[j]);
                    currentarrow[j] = null;
                    currentlabel[j].move(50 - currentlabel[j].getPositionX(), 0);

                }
            }
            lengthlabel.setLabel(String.valueOf(length));
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
            GElementLabel eleml = createLabel(list[current[itr]].getLabel(),
                                              list[current[itr]].getPositionX(),
                                              list[current[itr]].getPositionY());
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

        int j;
        if ((current[itr] < 0) || (current[itr] >= length)) {
            GElement l = createLabel("Iterator is not in the list, cannot advance", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            if (current[itr] == length - 1) {
                current[itr] = -1;
                removeAny(currentarrow[itr]);
                currentarrow[itr] = null;
                currentlabel[itr].move(50 - currentlabel[itr].getPositionX(), 0);
            } else {
                for (j = 0; j < NUMSTEPS; j++) {
                    currentlabel[itr].move(50 / NUMSTEPS, 0);
                    currentarrow[itr].move(50 / NUMSTEPS, 0);
                    repaintwaitmin();
                }
                current[itr]++;

            }

        }
        repaint();
    }

    private void first(int itr) {


        if (currentarrow[itr] != null) {
            removeAny(currentarrow[itr]);
            currentarrow[itr] = null;
        }
        if (length > 0) {
            currentlabel[itr].move(50 - currentlabel[itr].getPositionX(), 0);
            if (itr == 0) {
                currentarrow[0] = createArrow(50, 170, 50, 225, 20);

            } else { /* itr == 1 */
                currentarrow[1] = createArrow(50, 345, 50, 300, 20);
            }
            currentarrow[itr].setColor(iteratorColors[itr]);
        }
        current[itr] = 0;
        repaint();
    }


    private void addIterator() {
        if (numiterator == 0) {
            currentlabel[0] = createLabel("current", 50, 150);
            currentlabel[0].setLabelColor(iteratorColors[0]);
            numiterator = 1;
            current[0] = 0;
            if (length > 0) {
                currentarrow[0] = createArrow(50, 170, 50, 225, 20);
                currentarrow[0].setColor(iteratorColors[0]);

            }
        } else if (numiterator == 1) {
            currentlabel[1] = createLabel("current", 50, 355);
            currentlabel[1].setLabelColor(iteratorColors[1]);
            numiterator = 2;
            current[1] = 0;
            current[0] = 0;
            if (length > 0) {
                currentarrow[1] = createArrow(50, 345, 50, 300, 20);
                currentarrow[1].setColor(iteratorColors[1]);
            }
        }
    }

    private void length() {
        Vector2D[] path;
        int j;
        GElementLabel l = createLabel("Length = ", 100, 10);
        GElementLabel l2 = createLabel(lengthlabel.getLabel(), lengthlabel.getPositionX(), lengthlabel.getPositionY());

        path = createPath(l2.getPosition(), new Vector2D(140, 10), NUMSTEPS);
        for (j = 0; j < path.length; j++) {
            l2.setPosition(path[j]);
            repaintwaitmin();
        }
        HoldoverGraphics.add(l);
        HoldoverGraphics.add(l2);
    }


    private void setCurrent(int itr, int indx) {
        int j;
        if ((indx < 0) || (indx >= length)) {
            GElement l = createLabel("Index " + indx + " is not in the list, cannot set iterator", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            GElementLabel l = createLabel("Setting Current:",100,10);
            l.setLabelColor(iteratorColors[itr]);
            GElementLabel l2 = createLabel(String.valueOf(indx), 180, 10);
            repaintwait();
            l2.setLabelColor(Color.RED);
            index[indx].setLabelColor(Color.RED);
            repaintwait();

            if ((current[itr] < 0) || (current[itr] >= length)) {
                currentlabel[itr].move(indx * 50 + 50 - currentlabel[itr].getPositionX(), 0);
                current[itr] = indx;
                if (itr == 0) {
                    currentarrow[itr] = createArrow(indx * 50 + 50, 170, indx * 50 + 50, 225, 20, false);

                } else {

                    currentarrow[itr] = createArrow(indx * 50 + 50, 330, indx * 50 + 50, 285, 20, false);


                }
                currentarrow[itr].setColor(iteratorColors[itr]);
            } else {
                current[itr] = indx;
                int arrowmovedelta = (int) (indx * 50 + 50 - currentarrow[itr].getPositionX()) / NUMSTEPS;
                int labelmovedelta = (int) (indx * 50 + 50 - currentlabel[itr].getPositionX()) / NUMSTEPS;
                for (j = 0; j < NUMSTEPS; j++) {
                    currentarrow[itr].move(arrowmovedelta, 0);
                    currentlabel[itr].move(labelmovedelta, 0);
                    repaintwaitmin();


                }
            }
            index[indx].setLabelColor(Color.BLUE);
            removeAny(l);
            removeAny(l2);
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
        if (current[itr] >= 0 && current[itr] < length && length < MAXLIST) {
            shiftElementsRight(current[itr] + 1);
            list[current[itr] + 1].setLabel(String.valueOf(elem));
            length++;
        } else if (length == MAXLIST) {
            GElementLabel l = createLabel("List is full", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            GElementLabel l = createLabel("Iterator not in the list, cannot insert", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        }
        lengthlabel.setLabel(String.valueOf(length));

        repaint();


    }

    private void insertBefore(int itr, String elem) {
        int j;
        Vector2D path[];

        if (current[itr] >= 0 && current[itr] < length && length < MAXLIST) {
            GElementLabel l = createLabel("Inserting: ", 100, 10);
            l.setLabelColor(iteratorColors[itr]);
            GElementLabel l2 = createLabel(elem, 140, 10);

            shiftElementsRight(current[itr]);

            path = createPath(l2.getPosition(),list[current[itr]-1].getPosition(),NUMSTEPS);
            for (j=0; j < path.length; j++) {
                l2.setPosition(path[j]);
                repaintwaitmin();
            }
            removeAny(l2);
            removeAny(l);

            list[current[itr] - 1].setLabel(String.valueOf(elem));
            length++;
        } else if (length == MAXLIST) {
            GElementLabel l = createLabel("List is full", 100, 10);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        } else {
            GElementLabel l = createLabel("Iterator not in the list, cannot insert", 200, 20);
            l.setLabelColor(iteratorColors[itr]);
            HoldoverGraphics.add(l);
        }
        lengthlabel.setLabel(String.valueOf(length));

        repaint();


    }

}
