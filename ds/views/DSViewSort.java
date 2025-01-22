package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;

import java.awt.*;

public class DSViewSort extends DSView {


    public final int BUBBLESORT = 1;
    public final int SELECTIONSORT = 2;
    public final int INSERTIONSORT = 3;
    public final int MERGESORT = 4;
    public final int QUICKSORT = 5;
    public final int SHELLSORT = 6;

    protected final double MAXHEIGHT = 200.0;
    protected final int SIZE = 45;
    protected final int ALLOCSIZE = 2 * SIZE;

    protected int SortData[];
    protected GElement SortElements[];
    protected GElement GraphicalElements[];
    protected GElement ArrayFrame[];
    protected GElement SortIndex[];
    protected int Xpos[];
    protected int Ypos[];
    protected boolean Graphical;
    protected boolean visableIndices;


    public DSViewSort() {
        visableIndices = true;
        Graphical = true;
        waitscalefactor = 10;
        SortData = new int[ALLOCSIZE];
        SortElements = new GElement[ALLOCSIZE];
        SortIndex = new GElement[ALLOCSIZE];
        ArrayFrame = new GElement[ALLOCSIZE];
        GraphicalElements = new GElement[ALLOCSIZE];
        Xpos = new int[ALLOCSIZE];
        Ypos = new int[ALLOCSIZE];
        int i;
        minwaitscalefactor = 0.05;
        sleeptime = 25;

        for (i = 0; i < SIZE; i++) {
            Xpos[i] = 20 + i * 20;
            Ypos[i] = 210;
            ArrayFrame[i] = createRectangle("", Xpos[i], Ypos[i] + 2, 20, 20, false);
            SortIndex[i] = createLabel(String.valueOf(i), Xpos[i], Ypos[i] + 22, false);
            SortIndex[i].setLabelVisible(visableIndices);
            SortIndex[i].setLabelColor(Color.BLUE);
            SortData[i] = (int) (99 * Math.random());


        }

        RecreateArray();

    }


    protected void CallFunction(int function) {
        switch (function) {
            case BUBBLESORT:
                BubbleSort();
                break;
            case SELECTIONSORT:
                SelectionSort();
                break;
            case INSERTIONSORT:
                InsertionSort();
                break;
            case MERGESORT:
                MergeSort();
                break;
            case QUICKSORT:
                Quicksort();
                break;
            case SHELLSORT:
                ShellSort();
                break;



        }

    }

    public void ToggleIndicesViewable() {
        int i;
        visableIndices = !visableIndices;
        for (i = 0; i < SIZE; i++)
            SortIndex[i].setLabelVisible(!SortIndex[i].isLabelVisible());
        repaint();

    }

    public void Randomize() {
        int i;
        for (i = 0; i < SIZE; i++) {
            SortData[i] = (int) (Math.random() * 100);
        }
        RecreateArray();

    }

    private void RecreateArray() {
        int i;

        for (i = 0; i < SIZE; i++) {
            removeAny(SortElements[i]);
            removeAny(GraphicalElements[i]);
        }
        if (Graphical) {
            for (i = 0; i < SIZE; i++)
                GraphicalElements[i] = BuildGrapicElement(SortData[i], Xpos[i], Ypos[i]);
        }
        for (i = 0; i < SIZE; i++)
            SortElements[i] = createLabel(String.valueOf(SortData[i]), Xpos[i], Ypos[i]);


        repaint();

    }

    private int GetYPosGraphic(int value, int y) {
        return (int) (y - ((((double) value) / 100 * MAXHEIGHT) / 2 + 12));
    }

    private GElement BuildGrapicElement(int value, int xbase, int ybase) {
        int height = (int) (((double) value) / 100 * MAXHEIGHT);
        return createRectangle("", xbase, GetYPosGraphic(value, ybase), 10, height, false);
    }


    public void SwitchToNumeric() {
        Graphical = false;
        RecreateArray();
    }

