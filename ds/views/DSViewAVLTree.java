package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;

public class DSViewAVLTree extends DSView {

    public final int INSERT = 1;
    public final int FIND = 2;
    public final int DELETE = 3;
    public final int SHOW_HEIGHT = 4;
    public final int HIDE_HEIGHT = 5;


    protected final int HEIGHT_OFFSET_VERTICAL = 25;
    protected final int HEIGHT_OFFSET_HORIZONTAL = 20;

    protected final int STARTING_Y = 50;

    protected AVLNode root;
    protected int widthdelta = 60;
    protected int heightdelta = 50;
    protected final int MOVESTEPS = 40;
    protected GElement elementLabel;
    protected GLink rotateLink1;
    protected GLink rotateLink2;

    protected GElementLabel rotatelabel = null;

    protected boolean showHeight = true;


    public DSViewAVLTree() {
        createLabel("", 0, 0, false);
        root = null;

    }


    protected void CallFunction(int function) {
        switch (function) {
            case SHOW_HEIGHT:
                showHeight();
                break;
            case HIDE_HEIGHT:
                hideHeight();
                break;

        }
    }


    protected void showHeight() {
        showHeight = true;
        updateShowHeight(root, true);

    }

    protected void hideHeight() {
        showHeight = false;
        updateShowHeight(root, false);

    }

    protected void updateShowHeight(AVLNode tree, boolean visable) {
        if (tree != null) {
            tree.HeightLabel.setLabelVisible(visable);
            updateShowHeight(tree.left, visable);
            updateShowHeight(tree.right, visable);
        }
    }


    protected void CallFunction(int function, Object param1) {
        switch (function) {
            case INSERT:
                insert(padString((String) param1, 4));
                break;

            case FIND:
                find(padString((String) param1, 4));
                break;
            case DELETE:
                delete(padString((String) param1, 4));
        }
    }


    protected void cleanupRotate() {
        resizetree();
        if (rotatelabel != null) {
            removeAny(rotatelabel);
            rotatelabel = null;
        }
        if (rotateLink1 != null) {
            rotateLink1.setColor(Color.BLACK);
            rotateLink1 = null;
        }
        if (rotateLink2 != null) {
            rotateLink2.setColor(Color.BLACK);
            rotateLink2 = null;
        }

    }

    private void insert(String insertElem) {
        if (root == null) {
            root = new AVLNode(insertElem, createCircle(String.valueOf(insertElem), 500, STARTING_Y), 1);
        } else {
            AVLNode newelem = new AVLNode(insertElem, createCircle(String.valueOf(insertElem),
                                                                   100, 40), 1);
            root = insert(newelem, root, null, false);


            cleanupRotate();
        }

    }

    protected void resetHeight(AVLNode tree) {
        if (tree != null) {
            tree.height = 1 + Math.max(getHeight(tree.left), getHeight(tree.right));
            tree.HeightLabel.setLabel(String.valueOf(tree.height));
        }
    }


