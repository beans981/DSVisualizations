package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeNullPointer;
import edu.usfca.ds.shapes.DSShapeSingleLLU;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;


public class DSViewHashOpen extends DSViewHash {


   /* public final static int INSERT = 1;
    public final static int DELETE = 2;
    public final static int FIND = 3;
    public final static int HASHINTEGER = 4;
    public final static int HASHSTRING = 5;   */  // Defined in superclass


    protected final int ELEMWIDTH = 75;
    protected final int ELEMHEIGHT = 50;
    protected final int ARRAYELEMWIDTH = 80;
    protected final int ARRAYELEMHEIGHT = 30;



    protected HashElem HashList[];
    protected GElementLabel index[];
    protected DSShapeNullPointer frame[];
    protected int Xpos[];
    protected final int Ypos = 400;

    protected final int YSPACING = ELEMHEIGHT + 15;
    protected final int FIRSTY = Ypos - YSPACING;


    public DSViewHashOpen() {

        int i;

        HASHSIZE = 11;

        Xpos = new int[HASHSIZE];
        HashList = new HashElem[HASHSIZE];
        index = new GElementLabel[HASHSIZE];
        frame = new DSShapeNullPointer[HASHSIZE];


        for (i = 0; i < HASHSIZE; i++) {
            Xpos[i] = i * ARRAYELEMWIDTH + 2 * ARRAYELEMWIDTH / 3;
            frame[i] = createNullPointer(Xpos[i], Ypos, ARRAYELEMWIDTH, ARRAYELEMHEIGHT);
            index[i] = createLabel(String.valueOf(i), Xpos[i], Ypos + ARRAYELEMHEIGHT / 2 + 12);
            index[i].setLabelColor(Color.BLUE);
            HashList[i] = null;
        }

    }


    protected void CallFunction(int function) {
        switch (function) {
            case HASHINTEGER:
                deleteElems();
                HashingIntegers = true;
                break;
            case HASHSTRING:
                deleteElems();
                HashingIntegers = false;

                break;
        }

    }


    protected void CallFunction(int function, Object param1) {
        switch (function) {

            case INSERT:
                if (HashingIntegers) {
                    insert(((Integer) param1).intValue());
                } else {
                    insert((String) param1);
                }
                break;
            case DELETE:
                if (HashingIntegers) {
                    delete(((Integer) param1).intValue());
                } else {
                    delete((String) param1);
                }

                break;
            case FIND:
                if (HashingIntegers) {
                    find(((Integer) param1).intValue());
                } else {
                    find((String) param1);
                }

                break;

        }
    }