    private void swap(int index1, int index2) {
        int i;
        Vector2D G1from = null;
        Vector2D G1to = null;
        Vector2D G2from = null;
        Vector2D G2to = null;

        Vector2D path1G[] = null;
        Vector2D path2G[] = null;
        Vector2D path1[];
        Vector2D path2[];

        int numsteps = Math.max((int) (Math.abs(SortElements[index1].getPositionX() - SortElements[index2].getPositionX()) / 2),
                                10);

        double G1newY;
        double G2newY;

        if (Graphical) {
            G1newY = GraphicalElements[index1].getPositionY() - Ypos[index1] + Ypos[index2];
            G2newY = GraphicalElements[index2].getPositionY() - Ypos[index2] + Ypos[index1];
            G1from = GraphicalElements[index1].getPosition();
            G2from = GraphicalElements[index2].getPosition();
            G1to = new Vector2D(G2from.getX(), G1newY);
            G2to = new Vector2D(G1from.getX(), G2newY);
        }

        path1 = createPath(SortElements[index1].getPosition(), SortElements[index2].getPosition(), numsteps);
        path2 = createPath(SortElements[index2].getPosition(), SortElements[index1].getPosition(), numsteps);
        if (Graphical) {
            path1G = createPath(G1from, G1to, numsteps);
            path2G = createPath(G2from, G2to, numsteps);
        }
        for (i = 0; i < numsteps; i++) {
            SortElements[index1].setPosition(path1[i]);
            SortElements[index2].setPosition(path2[i]);
            if (Graphical) {
                GraphicalElements[index1].setPosition(path1G[i]);
                GraphicalElements[index2].setPosition(path2G[i]);

            }
            repaintwaitmin();

        }
        int tmp = SortData[index1];
        SortData[index1] = SortData[index2];
        SortData[index2] = tmp;
        GElement tmp2 = SortElements[index1];
        SortElements[index1] = SortElements[index2];
        SortElements[index2] = tmp2;
        if (Graphical) {
            tmp2 = GraphicalElements[index1];
            GraphicalElements[index1] = GraphicalElements[index2];
            GraphicalElements[index2] = tmp2;

        }

    }


    protected void moveto(int index1, int index2) {
        moveto(index1, index2, true);
    }

    protected void moveto(int index1, int index2, boolean killOldindex1) {
        int i;
        Vector2D G1from = null;
        Vector2D G1to = null;

        Vector2D path1G[] = null;
        Vector2D path1[];

        int numsteps = (int) Math.max(Math.sqrt((Xpos[index1] - Xpos[index2]) * (Xpos[index1] - Xpos[index2]) +
                                                (Ypos[index1] - Ypos[index2]) * (Ypos[index1] - Ypos[index2]) / 4),
                                      10);

        double G1newY;
        double G1newX;

        if (Graphical) {
            G1newY = GetYPosGraphic(SortData[index1], Ypos[index2]);
            G1newX = Xpos[index2];
            G1from = GraphicalElements[index1].getPosition();
            G1to = new Vector2D(G1newX, G1newY);
            if (GraphicalElements[index2] != null)
                removeAny(GraphicalElements[index2]);
            GraphicalElements[index2] = BuildGrapicElement(SortData[index1], Xpos[index1], Ypos[index1]);
        }
        path1 = createPath(SortElements[index1].getPosition(), new Vector2D(Xpos[index2], Ypos[index2]), numsteps);
        if (SortElements[index2] != null)
            removeAny(SortElements[index2]);
        SortElements[index2] = createLabel(String.valueOf(SortData[index1]), Xpos[index1], Ypos[index1]);
        if (Graphical) {
            path1G = createPath(G1from, G1to, numsteps);
        }
        if (killOldindex1) {
            if (Graphical)
                removeAny(GraphicalElements[index1]);
            removeAny(SortElements[index1]);

        }

        for (i = 0; i < numsteps; i++) {
            SortElements[index2].setPosition(path1[i]);
            if (Graphical) {
                GraphicalElements[index2].setPosition(path1G[i]);

            }
            repaintwaitmin();

        }
        SortData[index2] = SortData[index1];
    }


    protected boolean lessthanBC(int index1, int index2) {
        if ((index1 < 0) || (index1 > SIZE) ||
            (index2 < 0) || (index2 > SIZE))
            return false;
        return lessthan(index1, index2);
    }

