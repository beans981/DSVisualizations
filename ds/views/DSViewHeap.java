package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;

public class DSViewHeap extends DSView {

    public final int INSERT = 1;
    public final int REMOVESMALLEST = 2;
    public final int BUILDHEAP = 3;
    public final int DELETEHEAP = 4;


    protected int HeapData[];
    protected int size;
    protected int Xpos[];
    protected int Ypos[];





    protected void CallFunction(int function) {
        switch (function) {
            case REMOVESMALLEST:
                removeSmallest();
                break;

            case BUILDHEAP:
                buildHeap();
                break;

                case DELETEHEAP:
                deleteHeap();
                break;

        }

    }

    protected void CallFunction(int function, Object param1) {
        switch (function) {
            case INSERT:
                Integer param = (Integer) param1;
                insert(param.intValue());
                break;
        }
    }




    public DSViewHeap() {
        // super(contained);
        HeapData = new int[50];
        size = 0;
        int i;
        sleeptime = 100;

        Xpos = new int[]{450, 250, 650, 150, 350, 550, 750, 100, 200, 300, 400, 500, 600, 700, 800,
                         75, 125, 175, 225, 275, 325, 375, 425, 475, 525, 575, 625, 675, 725, 775, 825};
        Ypos = new int[]{100, 170, 170, 240, 240, 240, 240, 310, 310, 310, 310, 310, 310, 310, 310,
                         380, 380, 380, 380, 380, 380, 380, 380, 380, 380, 380, 380, 380, 380, 380, 380};

        // Create a tree (the root element will be the first node added into this view)

        createRectangle("-inf", 50, 50, 25, 25, false);
        for (i = 1; i < 32; i++)
            createRectangle("", i * 25 + 50, 50, 25, 25, false);



        // Create two kinds of link between node 0-1 and 0-2

        //createLink(0, 2, LINK_SHAPE_ARC, DIRECTION_FREE, DIRECTION_FREE, "r2");
    }


    private void setLabel(int index, String label) {
        GElement treenode = (GElement) shapes.get(index + 32);
        GElement arraynode = (GElement) shapes.get(index + 1);
        treenode.setLabel(label);
        arraynode.setLabel(label);
    }

    private void setColor(int index, Color clr) {
        GElement treenode = (GElement) shapes.get(index + 32);
        GElement arraynode = (GElement) shapes.get(index + 1);
        treenode.setLabelColor(clr);
        arraynode.setLabelColor(clr);
    }





    private void insert(int insertElem) {
        int nextKey = insertElem;
        HeapData[size] = nextKey;
        if (size > 31) return;
        createCircle(String.valueOf(nextKey), Xpos[size], Ypos[size]);
        setLabel(size, String.valueOf(HeapData[size]));
        if (size != 0) {
            if (size % 2 == 0) {
                createLink((size - 1) / 2 + 32, size + 32, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 15);
            } else {
                createLink((size - 1) / 2 + 32, size + 32, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -15);
            }
        }
        repaintwait();

        SiftUp(size);
        repaint();
        size++;
    }





    public void removeSmallest() {

        if (size > 0) {
            size = size - 1;

            Vector2D startPos = ((GElement) shapes.get(32)).getPosition();
            GElement Removed = createLabel(String.valueOf(HeapData[0]), startPos.getX(), startPos.getY(),false);
            GElement RemLab = createLabel("Element Removed:", 100,100 , false);
            Vector2D path[] = createPath(startPos, new Vector2D(170, 100), 40);
            setLabel(0, "");            
            for (int i=0; i<40; i++) {
                Removed.setPosition(path[i]);
                repaintwaitmin();
            }


            HeapData[0] = HeapData[size];
            setLabel(0, "");
            repaintwait();
            swapLabels(0,size);
            if (size > 0) {
                removeLink(((size - 1) / 2 + 32), (size + 32));
            }
            removeLastShape();
            SiftDown(0);
            HoldoverGraphics.addElement(Removed);
            HoldoverGraphics.addElement(RemLab);
        }
        repaint();
    }

