package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeRectMultiLabel;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;

public class DSViewBTree extends DSView {

    public final int INSERT = 1;
    public final int FIND = 2;
    public final int DELETE = 3;

    protected final int STARTING_Y = 50;


    protected BTreeNode root;
    protected int widthdelta = 50;
    protected int HEIGHT_DELTA = 60;
    protected final int MOVESTEPS = 40;
    GElement elementLabel;

    protected static final int MIN_CHILDREN = 2;
    protected static final int MIN_KEYS = MIN_CHILDREN - 1;
    protected static final int MAX_CHILDREN = 4;
    protected static final int MAX_KEYS = MAX_CHILDREN - 1;
    protected static final int[] WIDTHS = {10, 50, 76, 102};
    protected static final int NODE_SPACING = 3;
    protected static final int NODE_HEIGHT = 30;
    protected static final int FIRST_ARROW_OFFSET = 5;
    protected static final int ARROW_Y_OFFSET = 3;

    protected GElementLabel InsertLabel;


    public DSViewBTree() {
        createLabel("", 0, 0, false);
        root = null;

    }


    protected void CallFunction(int function, Object param1) {
        switch (function) {
            case INSERT:
                insert(padString((String) param1, 2));
                break;

            case FIND:
                find(padString((String) param1, 2));
                break;
            case DELETE:
                delete(padString((String) param1, 2));
        }
    }


    private void insert(String insertElem) {
        if (root == null) {
            root = new BTreeNode(createMultiLabelRectange(insertElem, 500, STARTING_Y, WIDTHS[1], NODE_HEIGHT));
            root.numkeys = 1;
            root.keys[0] = insertElem;
        } else {
            GElementLabel insertcaption = createLabel("Inserting: ", 100, 40);
            InsertLabel = createLabel(insertElem, -10, -10);
            LineupHorizontal(insertcaption, InsertLabel);
            insert_preemptive(insertElem, root);
            removeAny(insertcaption);
        }
        resizetree();

    }

