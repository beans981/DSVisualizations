package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;

import java.awt.*;

public class DSViewCountSort extends DSView {


    public final static int COUNTINGSORT = 1;
    public final static int CHANGETOSMALL = 2;
    public final static int CHANGETOLARGE = 3;


    protected int ArrayData[];
    protected GElement ArrayElements[];
    protected GElement ArrayFrame[];
    protected GElement ArrayIndex[];

    protected int BData[];
    protected GElement BElements[];
    protected GElement BFrame[];
    protected GElement BIndex[];

    protected GElement TempFrame[];
    protected GElement TempIndex[];
    protected int TempData[];


    protected int Xpos[];
    protected int Ypos[];
    protected int XposB[];
    protected int YposB[];
    protected int XposTemp[];
    protected int YposTemp[];

    protected final int MOVESTEPS = 40;


    protected final int LARGE_SIZE = 45;
    protected final int LARGE_BSIZE= 41;
    protected final int LARGE_BOXSIZE = 20;

    protected final int SMALL_SIZE = 22;
    protected final int SMALL_BSIZE= 20;
    protected final int SMALL_BOXSIZE = 40;



    protected int SIZE = SMALL_SIZE;
    protected int BSIZE = SMALL_BSIZE;
    protected int BOXSIZE = SMALL_BOXSIZE;


    public DSViewCountSort() {
        waitscalefactor = 20;
        minwaitscalefactor = 0.025;
        sleeptime = 25;

        createLabel("",0,0,false);

        BuildArrays();


    }




    protected void removeArrays() {

        int i;

         for (i=0; i<SIZE;i++) {
             removeAny(ArrayElements[i]);
             removeAny(ArrayIndex[i]);
             removeAny(ArrayFrame[i]);
         }
         for (i=0; i<BSIZE; i++) {
             removeAny(BElements[i]);
             removeAny(BIndex[i]);
             removeAny(BFrame[i]);
         }

    }

    protected void ChangeToLarge() {
          removeArrays();
            SIZE = LARGE_SIZE;
            BOXSIZE = LARGE_BOXSIZE;
            BSIZE = LARGE_BSIZE;
            BuildArrays();
    }

    protected void ChangeToSmall() {
          removeArrays();
            SIZE = SMALL_SIZE;
            BOXSIZE = SMALL_BOXSIZE;
            BSIZE = SMALL_BSIZE;
            BuildArrays();
    }


    protected void BuildArrays() {
        int i;
        ArrayData = new int[SIZE];
         ArrayElements = new GElement[SIZE];
         ArrayIndex = new GElement[SIZE];
         ArrayFrame = new GElement[SIZE];

         TempIndex = new GElement[SIZE];
         TempFrame = new GElement[SIZE];
         TempData = new int[SIZE];

         BData = new int[BSIZE];
         BElements = new GElement[BSIZE];
         BIndex = new GElement[BSIZE];
         BFrame = new GElement[BSIZE];


         Xpos = new int[SIZE];
         Ypos = new int[SIZE];

         XposTemp = new int[SIZE];
         YposTemp = new int[SIZE];

         XposB = new int[BSIZE];
         YposB = new int[BSIZE];

                for (i = 0; i < SIZE; i++) {
            Xpos[i] = BOXSIZE + i * BOXSIZE;
            Ypos[i] = 100;
                    XposTemp[i] = BOXSIZE + i * BOXSIZE;
                    YposTemp[i] = 212;

            ArrayFrame[i] = createRectangle("", Xpos[i], Ypos[i] + 2, BOXSIZE, BOXSIZE, false);
            ArrayIndex[i] = createLabel(String.valueOf(i), Xpos[i], Ypos[i] + BOXSIZE/2+12, false);
            ArrayIndex[i].setLabelColor(Color.BLUE);

            ArrayData[i] = (int) (BSIZE * Math.random());
            ArrayElements[i] = createLabel(String.valueOf(ArrayData[i]), Xpos[i], Ypos[i], false);

        }
        //    createLabel("Data",20,Ypos[0]-50);

        for (i = 0; i < BSIZE; i++) {
            XposB[i] = 2*BOXSIZE + i * BOXSIZE;
            YposB[i] = 325;
            BFrame[i] = createRectangle("", XposB[i], YposB[i] + 2, BOXSIZE, BOXSIZE, false);
            BIndex[i] = createLabel(String.valueOf(i), XposB[i], YposB[i] + BOXSIZE/2+12, false);
            BIndex[i].setLabelColor(Color.BLUE);

            BData[i] = 0;
            BElements[i] = createLabel("", XposB[i], YposB[i], false);

        }

    }