    public boolean lessthan(int index1, int index2) {

        SortElements[index1].setLabelColor(Color.RED);
        SortElements[index2].setLabelColor(Color.RED);
        if (Graphical) {
            GraphicalElements[index1].setColor(Color.RED);
            GraphicalElements[index2].setColor(Color.RED);
        }
        repaintwait();
        SortElements[index1].setLabelColor(Color.BLACK);
        SortElements[index2].setLabelColor(Color.BLACK);
        if (Graphical) {
            GraphicalElements[index1].setColor(Color.BLACK);
            GraphicalElements[index2].setColor(Color.BLACK);
        }
        return SortData[index1] < SortData[index2];
    }



    protected void GrayElement(int index, boolean graybottom) {
        SortElements[index].setLabelColor(Color.LIGHT_GRAY);
           ArrayFrame[index].setColor(Color.LIGHT_GRAY);
           SortIndex[index].setLabelColor(Color.LIGHT_GRAY);
           if (Graphical)
               GraphicalElements[index].setColor(Color.LIGHT_GRAY);
           if (graybottom) {
               SortElements[index + SIZE].setLabelColor(Color.LIGHT_GRAY);
               ArrayFrame[index + SIZE].setColor(Color.LIGHT_GRAY);
               SortIndex[index + SIZE].setLabelColor(Color.LIGHT_GRAY);
               if (Graphical && GraphicalElements[index + SIZE] != null)
                   GraphicalElements[index + SIZE].setColor(Color.LIGHT_GRAY);
           }
    }

    protected void UnGrayElement(int index, boolean ungraybottom) {
        SortElements[index].setLabelColor(Color.BLACK);
        ArrayFrame[index].setColor(Color.BLACK);
        SortIndex[index].setLabelColor(Color.BLUE);
        if (Graphical)
            GraphicalElements[index].setColor(Color.BLACK);
        if (ungraybottom) {
            SortElements[index + SIZE].setLabelColor(Color.BLACK);
            ArrayFrame[index + SIZE].setColor(Color.BLACK);
            SortIndex[index + SIZE].setLabelColor(Color.BLUE);
            if (Graphical && (GraphicalElements[index + SIZE] != null))
                GraphicalElements[index + SIZE].setColor(Color.BLACK);

        }


    }



    protected void HighlightRange(int low, int high, boolean highlightbottom) {
        int i;
        for (i = 0; i < SIZE; i++) {
            if ((i < low) || (i > high)) {
                GrayElement(i,highlightbottom);

            } else {
                UnGrayElement(i,highlightbottom);
            }
        }
    }

    protected void Merge(int index1low, int index1high, int index2low, int index2high) {
        int i;

        int oldlow = index1low;
        for (i = index1low; i <= index2high; i++) {
            if (index1low > index1high)
                moveto(index2low++, i + SIZE, true);
            else if (index2low > index2high)
                moveto(index1low++, i + SIZE, true);
            else if (lessthan(index1low, index2low))
                moveto(index1low++, i + SIZE, true);
            else
                moveto(index2low++, i + SIZE, true);
        }
        for (i = oldlow; i <= index2high; i++)
            moveto(i + SIZE, i, true);


    }


    protected void MergeSort(int lowindex, int highindex) {
        HighlightRange(lowindex, highindex, true);
        waitscalefactor *= 2;
        repaintwait();
        waitscalefactor /= 2;

        if (lowindex < highindex) {
            MergeSort(lowindex, (int) (lowindex + highindex) / 2);
            MergeSort((int) (lowindex + highindex) / 2 + 1, highindex);
            HighlightRange(lowindex, highindex, true);
            repaintwait();
            Merge(lowindex, (lowindex + highindex) / 2, (lowindex + highindex) / 2 + 1,
                  highindex);
        }

    }