    protected BTreeNode split(BTreeNode tree) {
        GElement circle = createCircle("", tree.display.getPositionX(), tree.display.getPositionY(), false);
        circle.setColor(Color.RED);
        tree.display.setLabelColor(0, Color.GREEN);
        tree.display.setLabelColor(1, Color.BLUE);
        tree.display.setLabelColor(2, Color.YELLOW);
        repaintwait();
        removeAny(circle);
        BTreeNode parent = tree.parent;
        BTreeNode right = new BTreeNode(createMultiLabelRectange(tree.keys[2], tree.display.getPositionX(), tree.display.getPositionY(), WIDTHS[1], NODE_HEIGHT));
        right.keys[0] = tree.keys[2];
        right.display.setLabelColor(0, Color.YELLOW);
        right.numkeys = 1;
        right.display.setWidth(WIDTHS[1]);
        right.leaf = tree.leaf;
        if (!right.leaf) {
            right.children[0] = tree.children[2];
            right.children[1] = tree.children[3];
            tree.children[2].parent = right;
            tree.children[3].parent = right;
            right.edges[0] = createLink(right.display, tree.children[2].display, GLink.SHAPE_ARC, GElement.ANCHOR_LEFT, GElement.ANCHOR_TOP, "", 0);
            right.edges[1] = createLink(right.display, tree.children[3].display, GLink.SHAPE_ARC, GElement.ANCHOR_LEFT, GElement.ANCHOR_TOP, "", 0);
            right.edges[0].setSourceOffset(FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
            right.edges[1].setSourceOffset(WIDTHS[1] - FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
        }
        BTreeNode left = tree;
        left.display.setNumLabels(1);
        left.display.setWidth(WIDTHS[1]);
        left.numkeys = 1;
        if (!left.leaf) {
            removeLink(left.display, left.children[2].display);
            removeLink(left.display, left.children[3].display);
            left.edges[0].setSourceOffset(FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
            left.edges[1].setSourceOffset(WIDTHS[1] - FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
        }
        if (tree.parent == null) {
            root = new BTreeNode(createMultiLabelRectange(tree.keys[1], tree.display.getPositionX(), tree.display.getPositionY(), WIDTHS[1], NODE_HEIGHT));
            root.keys[0] = tree.keys[1];
            root.display.setLabelColor(0, Color.BLUE);
            root.children[0] = left;
            root.children[1] = right;
            root.numkeys = 1;
            left.parent = root;
            right.parent = root;
            root.edges[0] = createLink(root.display, left.display, GLink.SHAPE_ARC, GElement.ANCHOR_LEFT, GElement.ANCHOR_TOP, "", 0);
            root.edges[0].setSourceOffset(FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
            root.edges[1] = createLink(root.display, right.display, GLink.SHAPE_ARC, GElement.ANCHOR_LEFT, GElement.ANCHOR_TOP, "", 0);
            root.edges[1].setSourceOffset(WIDTHS[1] - FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
            root.leaf = false;
            resizetree();
            repaintwait();
            root.display.setLabelColor(0, Color.BLACK);
            root.children[0].display.setLabelColor(0, Color.BLACK);
            root.children[1].display.setLabelColor(0, Color.BLACK);
            return root;
        } else {
            int i, j;
            for (i = 0; parent.children[i] != tree; i++) ;
            for (j = parent.numkeys; j > i; j--) {
                parent.keys[j] = parent.keys[j - 1];
                parent.edges[j + 1] = parent.edges[j];
                parent.children[j + 1] = parent.children[j];
            }
            parent.edges[j + 1] = createLink(parent.display, right.display, GLink.SHAPE_ARC, GElement.ANCHOR_LEFT, GElement.ANCHOR_TOP, "", 0);
            parent.keys[j] = tree.keys[1];
            parent.children[j + 1] = right;
            parent.numkeys++;
            parent.display.insertLabel(tree.keys[1]);
            parent.display.setLabelColor(j, Color.BLUE);
            parent.display.setWidth(WIDTHS[parent.numkeys]);
            if (parent.numkeys == 2) {
                parent.edges[0].setSourceOffset(FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
                parent.edges[1].setSourceOffset(WIDTHS[2] / 2, ARROW_Y_OFFSET);
                parent.edges[2].setSourceOffset(WIDTHS[2] - FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
            } else if (parent.numkeys == 3) {
                parent.edges[0].setSourceOffset(FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);
                parent.edges[1].setSourceOffset(WIDTHS[3] / 3, ARROW_Y_OFFSET);
                parent.edges[2].setSourceOffset(2 * WIDTHS[3] / 3, ARROW_Y_OFFSET);
                parent.edges[3].setSourceOffset(WIDTHS[3] - FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);

            } else {
                /* ERROR! Shouldn't be here! */
            }
            left.parent = parent;
            right.parent = parent;
            resizetree();
            repaintwait();
            parent.children[j].display.setLabelColor(0, Color.BLACK);
            parent.children[j + 1].display.setLabelColor(0, Color.BLACK);
            parent.display.setLabelColor(Color.BLACK);
            return parent;
        }
    }


    protected void insert_preemptive_safe(String insertElem, BTreeNode tree) {
        int i;
        for (i = 0; i < tree.numkeys && insertElem.compareTo(tree.keys[i]) > 0; i++) {
        }
            tree.edges[i].setColor(Color.RED);
            repaintwait();
            tree.edges[i].setColor(Color.BLACK);

        insert_preemptive(insertElem, tree.children[i]);

    }

    protected void insert_preemptive(String insertElem, BTreeNode tree) {
        int i;
        tree.display.setColor(Color.RED);
        repaintwait();
        if (tree.numkeys == MAX_KEYS) {
            tree.display.setColor(Color.BLACK);
            BTreeNode newroot = split(tree);
            insert_preemptive_safe(insertElem, newroot);
        } else {
            if (tree.leaf) {
                tree.display.setLabelColor(0, Color.BLACK);
                tree.display.setNumLabels(tree.numkeys + 1);
                tree.display.setLabel(tree.numkeys, "");
                tree.numkeys++;
                tree.display.setWidth(WIDTHS[tree.numkeys]);
                resizetree();
                tree.display.setLabelColor(tree.numkeys - 2, Color.RED);
                repaintwait();
                tree.display.setLabelColor(tree.numkeys - 2, Color.BLACK);

                for (i = tree.numkeys - 1; i > 0 && tree.keys[i - 1].compareTo(insertElem) > 0; i--) {
                    tree.display.setLabel(i, tree.display.getLabel(i - 1));
                    tree.display.setLabel(i - 1, "");
                    tree.keys[i] = tree.keys[i - 1];
                    if (i - 2 >= 0) {
                        tree.display.setLabelColor(i - 2, Color.RED);
                        repaintwait();
                        tree.display.setLabelColor(i - 2, Color.BLACK);
                    }
                }
                tree.keys[i] = insertElem;
                Vector2D finalPos;
                finalPos = new Vector2D(tree.display.getPositionX() - tree.display.getWidth() / 2 +
                                        i * tree.display.getWidth() / tree.numkeys, tree.display.getPositionY());
                AnimatePath(InsertLabel, InsertLabel.getPosition(), finalPos, 20);
                removeAny(InsertLabel);
                tree.display.setLabel(i, insertElem);
                repaintwait();
                tree.display.setColor(Color.BLACK);
            } else {
                tree.display.setLabelColor(0, Color.RED);
                repaintwait();
                tree.display.setLabelColor(0, Color.BLACK);
                for (i = 0; i < tree.numkeys && insertElem.compareTo(tree.keys[i]) > 0; i++) {
                    if (i + 1 < tree.numkeys) {
                        tree.display.setLabelColor(i + 1, Color.RED);
                        repaintwait();
                        tree.display.setLabelColor(i + 1, Color.BLACK);
                    }
                }
                tree.display.setColor(Color.BLACK);
                tree.edges[i].setColor(Color.RED);
                repaintwait();
                tree.edges[i].setColor(Color.BLACK);
                insert_preemptive(insertElem, tree.children[i]);

            }

        }
    }

    private void insert(String insertElem, BTreeNode tree) {
        int i;
        tree.display.setColor(Color.RED);
        repaintwait();

        if (tree.leaf) {
            tree.display.setLabelColor(0, Color.BLACK);
            tree.display.setNumLabels(tree.numkeys + 1);
            tree.display.setLabel(tree.numkeys, "");
            tree.numkeys++;
            tree.display.setWidth(WIDTHS[tree.numkeys]);
            resizetree();
            tree.display.setLabelColor(tree.numkeys - 2, Color.RED);
            repaintwait();
            tree.display.setLabelColor(tree.numkeys - 2, Color.BLACK);

            for (i = tree.numkeys - 1; i > 0 && tree.keys[i - 1].compareTo(insertElem) > 0; i--) {
                tree.display.setLabel(i, tree.display.getLabel(i - 1));
                tree.display.setLabel(i - 1, "");
                tree.keys[i] = tree.keys[i - 1];
                if (i - 2 >= 0) {
                    tree.display.setLabelColor(i - 2, Color.RED);
                    repaintwait();
                    tree.display.setLabelColor(i - 2, Color.BLACK);
                }
            }
            tree.keys[i] = insertElem;
            Vector2D finalPos;
            finalPos = new Vector2D(tree.display.getPositionX() - tree.display.getWidth() / 2 +
                                    i * tree.display.getWidth() / tree.numkeys, tree.display.getPositionY());
            AnimatePath(InsertLabel, InsertLabel.getPosition(), finalPos, 20);
            removeAny(InsertLabel);
            tree.display.setLabel(i, insertElem);
            repaintwait();
            tree.display.setColor(Color.BLACK);
        } else {
            tree.display.setLabelColor(0, Color.RED);
            repaintwait();
            tree.display.setLabelColor(0, Color.BLACK);
            for (i = 0; i < tree.numkeys && insertElem.compareTo(tree.keys[i]) > 0; i++) {
                if (i + 1 < tree.numkeys) {
                    tree.display.setLabelColor(i + 1, Color.RED);
                    repaintwait();
                    tree.display.setLabelColor(i + 1, Color.BLACK);
                }
            }
            tree.display.setColor(Color.BLACK);
            tree.edges[i].setColor(Color.RED);
            repaintwait();
            tree.edges[i].setColor(Color.BLACK);
            insert_preemptive(insertElem, tree.children[i]);

        }


    }


    public String padString(String s, int numchars) {

        try {
            int x = Integer.parseInt(s);
            if (x >= 0) {
                return toString(x, 3);
            } else {
                return s;

            }
        } catch (Exception e) {
            return s;
        }


    }


    public String toString(int num, int digits) {
        if (digits == 0)
            return "";
        return toString(num / 10, digits - 1) + String.valueOf(num % 10);

    }


    private void resizetree() {
        int i;
        int startingpoint = 500;
        ResetWidths(root);
        if (SetNewPositions(root, startingpoint, 0)) {
            SetPaths(root, MOVESTEPS / 2);
            for (i = 0; i < MOVESTEPS / 2; i++) {
                MoveAlongPath(root, i);
                repaintwaitmin();
            }
            repaint();
        }
    }

    void MoveAlongPath(BTreeNode root, int step) {
        if (root != null) {
            root.display.moveToPosition(root.path[step]);
            if (!root.leaf)
                for (int i = 0; i < root.numkeys + 1; i++)
                    MoveAlongPath(root.children[i], step);
        }
    }


    void SetPaths(BTreeNode root, int pathlength) {
        if (root != null) {
            root.path = createPath(root.display.getPosition(), new Vector2D(root.newX, root.newY), pathlength);
            if (!root.leaf)
                for (int i = 0; i < root.numkeys + 1; i++)
                    SetPaths(root.children[i], pathlength);
        }
    }


    private boolean SetNewPositions(BTreeNode tree, int Xposition, int depth) {
        if (tree != null) {
            tree.newY = depth * HEIGHT_DELTA + STARTING_Y;
            tree.newX = Xposition;
            boolean changed = false;
            if (!tree.leaf) {
                int leftedge = Xposition - tree.width / 2;
                if (tree.numkeys == 1) {
                    changed |= SetNewPositions(tree.children[0], leftedge + tree.widths[0] / 2, depth + 1);
                    changed |= SetNewPositions(tree.children[1], leftedge + tree.widths[0] + tree.widths[1] / 2, depth + 1);
                    tree.newX = leftedge + tree.widths[0];
                } else if (tree.numkeys == 2) {
                    changed |= SetNewPositions(tree.children[0], leftedge + tree.widths[0] / 2, depth + 1);
                    changed |= SetNewPositions(tree.children[1], leftedge + tree.widths[0] + tree.widths[1] / 2, depth + 1);
                    changed |= SetNewPositions(tree.children[2], leftedge + tree.widths[0] + tree.widths[1] + tree.widths[2] / 2, depth + 1);
                    tree.newX = leftedge + tree.widths[0] + tree.widths[1] / 2;
                } else if (tree.numkeys == 3) {
                    changed |= SetNewPositions(tree.children[0], leftedge + tree.widths[0] / 2, depth + 1);
                    changed |= SetNewPositions(tree.children[1], leftedge + tree.widths[0] + tree.widths[1] / 2, depth + 1);
                    changed |= SetNewPositions(tree.children[2], leftedge + tree.widths[0] + tree.widths[1] + tree.widths[2] / 2, depth + 1);
                    changed |= SetNewPositions(tree.children[3], leftedge + tree.widths[0] + tree.widths[1] + tree.widths[2] + tree.widths[3] / 2, depth + 1);
                    tree.newX = leftedge + tree.widths[0] + tree.widths[1];
                } else if (tree.numkeys==0) {
                    changed |= SetNewPositions(tree.children[0],Xposition,depth+1);
                    tree.newX = Xposition;
                }
            }
            changed |= tree.newY != tree.display.getPositionY();
            changed |= tree.newX != tree.display.getPositionX();
            return changed;
        }
        return false;
    }


    private int ResetWidths(BTreeNode root) {
        int i;
        int width;
        if (root == null)
            return 0;
        if (root.leaf) {
            for (i = 0; i < root.numkeys + 1; i++)
                root.widths[i] = 0;
            root.width = WIDTHS[root.numkeys] + NODE_SPACING;
            return WIDTHS[root.numkeys] + NODE_SPACING;

        }
        width = 0;
        for (i = 0; i < root.numkeys + 1; i++) {
            root.widths[i] = ResetWidths(root.children[i]);
            width += root.widths[i];
        }
        width = Math.max(width, WIDTHS[root.numkeys] + NODE_SPACING);
        root.width = width;
        return width;
    }


    public void delete(String removeitem) {

        GElementLabel deletecaption = createLabel("Deleting: ", 100, 40);
        GElementLabel deleteLabel = createLabel(removeitem, -10, -10);
        LineupHorizontal(deletecaption, deleteLabel);


        delete_preemptive_safe(removeitem,root);
        if (root.numkeys == 0) {
            removeAny(root.edges[0]);
            removeAny(root.display);
            root = root.children[0];
            if (root != null)
                root.parent = null;
            resizetree();
        }
        removeAny(deletecaption);
        removeAny(deleteLabel);

        /* delete_preemptive(removeitem, root); */
    }


    private BTreeNode combine(BTreeNode parent, int childindex) {
        if (parent.leaf)
            return parent;
        BTreeNode child = parent.children[childindex];
        if (child.numkeys > MIN_KEYS)
            return child;
        if (childindex == 0) {
            if (parent.children[1].numkeys > MIN_KEYS) {
                return StealRight(parent,0);
            } else {
                return CombineRight(parent,0);
            }
        } else if (childindex == parent.numkeys) {
            if (parent.children[childindex-1].numkeys > MIN_KEYS) {
                return StealLeft(parent,childindex);
            } else {
                return CombineLeft(parent,childindex);
            }
        } else if (parent.children[childindex-1].numkeys > MIN_KEYS) {
           return StealLeft(parent,childindex);
        }  else if (parent.children[childindex+1].numkeys > MIN_KEYS) {
           return StealRight(parent,childindex);
        } else {
           return CombineRight(parent,childindex);
        }

    }



    private void resetEdges(BTreeNode t) {
        if (!t.leaf) {
            t.edges[0].setSource(t.display);
            t.edges[0].setSourceOffset(FIRST_ARROW_OFFSET,ARROW_Y_OFFSET);
            for (int i=1; i< t.numkeys;i++) {
                t.edges[i].setSource(t.display);
                t.edges[i].setSourceOffset(i*WIDTHS[t.numkeys]/t.numkeys,ARROW_Y_OFFSET);
            }
            t.edges[t.numkeys].setSource(t.display);
            t.edges[t.numkeys].setSourceOffset(WIDTHS[t.numkeys] - FIRST_ARROW_OFFSET, ARROW_Y_OFFSET);

        }
    }

    private BTreeNode CombineLeft(BTreeNode parent, int childIndex) {
        return CombineRight(parent,childIndex-1);
    }

    private BTreeNode StealLeft(BTreeNode parent, int childIndex) {
        int i;
        BTreeNode child = parent.children[childIndex];
        BTreeNode leftsib = parent.children[childIndex-1];


        child.display.setNumLabels(child.numkeys+1);
        for (i=child.numkeys; i>=0; i--) {
            child.children[i+1] = child.children[i];
            child.keys[i+1] = child.keys[i];
            child.display.setLabel(i+1,child.keys[i+1]);
            child.edges[i+1] = child.edges[i];
        }
        child.keys[0] = parent.keys[childIndex-1];
        child.display.setLabel(0,"");
        parent.keys[childIndex-1] = leftsib.keys[leftsib.numkeys-1];
        parent.display.setLabel(childIndex-1,"");
        child.children[0] = leftsib.children[leftsib.numkeys];
        if (leftsib.children[leftsib.numkeys] != null)
            leftsib.children[leftsib.numkeys].parent = child;
        child.edges[0] = leftsib.edges[leftsib.numkeys];
        leftsib.numkeys--;
        child.numkeys++;
        leftsib.display.setLabel(leftsib.numkeys,"");
        GElementLabel l1 = createLabel(parent.keys[childIndex-1],leftsib.display.getLabelPosition(leftsib.numkeys));
        GElementLabel l2 = createLabel(child.keys[0],parent.display.getLabelPosition(childIndex-1));
        AnimatePath(l1,l1.getPosition(),  l2.getPosition(),
                    l2, l2.getPosition(),child.display.getLabelPosition(0),40);
        removeAny(l1);
        removeAny(l2);

        child.display.setWidth(WIDTHS[child.numkeys]);

        child.display.setLabel(0,child.keys[0]);

        parent.display.setLabel(childIndex-1,parent.keys[childIndex-1]);

        leftsib.display.setNumLabels(leftsib.numkeys);
        leftsib.display.setWidth(WIDTHS[leftsib.numkeys]);

        resetEdges(child);
        resetEdges(leftsib);


        resizetree();
        return child;

    }

    private BTreeNode CombineRight(BTreeNode parent, int childIndex) {
        BTreeNode child = parent.children[childIndex];
        BTreeNode rightsib = parent.children[childIndex+1];
        int i,j;


        parent.display.setLabel(childIndex,"");
        GElementLabel l = createLabel(parent.keys[childIndex],parent.display.getLabelPosition(childIndex));


        double middle = (child.display.getPositionX() + rightsib.display.getPositionX()) / 2;
        Vector2D leftpos = new Vector2D(middle - child.display.getWidth()/2, child.display.getPositionY());
        Vector2D rightpos = new Vector2D(middle + rightsib.display.getWidth()/2, rightsib.display.getPositionY());



        AnimatePath(l,l.getPosition(),new Vector2D(middle,child.display.getPositionY()),
                    child.display, child.display.getPosition(), leftpos,
                    rightsib.display, rightsib.display.getPosition(), rightpos,40);
        removeAny(l);

        child.display.setNumLabels(child.numkeys+rightsib.numkeys+1);
        child.display.setWidth(WIDTHS[child.numkeys+rightsib.numkeys+1]);


        j = child.numkeys;
        child.keys[j++] = parent.keys[childIndex];
        child.display.setLabel(j-1,child.keys[j-1]);

        for (i=0; i<rightsib.numkeys;i++,j++) {
            child.keys[j] = rightsib.keys[i];
            child.display.setLabel(j,child.keys[j]);
            child.children[j] = rightsib.children[i];
            if (rightsib.children[i] != null)
                rightsib.children[i].parent = child;
            child.edges[j] = rightsib.edges[i];
        }
        child.children[j] = rightsib.children[i];
        if (rightsib.children[i] != null)
            rightsib.children[i].parent = child;
        child.edges[j] = rightsib.edges[i];

        child.numkeys = child.numkeys + rightsib.numkeys+1;
        child.children[child.numkeys] = rightsib.children[rightsib.numkeys];
        if (rightsib.children[rightsib.numkeys] != null)
            rightsib.children[rightsib.numkeys].parent = child;
        child.edges[child.numkeys] = rightsib.edges[rightsib.numkeys];

        removeAny(parent.edges[childIndex+1]);
        removeAny(rightsib.display);
        for(i=childIndex+1; i<parent.numkeys; i++) {
            parent.keys[i-1] = parent.keys[i];
            parent.display.setLabel(i-1,parent.keys[i-1]);
            parent.children[i] = parent.children[i+1];
            parent.edges[i] = parent.edges[i+1];
        }
        parent.numkeys--;
        parent.display.setNumLabels(parent.numkeys);
        parent.display.setWidth(WIDTHS[parent.numkeys]);
        resetEdges(parent);
        resetEdges(child);
        resizetree();
        return child;
    }

    private BTreeNode StealRight(BTreeNode parent, int childIndex) {
       int i;
        BTreeNode child = parent.children[childIndex];
        BTreeNode rightsib = parent.children[childIndex+1];


        child.numkeys++;


        child.display.setNumLabels(child.numkeys);



        rightsib.display.setLabel(0,"");
        parent.display.setLabel(childIndex,"");
        child.display.setNumLabels(child.numkeys);
        child.display.setWidth(WIDTHS[child.numkeys]);

        GElementLabel l1 = createLabel(rightsib.keys[0],rightsib.display.getLabelPosition(0));
        GElementLabel l2 = createLabel(parent.keys[childIndex],parent.display.getLabelPosition(childIndex));
        AnimatePath(l1,l1.getPosition(),  l2.getPosition(),
                    l2, l2.getPosition(),child.display.getLabelPosition(child.numkeys-1),40);

        removeAny(l1);
        removeAny(l2);

        rightsib.numkeys--;

        child.keys[child.numkeys-1] = parent.keys[childIndex];
        parent.keys[childIndex] = rightsib.keys[0];
        child.edges[child.numkeys] = rightsib.edges[0];
        child.children[child.numkeys] = rightsib.children[0];
        if (rightsib.children[0] != null)
            rightsib.children[0].parent = child;

        for (i=1; i <= rightsib.numkeys; i++) {
            rightsib.children[i-1] = rightsib.children[i];
            rightsib.keys[i-1] = rightsib.keys[i];
            rightsib.display.setLabel(i-1,rightsib.keys[i-1]);
            rightsib.edges[i-1] = rightsib.edges[i];
        }
        rightsib.children[rightsib.numkeys] = rightsib.children[rightsib.numkeys+1];
        rightsib.edges[rightsib.numkeys] = rightsib.edges[rightsib.numkeys+1];
        rightsib.display.setNumLabels(rightsib.numkeys);

        child.display.setLabel(child.numkeys-1,child.keys[child.numkeys-1]);
        parent.display.setLabel(childIndex,parent.keys[childIndex]);

        rightsib.display.setWidth(WIDTHS[rightsib.numkeys]);

        rightsib.display.setWidth(WIDTHS[rightsib.numkeys]);
        resetEdges(child);
        resetEdges(rightsib);

        resizetree();
        return child;
    }

    private int findChildIndex(BTreeNode tree,BTreeNode parent) {
        int i;
        for (i=0; parent.children[i] != tree; i++);
        return i;
    }

    private void delete_preemptive_safe(String removeitem, BTreeNode newroot) {
        int i,j;

        newroot.display.setColor(Color.RED);
        repaintwait();
        if (newroot.leaf) {
            for (i=0; i<newroot.numkeys && removeitem.compareTo(newroot.keys[i]) > 0; i++) {
                newroot.display.setLabelColor(i,Color.RED);
                repaintwait();
                newroot.display.setLabelColor(i,Color.BLACK);
            }
            if (i < newroot.numkeys && removeitem.compareTo(newroot.keys[i]) == 0) {
                newroot.display.setLabelColor(i,Color.RED);
                repaintwait();
                newroot.display.setLabelColor(i,Color.BLACK);
               for (;i<newroot.numkeys-1;i++) {
                   newroot.display.setLabel(i,"");
                   newroot.display.setLabel(i+1,"");
                   GElementLabel l = createLabel(newroot.keys[i+1],newroot.display.getLabelPosition(i+1));
                   AnimatePath(l, l.getPosition(), newroot.display.getLabelPosition(i),10);
                   removeAny(l);
                   newroot.keys[i] = newroot.keys[i+1];
                   newroot.display.setLabel(i,newroot.keys[i]);
               }
               newroot.numkeys--;
                newroot.display.setNumLabels(newroot.numkeys);
                newroot.display.setWidth(WIDTHS[newroot.numkeys]);
                resetEdges(newroot);
            } else {
                System.out.println("Didn't find it!");
                if (i >= newroot.numkeys) {
                    System.out.println("Went past end!");
                } else {
                    System.out.println("Comparing "+ newroot.keys[i] + " and " +removeitem);
                }
            }
            newroot.display.setColor(Color.BLACK);
            return;
        }


        for (i=0; i<newroot.numkeys && removeitem.compareTo(newroot.keys[i]) > 0; i++) {
            newroot.display.setLabelColor(i,Color.RED);
            repaintwait();
            newroot.display.setLabelColor(i,Color.BLACK);
        }
        if (i == newroot.numkeys) {
            /* past last key */
            newroot.display.setColor(Color.BLACK);
            newroot.edges[i].setColor(Color.RED);
            repaintwait();
            newroot.edges[i].setColor(Color.BLACK);
            delete_preemptive_safe(removeitem,combine(newroot,i));
        } else if (removeitem.compareTo(newroot.keys[i]) == 0) {
            /* Key found! */

            if (newroot.children[i].numkeys == MIN_KEYS && newroot.children[i+1].numkeys == MIN_KEYS) {
                newroot.display.setColor(Color.BLACK);
                BTreeNode leftchild = newroot.children[i];
                BTreeNode rightchild = newroot.children[i+1];
                GElementLabel l = createLabel(removeitem,newroot.display.getLabelPosition(i));
                l.setLabelColor(Color.RED);

                newroot.display.setLabel(i,"");

                double middle = (leftchild.display.getPositionX() + rightchild.display.getPositionX()) / 2;
                Vector2D leftpos = new Vector2D(middle - leftchild.display.getWidth()/2, leftchild.display.getPositionY());
                Vector2D rightpos = new Vector2D(middle + rightchild.display.getWidth()/2, rightchild.display.getPositionY());



                AnimatePath(l,l.getPosition(),new Vector2D(middle,leftchild.display.getPositionY()),
                            leftchild.display, leftchild.display.getPosition(), leftpos,
                            rightchild.display, rightchild.display.getPosition(), rightpos,40);
                removeAny(l);





                leftchild.keys[MIN_KEYS] = removeitem;
                leftchild.numkeys = 2*MIN_KEYS+1;
                leftchild.display.setNumLabels(2*MIN_KEYS+1);
                leftchild.display.setLabel(MIN_KEYS,removeitem);
                leftchild.display.setWidth(WIDTHS[2*MIN_KEYS+1]);
                for (j=0; j < MIN_KEYS; j++) {
                    leftchild.keys[MIN_KEYS+1+j] = rightchild.keys[j];
                    leftchild.display.setLabel(MIN_KEYS+1+j,rightchild.keys[j]);
                }
                for (j=0; j<=MIN_KEYS; j++) {
                    leftchild.edges[MIN_KEYS+1+j] = rightchild.edges[j];
                    leftchild.children[MIN_KEYS+1+j] = rightchild.children[j];
                    if (rightchild.children[j] != null)
                        rightchild.children[j].parent = leftchild;
                }
                removeAny(newroot.edges[i+1]);
                for (j=i+2; j<=newroot.numkeys;j++) {
                    newroot.edges[j-1] = newroot.edges[j];
                    newroot.children[j-1] = newroot.children[j];
                }
                for (j=i+1; j<newroot.numkeys;j++) {
                    newroot.keys[j-1] = newroot.keys[j];
                    newroot.display.setLabel(j-1,newroot.keys[j-1]);
                }
                removeAny(rightchild.display);
                newroot.numkeys--;
                newroot.display.setNumLabels(newroot.numkeys);
                newroot.display.setWidth(WIDTHS[newroot.numkeys]);
                resetEdges(newroot);
                resetEdges(leftchild);
                resizetree();

/*                if (newroot == root && root.numkeys == 0) {
                    removeAny(root.display);
                    removeAny(root.edges[0]);
                    root = leftchild;
                    leftchild.parent = null;
                } */
                delete_preemptive_safe(removeitem,leftchild);




                /* Merge children around key */
            } else if (newroot.children[i].numkeys == MIN_KEYS) {
                newroot.display.setLabelColor(i,Color.RED);
                repaintwait();
                BTreeNode smallest = findSmallest(newroot.children[i+1]);
                newroot.keys[i] = smallest.keys[0];
                newroot.display.setLabelColor(i,Color.BLACK);
                newroot.display.setLabel(i,"");
                smallest.display.setLabel(0,"");
                GElementLabel l = createLabel(smallest.keys[0],smallest.display.getLabelPosition(0));
                AnimatePath(l,l.getPosition(), newroot.display.getLabelPosition(i),30);
                removeAny(l);
                smallest.display.setColor(Color.BLACK);



                newroot.display.setLabelColor(i,Color.BLACK);
                newroot.display.setLabel(i,newroot.keys[i]);
                for (i=1; i< smallest.numkeys;i++) {
                    smallest.keys[i-1] = smallest.keys[i];
                    smallest.display.setLabel(i-1,smallest.keys[i-1]);
                }
                smallest.numkeys--;
                smallest.display.setNumLabels(smallest.numkeys);
                smallest.display.setWidth(WIDTHS[smallest.numkeys]);
            } else {
                newroot.display.setLabelColor(i,Color.RED);
                repaintwait();

                BTreeNode largest = findLargest(newroot.children[i]);
                newroot.keys[i] = largest.keys[largest.numkeys-1];

                newroot.display.setLabel(i,"");
                newroot.display.setLabelColor(i,Color.BLACK);
                largest.display.setLabel(largest.numkeys-1,"");
                GElementLabel l = createLabel(largest.keys[largest.numkeys-1],largest.display.getLabelPosition(largest.numkeys-1));
                AnimatePath(l,l.getPosition(), newroot.display.getLabelPosition(i),30);
                removeAny(l);
                largest.display.setColor(Color.BLACK);




                newroot.display.setLabel(i,largest.keys[largest.numkeys-1]);
                largest.numkeys--;
                largest.display.setNumLabels(largest.numkeys);
                largest.display.setWidth(WIDTHS[largest.numkeys]);
            }
            newroot.display.setColor(Color.BLACK);
        } else {
            /* Before next key */
            newroot.display.setColor(Color.BLACK);
            newroot.edges[i].setColor(Color.RED);
            repaintwait();
            newroot.edges[i].setColor(Color.BLACK);
            delete_preemptive_safe(removeitem,combine(newroot,i));
        }
    }



    private BTreeNode findLargest(BTreeNode tree) {
        tree.display.setColor(Color.BLUE);
        repaintwait();
        if (tree.leaf) {
            return tree;
        }
        tree.display.setColor(Color.BLACK);
        tree.edges[tree.numkeys].setColor(Color.BLUE);
        repaintwait();
        tree.edges[tree.numkeys].setColor(Color.BLACK);
        return findLargest(combine(tree,tree.numkeys));
    }

    private BTreeNode findSmallest(BTreeNode tree) {
        tree.display.setColor(Color.BLUE);
        repaintwait();
        if (tree.leaf) {
            return tree;
        }
        tree.display.setColor(Color.BLACK);
        tree.edges[0].setColor(Color.BLUE);
        repaintwait();
        tree.edges[0].setColor(Color.BLACK);
        return findSmallest(combine(tree,0));
    }





    public void find(String finditem) {

        GElement findlabel = createLabel("Finding:", 100, 40, false);
        elementLabel = createLabel(String.valueOf(finditem), 170, 40, false);
        elementLabel.setLabelColor(Color.RED);
        GElement found = find(finditem, root);
        if (found != null) {
            findlabel.setLabel("Found:");
            repaintwait();
            found.setLabelColor(Color.BLACK);
            elementLabel.setLabelColor(Color.BLACK);
        } else {
            findlabel.setLabel("Not Found:");
            findlabel.setColor(Color.BLACK);
            elementLabel.setLabelColor(Color.BLACK);
        }
        HoldoverGraphics.addElement(findlabel);
        HoldoverGraphics.addElement(elementLabel);
    }

    public GElement find(String finditem, BTreeNode tree) {
        int i;
        if (tree == null)
            return null;

        tree.display.setColor(Color.RED);
        tree.display.setLabelColor(0, Color.RED);
        repaintwait();
        for (i = 0; i < tree.numkeys && tree.keys[i].compareTo(finditem) < 0; i++) {
            tree.display.setLabelColor(i, Color.BLACK);
            if (i < tree.numkeys - 1) {
                tree.display.setLabelColor(i + 1, Color.RED);
                repaintwait();
            }

        }
        tree.display.setColor(Color.BLACK);
        if (i < tree.numkeys && tree.keys[i].compareTo(finditem) == 0)
            return tree.display;
        if (i < tree.numkeys)
            tree.display.setLabelColor(i, Color.BLACK);
        if (tree.leaf)
            return null;
        tree.edges[i].setColor(Color.RED);
        repaintwait();
        tree.edges[i].setColor(Color.BLACK);
        return find(finditem, tree.children[i]);

    }


    protected class BTreeNode {
        BTreeNode[] children;
        GLink[] edges;
        int numkeys;
        BTreeNode parent;
        String keys[];
        boolean leaf;

        DSShapeRectMultiLabel display;
        int widths[];
        int width;
        int newX;
        int newY;
        Vector2D path[];

        public BTreeNode() {
            int i;
            numkeys = 0;
            children = new BTreeNode[MAX_CHILDREN];
            for (i = 0; i < MAX_CHILDREN; i++)
                children[i] = null;
            keys = new String[MAX_KEYS];
            for (i = 0; i < MAX_KEYS; i++)
                keys[i] = null;
            parent = null;
            newY = -1;
            leaf = true;
            edges = new GLink[MAX_CHILDREN];
            widths = new int[MAX_CHILDREN];
        }

        public BTreeNode(DSShapeRectMultiLabel disp) {
            int i;
            numkeys = 0;
            children = new BTreeNode[MAX_CHILDREN];
            for (i = 0; i < MAX_CHILDREN; i++)
                children[i] = null;
            keys = new String[MAX_KEYS];
            for (i = 0; i < MAX_KEYS; i++)
                keys[i] = null;

            parent = null;
            newY = -1;
            display = disp;
            leaf = true;
            edges = new GLink[MAX_CHILDREN];
            widths = new int[MAX_CHILDREN];


        }

    }


}
