package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;

public class DSViewBST extends DSView {

    public final int INSERT = 1;
    public final int FIND = 2;
    public final int DELETE = 3;


    protected BSTNode root;
    protected int widthdelta = 50;
    protected int heightdelta = 50;
    protected final int MOVESTEPS = 40;
    GElement elementLabel;


    public DSViewBST() {
        createLabel("", 0, 0, false);
        root = null;

    }


    protected void CallFunction(int function, Object param1) {
         switch (function) {
             case INSERT:
                 insert(padString((String) param1,4));
                 break;

             case FIND:
               find(padString((String) param1,4));
                 break;
                 case DELETE:
                 delete(padString ((String) param1,4));
         }
     }





    private void insert(String insertElem) {
        if (root == null) {
            root = new BSTNode(insertElem, createCircle(String.valueOf(insertElem), 500, 50));
        } else {
            BSTNode newelem = new BSTNode(insertElem, createCircle(String.valueOf(insertElem),
                    100, 40));
            insert(newelem, root);


        }

    }

    private void insert(BSTNode insertElem, BSTNode treeroot) {
        int i;
        insertElem.display.setLabelColor(Color.RED);
        treeroot.display.setLabelColor(Color.RED);
        repaintwait();
        insertElem.display.setLabelColor(Color.BLACK);
        treeroot.display.setLabelColor(Color.BLACK);
        if (insertElem.data.compareTo(treeroot.data) <0) {
            if (treeroot.left == null) {
                Vector2D endPosition = new Vector2D(treeroot.display.getPositionX() - widthdelta / 2,
                        treeroot.display.getPositionY() + heightdelta);
                Vector2D[] path = createPath(insertElem.display.getPosition(),
                        endPosition, MOVESTEPS);
                for (i = 0; i < MOVESTEPS; i++) {
                    insertElem.display.setPosition(path[i]);
                    repaintwaitmin();
                }
                treeroot.left = insertElem;
                insertElem.parent = treeroot;
                createLink(treeroot.display, insertElem.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
                resizetree();

            } else {
                insert(insertElem, treeroot.left);
            }

        } else {
            if (treeroot.right == null) {
                Vector2D endPosition = new Vector2D(treeroot.display.getPositionX() + widthdelta / 2,
                        treeroot.display.getPositionY() + heightdelta);
                Vector2D[] path = createPath(insertElem.display.getPosition(),
                        endPosition, MOVESTEPS);
                for (i = 0; i < MOVESTEPS; i++) {
                    insertElem.display.setPosition(path[i]);
                    repaintwaitmin();
                }
                treeroot.right = insertElem;
                insertElem.parent = treeroot;
                createLink(treeroot.display, insertElem.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
                resizetree();
            } else {
                insert(insertElem, treeroot.right);
            }


        }

    }


    public String padString(String s, int numchars) {

     try {
         int x = Integer.parseInt(s);
         if (x >= 0) {
             return toString(x,4);
         } else {
             return s;

         }
     }   catch (Exception e) {
         return s;
     }


    }


    public String toString(int num,int digits) {
        if (digits == 0)
            return "";
        return toString(num/10,digits-1) + String.valueOf(num % 10);

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
        SetNewPositions(root, startingpoint, 0);
        SetPaths(root, MOVESTEPS / 2);
        for (i = 0; i < MOVESTEPS / 2; i++) {
            MoveAlongPath(root, i);
            repaintwaitmin();
        }
        repaint();
    }

    void MoveAlongPath(BSTNode root, int step) {
        if (root != null) {
            root.display.setPosition(root.path[step]);
            MoveAlongPath(root.left, step);
            MoveAlongPath(root.right, step);


        }
    }


    void SetPaths(BSTNode root, int pathlength) {
        if (root != null) {
            root.path = createPath(root.display.getPosition(), new Vector2D(root.newX, root.newY), pathlength);
            SetPaths(root.left, pathlength);
            SetPaths(root.right, pathlength);
        }
    }


   
    private void SetNewPositions(BSTNode tree, int Xposition, int position) {
        if (tree != null) {
            if (position == -1) {
                Xposition = Xposition - tree.rightwidth;
            } else if (position == 1) {
                Xposition = Xposition + tree.leftwidth;
            }
            tree.newX = Xposition;
            if (tree.newY < 0)
                tree.newY = (int) tree.display.getPositionY();
            SetNewPositions(tree.left, Xposition, -1);
            SetNewPositions(tree.right, Xposition, 1);
        }
    }

    private int ResetWidths(BSTNode root) {
        if (root == null)
            return 0;
        root.leftwidth = Math.max(ResetWidths(root.left) , widthdelta / 2);
        root.rightwidth = Math.max(ResetWidths(root.right) , widthdelta / 2);
        //      return Math.max(root.leftwidth, widthdelta/2) +
        //              Math.max(root.rightwidth, widthdelta/2);
        return root.leftwidth + root.rightwidth;
    }




    public void delete(String removeitem) {
        GElement Deletelabel = createLabel("Deleting:", 100, 40, false);
        elementLabel = createLabel(String.valueOf(removeitem), 170, 40, false);
        elementLabel.setLabelColor(Color.RED);
        delete(removeitem, root);
        removeAny(Deletelabel);
        removeAny(elementLabel);
    }

    private void delete(String removeitem, BSTNode tree) {
        boolean leftchild = false;
        if (tree != null) {
            if (tree.parent != null)
                leftchild = tree.parent.left == tree;
            tree.display.setLabelColor(Color.RED);
            repaintwait();
            tree.display.setLabelColor(Color.BLACK);
            if (removeitem.compareTo(tree.data) == 0) {
                elementLabel.setLabelColor(Color.BLACK);
                if ((tree.left == null) && (tree.right == null)) {
                    if (tree.parent != null) {
                        removeLink(tree.parent.display, tree.display);
                        if (leftchild) {
                            tree.parent.left = null;
                        } else {
                            tree.parent.right = null;
                        }

                    } else {
                        root = null;

                    }
                    removeAny(tree.display);
                    resizetree();
                } else if (tree.left == null) {
                    if (tree.parent != null) {
                        removeLink(tree.parent.display, tree.display);
                        if (leftchild) {
                            createLink(tree.parent.display, tree.right.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
                        } else {
                            createLink(tree.parent.display, tree.right.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
                        }
                        repaintwait();
                        if (leftchild) {
                            tree.parent.left = tree.right;
                            tree.right.parent = tree.parent;
                        } else {
                            tree.parent.right = tree.right;
                            tree.right.parent = tree.parent;
                        }
                    } else {
                        root = tree.right;
                        tree.right.parent = null;

                    }
                    removeLink(tree.display, tree.right.display);
                    removeAny(tree.display);
                    ChangeHeights(tree.right);
                    resizetree();

                } else if (tree.right == null) {
                    if (tree.parent != null) {
                        removeLink(tree.parent.display, tree.display);
                        if (leftchild) {
                            createLink(tree.parent.display, tree.left.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", -1);
                        } else {
                            createLink(tree.parent.display, tree.left.display, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "", 1);
                        }
                        repaintwait();
                        if (leftchild) {
                            tree.parent.left = tree.left;
                            tree.left.parent = tree.parent;
                        } else {
                            tree.parent.right = tree.left;
                            tree.left.parent = tree.parent;
                        }
                    } else {
                        root = tree.left;
                        tree.left.parent = null;

                    }
                    removeLink(tree.display, tree.left.display);
                    removeAny(tree.display);
                    ChangeHeights(tree.left);
                    resizetree();


                } else {
                    BSTNode tmp = tree;
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
                    GElement label = createLabel(tmp.display.getLabel(),tmp.display.getPositionX(),tmp.display.getPositionY(),false);
                    Vector2D path[] = createPath(label.getPosition(),tree.display.getPosition(),MOVESTEPS);
                    for (int i=0; i<MOVESTEPS; i++) {
                        label.setPosition(path[i]);
                        repaintwaitmin();
                    }
                    tree.data = tmp.data;
                    tree.display.setLabel(tmp.display.getLabel());
                    removeAny(label);
                    elementLabel.setLabel(String.valueOf(tmp.data));
                    elementLabel.setLabelColor(Color.RED);
                    delete(tmp.data,tmp);

                }
            } else if (removeitem.compareTo(tree.data) < 0) {
                delete(removeitem, tree.left);

            } else {
                delete(removeitem, tree.right);
            }

        }
    }

    private void ChangeHeights(BSTNode tree) {
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

    public GElement find(String finditem, BSTNode tree) {
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


    protected class BSTNode {
        String data;
        BSTNode left;
        BSTNode right;
        BSTNode parent;
        GElement display;
        int leftwidth;
        int rightwidth;
        int newX;
        int newY;
        Vector2D path[];

        public BSTNode(String elem) {
            data = elem;
            left = null;
            right = null;
            parent = null;
            newY = -1;
        }

        public BSTNode(String elem, GElement disp) {
            data = elem;
            left = null;
            right = null;
            display = disp;
            parent = null;
            newY = -1;
        }

    }


}
