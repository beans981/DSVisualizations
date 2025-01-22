package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;

public class DSViewBinomialQueue extends DSView {

    public final static int INSERT = 1;
    public final static int REMOVE_SMALLEST = 2;
    public final int SHOW_HEIGHT = 4;
    public final int HIDE_HEIGHT = 5;

    public final static int VIEW_LOGICAL = 6;
    public final static int VIEW_INTERNAL = 7;


    protected final int DEGREE_OFFSET_VERTICAL = -25;
    protected final int DEGREE_OFFSET_HORIZONTAL = -20;

    protected final int INSERT_X = 50;
    protected final int INSERT_Y = 50;


    protected final int MERGE_DIVIDE_HEIGHT = 300;
    protected final int MERGE_DIVIDE_YPOS = 200;
    protected final int STARTING_Y = 100;
    protected final int STARTING_X = 50;
    protected final int NODE_WIDTH = 70;
    protected final int NODE_HEIGHT = 70;
    protected final int TREE_SEPARATION = 5;

    protected final int NUM_STEPS = 20;

    protected BinomialNode root;
    protected BinomialNode root2;
    protected int widthdelta = 60;
    protected int heightdelta = 50;
    protected final int MOVESTEPS = 40;
    protected GElement elementLabel;


    protected int viewingType = VIEW_LOGICAL;


    public DSViewBinomialQueue() {
        createLabel("", 0, 0, false);
        root = null;

    }


    protected void CallFunction(int function) {
        switch (function) {
            case VIEW_INTERNAL:
                switchToInternal();
                break;
            case VIEW_LOGICAL:
                switchToLogical();
                break;
            case REMOVE_SMALLEST:
                removeSmallest();

        }
    }

    private void removeSmallest() {
        if (root == null)
            return;
        GElement Deletelabel = createLabel("Element Removed:", 100, 40, false);
        GElement DeleteValLabel;
        BinomialNode tmp;
        BinomialNode prev = null;
        BinomialNode smallest = root;

        smallest.display.setColor(Color.RED);
        smallest.display.setLabelColor(Color.RED);
        for (tmp = root.rightsibling; tmp != null; tmp = tmp.rightsibling) {
            tmp.display.setColor(Color.RED);
            tmp.display.setLabelColor(Color.RED);
            repaintwait();
            if (tmp.data.compareTo(smallest.data) < 0) {
                smallest.display.setLabelColor(Color.BLACK);
                smallest.display.setColor(Color.BLACK);
                smallest = tmp;
            } else {
                tmp.display.setLabelColor(Color.BLACK);
                tmp.display.setColor(Color.BLACK);
            }
        }

        if (smallest == root) {
            root = root.rightsibling;
        } else {
            for (prev = root; prev.rightsibling != smallest; prev = prev.rightsibling) ;
            prev.rightsibling = prev.rightsibling.rightsibling;
        }

        DeleteValLabel = createLabel(smallest.display.getLabel(), smallest.display.getPosition());
        AnimatePath(DeleteValLabel,DeleteValLabel.getPosition(), new Vector2D(180,40),NUM_STEPS);
        removeAny(smallest.display);
        removeAny(smallest.rightsiblingGraphic);
        removeAny(smallest.degreelabel);
        removeAny(smallest.leftchildGraphic);
        root2 = reverse(smallest.leftchild);
        for (tmp = root2; tmp != null; tmp = tmp.rightsibling)
            tmp.parent = null;
        resetEdgeDisplays(root2);
        resetEdgeDisplays(root);
        merge();
        HoldoverGraphics.add(Deletelabel);
        HoldoverGraphics.add(DeleteValLabel);

    }

    private void switchToLogical() {
        viewingType = VIEW_LOGICAL;
        RemoveDegreeLabels(root);
        resetEdgeDisplays(root);
        repaint();

    }