    private AVLNode insert(AVLNode insertElem, AVLNode tree, AVLNode parent, boolean leftchild) {
        int i;
        int offsetsign;

        if (leftchild)
            offsetsign = -1;
        else
            offsetsign = 1;


        insertElem.display.setLabelColor(Color.RED);

        if (tree == null) {
            insertElem.display.setLabelColor(Color.BLACK);
            Vector2D endPosition = new Vector2D(parent.display.getPositionX() + offsetsign * widthdelta / 2,
                                                parent.display.getPositionY() + heightdelta);
            Vector2D[] path = createPath(insertElem.display.getPosition(),
                                         endPosition, MOVESTEPS);
            for (i = 0; i < MOVESTEPS; i++) {
                insertElem.display.setPosition(path[i]);
                insertElem.HeightLabel.setPosition(path[i].getX() + offsetsign * HEIGHT_OFFSET_HORIZONTAL, path[i].getY() - HEIGHT_OFFSET_VERTICAL);
                repaintwaitmin();
            }
            insertElem.isLeftChild = leftchild;
            insertElem.parent = parent;
            createLink(parent.display, insertElem.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", offsetsign);
            if (leftchild && parent != null) {
                parent.left = insertElem;
            } else if (parent != null) {
                parent.right = insertElem;
            }
            for (AVLNode tmp = insertElem; tmp != null; tmp = tmp.parent) {
                resetHeight(tmp);
            }
            return insertElem;
        }
        tree.display.setLabelColor(Color.RED);
        repaintwait();
        tree.display.setLabelColor(Color.BLACK);
        if (insertElem.data.compareTo(tree.data) < 0) {
            tree.left = insert(insertElem, tree.left, tree, true);
            tree.left.isLeftChild = true;
        } else {
            tree.right = insert(insertElem, tree.right, tree, false);
            tree.right.isLeftChild = false;
        }
        resetHeight(tree);

        AVLNode rotated = tree;
        if (getHeight(tree.left) - getHeight(tree.right) > 1) {
            if (insertElem.data.compareTo(tree.left.data) >= 0) {
                rotated = doubleRotateRight(tree);

            } else {
                rotated = singleRotateRight(tree);
            }
        } else if (getHeight(tree.right) - getHeight(tree.left) > 1) {
            if (insertElem.data.compareTo(tree.right.data) < 0) {
                rotated = doubleRotateLeft(tree);

            } else {
                rotated = singleRotateLeft(tree);
            }
        }

        return rotated;
    }

    protected AVLNode singleRotateLeft(AVLNode tree) {


        AVLNode parent = tree.parent;
        AVLNode A = tree;
        AVLNode B = tree.right;
        AVLNode t1 = A.left;
        AVLNode t2 = B.left;
        AVLNode t3 = B.right;

        boolean leftchild = tree.isLeftChild;
        int direction;
        if (leftchild)
            direction = -1;
        else
            direction = 1;

        rotateLink1 = getLink(A.display, B.display);
        rotateLink1.setColor(Color.RED);
        rotatelabel = createLabel("Single Rotate Left", 500, 10);
        rotatelabel.setLabelColor(Color.RED);
        repaintwait();
        rotateLink1.setColor(Color.BLACK);
        rotatelabel.setLabelColor(Color.BLACK);
        if (t2 != null) {
            removeLink(B.display, t2.display);
            createLink(A.display, t2.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
            t2.isLeftChild = false;
            t2.parent = A;
        }
        removeLink(A.display, B.display);
        if (parent != null)
            removeLink(parent.display, A.display);
        rotateLink1 = createLink(B.display, A.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
        rotateLink1.setColor(Color.RED);
        B.isLeftChild = true;
        if (parent != null) {
            createLink(parent.display, B.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", direction);
        }
        B.left = A;
        A.parent = B;
        A.right = t2;
        A.isLeftChild = true;

        resetHeight(A);
        resetHeight(B);
        B.parent = parent;
        return B;

    }

    protected AVLNode singleRotateRight(AVLNode tree) {
        System.out.println("Single Rotate Right");
        AVLNode parent = tree.parent;
        AVLNode B = tree;
        AVLNode A = B.left;
        AVLNode t1 = A.left;
        AVLNode t2 = A.right;
        AVLNode t3 = B.right;


        boolean leftchild = tree.isLeftChild;
        int direction;
        if (leftchild)
            direction = -1;
        else
            direction = 1;


        rotateLink1 = getLink(B.display, A.display);
        rotateLink1.setColor(Color.RED);
        rotatelabel = createLabel("Single Rotate Right", 500, 10);
        rotatelabel.setLabelColor(Color.RED);
        repaintwait();
        rotatelabel.setColor(Color.BLACK);
        repaintwait();


        if (t2 != null) {
            removeLink(A.display, t2.display);
            createLink(B.display, t2.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
            t2.isLeftChild = true;
            t2.parent = B;
        }
        B.left = t2;

        removeLink(B.display, A.display);
        if (parent != null)
            removeLink(parent.display, B.display);

        if (parent != null) {
            createLink(parent.display, A.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", direction);
        }
        A.isLeftChild = leftchild;
        A.parent = parent;


        rotateLink1 = createLink(A.display, B.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
        rotateLink1.setColor(Color.RED);
        A.right = B;
        B.parent = A;
        B.isLeftChild = false;


        resetHeight(B);
        resetHeight(A);
        return A;

    }

    protected AVLNode doubleRotateLeft(AVLNode tree) {
        AVLNode A = tree;
        AVLNode C = A.right;
        AVLNode B = C.left;
        AVLNode t1 = A.left;
        AVLNode t2 = B.left;
        AVLNode t3 = B.right;
        AVLNode t4 = C.right;
        AVLNode parent = tree.parent;
        boolean leftchild = tree.isLeftChild;

        rotateLink1 = getLink(A.display, C.display);
        rotateLink1.setColor(Color.RED);
        rotateLink2 = getLink(C.display, B.display);
        rotateLink2.setColor(Color.RED);
        rotatelabel = createLabel("Double Rotate Left", 500, 10);
        rotatelabel.setLabelColor(Color.RED);
        repaintwait();
        rotatelabel.setColor(Color.BLACK);
        repaintwait();

        int direction;
        if (leftchild)
            direction = -1;
        else
            direction = 1;


        if (t2 != null) {
            removeLink(B.display, t2.display);
            createLink(A.display, t2.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
            t2.isLeftChild = false;
            t2.parent = A;
        }
        A.right = t2;

        if (t3 != null) {
            removeLink(B.display, t3.display);
            createLink(C.display, t3.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
            t3.isLeftChild = true;
            t3.parent = C;
        }
        C.left = t3;

        removeLink(A.display, C.display);
        removeLink(C.display, B.display);

        rotateLink1 = createLink(B.display, A.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
        rotateLink1.setColor(Color.RED);
        A.parent = B;
        A.isLeftChild = true;
        B.left = A;

        rotateLink2 = createLink(B.display, C.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
        rotateLink2.setColor(Color.RED);
        C.parent = B;
        C.isLeftChild = false;
        B.right = C;

        if (parent != null) {
            removeLink(parent.display, A.display);
            createLink(parent.display, B.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", direction);
        }
        B.parent = parent;
        B.isLeftChild = leftchild;

        resetHeight(A);
        resetHeight(C);
        resetHeight(B);

        System.out.println("Double Rotate Left");
        return B;
    }

    protected AVLNode doubleRotateRight(AVLNode tree) {
        System.out.println("Double Rotate Right");
        AVLNode C = tree;
        AVLNode A = C.left;
        AVLNode B = A.right;
        AVLNode t1 = A.left;
        AVLNode t2 = B.left;
        AVLNode t3 = B.right;
        AVLNode t4 = C.right;
        AVLNode parent = tree.parent;
        boolean leftchild = tree.isLeftChild;

        rotateLink1 = getLink(C.display, A.display);
        rotateLink1.setColor(Color.RED);
        rotateLink2 = getLink(A.display, B.display);
        rotateLink2.setColor(Color.RED);
        rotatelabel = createLabel("Double Rotate Right", 500, 10);
        rotatelabel.setLabelColor(Color.RED);
        repaintwait();
        rotatelabel.setColor(Color.BLACK);
        repaintwait();

        int direction;
        if (leftchild)
            direction = -1;
        else
            direction = 1;


        if (t2 != null) {
            removeLink(B.display, t2.display);
            createLink(A.display, t2.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
            t2.isLeftChild = false;
            t2.parent = A;
        }
        A.right = t2;

        if (t3 != null) {
            removeLink(B.display, t3.display);
            createLink(C.display, t3.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
            t3.isLeftChild = true;
            t3.parent = C;
        }
        C.left = t3;

        removeLink(C.display, A.display);
        removeLink(A.display, B.display);

        rotateLink1 = createLink(B.display, A.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
        rotateLink1.setColor(Color.RED);
        A.parent = B;
        A.isLeftChild = true;
        B.left = A;

        rotateLink2 = createLink(B.display, C.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
        rotateLink2.setColor(Color.RED);
        C.parent = B;
        C.isLeftChild = false;
        B.right = C;

        if (parent != null) {
            removeLink(parent.display, C.display);
            createLink(parent.display, B.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", direction);
        }
        B.parent = parent;
        B.isLeftChild = leftchild;

        resetHeight(A);
        resetHeight(C);
        resetHeight(B);

        return B;

    }

    protected int getHeight(AVLNode tree) {
        if (tree == null)
            return 0;
        return tree.height;
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


    public String toString(int num, int digits) {
        if (digits == 0)
            return "";
        return toString(num / 10, digits - 1) + String.valueOf(num % 10);

    }


    private void resizetree() {
        int i;
        int startingpoint = 500;
        ResetWidths(root);
        if (root != null) {
            if (root.leftwidth > 500 && root.rightwidth < 500) {
                startingpoint = startingpoint + root.leftwidth - 500;
            } else if (root.rightwidth > 500 && root.leftwidth < 500) {
                startingpoint = startingpoint - root.rightwidth + 500;
            } else if (root.leftwidth > 500 && root.rightwidth > 500) {
                startingpoint = startingpoint - root.rightwidth + root.leftwidth;
            }
        }
        boolean changed = SetNewPositions(root, startingpoint, 0, 0);
        if (changed) {
            SetPaths(root, MOVESTEPS / 2);

            for (i = 0; i < MOVESTEPS / 2; i++) {
                MoveAlongPath(root, i);
                repaintwaitmin();
            }
        }
        repaint();
    }

    void MoveAlongPath(AVLNode root, int step) {
        if (root != null) {
            root.display.setPosition(root.path[step]);
            root.HeightLabel.setPosition(root.pathHeight[step]);
            MoveAlongPath(root.left, step);
            MoveAlongPath(root.right, step);
        }
    }


    void SetPaths(AVLNode root, int pathlength) {
        if (root != null) {
            root.path = createPath(root.display.getPosition(), new Vector2D(root.newX, root.newY), pathlength);
            if (root.isLeftChild) {
                root.pathHeight = createPath(root.HeightLabel.getPosition(), new Vector2D(root.newX - HEIGHT_OFFSET_HORIZONTAL, root.newY - HEIGHT_OFFSET_VERTICAL), pathlength);
            } else {
                root.pathHeight = createPath(root.HeightLabel.getPosition(), new Vector2D(root.newX + HEIGHT_OFFSET_HORIZONTAL, root.newY - HEIGHT_OFFSET_VERTICAL), pathlength);

            }
            SetPaths(root.left, pathlength);
            SetPaths(root.right, pathlength);
        }
    }

    private boolean SetNewPositions(AVLNode tree, int Xposition, int depth, int position) {
        if (tree != null) {
            if (position == -1) {
                Xposition = Xposition - tree.rightwidth;
            } else if (position == 1) {
                Xposition = Xposition + tree.leftwidth;
            }
            tree.newX = Xposition;
            tree.newY = STARTING_Y + depth * heightdelta;
            if (tree.newY < 0)
                tree.newY = (int) tree.display.getPositionY();
            boolean changed_left = SetNewPositions(tree.left, Xposition, depth + 1, -1);
            boolean changed_right = SetNewPositions(tree.right, Xposition, depth + 1, 1);
            return changed_left || changed_right || tree.display.getPositionX() != tree.newX || tree.display.getPositionY() != tree.newY;
        }
        return false;
    }

    private int ResetWidths(AVLNode root) {
        if (root == null)
            return 0;
        root.leftwidth = Math.max(ResetWidths(root.left), widthdelta / 2);
        root.rightwidth = Math.max(ResetWidths(root.right), widthdelta / 2);
        //      return Math.max(root.leftwidth, widthdelta/2) +
        //              Math.max(root.rightwidth, widthdelta/2);
        return root.leftwidth + root.rightwidth;
    }


    public void delete(String removeitem) {
        GElement Deletelabel = createLabel("Deleting:", 100, 40, false);
        elementLabel = createLabel(String.valueOf(removeitem), 170, 40, false);
        elementLabel.setLabelColor(Color.RED);
        root = delete(removeitem, root);
        resizetree();
        removeAny(Deletelabel);
        removeAny(elementLabel);
    }

    private AVLNode delete(String removeitem, AVLNode tree) {
        boolean isLeftChild = false;
        if (tree != null) {
            if (tree.parent != null)
                isLeftChild = tree.parent.left == tree;
            else
                isLeftChild = true;
            tree.display.setLabelColor(Color.RED);
            repaintwait();
            tree.display.setLabelColor(Color.BLACK);
            if (removeitem.compareTo(tree.data) == 0) {
                elementLabel.setLabelColor(Color.BLACK);
                if ((tree.left == null) && (tree.right == null)) {
                    if (tree.parent != null) {
                        removeLink(tree.parent.display, tree.display);
                        tree.height = 0; // HACK!
                        for (AVLNode tmp = tree.parent; tmp != null; tmp = tmp.parent)
                            resetHeight(tmp);

                    } else {
                        root = null;

                    }
                    removeAny(tree.display);
                    removeAny(tree.HeightLabel);
                    return null;
                } else if (tree.left == null) {
                    if (tree.parent != null) {
                        removeLink(tree.parent.display, tree.display);
                        if (isLeftChild) {
                            createLink(tree.parent.display, tree.right.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
                            tree.right.isLeftChild = true;
                        } else {
                            createLink(tree.parent.display, tree.right.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
                            tree.right.isLeftChild = false;
                        }
                        repaintwait();

                        tree.height = tree.right.height;

                        for (AVLNode tmp = tree.parent; tmp != null; tmp = tmp.parent)
                            resetHeight(tmp);

                    }
                    removeLink(tree.display, tree.right.display);
                    removeAny(tree.display);
                    removeAny(tree.HeightLabel);

                    ChangeHeights(tree.right);
                    tree.right.parent = tree.parent;
                    return tree.right;

                } else if (tree.right == null) {
                    if (tree.parent != null) {
                        removeLink(tree.parent.display, tree.display);
                        if (isLeftChild) {
                            createLink(tree.parent.display, tree.left.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
                            tree.left.isLeftChild = true;
                        } else {
                            createLink(tree.parent.display, tree.left.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
                            tree.left.isLeftChild = false;
                        }
                        repaintwait();
                        tree.height = tree.left.height; // HACK!  to get heights set correctly on the next line
                        for (AVLNode tmp = tree.parent; tmp != null; tmp = tmp.parent)
                            resetHeight(tmp);

                    }
                    removeLink(tree.display, tree.left.display);
                    removeAny(tree.display);
                    removeAny(tree.HeightLabel);
                    ChangeHeights(tree.left);
                    tree.left.parent = tree.parent;
                    return tree.left;

                } else {
                    AVLNode tmp = tree;
                    tmp = tree.left;
                    tmp.display.setColor(Color.BLUE);
                    repaintwait();
                    while (tmp.right != null) {
                        tmp.display.setColor(Color.BLACK);
                        tmp = tmp.right;
                        tmp.display.setColor(Color.BLUE);
                        repaintwait();
                    }
                    tree.display.setLabel("");
                    elementLabel.setLabelColor(Color.BLACK);
                    GElement label = createLabel(tmp.display.getLabel(), tmp.display.getPositionX(), tmp.display.getPositionY(), false);
                    Vector2D path[] = createPath(label.getPosition(), tree.display.getPosition(), MOVESTEPS);
                    for (int i = 0; i < MOVESTEPS; i++) {
                        label.setPosition(path[i]);
                        repaintwaitmin();
                    }
                    tree.data = tmp.data;
                    tree.display.setLabel(tmp.display.getLabel());
                    removeAny(label);
                    elementLabel.setLabel(String.valueOf(tmp.data));
                    elementLabel.setLabelColor(Color.RED);
                    if (tmp.isLeftChild) {
                        tmp.parent.left = delete(tmp.data, tmp);
                    } else {
                        tmp.parent.right = delete(tmp.data, tmp);
                    }
                    if (tmp == null)
                        System.out.println("tmp is null!");
                    tree.left = rotateAfterDelete(tree.left);

                    if (getHeight(tree.right) - getHeight(tree.left) > 1) {
                        if (getHeight(tree.right.left) > getHeight(tree.right.right))
                            tree = doubleRotateLeft(tree);
                        else
                            tree = singleRotateLeft(tree);
                        resetHeight(tree);
                        connect_parent(tree, isLeftChild);
                        cleanupRotate();
                    }

                    return tree;
                }
            } else if (removeitem.compareTo(tree.data) < 0) {
                tree.left = delete(removeitem, tree.left);
                if (getHeight(tree.right) - getHeight(tree.left) > 1) {
                    if (getHeight(tree.right.left) > getHeight(tree.right.right))
                        tree = doubleRotateLeft(tree);
                    else
                        tree = singleRotateLeft(tree);
                    resetHeight(tree);
                    connect_parent(tree, isLeftChild);
                    cleanupRotate();
                }

                return tree;

            } else {
                tree.right = delete(removeitem, tree.right);
                if (getHeight(tree.left) - getHeight(tree.right) > 1) {
                    if (getHeight(tree.left.right) > getHeight(tree.left.left))
                        tree = doubleRotateRight(tree);
                    else
                        tree = singleRotateRight(tree);
                    resetHeight(tree);
                    connect_parent(tree, isLeftChild);
                    cleanupRotate();

                }
                return tree;
            }

        }
        return tree; // Shouldn't get here!
    }

    protected AVLNode rotateAfterDelete(AVLNode tree) {
        if (tree == null)
            return null;
        if (tree.right == null) {
            if (getHeight(tree.left) - getHeight(tree.right) > 1) {
                if (getHeight(tree.left.right) > getHeight(tree.left.left))
                    tree = doubleRotateRight(tree);
                else
                    tree = singleRotateRight(tree);
                resetHeight(tree);
                cleanupRotate();
            }
            return tree;
        } else {
            tree.right = rotateAfterDelete(tree.right);
            if (getHeight(tree.left) - getHeight(tree.right) > 1) {
                if (getHeight(tree.left.right) > getHeight(tree.left.left))
                    tree = doubleRotateRight(tree);
                else
                    tree = singleRotateRight(tree);
                resetHeight(tree);
                cleanupRotate();
            }
            return tree;
        }
    }

    protected void connect_parent(AVLNode tree, boolean isLeftChild) {
        //      if (tree.parent != null) {
        //         if (isLeftChild)
        //            tree.parent.left = tree;
        //         else
        //             tree.parent.right = tree;
        //     } else {
        //         root =tree;
        //    }
    }

    private void ChangeHeights(AVLNode tree) {
        if (tree != null) {
            tree.newY = (int) tree.display.getPositionY() - heightdelta;
            ChangeHeights(tree.left);
            ChangeHeights(tree.right);
        }
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

    public GElement find(String finditem, AVLNode tree) {
        if (tree == null)
            return null;
        tree.display.setLabelColor(Color.RED);
        repaintwait();
        if (tree.data.compareTo(finditem) == 0)
            return tree.display;
        else {
            tree.display.setLabelColor(Color.BLACK);
            if (finditem.compareTo(tree.data) < 0) {
                return find(finditem, tree.left);

            } else {
                return find(finditem, tree.right);
            }

        }


    }


    protected class AVLNode {
        String data;
        AVLNode left;
        AVLNode right;
        AVLNode parent;
        GElement display;
        GElementLabel HeightLabel;
        boolean isLeftChild = true;

        int height;
        int leftwidth;
        int rightwidth;
        int newX;
        int newY;
        Vector2D path[];
        Vector2D pathHeight[];

        public AVLNode(String elem) {
            data = elem;
            left = null;
            right = null;
            parent = null;
            newY = -1;
        }

        public AVLNode(String elem, GElement disp, int newHeight) {
            data = elem;
            left = null;
            right = null;
            display = disp;
            parent = null;
            height = newHeight;
            newY = -1;
            HeightLabel = createLabel(String.valueOf(newHeight), disp.getPositionX() - HEIGHT_OFFSET_HORIZONTAL, disp.getPositionY() - HEIGHT_OFFSET_VERTICAL);
            HeightLabel.setLabelColor(Color.BLUE);
            HeightLabel.setLabelVisible(showHeight);
        }

    }


}

