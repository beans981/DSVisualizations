package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeColoredLabel;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;

import java.awt.*;


public class DSViewHash extends DSView {


    public final static int INSERT = 1;
    public final static int DELETE = 2;
    public final static int FIND = 3;
    public final static int HASHINTEGER = 4;
    public final static int HASHSTRING = 5;


    protected final int NUMSTEPS = 25;
    protected  int HASHSIZE = 11;

    protected long hashval;

    protected boolean HashingIntegers = true;

    protected GElementLabel elemLabel;
    protected GElementLabel elemIndexLabel;

    public DSViewHash() {


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


    }




    protected void insert(int insertval) {
        int insertindex = insertval % HASHSIZE;
        if (insertindex < 0)
            insertindex += HASHSIZE;

        String insertstring = String.valueOf(insertval);
        hashval = insertval;


        GElement l1 = createLabel("Inserting ", -10, -10);
        elemLabel = createLabel(insertstring, -10, -10);

        GElement lineup[] = {l1, elemLabel};
        LineupHorizontal(new Vector2D(20, 10), lineup);


        repaintwait();


        GElementLabel glab = createLabel("hash(" + insertval + ") = " + insertval + " % " + HASHSIZE + " = ",
                                         -10, -10);
        elemIndexLabel = createLabel(String.valueOf(insertindex), -10, -10);


        GElement lineup2[] = {glab, elemIndexLabel};
        LineupHorizontal(new Vector2D(20, 30), lineup2);


        repaintwait();

        insertinto(insertindex, String.valueOf(insertval));


        removeAny(l1);
        removeAny(glab);
        removeAny(elemIndexLabel);
        removeAny(elemLabel);
    }

    protected void insert(String insertval) {

        GElement l1 = createLabel("Inserting: ", -10, -10);
        elemLabel = createLabel(insertval, -10, -10);

        GElement lineup[] = {l1, elemLabel};
        LineupHorizontal(new Vector2D(20, 10), lineup);


        repaintwait();

        GElementLabel glab = createLabel("hash(" + insertval + ") = ",
                                         -10, -10);

        GElement lineup2[] = {glab};
        LineupHorizontal(new Vector2D(20, 30), lineup2);


        int insertindex = (int) hash(insertval, HASHSIZE);
        if (insertindex < 0)
            insertindex += HASHSIZE;

        elemIndexLabel = createLabel(String.valueOf(insertindex), 50 + 4 * (insertval.length() + 9) + 2 * String.valueOf(insertindex).length(), 30);

        GElement lineup3[] = {glab, elemIndexLabel};

        LineupHorizontal(lineup3);

        repaintwait();

        insertinto(insertindex, insertval);


        removeAny(l1);
        removeAny(glab);
        removeAny(elemIndexLabel);
        removeAny(elemLabel);


    }

    protected void delete(int deleteval) {

        int deleteindex = deleteval % HASHSIZE;
        if (deleteindex < 0)
            deleteindex += HASHSIZE;

        String deletestring = String.valueOf(deleteval);

        hashval = deleteval;

        GElement l1 = createLabel("Deleting ", -10, -10);
        elemLabel = createLabel(deletestring, -10, -10);

        GElement lineup[] = {l1, elemLabel};
        LineupHorizontal(new Vector2D(20, 10), lineup);


        repaintwait();


        GElementLabel glab = createLabel("hash(" + deleteval + ") = " + deleteval + " % " + HASHSIZE + " = ",
                                         -10, -10);
        elemIndexLabel = createLabel(String.valueOf(deleteindex), -10, -10);

        GElement lineup2[] = {glab, elemIndexLabel};
        LineupHorizontal(new Vector2D(20, 30), lineup2);


        repaintwait();

        deletefrom(deleteindex, deletestring);

        removeAny(l1);
        removeAny(glab);
        removeAny(elemIndexLabel);
        removeAny(elemLabel);


    }

    protected void deletefrom(int deleteindex, String deletestr) {


    }

    protected void delete(String insertval) {
        GElement l1 = createLabel("Deleting: ", -10, -10);
        elemLabel = createLabel(insertval, -10, -10);

        GElement lineup[] = {l1, elemLabel};
        LineupHorizontal(new Vector2D(20, 10), lineup);


        repaintwait();

        GElementLabel glab = createLabel("hash(" + insertval + ") = ",
                                         -10, -10);

        GElement lineup2[] = {glab};
        LineupHorizontal(new Vector2D(20, 30), lineup2);


        int insertindex = (int) hash(insertval, HASHSIZE);
        if (insertindex < 0)
            insertindex += HASHSIZE;

        elemIndexLabel = createLabel(String.valueOf(insertindex), 50 + 4 * (insertval.length() + 9) + 2 * String.valueOf(insertindex).length(), 30);

        GElement lineup3[] = {glab, elemIndexLabel};

        LineupHorizontal(lineup3);

        repaintwait();

        deletefrom(insertindex, insertval);


        removeAny(l1);
        removeAny(glab);
        removeAny(elemIndexLabel);
        removeAny(elemLabel);


    }