    private void SiftDown(int index) {
        int leftchild = index * 2 + 1;
        int rightchild = index * 2 + 2;
        int minchild;

        if (size <= leftchild)
            return;
        minchild = leftchild;
        if (rightchild < size) {
            setColor(leftchild, Color.red);
            setColor(rightchild, Color.red);
            repaintwait();
            if (HeapData[rightchild] < HeapData[leftchild])
                minchild = rightchild;
            setColor(leftchild, Color.black);
            setColor(rightchild, Color.black);
        }
        setColor(index, Color.red);
        setColor(minchild, Color.red);
        repaintwait();
        if (HeapData[minchild] < HeapData[index]) {
            int tmp = HeapData[index];
            HeapData[index] = HeapData[minchild];
            HeapData[minchild] = tmp;
            setColor(index, Color.black);
            setColor(minchild, Color.black);
            swapLabels(minchild, index);
            SiftDown(minchild);
        } else {
            setColor(index, Color.black);
            setColor(minchild, Color.black);
            repaintwait();
        }


    }





    protected void swapLabels(int index1, int index2) {

        int pathlength = 40;
        int i;

        Vector2D array1 = ((GElement) shapes.get(index1 + 1)).getPosition();
        Vector2D array2 = ((GElement) shapes.get(index2 + 1)).getPosition();
        Vector2D tree1 = ((GElement) shapes.get(index1 + 32)).getPosition();
        Vector2D tree2 = ((GElement) shapes.get(index2 + 32)).getPosition();
        Vector2D treepath1[] = createPath(tree1, tree2, pathlength);
        Vector2D treepath2[] = createPath(tree2, tree1, pathlength);
        Vector2D arraypath1[] = createPath(array1, array2, pathlength);
         Vector2D arraypath2[] = createPath(array2, array1, pathlength);



        String Label1 = ((GElement) shapes.get(index1 + 1)).getLabel();
        String Label2 = ((GElement) shapes.get(index2 + 1)).getLabel();
        setLabel(index1, "");
        setLabel(index2, "");


        GElement ArrayLabel1 = createLabel(Label1, array1.getX(), array1.getY(), false);
        GElement ArrayLabel2 = createLabel(Label2, array2.getX(), array2.getY(), false);
        GElement TreeLabel1 = createLabel(Label1, tree1.getX(), tree1.getY(), false);
        GElement TreeLabel2 = createLabel(Label2, tree2.getX(), tree2.getY(), false);

        for (i = 0; i < pathlength; i++) {
            TreeLabel1.setPosition(treepath1[i]);
            TreeLabel2.setPosition(treepath2[i]);
            ArrayLabel1.setPosition(arraypath1[i]);
            ArrayLabel2.setPosition(arraypath2[i]);
            repaintwaitmin();
        }

        getRootElement().removeElement(ArrayLabel1);
        getRootElement().removeElement(ArrayLabel2);
        getRootElement().removeElement(TreeLabel1);
        getRootElement().removeElement(TreeLabel2);


        setLabel(index1, Label2);
        setLabel(index2, Label1);
        repaint();

    }

    protected void SiftUp(int index) {
        int tmp;
        int parent_index = (index - 1) / 2;
        if (index != 0) {
            setColor(index, Color.red);
            setColor(parent_index, Color.red);
            repaintwait();
            if (HeapData[index] < HeapData[parent_index]) {
                tmp = HeapData[index];
                HeapData[index] = HeapData[parent_index];
                HeapData[parent_index] = tmp;
                setColor(index, Color.black);
                setColor(parent_index, Color.black);
                swapLabels(index, parent_index);
                SiftUp(parent_index);
            } else {
                setColor(index, Color.black);
                setColor(parent_index, Color.black);


            }


        }


    }

    public void deleteHeap() {
        int i;
        for (i = size - 1; i > 0; i--) {
            removeLink((i - 1) / 2 + 32, i + 32);
        }
        for (i = size - 1; i >= 0; i--) {
            setLabel(i, "");
            removeLastShape();
        }
        size = 0;
        repaint();
    }





    public void buildHeap() {
        deleteHeap();
        int i;
        for (i = 0; i < 31; i++) {
            HeapData[i] = 31 - i;
            createCircle(String.valueOf(HeapData[i]), Xpos[i], Ypos[i]);
            setLabel(i, String.valueOf(HeapData[i]));
            if (i != 0) {
                if (i % 2 == 0) {
                    createLink((i - 1) / 2 + 32, i + 32, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 15);
                } else {
                    createLink((i - 1) / 2 + 32, i + 32, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -15);
                }
            }
        }
        size = 31;
        repaintwait();
        for (i = 31 / 2; i >= 0; i--)
            SiftDown(i);


    }


}