    private void RemoveDegreeLabels(BinomialNode tree) {
        if (tree != null) {
            removeAny(tree.degreelabel);
            tree.degreelabel = null;
            RemoveDegreeLabels(tree.leftchild);
            RemoveDegreeLabels(tree.rightsibling);

        }
    }

    private void switchToInternal() {
        viewingType = VIEW_INTERNAL;
        AddDegreeLabels(root);
        resetEdgeDisplays(root);
        repaint();

    }

    private void AddDegreeLabels(BinomialNode tree) {
        if (tree != null) {
            removeAny(tree.degreelabel);
            tree.degreelabel = createLabel(Integer.toString(tree.degree),
                                           tree.display.getPositionX() + DEGREE_OFFSET_HORIZONTAL,
                                           tree.display.getPositionY() + DEGREE_OFFSET_VERTICAL, false);
            tree.degreelabel.setLabelColor(Color.BLUE);
            AddDegreeLabels(tree.leftchild);
            AddDegreeLabels(tree.rightsibling);

        }

    }


    protected void CallFunction(int function, Object param1) {
        switch (function) {
            case INSERT:
                insert(padString((String) param1, 4));
                break;

        }
    }


    private void insert(String insertElem) {

        GElementLabel insertLabel = createLabel("Inserting:",INSERT_X, INSERT_Y);

        if (root == null) {
            root = new BinomialNode(insertElem, createCircle(String.valueOf(insertElem), INSERT_X + 50, INSERT_Y), 0);
            if (viewingType == VIEW_INTERNAL) {
                root.degreelabel = createLabel("0", INSERT_X + DEGREE_OFFSET_HORIZONTAL+50, INSERT_Y + DEGREE_OFFSET_VERTICAL, false);
                root.degreelabel.setLabelColor(Color.BLUE);
            }
            resizetree();

        } else {
            root2 = new BinomialNode(insertElem, createCircle(String.valueOf(insertElem), INSERT_X + 50, INSERT_Y), 0);
            if (viewingType == VIEW_INTERNAL) {
                root2.degreelabel = createLabel("0", INSERT_X+50 + DEGREE_OFFSET_HORIZONTAL, INSERT_Y + DEGREE_OFFSET_VERTICAL, false);
                root2.degreelabel.setLabelColor(Color.BLUE);
            }
            merge();
        }
        removeAny(insertLabel);
    }


    public String padString(String s, int numchars) {

        try {
            int x = Integer.parseInt(s);
            if (x >= 0) {
                return toString(x, 4);
            } else {
                return s;

            }
        } catch (Exception e) {
            return s;
        }


    }

    private void merge() {
        int i;
        GElement MergeLabel = null;
        int leftsize = SetNewPositions(root, STARTING_X, 0);
        SetNewPositions(root2, leftsize+NODE_WIDTH, 0);
        SetPaths(root, NUM_STEPS);
        SetPaths(root2, NUM_STEPS);
        if (root != null) {
            for (i = 0; i < NUM_STEPS; i++) {
                MoveAlongPath(root, i);
                MoveAlongPath(root2, i);
                repaintwaitmin();
            }

        MergeLabel = createLabel("Merging Lists:",500,20,false);
        GElement divide  = createRectangle("",leftsize,MERGE_DIVIDE_YPOS ,2,MERGE_DIVIDE_HEIGHT );
        divide.setColor(Color.BLUE);
        repaintwait();
        removeAny(divide);
        } else  {
            root = root2;
            root2 = null;

        }

        while (root2 != null) {
            BinomialNode tmp = root2;
            root2 = root2.rightsibling;
            if (tmp.degree <= root.degree) {
                tmp.rightsibling = root;
                root = tmp;
            } else {
                BinomialNode tmp2 = root;
                while (tmp2.rightsibling != null && tmp2.rightsibling.degree < tmp.degree)
                    tmp2 = tmp2.rightsibling;
                tmp.rightsibling = tmp2.rightsibling;
                tmp2.rightsibling = tmp;
            }
        }
        resetEdgeDisplays(root);
        resizetree();
        CombineNodes();
        removeAny(MergeLabel);
    }