    protected void CallFunction(int function) {
        switch (function) {
            case COUNTINGSORT:
                CountingSort();
                break;
            case CHANGETOSMALL:
               ChangeToSmall();
                break;
                case CHANGETOLARGE:
                ChangeToLarge();
                break;
        }
    }

    void CountingSort() {
        int TmpIndex;
        GElementArrow emphasizeArray = createArrow(Xpos[0], Ypos[0] + BOXSIZE/2+55, Xpos[0], Ypos[0]+ BOXSIZE/2+25, 10);
        GElementArrow emphasizeB = createArrow(XposB[0] - 20, YposB[0] + BOXSIZE/2+55, XposB[0] - 20, YposB[0] + BOXSIZE/2+25, 10);
        int i, j;
        Vector2D[] path;

        for (i = 0; i < BSIZE; i++) {
            BData[i] = 0;
            BElements[i].setLabel("0");
        }
        for (i = 0; i < SIZE; i++) {
            path = createPath(emphasizeArray.getPosition(), new Vector2D(Xpos[i], Ypos[i] + BOXSIZE/2+55));
            for (j = 0; j < path.length; j++) {
                emphasizeArray.moveToPosition(path[j]);
                repaintwaitmin();
            }
            ArrayElements[i].setLabelColor(Color.RED);
            BIndex[ArrayData[i]].setLabelColor(Color.RED);
            path = createPath(emphasizeB.getPosition(), new Vector2D(XposB[ArrayData[i]], YposB[ArrayData[i]] + BOXSIZE/2+55));
            for (j = 0; j < path.length; j++) {
                emphasizeB.moveToPosition(path[j]);
                repaintwaitmin();
            }

            repaintwait();
            BData[ArrayData[i]]++;
            BElements[ArrayData[i]].setLabel(String.valueOf(BData[ArrayData[i]]));
            repaintwait();
            ArrayElements[i].setLabelColor(Color.BLACK);
            BIndex[ArrayData[i]].setLabelColor(Color.BLUE);
        }
        removeAny(emphasizeArray);
        path = createPath(emphasizeB.getPosition(), new Vector2D(XposB[0], YposB[0] + BOXSIZE/2+55));
        for (j = 0; j < path.length; j++) {
            emphasizeB.moveToPosition(path[j]);
            repaintwaitmin();
        }
        for (i = 1; i < BSIZE; i++) {
            path = createPath(emphasizeB.getPosition(), new Vector2D(XposB[i], YposB[i] + BOXSIZE/2+55));
            for (j = 0; j < path.length; j++) {
                emphasizeB.moveToPosition(path[j]);
                repaintwaitmin();
            }
            BElements[i].setLabelColor(Color.RED);
            BElements[i - 1].setLabelColor(Color.RED);
            repaintwait();
            BData[i] = BData[i] + BData[i - 1];
            BElements[i].setLabelColor(Color.BLACK);
            BElements[i - 1].setLabelColor(Color.BLACK);
            BElements[i].setLabel(String.valueOf(BData[i]));
            repaintwait();
        }

        for (i = 0; i < SIZE; i++) {
            TempFrame[i] = createRectangle("", XposTemp[i], YposTemp[i] + 2, BOXSIZE, BOXSIZE, false);
            TempIndex[i] = createLabel(String.valueOf(i), XposTemp[i], YposTemp[i] + BOXSIZE/2+12, false);
            TempData[i] = 0;
            TempIndex[i].setLabelColor(Color.BLUE);
        }


        emphasizeArray = createArrow(Xpos[SIZE-1], Ypos[0] - (BOXSIZE/2+35), Xpos[SIZE-1], Ypos[0] - (BOXSIZE/2+5), 10);
        GElementArrow emphasizeTemp = createArrow(XposTemp[0],YposTemp[0]+BOXSIZE/2+55,XposTemp[0],YposTemp[0]+BOXSIZE/2+25,10);
        for (i = SIZE - 1; i >= 0; i--) {
            path = createPath(emphasizeArray.getPosition(), new Vector2D(Xpos[i], Ypos[i] - (BOXSIZE/2+35)));
            for (j = 0; j < path.length; j++) {
                emphasizeArray.moveToPosition(path[j]);
                repaintwaitmin();
            }
            ArrayElements[i].setLabelColor(Color.RED);
            path = createPath(emphasizeB.getPosition(), new Vector2D(XposB[ArrayData[i]],
                                                                     YposB[ArrayData[i]] + BOXSIZE/2+55));

            BIndex[ArrayData[i]].setLabelColor(Color.RED);
            for (j = 0; j < path.length; j++) {
                emphasizeB.moveToPosition(path[j]);
                repaintwaitmin();
            }
            repaintwait();
            BData[ArrayData[i]]--;
            BElements[ArrayData[i]].setLabel(String.valueOf(BData[ArrayData[i]]));
            repaintwait();
            BIndex[ArrayData[i]].setLabelColor(Color.BLUE);
            ArrayElements[i].setLabelColor(Color.BLACK);
            TmpIndex = BData[ArrayData[i]];
            BElements[ArrayData[i]].setLabelColor(Color.RED);
            TempIndex[TmpIndex].setLabelColor(Color.RED);
            path = createPath(emphasizeTemp.getPosition(),new Vector2D(XposTemp[TmpIndex],YposTemp[TmpIndex]+BOXSIZE/2+55));
            for (j=0; j<path.length;j++) {
                emphasizeTemp.moveToPosition(path[j]);
                repaintwaitmin();
            }
            TempData[TmpIndex] = ArrayData[i];
            path = createPath(ArrayElements[i].getPosition(),new Vector2D(XposTemp[TmpIndex],YposTemp[TmpIndex]));
            for (j=0; j<path.length;j++) {
                ArrayElements[i].setPosition(path[j]);
                repaintwaitmin();
            }
            BElements[ArrayData[i]].setLabelColor(Color.BLACK);
            TempIndex[TmpIndex].setLabelColor(Color.BLUE);
        }
        removeAny(emphasizeB);
        removeAny(emphasizeTemp);
        removeAny(emphasizeArray);
        for (i=0; i<SIZE; i++) {
            ArrayData[i] = TempData[i];
            ArrayElements[i].setLabel(String.valueOf(ArrayData[i]));
            ArrayElements[i].setPosition(XposTemp[i],YposTemp[i]);
        }
        for (i=0; i<SIZE; i++) {
            path = createPath(ArrayElements[i].getPosition(),
                              new Vector2D(Xpos[i],Ypos[i]));
            for (j=0; j<path.length;j++) {
                ArrayElements[i].setPosition(path[j]);
                repaintwaitmin();
            }
        }
        repaintwait();
        for (i=0; i<SIZE;i++) {
            TempIndex[i].setLabelVisible(false);
            TempFrame[i].setColor(Color.WHITE);
        }
        for (i=0;i<BSIZE;i++) {
            BElements[i].setLabel("");
            BData[i] = 0;
        }
        for (i = 0; i < SIZE; i++) {
              removeAny(TempFrame[i]);
              removeAny(TempIndex[i]);
          }


    }

    public void Randomize() {
        int i;
        for (i = 0; i < SIZE; i++) {
            ArrayData[i] = (int) (Math.random() * BSIZE);
            ArrayElements[i].setLabel(String.valueOf(ArrayData[i]));
        }
        repaint();

    }




}