    protected void MergeSort() {
        int i;
        for (i = SIZE; i < 2 * SIZE; i++) {
            Xpos[i] = 20 + (i - SIZE) * 20;
            Ypos[i] = 440;
            ArrayFrame[i] = createRectangle("", Xpos[i], Ypos[i] + 2, 20, 20, false);
            SortIndex[i] = createLabel(String.valueOf(i - SIZE), Xpos[i], Ypos[i] + 22, false);
            SortIndex[i].setLabelVisible(visableIndices);
            SortIndex[i].setLabelColor(Color.BLUE);
            SortElements[i] = createLabel("", Xpos[i], Ypos[i]);
        }
        MergeSort(0, SIZE - 1);
        for (i = SIZE; i < 2 * SIZE; i++) {
            removeAny(ArrayFrame[i]);
            removeAny(SortIndex[i]);
            removeAny(SortIndex[i]);
            removeAny(SortIndex[i]);
            removeAny(SortElements[i]);
        }

    }


    protected void InsertionSortSkip(int inc, int offset) {
        int i,j;

        for (i = inc + offset; i < SIZE; i = i + inc) {
            moveto(i, SIZE, true);
            for (j = i - inc; lessthanBC(SIZE, j); j = j - inc)
                moveto(j, j + inc, true);
            moveto(SIZE, j + inc, true);
        }
    }


    public void ShellSort() {
        int inc;
        int offset;
        int k;
        Xpos[SIZE] = 435;
        Ypos[SIZE] = 440;

        ArrayFrame[SIZE] = createRectangle("", Xpos[SIZE], Ypos[SIZE] + 2, 20, 20, false);
        SortIndex[SIZE] = createLabel("Temp", Xpos[SIZE], Ypos[SIZE] + 22, false);
        SortIndex[SIZE].setLabelVisible(visableIndices);
        SortIndex[SIZE].setLabelColor(Color.BLUE);
        if (Graphical)
            GraphicalElements[SIZE] = BuildGrapicElement(0, Xpos[SIZE], Ypos[SIZE]);
        SortElements[SIZE] = createLabel("", Xpos[SIZE], Ypos[SIZE]);

        for (inc = SIZE/2; inc >=1; inc /= 2)
           for (offset = 0; offset < inc; offset++) {
               for (k=0;k<SIZE;k++)
                if ((k - offset) % inc == 0)
                    UnGrayElement(k,false);
               else
                    GrayElement(k,false);

               InsertionSortSkip(inc,offset);
           }



        removeAny(ArrayFrame[SIZE]);
        removeAny(SortIndex[SIZE]);
        if (Graphical)
            removeAny(GraphicalElements[SIZE]);

    }

    public void InsertionSort() {
        Xpos[SIZE] = 435;
        Ypos[SIZE] = 440;

        ArrayFrame[SIZE] = createRectangle("", Xpos[SIZE], Ypos[SIZE] + 2, 20, 20, false);
        SortIndex[SIZE] = createLabel("Temp", Xpos[SIZE], Ypos[SIZE] + 22, false);
        SortIndex[SIZE].setLabelVisible(visableIndices);
        SortIndex[SIZE].setLabelColor(Color.BLUE);
        if (Graphical)
            GraphicalElements[SIZE] = BuildGrapicElement(0, Xpos[SIZE], Ypos[SIZE]);
        SortElements[SIZE] = createLabel("", Xpos[SIZE], Ypos[SIZE]);

        InsertionSortSkip(1,0);
        removeAny(ArrayFrame[SIZE]);
        removeAny(SortIndex[SIZE]);
        if (Graphical)
            removeAny(GraphicalElements[SIZE]);

    }


    public void SelectionSort() {
        int i, j, smallestIndex;

        for (i = 0; i < SIZE - 1; i++) {
            smallestIndex = i;
            for (j = i + 1; j < SIZE; j++)
                if (lessthan(j, smallestIndex))
                    smallestIndex = j;
            if (smallestIndex != i)
                swap(i, smallestIndex);
        }
    }


    public void BubbleSort() {
        int i, j;

        for (i = 0; i < SIZE - 1; i++)
            for (j = 0; j < SIZE - 1 - i; j++)
                if (lessthan(j + 1, j))
                    swap(j + 1, j);


    }


