package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeNullPointer;
import edu.usfca.ds.shapes.DSShapeSingleLLL;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;


public class DSViewQueueLL extends DSView {

    public final int ENQUEUE = 1;
    public final int DEQUEUE = 2;

    protected final int NUMSTEPS = 40;
    protected final int QUEUESIZE = 28;
    protected int size;
    protected GElementArrow tailarrow, headarrow;
    protected GElementLabel taillabel,headlabel;
    protected DSShapeNullPointer nullptrtail, nullptrhead;
    protected DSShapeSingleLLL queue[];

    protected int Xpos[];
    protected int Ypos[];



    public DSViewQueueLL() {
        super();

        createLabel("", 0, 0, false); // empty label to be the root

        Xpos = new int[]{100, 225, 350, 475, 600, 725, 850, 100, 225, 350, 475, 600, 725, 850,
                         100, 225, 350, 475, 600, 725, 850, 100, 225, 350, 475, 600, 725, 850};
        Ypos = new int[]{150, 150, 150, 150, 150, 150, 150, 225, 225, 225, 225, 225, 225, 225,
                         300, 300, 300, 300, 300, 300, 300, 375, 375, 375, 375, 375, 375, 375};
        size = 0;
        queue = new DSShapeSingleLLL[QUEUESIZE];
        nullptrhead = createNullPointer(Xpos[0], Ypos[0] + 65, 20, 20);
        headlabel = createLabel("head", Xpos[0], Ypos[0] + 80);
        nullptrtail = createNullPointer(Xpos[0], Ypos[0] - 65, 20, 20);
        taillabel = createLabel("tail", Xpos[0], Ypos[0] - 85);


    }




    protected void CallFunction(int function) {
          switch (function) {
              case DEQUEUE:
                  dequeue();
                  break;
          }

      }

      protected void CallFunction(int function, Object param1) {
          switch (function) {
              case ENQUEUE:
                  enqueue((String) param1);
                  break;

          }
      }




    public void enqueue(String value) {

        GElement Enqueuelabel = createLabel("Enqueueing: ", 250, 40, false);
        GElement Enqueued = null;
        int i,j;
        Vector2D path[];
        Vector2D path2[];
        Vector2D path3[];
        Vector2D paths[][];

        if (size < QUEUESIZE) {
            Enqueued = createLabel(value, -10, -10, false);
            GElement list[] = {Enqueuelabel, Enqueued };
            LineupHorizontal(list);
            repaintwait();
            if (size == 0) {
                queue[size] = createSingleLinkedListRectL("", Xpos[0], Ypos[0], 75, 50);
                queue[size].setPointerVoid(true);
                repaintwait();
                path = createPath(Enqueued.getPosition(), queue[size].getPosition(), NUMSTEPS);
                for (i = 0; i < NUMSTEPS; i++) {
                    Enqueued.setPosition(path[i]);
                    repaintwaitmin();
                }
                queue[size].setLabel(String.valueOf(value));
                removeAny(Enqueued);
                removeShape(nullptrtail);
                removeShape(nullptrhead);
                headarrow = createArrow(Xpos[0], Ypos[0] + 75, Xpos[0], Ypos[0] + 25, 20, false);
                tailarrow = createArrow(Xpos[0], Ypos[0] - 75, Xpos[0], Ypos[0] - 25, 20, false);
                //toplabel = createLabel("top", Xpos[0], Ypos[0] + 80);
                size++;
                repaintwait();
            } else {
                for (i=size; i>0; i--)
                    queue[i] = queue[i-1];
                queue[0] = createSingleLinkedListRectL("", 380, 40,75, 50);
                queue[0].setPointerVoid(true);
                size++;
                repaintwait();

                path = createPath(Enqueued.getPosition(), queue[0].getPosition(),NUMSTEPS/2);
                for (i=0; i<NUMSTEPS/2;i++) {
                    Enqueued.setPosition(path[i]);
                    repaintwaitmin();
                }
                removeAny(Enqueued);
                queue[0].setLabel(String.valueOf(value));
                repaintwait();
                queue[1].setPointerVoid(false);
                GLink l = createLink(queue[1], queue[0], GLink.SHAPE_ELBOW, GElement.ANCHOR_LEFT, GElement.ANCHOR_RIGHT, "", 0);
                // l.setTargetOffset(0, 0);
                repaintwait();
                Vector2D oldhead = tailarrow.getTarget();
                tailarrow.setTarget(350,75);
                path = createPath(tailarrow.getTarget(), oldhead,NUMSTEPS);
                path2 = createPath(headarrow.getPosition(), new Vector2D(Xpos[size-1],Ypos[size-1]-75),NUMSTEPS);
                path3 = createPath(headlabel .getPosition(),new Vector2D(Xpos[size-1],Ypos[size-1]+80),NUMSTEPS);
                paths = new Vector2D[size][];
                for (j=0; j<size;j++)
                    paths[j] = createPath(queue[j].getPosition(), new Vector2D(Xpos[j],Ypos[j]),NUMSTEPS);
                path2 = createPath(headarrow.getPosition(), new Vector2D(Xpos[size-1],Ypos[size-1]+75),NUMSTEPS);
                for (i=0; i<NUMSTEPS;i++) {
                    headarrow.moveToPosition(path2[i]);
                    headlabel.moveToPosition(path3[i]);
                    tailarrow.setTarget(path[i].getX(),path[i].getY());
                    for (j=0; j<size;j++) {
                        queue[j].setPosition(paths[j][i]);
                    }
                    repaintwaitmin();
                }
                repaintwait();
               /* Vector2D endposlab = new Vector2D(toplabel.getPositionX() + Xpos[stacktop] - Xpos[stacktop - 1],
                                                  toplabel.getPositionY() + Ypos[stacktop] - Ypos[stacktop - 1]);
                Vector2D endposarrow = new Vector2D(toparrow.getPositionX() + Xpos[stacktop] - Xpos[stacktop - 1],
                                                  toparrow.getPositionY() + Ypos[stacktop] - Ypos[stacktop - 1]);
                path = createPath(toplabel.getPosition(), endposlab, NUMSTEPS);
                path2 =  createPath(toparrow.getPosition(), endposarrow, NUMSTEPS);
               // toparrow.setTarget(Xpos[stacktop],Ypos[stacktop] + 25);
                for (i=0; i<NUMSTEPS;i++) {
                    toplabel.setPosition(path[i]);
                    toparrow.moveToPosition(path2[i]);
                    //toparrow.setPosition(path2[i]);
                    //toparrow.setTarget(Xpos[stacktop],Ypos[stacktop] + 25);
                    repaintwaitmin();
                }
                stacktop++;
                repaintwait();  */
            }
            removeAny(Enqueuelabel);
        } else {

            Enqueued = createLabel("Reached Max size of stack for visualization", 300, 40, false);
            HoldoverGraphics.addElement(Enqueued);
            HoldoverGraphics.addElement(Enqueuelabel);


        }
        repaint();
    }