    private void resetEdgeDisplays(BinomialNode tree) {
        if (viewingType == VIEW_LOGICAL)
            resetEdgeDisplaysLogical(tree);
        else
            resetEdgeDisplaysInternal(tree);

    }


    private void resetEdgeDisplaysLogical(BinomialNode tree) {
        if (tree != null) {
            removeAny(tree.rightsiblingGraphic);
            removeAny(tree.parentGraphic);
            removeAny(tree.leftchildGraphic);

            if (tree.parent != null) {
                tree.parentGraphic = createLink(tree.parent.display, tree.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
            }

            resetEdgeDisplaysLogical(tree.leftchild);
            resetEdgeDisplaysLogical(tree.rightsibling);
        }
    }


    private void resetEdgeDisplaysInternal(BinomialNode tree) {
        if (tree != null) {
            removeAny(tree.rightsiblingGraphic);
            removeAny(tree.parentGraphic);
            removeAny(tree.leftchildGraphic);
            if (tree.degreelabel != null)
                tree.degreelabel.setLabel(Integer.toString(tree.degree));
            if (tree.rightsibling != null) {
                tree.rightsiblingGraphic = createLink(tree.display, tree.rightsibling.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
            }

            if (tree.parent != null) {
                tree.parentGraphic = createLink(tree.display, tree.parent.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -10);
            }

            if (tree.leftchild != null) {
                tree.leftchildGraphic = createLink(tree.display, tree.leftchild.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -10);
            }
            resetEdgeDisplaysInternal(tree.leftchild);
            resetEdgeDisplaysInternal(tree.rightsibling);
        }
    }


    private void CombineNodes() {
        BinomialNode tmp, tmp2;
        while ((root != null && root.rightsibling != null && root.degree == root.rightsibling.degree) &&
               (root.rightsibling.rightsibling == null || root.rightsibling.degree != root.rightsibling.rightsibling.degree))
        {
            repaintwait();
            if (root.data.compareTo(root.rightsibling.data) < 0) {
                tmp = root.rightsibling;
                root.rightsibling = tmp.rightsibling;
                tmp.rightsibling = root.leftchild;
                root.leftchild = tmp;
                tmp.parent = root;
                root.degree++;
                resetEdgeDisplays(root);
                resizetree();
            } else {
                tmp = root;
                root = root.rightsibling;
                tmp.rightsibling = root.leftchild;
                root.leftchild = tmp;
                tmp.parent = root;
                root.degree++;
                resetEdgeDisplays(root);
                resizetree();

            }
        }
        tmp2 = root;
        while (tmp2 != null && tmp2.rightsibling != null && tmp2.rightsibling.rightsibling != null) {
            if (tmp2.rightsibling.degree != tmp2.rightsibling.rightsibling.degree) {
                tmp2 = tmp2.rightsibling;
            } else if ((tmp2.rightsibling.rightsibling.rightsibling != null) &&
                       (tmp2.rightsibling.rightsibling.degree == tmp2.rightsibling.rightsibling.rightsibling.degree)) {
                tmp2 = tmp2.rightsibling;
            } else {
                repaintwait();
                if (tmp2.rightsibling.data.compareTo(tmp2.rightsibling.rightsibling.data) < 0) {
                    tmp = tmp2.rightsibling.rightsibling;
                    tmp2.rightsibling.rightsibling = tmp.rightsibling;
                    tmp.rightsibling = tmp2.rightsibling.leftchild;
                    tmp2.rightsibling.leftchild = tmp;
                    tmp.parent = tmp2.rightsibling;
                    tmp2.rightsibling.degree++;
                    resetEdgeDisplays(root);
                    resizetree();
                } else {
                    tmp = tmp2.rightsibling;
                    tmp2.rightsibling = tmp2.rightsibling.rightsibling;
                    tmp.rightsibling = tmp2.rightsibling.leftchild;
                    tmp2.rightsibling.leftchild = tmp;
                    tmp.parent = tmp2.rightsibling;
                    tmp2.rightsibling.degree++;
                    resetEdgeDisplays(root);
                    resizetree();
                }
            }
        }
    }


    public String toString(int num, int digits) {
        if (digits == 0)
            return "";
        return toString(num / 10, digits - 1) + String.valueOf(num % 10);

    }


    void MoveAlongPath(BinomialNode root, int step) {
        if (root != null) {
            root.display.setPosition(root.path[step]);
            if (root.degreelabel != null)
                root.degreelabel.setPosition(root.pathDegree[step]);
            MoveAlongPath(root.leftchild, step);
            MoveAlongPath(root.rightsibling, step);
        }
    }


    void SetPaths(BinomialNode root, int pathlength) {
        if (root != null) {
            root.path = createPath(root.display.getPosition(), new Vector2D(root.newX, root.newY), pathlength);
            if (root.degreelabel != null) {
                root.pathDegree = createPath(root.degreelabel.getPosition(), new Vector2D(root.newX + DEGREE_OFFSET_HORIZONTAL, root.newY + DEGREE_OFFSET_VERTICAL), pathlength);
            }
            SetPaths(root.leftchild, pathlength);
            SetPaths(root.rightsibling, pathlength);
        }
    }

    private int SetNewPositions(BinomialNode tree, int Xposition, int depth) {
        if (tree != null) {
            if (tree.degree == 0) {
                tree.newX = Xposition;
                tree.newY = STARTING_Y + depth * NODE_HEIGHT;
                return SetNewPositions(tree.rightsibling, Xposition + NODE_WIDTH, depth);
            } else if (tree.degree == 1) {
                tree.newX = Xposition;
                tree.newY = STARTING_Y + depth * NODE_HEIGHT;
                SetNewPositions(tree.leftchild, Xposition, depth + 1);
                return SetNewPositions(tree.rightsibling, Xposition + NODE_WIDTH, depth);

            } else {
                int width = (int) Math.pow(2, tree.degree - 1);
                tree.newX = Xposition + (width - 1) * NODE_WIDTH;
                tree.newY = STARTING_Y + depth * NODE_HEIGHT;
                SetNewPositions(tree.leftchild, Xposition, depth + 1);
                return SetNewPositions(tree.rightsibling, Xposition + width * NODE_WIDTH, depth);
            }
        } else {
            return Xposition;
        }

    }


    void resizetree() {
        int i;
        SetNewPositions(root, STARTING_X, 0);
        SetPaths(root, NUM_STEPS);
        for (i = 0; i < NUM_STEPS; i++) {
            MoveAlongPath(root, i);
            repaintwaitmin();
        }

    }


    public void delete(String removeitem) {
    }

    private BinomialNode reverse(BinomialNode tree) {
        BinomialNode newtree = null;
        BinomialNode tmp;
        while (tree != null) {
            tmp = tree;
            tree = tree.rightsibling;
            tmp.rightsibling = newtree;
            tmp.parent=null;
            newtree = tmp;
        }
        return newtree;
    }


    protected class BinomialNode {
        String data;
        BinomialNode leftchild;
        BinomialNode rightsibling;
        BinomialNode parent;
        GElement display;
        GElement parentGraphic;
        GElement leftchildGraphic;
        GElement rightsiblingGraphic;
        int degree;
        GElementLabel degreelabel;

        int newX;
        int newY;
        Vector2D path[];
        Vector2D pathDegree[];

        public BinomialNode(String elem) {
            data = elem;
            leftchild = null;
            rightsibling = null;
            parent = null;
            newY = -1;
        }

        public BinomialNode(String elem, GElement disp, int deg) {
            data = elem;
            leftchild = null;
            rightsibling = null;
            display = disp;
            parent = null;
            parentGraphic = null;
            degree = deg;
            newY = -1;
        }

    }


}