    protected void findin(int indx, String key) {

    }

    protected void find(int insertval) {
        int insertindex = insertval % HASHSIZE;
        if (insertindex < 0)
            insertindex += HASHSIZE;

        String insertstring = String.valueOf(insertval);

        hashval = insertval;

        GElement l1 = createLabel("finding ", -10, -10);
        elemLabel = createLabel(insertstring, -10, -10);
        GElement lineup[] = {l1, elemLabel};
        LineupHorizontal(new Vector2D(20, 10), lineup);

        repaintwait();


        GElementLabel glab = createLabel("hash(" + insertval + ") = " + insertval + " % " + HASHSIZE + " = ",
                                         -10, -10);
        elemIndexLabel = createLabel(String.valueOf(insertindex), -10, 10);
        GElement lineup2[] = {glab, elemIndexLabel};
        LineupHorizontal(new Vector2D(20, 30), lineup2);

        repaintwait();

        findin(insertindex, String.valueOf(insertval));

        removeAny(glab);
        removeAny(l1);
        removeAny(elemLabel);
        removeAny(elemIndexLabel);


    }


    int ithBit(char c, int i) {
        int j;
        int result = (int) c;


        for (j = 7; j > i; j--) {
            result = result / 2;
        }
        return result % 2;

    }


    protected void find(String insertval) {
        GElement l1 = createLabel("Finding: ", -10, -10);
        elemLabel = createLabel(insertval, -10, -10);

        GElement lineup[] = {l1, elemLabel};
        LineupHorizontal(new Vector2D(20, 10), lineup);


        repaintwait();

        GElementLabel glab = createLabel("hash(" + insertval + ") = ",
                                         -10, -10);

        GElement lineup2[] = {glab};
        LineupHorizontal(new Vector2D(20, 30), lineup2);


        int insertindex = (int) hash(insertval, HASHSIZE);
        if (insertindex < 0)
            insertindex += HASHSIZE;

        elemIndexLabel = createLabel(String.valueOf(insertindex), 50 + 4 * (insertval.length() + 9) + 2 * String.valueOf(insertindex).length(), 30);

        GElement lineup3[] = {glab, elemIndexLabel};

        LineupHorizontal(lineup3);

        repaintwait();

        findin(insertindex, insertval);


        removeAny(l1);
        removeAny(glab);
        removeAny(elemIndexLabel);
        removeAny(elemLabel);


    }


    long getInteger(GElementLabel result[]) {
        long intval = 0;
        int i;
        for (i = 0; i < 32; i++) {
            intval = intval * 2 + Integer.parseInt(result[i].getLabel());
        }
        return intval;
    }


