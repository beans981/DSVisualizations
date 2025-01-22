package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeNullPointer;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;

import java.awt.*;


public class DSViewHashClosed extends DSViewHash {


//    public final static int INSERT = 1;   defined in superclass
    //   public final static int DELETE = 2;
    //   public final static int FIND = 3;
    //   public final static int HASHINTEGER = 4;
    //  public final static int HASHSTRING = 5;
    public final static int LINEARPROBING = 6;
    public final static int QUADRATICPROBING = 7;
    public final static int DOUBLEHASHING = 8;


    protected final int ELEMWIDTH = 75;
    protected final int ELEMHEIGHT = 50;
    protected final int ARRAYELEMWIDTH = 75;
    protected final int ARRAYELEMHEIGHT = 30;


    protected GElementLabel index[];
    protected DSShapeNullPointer frame[];


    protected GElement HashList[];
    protected boolean deleted[];
    protected int Ypos[];
    protected int Xpos[];

    protected int hashStrategy = LINEARPROBING;

    public DSViewHashClosed() {

        int i;

        HASHSIZE = 23;
        Xpos = new int[HASHSIZE];
        Ypos = new int[HASHSIZE];
        HashList = new GElement[HASHSIZE];
        index = new GElementLabel[HASHSIZE];
        deleted = new boolean[HASHSIZE];


        for (i = 0; i < 12; i++) {
            Xpos[i] = i * ARRAYELEMWIDTH + 2 * ARRAYELEMWIDTH / 3;
            Ypos[i] = 200;
        }

        for (i = 0; i < 11; i++) {
            Xpos[i + 12] = i * ARRAYELEMWIDTH + ARRAYELEMWIDTH;
            Ypos[i + 12] = 300;
        }

        for (i = 0; i < HASHSIZE; i++) {
            HashList[i] = createRectangle("", Xpos[i], Ypos[i], ARRAYELEMWIDTH, ARRAYELEMHEIGHT, false);
            index[i] = createLabel(String.valueOf(i), Xpos[i], Ypos[i] + ARRAYELEMHEIGHT / 2 + 12);
            index[i].setLabelColor(Color.BLUE);
            deleted[i] = false;

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
            case LINEARPROBING:
            case QUADRATICPROBING:
            case DOUBLEHASHING:
                hashStrategy = function;
                deleteElems();
                break;
        }

    }


    int next(int indx, int i) {
        if (hashStrategy == LINEARPROBING)
            return (indx + i) % HASHSIZE;
        else if (hashStrategy == QUADRATICPROBING)
            return (indx + i * i) % HASHSIZE;
        else if (hashStrategy == DOUBLEHASHING)
            return (indx + i * (7 - (int) (hashval % 7))) % HASHSIZE;
        System.out.println("Something's wrong:  hashStrategy = " + hashStrategy);
        return -1; /* Something went wrong to get here ... */
    }


    int findElement(int startindex, String key) {
        int i, j;
        Vector2D[] path;
        int nextindex;
        GElementArrow emphasis = createArrow(Xpos[0] - ARRAYELEMWIDTH / 2, Ypos[0] + ARRAYELEMHEIGHT / 2 + 22 + 20,
                                             Xpos[0] - ARRAYELEMWIDTH / 2, Ypos[0] + ARRAYELEMHEIGHT / 2 + 22, 10);
        for (i = 0; i < 2 * HASHSIZE; i++) {
            nextindex = next(startindex, i);
            index[nextindex].setLabelColor(Color.RED);
            Color oldColor = HashList[nextindex].getLabelColor();
            HashList[nextindex].setColor(Color.RED);
            if (oldColor != Color.LIGHT_GRAY)
                HashList[nextindex].setLabelColor(Color.RED);
            path = createPath(emphasis.getPosition(), new Vector2D(Xpos[nextindex], Ypos[nextindex] + ARRAYELEMHEIGHT / 2 + 22 + 20), NUMSTEPS);
            for (j = 0; j < NUMSTEPS; j++) {
                emphasis.moveToPosition(path[j]);
                repaintwaitmin();
            }
            repaintwait();
            HashList[nextindex].setColor(Color.BLACK);
            HashList[nextindex].setLabelColor(oldColor);
            index[nextindex].setLabelColor(Color.BLUE);

            if (HashList[nextindex].getLabel().compareTo(key) == 0) {
                  removeAny(emphasis);
                  return nextindex;
              }
            if (HashList[nextindex].getLabel().compareTo("") == 0) {
                  removeAny(emphasis);
                  return -1;
              }
            }
        removeAny(emphasis);
        return -1;

    }