    public void dequeue() {
        GElement Dequeuelabel = createLabel("Dequeueing: ", 250, 40, false);
        GElement Dequeued = null;
          int i;
          Vector2D path[];
          Vector2D path2[];


        if (size > 0) {
            size--;
            Dequeued = createLabel(queue[size].getLabel(),queue[size].getPositionX(),queue[size].getPositionY(),false);
            path = createPath(Dequeued.getPosition(), new Vector2D(315,40),NUMSTEPS);
            for (i=0; i<NUMSTEPS;i++) {
                Dequeued.setPosition(path[i]);
                repaintwaitmin();
            }
            GElement lst[] = {Dequeuelabel, Dequeued};
            LineupHorizontal(lst);


            if (size == 0) {
                repaintwait();
                removeAny(tailarrow);
                removeAny(headarrow);
                nullptrhead = createNullPointer(Xpos[0], Ypos[0] + 65, 20, 20);
                nullptrtail = createNullPointer(Xpos[0], Ypos[0] - 65, 20, 20);
                repaintwait();
                removeAny(queue[0]);
          //      toplabel = createLabel("top", Xpos[0], Ypos[0] + 80);


            } else {
                repaintwait();
                Vector2D endposlab = new Vector2D(headlabel.getPositionX() + Xpos[size-1] - Xpos[size],
                                                        headlabel.getPositionY() + Ypos[size-1] - Ypos[size]);
                      Vector2D endposarrow = new Vector2D(headarrow.getPositionX() + Xpos[size-1] - Xpos[size],
                                                        headarrow.getPositionY() + Ypos[size-1] - Ypos[size]);
                      path = createPath(headlabel.getPosition(), endposlab, NUMSTEPS);
                      path2 =  createPath(headarrow.getPosition(), endposarrow, NUMSTEPS);
                      headarrow.setTarget(Xpos[size-1],Ypos[size-1] + 25);
                      for (i=0; i<NUMSTEPS;i++) {
                          headlabel.setPosition(path[i]);
                          headarrow.setPosition(path2[i]);
                          headarrow.setTarget(Xpos[size-1],Ypos[size-1] + 25);
                          repaintwaitmin();
                      }
                repaintwait();
                removeLink(queue[size], queue[size - 1]);
                removeShape(queue[size]);
            }

        }  else {
           Dequeued = createLabel("Queue is empty", 290, 40, false);
        }
        HoldoverGraphics.addElement(Dequeuelabel);
        HoldoverGraphics.addElement(Dequeued);

        repaint();
    }
}