    long hash(String key, int tableSize) {
        int i, j, k;

        DSShapeColoredLabel Key = createColoredLabel(key, 400, 20);
        GElementLabel result[] = new GElementLabel[32];
        GElementLabel NextChar[] = new GElementLabel[8];
        GElementLabel Extra4[] = new GElementLabel[4];
        Vector2D[] path[] = new Vector2D[32][];
        GElement plus = createLabel("+", 625, 60);
        plus.setLabelVisible(false);
        GElement XOR = createLabel("XOR", 635, 80);
        XOR.setLabelVisible(false);
        GElement tmp[] = new GElement[32];
        GElement tmp2;

        //GElementLabel



        for (i = 0; i < 32; i++) {
            result[i] = createLabel("0", i * 10 + 300, 60);
        }
        for (i = 0; i < key.length(); i++) {
            Key.setLabelColor(Color.BLACK);
            Key.setLabelColorIndex(Color.RED, i);
            repaintwait();

            for (k = 0; k < 8; k++) {
                NextChar[k] = createLabel(String.valueOf(ithBit(key.charAt(i), k)), k * 10 + 390, 40);
                NextChar[k].setLabelColor(Color.RED);
                path[k] = createPath(new Vector2D(k * 10 + 390, 40), new Vector2D(k * 10 + 540, 40), NUMSTEPS);
            }

            repaintwait();
            for (k = 0; k < 8; k++)
                NextChar[k].setLabelColor(Color.BLACK);
            for (j = 0; j < NUMSTEPS; j++) {
                for (k = 0; k < 8; k++)
                    NextChar[k].setPosition(path[k][j]);
                repaintwaitmin();
            }

            plus.setLabelVisible(true);
            tmp2 = createRectangle("",455,70,320,1);

            repaintwait();

            int carry = 0;
            int next;



            for (k = 0; k < 32; k++) {
                tmp[k] = createLabel(result[k].getLabel(), k * 10 + 300, 60);
            }

            for (k = 7; k >= 0; k--) {
                next = Integer.parseInt(NextChar[k].getLabel()) +
                       Integer.parseInt(result[k + 24].getLabel()) + carry;
                carry = next / 2;
                next = next % 2;
                result[k + 24].setLabel(String.valueOf(next));
            }
            k = 23;
            while (k > 0 && carry > 0) {
                next = Integer.parseInt(result[k].getLabel()) + carry;
                carry = next / 2;
                next = next % 2;
                result[k].setLabel(String.valueOf(next));
                k--;
            }
            for (k = 0; k < 32; k++) {
                result[k].move(0, 20);
            }
            repaintwait();
            plus.setLabelVisible(false);
            removeAny(tmp2);
            for (k = 0; k < 8; k++)
                removeAny(NextChar[k]);
            for (k = 0; k < 32; k++)
                removeAny(tmp[k]);
            for (j = 0; j < 10; j++) {
                for (k = 0; k < 32; k++)
                    result[k].move(0, -2);
                repaintwaitmin();
            }

            if (i < key.length() - 1) {
                for (j = 0; j < 20; j++) {
                    for (k = 0; k < 32; k++)
                        result[k].move(-2, 0);
                    repaintwaitmin();
                }

                for (k = 0; k < 4; k++) {
                    Extra4[k] = createLabel(result[k].getLabel(), k * 10 + 260, 60);
                }

                for (k = 0; k <= 27; k++) {
                    result[k].setLabel(result[k + 4].getLabel());
                }
                for (k = 31; k >= 28; k--)
                    result[k].setLabel("0");
                for (k = 0; k < 32; k++)
                    result[k].setPosition(k * 10 + 300, 60);

                repaintwait();
                for (k = 0; k < 4; k++)
                    path[k] = createPath(Extra4[k].getPosition(), new Vector2D(300 + k * 10 + 10 * 10, 80), NUMSTEPS);
                for (j = 0; j < NUMSTEPS; j++) {
                    for (k = 0; k < 4; k++)
                        Extra4[k].moveToPosition(path[k][j]);
                    repaintwaitmin();
                }
                XOR.setLabelVisible(true);
                tmp2 = createRectangle("",455,90,320,1);
                repaintwait();

                for (k = 0; k < 32; k++) {
                    tmp[k] = createLabel(result[k].getLabel(), k * 10 + 300, 60);
                }

                for (k = 0; k < 4; k++) {
                    next = (Integer.parseInt(result[k + 10].getLabel()) + Integer.parseInt(Extra4[k].getLabel())) % 2;
                    result[k + 10].setLabel(String.valueOf(next));
                }

                for (k = 0; k < 32; k++) {
                    result[k].move(0, 40);
                }


                repaintwait();


                XOR.setLabelVisible(false);
                removeAny(tmp2);
                for (k = 0; k < 4; k++)
                    removeAny(Extra4[k]);



                for (k=0;k<32;k++)
                    removeAny(tmp[k]);
                for (j = 0; j < 10; j++) {
                    for (k = 0; k < 32; k++)
                        result[k].move(0, -4);
                    repaintwaitmin();
                }



            }

        }
        repaintwait();
        for (i = 0; i < 32; i++) {
            path[i] = createPath(result[i].getPosition(), new Vector2D(i * 2 + 620 - 64, 60), NUMSTEPS);
        }
        for (j = 0; j < NUMSTEPS; j++) {
            for (i = 0; i < 32; i++)
                result[i].setPosition(path[i][j]);
            repaintwaitmin();
        }

        hashval = getInteger(result);
        GElementLabel HVal = createLabel(String.valueOf(hashval), 620 - 5 * String.valueOf(hashval).length(), 60);

        for (i = 0; i < 32; i++)
            removeAny(result[i]);
        repaintwait();

        GElementLabel percent = createLabel(" % "+HASHSIZE +" = ", -10, -10);
        GElementLabel finalval = createLabel(String.valueOf(hashval % tableSize), -10, -10);
        GElement lineup[] = {HVal, percent, finalval};
        LineupHorizontal(lineup);


        repaintwait();

        path[0] = createPath(finalval.getPosition(),
                             new Vector2D(50 + 5 * (key.length() + 9) + 5 * finalval.getLabel().length() / 2, 30),
                             NUMSTEPS);

        for (j = 0; j < NUMSTEPS; j++) {
            finalval.setPosition(path[0][j]);
            repaintwaitmin();
        }


        removeAny(finalval);
        removeAny(Key);
        removeAny(percent);
        removeAny(HVal);
        removeAny(plus);
        removeAny(XOR);
        //removeAny(finalval);
        return hashval % tableSize;
    }

    /*
  long h = 0;
    long g;
  int i;
  for(i=0; i<key.length();i++) {
     h = (h << 4) + (int) key.charAt(i);
     g = h & 0xF0000000L;
     if (g != 0)
        h ^= g >>> 24;
     h = h + g;
  }
  return h % tableSize;
}
                                          */
    protected void deleteElems() {

    }



}