    protected void insertinto(int indx, String key) {

        elemIndexLabel.setLabelColor(Color.RED);
        index[indx].setLabelColor(Color.RED);
        repaintwait();
        index[indx].setLabelColor(Color.BLUE);
        elemIndexLabel.setLabelColor(Color.BLACK);

        HashElem insertElem = new HashElem();
        insertElem.next = HashList[indx];
        insertElem.elem = createSingleLinkedListRectU(key, 300, 40, ELEMWIDTH, ELEMHEIGHT);
        insertElem.elem.setPointerVoid(true);
        repaintwait();
        if (HashList[indx] == null) {
            frame[indx].setNull(false);
        } else {
            insertElem.elem.setPointerVoid(false);
            removeLink(frame[indx], HashList[indx].elem);
            GLink l2 = createLink(insertElem.elem, HashList[indx].elem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
            l2.setSourceOffset(0, ELEMHEIGHT * insertElem.elem.getpercentLink() / 2);
        }
        GLink l = createLink(frame[indx], insertElem.elem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
        l.setSourceOffset(0, ARRAYELEMHEIGHT / 2);
        HashList[indx] = insertElem;
        repaintwait();
        UpdateList(HashList[indx], Xpos[indx]);
    }


    protected void UpdateList(HashElem list, int xpos) {
        HashElem tmp;
        int currentY = FIRSTY;
        int j;

        for (tmp = list; tmp != null; tmp = tmp.next) {
            tmp.newlocation = new Vector2D(xpos, currentY);
            tmp.path = createPath(tmp.elem.getPosition(), tmp.newlocation, NUMSTEPS);
            currentY -= YSPACING;
        }
        for (j = 0; j < NUMSTEPS; j++) {
            for (tmp = list; tmp != null; tmp = tmp.next) {
                tmp.elem.moveToPosition(tmp.path[j]);
            }
            repaintwaitmin();
        }


    }


    protected void deletefrom(int deleteindex, String deletestr) {

        elemIndexLabel.setLabelColor(Color.RED);
        index[deleteindex].setLabelColor(Color.RED);
        repaintwait();
        elemIndexLabel.setLabelColor(Color.BLACK);
        index[deleteindex].setLabelColor(Color.BLUE);
        if (HashList[deleteindex] == null) {
            return;
        }
        elemLabel.setLabelColor(Color.RED);
        HashList[deleteindex].elem.setLabelColor(Color.RED);
        repaintwait();
        HashList[deleteindex].elem.setLabelColor(Color.BLACK);
        if (HashList[deleteindex].elem.getLabel().compareTo(deletestr) == 0) {
            elemLabel.setLabelColor(Color.BLACK);

            removeLink(frame[deleteindex], HashList[deleteindex].elem);
            if (HashList[deleteindex].next != null) {
                removeLink(HashList[deleteindex].elem, HashList[deleteindex].next.elem);
                GLink l2 = createLink(frame[deleteindex], HashList[deleteindex].next.elem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
                l2.setSourceOffset(0, ARRAYELEMHEIGHT / 2);
            } else {
                frame[deleteindex].setNull(true);
            }
            removeAny(HashList[deleteindex].elem);
            HashList[deleteindex] = HashList[deleteindex].next;
            UpdateList(HashList[deleteindex], Xpos[deleteindex]);
            return;
        }
        HashElem tmp;
        for (tmp = HashList[deleteindex]; tmp.next != null; tmp = tmp.next) {
            tmp.next.elem.setLabelColor(Color.RED);
            repaintwait();
            tmp.next.elem.setLabelColor(Color.BLACK);
            if (tmp.next.elem.getLabel().compareTo(deletestr) == 0) {
                elemLabel.setLabelColor(Color.BLACK);
                removeLink(tmp.elem, tmp.next.elem);
                if (tmp.next.next == null) {
                    tmp.elem.setPointerVoid(true);
                } else {
                    removeLink(tmp.next.elem, tmp.next.next.elem);
                    GLink l2 = createLink(tmp.elem, tmp.next.next.elem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
                    l2.setSourceOffset(0, ELEMHEIGHT * tmp.elem.getpercentLink() / 2);

                }
                removeAny(tmp.next.elem);
                tmp.next = tmp.next.next;
                UpdateList(HashList[deleteindex], Xpos[deleteindex]);
                return;

            }
        }
    }


    protected void findin(int indx, String key) {
        boolean found = false;
        elemIndexLabel.setLabelColor(Color.RED);
        index[indx].setLabelColor(Color.RED);
        repaintwait();
        index[indx].setLabelColor(Color.BLUE);
        elemIndexLabel.setLabelColor(Color.BLACK);
        elemLabel.setLabelColor(Color.RED);
        for (HashElem tmp = HashList[indx]; tmp != null; tmp = tmp.next) {
            tmp.elem.setLabelColor(Color.RED);
            repaintwait();
            tmp.elem.setLabelColor(Color.BLACK);
            if (key.compareTo(tmp.elem.getLabel()) == 0) {
                found = true;
                break;
            }

        }
        if (found) {
            GElementLabel l = createLabel("Element " + key + " found", -10, -10);
            GElement lineup[] = {l};
            LineupHorizontal(new Vector2D(20, 50), lineup);
            HoldoverGraphics.add(l);
        } else {
            GElementLabel l = createLabel("Element " + key + " NOT found", -10, -10);
            GElement lineup[] = {l};
            LineupHorizontal(new Vector2D(20, 50), lineup);

            HoldoverGraphics.add(l);

        }
        repaintwait();
    }



    protected void deleteElems() {
        int i;
        HashElem tmp;
        for (i = 0; i < HASHSIZE; i++) {
            if (HashList[i] != null) {
                removeLink(frame[i], HashList[i].elem);
                for (tmp = HashList[i]; tmp != null; tmp = tmp.next) {
                    if (tmp.next != null) {
                        removeLink(tmp.elem, tmp.next.elem);
                    }
                    removeAny(tmp.elem);
                }
                HashList[i] = null;
                frame[i].setNull(true);
            }

        }

    }

    protected class HashElem {
        public DSShapeSingleLLU elem;
        public Vector2D newlocation;
        public HashElem next;
        public Vector2D path[];
    }

}