    protected int partition(int pivot, int low, int high) {
        int i,ydelta;
        GElement pivotheight = null;

        if (visableIndices)
            ydelta = 35;
        else
            ydelta = 15;
        if (Graphical) {

            pivotheight =  createRectangle("", Xpos[SIZE/2], GetYPosGraphic(SortData[pivot]*2, Ypos[0]), SIZE*(Xpos[1]-Xpos[0]),1, false);
            pivotheight.setColor(Color.RED);
        }
        GElementArrow iarrow = createArrow(Xpos[low], Ypos[low] + 50+ydelta, Xpos[low], Ypos[low] + ydelta, 20, false);
        GElementArrow jarrow = createArrow(Xpos[high], Ypos[high] +50+ydelta, Xpos[high], Ypos[high] +ydelta, 20, false);

        GElementLabel ilabel = createLabel("i", Xpos[low], Ypos[low] + 50+ydelta+5);
        GElementLabel jlabel = createLabel("j", Xpos[high], Ypos[high] + 50+ydelta+5);
        Vector2D patharrow[];
        Vector2D pathlabel[];



        while (low <= high) {
            while (low <= high) {
                if (!lessthan(pivot, low)) {
/*                    patharrow = createPath(iarrow.getPosition(),
                                           new Vector2D(iarrow.getPositionX() + Xpos[low + 1] - Xpos[low],
                                                        iarrow.getPositionY() + Ypos[low + 1] - Ypos[low]), 10);
                    pathlabel = createPath(ilabel.getPosition(),
                                           new Vector2D(ilabel.getPositionX() + Xpos[low + 1] - Xpos[low],
                                                        ilabel.getPositionY() + Ypos[low + 1] - Ypos[low]), 10);
                                                        */
                    patharrow = createPath(iarrow.getPosition(),
                                           new Vector2D(iarrow.getPositionX() + 20,
                                                        iarrow.getPositionY()), 10);
                    pathlabel = createPath(ilabel.getPosition(),
                                           new Vector2D(ilabel.getPositionX()+20,
                                                        ilabel.getPositionY()), 10);

                    for (i = 0; i < 10; i++) {
                        iarrow.moveToPosition(patharrow[i]);
                        ilabel.moveToPosition(pathlabel[i]);
                        repaintwaitmin();

                    }
                    low++;
                } else
                    break;
            }

            while (high >= low) {
                if (!lessthan(high, pivot)) {
     /*               patharrow = createPath(jarrow.getPosition(),
                                           new Vector2D(jarrow.getPositionX() + Xpos[high-1] - Xpos[high],
                                                        jarrow.getPositionY() + Ypos[high-1] - Ypos[high]), 10);
                    pathlabel = createPath(jlabel.getPosition(),
                                           new Vector2D(jlabel.getPositionX() + Xpos[high-1] - Xpos[high],
                                                        jlabel.getPositionY() + Ypos[high-1] - Ypos[high]), 10);  */
                    patharrow = createPath(jarrow.getPosition(),
                                           new Vector2D(jarrow.getPositionX() -20,
                                                        jarrow.getPositionY() ), 10);
                    pathlabel = createPath(jlabel.getPosition(),
                                           new Vector2D(jlabel.getPositionX()-20 ,
                                                        jlabel.getPositionY()), 10);
                    high--;
                    for (i = 0; i < 10; i++) {
                        jarrow.moveToPosition(patharrow[i]);
                        jlabel.moveToPosition(pathlabel[i]);
                        repaintwaitmin();

                    }

                } else
                    break;
            }
            if (low < high)
                swap(low, high);
            repaintwait();
        }
        if (high != pivot)
            swap(high, pivot);


        removeAny(iarrow);
        removeAny(jarrow);
        removeAny(ilabel);
        removeAny(jlabel);
        if (Graphical)
            removeAny(pivotheight);

        return high;
    }


    protected void Quicksort(int lowindex, int highindex) {
        int midpoint;
        HighlightRange(lowindex, highindex, false);

        if (lowindex < highindex) {
            midpoint = partition(lowindex, lowindex + 1, highindex);
            Quicksort(lowindex, midpoint - 1);
            Quicksort(midpoint + 1, highindex);
        }
        HighlightRange(lowindex, highindex, false);

    }

    public void Quicksort() {
        Quicksort(0, SIZE - 1);


    }


    public void SwitchToGraphic() {
        Graphical = true;
        RecreateArray();
    }


}