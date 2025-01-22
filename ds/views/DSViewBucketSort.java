package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeNullPointer;
import edu.usfca.ds.shapes.DSShapeSingleLLU;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;


public class DSViewBucketSort extends DSView {


    public final static int BUCKETSORT = 1;
    public final static int RANDOMIZE = 2;


    protected final int ELEMWIDTH = 26;
    protected final int ELEMHEIGHT = 40;
    protected final int ARRAYELEMWIDTH = 30;
    protected final int ARRAYELEMHEIGHT = 30;

    protected final int LISTWIDTH = 20;
    protected final int LISTHEIGHT = 20;
    protected final int NUMSTEPS = 40;

    protected final int SIZE = 45;
    protected final int BSIZE = 30;

    protected final int BUCKETSIZE = 3;

    protected BucketElem BList[];
    protected GElementLabel Bindex[];
    protected GElementLabel index[];
    protected DSShapeNullPointer Bframe[];
    protected GElement list[];
    protected int data[];
    protected int Xpos[];
    protected int BXpos[];
    protected final int Ypos = 40;
    protected final int BYpos = 400;

    protected final int YSPACING = ELEMHEIGHT + 15;
    protected final int FIRSTY = BYpos - YSPACING;


    public DSViewBucketSort() {

        int i;


        Xpos = new int[SIZE];
        BXpos = new int[BSIZE];
        BList = new BucketElem[BSIZE];
        index = new GElementLabel[SIZE];
        Bindex = new GElementLabel[BSIZE];
        Bframe = new DSShapeNullPointer[BSIZE];
        list = new GElement[SIZE];
        data = new int[SIZE];
        index = new GElementLabel[SIZE];


        for (i = 0; i < BSIZE; i++) {
            BXpos[i] = i * ARRAYELEMWIDTH + 2 * ARRAYELEMWIDTH / 3;
            Bframe[i] = createNullPointer(BXpos[i], BYpos, ARRAYELEMWIDTH, ARRAYELEMHEIGHT);
            Bframe[i].setNull(true);
            if (i % 2 == 0) {
                Bindex[i] = createLabel((BUCKETSIZE * i) + "-" + (BUCKETSIZE * i + BUCKETSIZE - 1), BXpos[i], BYpos + ARRAYELEMHEIGHT / 2 + 12);
            } else {
                Bindex[i] = createLabel((BUCKETSIZE * i) + "-" + (BUCKETSIZE * i + BUCKETSIZE - 1), BXpos[i], BYpos + ARRAYELEMHEIGHT / 2 + 12 + 14);
            }
            Bindex[i].setLabelColor(Color.BLUE);
            BList[i] = null;
        }
        for (i = 0; i < SIZE; i++) {
            Xpos[i] = LISTWIDTH + i * LISTWIDTH;
            data[i] = (int) (BSIZE * BUCKETSIZE * Math.random());
            list[i] = createRectangle(String.valueOf(data[i]), Xpos[i], Ypos, LISTWIDTH, LISTHEIGHT, false);
            index[i] = createLabel(String.valueOf(i), Xpos[i], Ypos + LISTHEIGHT / 2 + 12, false);
            index[i].setLabelColor(Color.BLUE);
        }
        minwaitscalefactor /= 2;

    }


    protected void CallFunction(int function) {
        switch (function) {
            case BUCKETSORT:
                bucketsort();
                break;
            case RANDOMIZE:
                Randomize();

                break;
        }

    }

    public void Randomize() {
        int i;
        for (i = 0; i < SIZE; i++) {
            data[i] = (int) (BSIZE * BUCKETSIZE * Math.random());
            list[i].setLabel(String.valueOf(data[i]));
        }
        repaint();
    }