    int findEmpty(int indx) {
        int i, j;
        Vector2D[] path;
        int nextindex;
        GElementArrow emphasis = createArrow(Xpos[0] - ARRAYELEMWIDTH / 2, Ypos[0] + ARRAYELEMHEIGHT / 2 + 22 + 20,
                                             Xpos[0] - ARRAYELEMWIDTH / 2, Ypos[0] + ARRAYELEMHEIGHT / 2 + 22, 10);
        for (i = 0; i < 2 * HASHSIZE; i++) {
            nextindex = next(indx, i);
            index[nextindex].setLabelColor(Color.RED);
            HashList[nextindex].setColor(Color.RED);
            path = createPath(emphasis.getPosition(), new Vector2D(Xpos[nextindex], Ypos[nextindex] + ARRAYELEMHEIGHT / 2 + 22 + 20), NUMSTEPS);
            for (j = 0; j < NUMSTEPS; j++) {
                emphasis.moveToPosition(path[j]);
                repaintwaitmin();
            }
            repaintwait();
            HashList[nextindex].setColor(Color.BLACK);
            index[nextindex].setLabelColor(Color.BLUE);

            if (HashList[nextindex].getLabel().compareTo("") == 0 || deleted[nextindex]) {
                removeAny(emphasis);
                return nextindex;
            }
        }
        removeAny(emphasis);
        return -1;

    }


    protected void insertinto(int indx, String key) {

       GElementLabel l1 = null;

        if (hashStrategy == DOUBLEHASHING) {
            l1 = createLabel("hash2("+key+") = 7 - "+(int) hashval+ " % 7 = " + (7-hashval%7),-10,-10);
            GElement lup[] = {l1};
            LineupHorizontal(new Vector2D(20,50),lup);
        }
        indx = findEmpty(indx);
        if (indx >= 0) {
            HashList[indx].setLabel(key);
            deleted[indx] = false;
            HashList[indx].setLabelColor(Color.BLACK);
        } else {
            GElementLabel failed = createLabel("Insertion failed", 300, 20);
            HoldoverGraphics.add(failed);
        }
        if (hashStrategy == DOUBLEHASHING)
            removeAny(l1);


    }


    protected void deleteElems() {
        int i;
        for (i = 0; i < HASHSIZE; i++) {
            HashList[i].setLabel("");
            HashList[i].setLabelColor(Color.BLACK);
            deleted[i] = false;

        }

    }


    protected void deletefrom(int deleteindex, String deletestr) {

        elemIndexLabel.setLabelColor(Color.RED);
        index[deleteindex].setLabelColor(Color.RED);
        repaintwait();

        int elemindex = findElement(deleteindex,deletestr);

        if (elemindex >= 0) {
            HashList[elemindex].setLabel("deleted");
            HashList[elemindex].setLabelColor(Color.LIGHT_GRAY);
            deleted[elemindex] = true;
        }


        elemIndexLabel.setLabelColor(Color.BLACK);
        index[deleteindex].setLabelColor(Color.BLUE);

    }


    protected void findin(int indx, String key) {

        int foundindex = findElement(indx, key);


        if (foundindex >= 0) {
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


}