    protected void insert(int index, int arrayindex, int data) {
        int j;

        DSShapeSingleLLU newelem = createSingleLinkedListRectU("", Xpos[arrayindex], 100, ELEMWIDTH, ELEMHEIGHT);
        newelem.setPointerVoid(true);
        GElementLabel nextval = createLabel(String.valueOf(data), Xpos[arrayindex], Ypos, false);
        list[arrayindex].setLabel("");
        Vector2D path[] = createPath(nextval.getPosition(), newelem.getPosition(), NUMSTEPS/2);

        for (j = 0; j < NUMSTEPS/2; j++) {
            nextval.setPosition(path[j]);
            repaintwaitmin();
        }

        newelem.setLabel(nextval.getLabel());
        removeAny(nextval);

        newelem.setLabelColor(Color.RED);
        Bindex[index].setLabelColor(Color.RED);
        repaintwait();
        Bindex[index].setLabelColor(Color.BLUE);
        // newelem.setLabelColor(Color.BLACK);


        if (BList[index] == null) {
            BList[index] = new BucketElem(newelem);
            GLink l2 = createLink(Bframe[index], newelem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
            l2.setSourceOffset(0, ARRAYELEMHEIGHT / 2);
            Bframe[index].setNull(false);
            newelem.setLabelColor(Color.BLACK);
            return;
        }


        BList[index].elem.setLabelColor(Color.RED);
        BList[index].elem.setColor(Color.RED);
        repaintwait();
        BList[index].elem.setLabelColor(Color.BLACK);
        BList[index].elem.setColor(Color.BLACK);
        if (Integer.parseInt(BList[index].elem.getLabel()) >= data) {
            newelem.setLabelColor(Color.BLACK);

            removeLink(Bframe[index], BList[index].elem);
            GLink l2 = createLink(Bframe[index], newelem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
            l2.setSourceOffset(0, ARRAYELEMHEIGHT / 2);
            l2 = createLink(newelem, BList[index].elem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
            l2.setSourceOffset(0, ELEMHEIGHT * newelem.getpercentLink() / 2);
            BList[index] = new BucketElem(newelem, BList[index]);
            newelem.setPointerVoid(false);
            return;

        }
        BucketElem tmp = BList[index];
        for (tmp = BList[index]; isGreaterSafe(data, tmp); tmp = tmp.next) {
        }
        newelem.setLabelColor(Color.BLACK);
        if (tmp.next != null) {
            removeLink(tmp.elem, tmp.next.elem);
            GLink l2 = createLink(newelem, tmp.next.elem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
            l2.setSourceOffset(0, ELEMHEIGHT * newelem.getpercentLink() / 2);
            newelem.setPointerVoid(false);
        } else {
            tmp.elem.setPointerVoid(false);
            newelem.setPointerVoid(true);
        }
        GLink l2 = createLink(tmp.elem, newelem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
        l2.setSourceOffset(0, ELEMHEIGHT * newelem.getpercentLink() / 2);
        tmp.next = new BucketElem(newelem, tmp.next);


    }

    protected boolean isGreaterSafe(int data, BucketElem elem) {
        if (elem.next == null)
            return false;
        elem.next.elem.setLabelColor(Color.RED);
        elem.next.elem.setColor(Color.RED);
        repaintwait();
        elem.next.elem.setLabelColor(Color.BLACK);
        elem.next.elem.setColor(Color.BLACK);
        return data > Integer.parseInt(elem.next.elem.getLabel());
    }

    protected void bucketsort() {
        int i, j;
        int dataindex;
        Vector2D path[];
        for (i = 0; i < SIZE; i++) {
            int Bindx = data[i] / BUCKETSIZE;
            insert(Bindx, i, data[i]);
            UpdateList(BList[Bindx], BXpos[Bindx]);
        }

        dataindex = 0;
        for (i = 0; i < BSIZE; i++) {
            GElementLabel value;
            while (BList[i] != null) {
                value = createLabel(BList[i].elem.getLabel(), BList[i].elem.getPositionX(),
                                    BList[i].elem.getPositionY(), false);
                path = createPath(value.getPosition(), list[dataindex].getPosition(), NUMSTEPS);
                for (j = 0; j < NUMSTEPS; j++) {
                    value.setPosition(path[j]);
                    repaintwaitmin();
                }
                list[dataindex].setLabel(value.getLabel());
                data[dataindex] = Integer.parseInt(list[dataindex].getLabel());
                removeAny(value);
                dataindex++;
                removeLink(Bframe[i], BList[i].elem);
                if (BList[i].next != null) {
                    removeLink(BList[i].elem, BList[i].next.elem);
                    GLink l2 = createLink(Bframe[i], BList[i].next.elem, GLink.SHAPE_ARC, GElement.ANCHOR_TOP, GElement.ANCHOR_BOTTOM, "", 0);
                    l2.setSourceOffset(0, ARRAYELEMHEIGHT / 2);
                }
                removeAny(BList[i].elem);
                BList[i] = BList[i].next;
            }
            Bframe[i].setNull(true);

        }
    }


    protected void UpdateList(BucketElem list, int xpos) {
        BucketElem tmp;
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


    protected void deleteElems() {
        int i;
        BucketElem tmp;
        for (i = 0; i < BUCKETSIZE; i++) {
            if (BList[i] != null) {
                removeLink(Bframe[i], BList[i].elem);
                for (tmp = BList[i]; tmp != null; tmp = tmp.next) {
                    if (tmp.next != null) {
                        removeLink(tmp.elem, tmp.next.elem);
                    }
                    removeAny(tmp.elem);
                }
                BList[i] = null;
                Bframe[i].setNull(true);
            }

        }

    }

    protected class BucketElem {
        public DSShapeSingleLLU elem;
        public Vector2D newlocation;
        public BucketElem next;
        public Vector2D path[];

        BucketElem(DSShapeSingleLLU newelem) {
            elem = newelem;
            newlocation = null;
            next = null;
            path = null;
        }

        BucketElem(DSShapeSingleLLU newelem, BucketElem newnext) {
            elem = newelem;
            newlocation = null;
            next = newnext;
            path = null;
        }
    }

}
