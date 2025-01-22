package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeNullPointer;
import edu.usfca.ds.shapes.DSShapeSingleLLL;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;


public class DSViewStackLL extends DSView {

    public final int PUSH = 1;
    public final int POP = 2;

    protected int stacktop;
    protected GElementArrow toparrow;
    protected GElementLabel toplabel;
    protected DSShapeNullPointer nullptr;
    protected DSShapeSingleLLL first;
    protected DSShapeSingleLLL stack[];
    public final int stacksize = 28;
    protected int Xpos[];
    protected int Ypos[];
    protected int NUMSTEPS = 40;


    public DSViewStackLL() {
        super();
        createLabel("", 0, 0, false); // empty label to be the root


        Xpos = new int[]{100, 225, 350, 475, 600, 725, 850, 100, 225, 350, 475, 600, 725, 850,
                         100, 225, 350, 475, 600, 725, 850, 100, 225, 350, 475, 600, 725, 850};
        Ypos = new int[]{150, 150, 150, 150, 150, 150, 150, 225, 225, 225, 225, 225, 225, 225,
                         300, 300, 300, 300, 300, 300, 300, 375, 375, 375, 375, 375, 375, 375};
        stacktop = 0;
        stack = new DSShapeSingleLLL[stacksize];
        nullptr = createNullPointer(Xpos[0], Ypos[0] + 65, 20, 20);
//        createArrow(50, 120, 50, 75, 20, false);
        toplabel = createLabel("top", Xpos[0], Ypos[0] + 80);


/*              createLabel("label_1", 200, 250);

              DSShapeSingleLLR r1 = createSingleLinkedListRecR("A", 200, 300, 100, 50);
              DSShapeSingleLLR r2 = createSingleLinkedListRecR("B", 350, 300, 100, 50);

              GLink l = createLink(r1, r2, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "A-B", 0);
              l.setSourceOffset(30, 0);
              l.setTargetOffset(-30, 0);

              DSShapeDoubleLLR r3 = createDoubleLinkedListRect("C", 200, 400, 100, 50);
              DSShapeDoubleLLR r4 = createDoubleLinkedListRect("D", 350, 400, 100, 50);

              l = createLink(r3, r4, GLink.SHAPE_ARC, GElement.ANCHOR_RIGHT, GElement.ANCHOR_LEFT, "C-D", 0);
              l.setSourceOffset(-20, -10);
              l.setTargetOffset(20, -10);

              l = createLink(r4, r3, GLink.SHAPE_ARC, GElement.ANCHOR_LEFT, GElement.ANCHOR_RIGHT, "D-C", 0);
              l.setSourceOffset(20, 10);
              l.setTargetOffset(-20, 10);  */
    }

    protected void CallFunction(int function) {
        switch (function) {
            case POP:
                pop();
                break;
        }

    }

    protected void CallFunction(int function, Object param1) {
        switch (function) {
            case PUSH:
                push((String) param1);
                break;
        }
    }




    public void push(String value) {


        GElement Pushlabel = createLabel("Pushing: ", 100, 40, false);
        GElement Pushed;
        int i;
        Vector2D path[];
        Vector2D path2[];

        if (stacktop < stacksize) {
            Pushed = createLabel(value, -10, -10, false);
            GElement lst[] = {Pushlabel ,Pushed }  ;
            LineupHorizontal(lst);
            repaintwait();
            if (stacktop == 0) {
                first = createSingleLinkedListRectL("", Xpos[0], Ypos[0], 75, 50);
                first.setPointerVoid(true);
                repaintwait();
                path = createPath(Pushed.getPosition(), first.getPosition(), NUMSTEPS);
                for (i = 0; i < NUMSTEPS; i++) {
                    Pushed.setPosition(path[i]);
                    repaintwaitmin();
                }
                first.setLabel(String.valueOf(value));
                removeAny(Pushed);
                //        setRootElement(first);
                removeShape(nullptr);
                toparrow = createArrow(Xpos[0], Ypos[0] + 75, Xpos[0], Ypos[0] + 25, 20, false);
                //toplabel = createLabel("top", Xpos[0], Ypos[0] + 80);
                stack[stacktop] = first;
                stacktop++;
                repaintwait();
            } else {
                stack[stacktop] = createSingleLinkedListRectL("", 220, 40,75, 50);
                stack[stacktop].setPointerVoid(true);
                repaintwait();
                path = createPath(Pushed.getPosition(), stack[stacktop].getPosition(),NUMSTEPS/2);
                for (i=0; i<NUMSTEPS/2;i++) {
                    Pushed.setPosition(path[i]);
                    repaintwaitmin();
                }
                removeAny(Pushed);
                stack[stacktop].setLabel(String.valueOf(value));
                repaintwait();
                stack[stacktop].setPointerVoid(false);
                GLink l = createLink(stack[stacktop], stack[stacktop - 1], GLink.SHAPE_ELBOW, GElement.ANCHOR_LEFT, GElement.ANCHOR_RIGHT, "", 0);
                l.setTargetOffset(0, 0);
                repaintwait();
                path = createPath(stack[stacktop].getPosition(), new Vector2D(Xpos[stacktop],Ypos[stacktop]),NUMSTEPS);
                for (i=0; i<NUMSTEPS;i++) {
                    stack[stacktop].setPosition(path[i]);
                    repaintwaitmin();
                }
                repaintwait();
                Vector2D endposlab = new Vector2D(toplabel.getPositionX() + Xpos[stacktop] - Xpos[stacktop - 1],
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
                repaintwait();
            }
            removeAny(Pushlabel);
        } else {

            Pushed = createLabel("Reached Max size of stack for visualization", 300, 40, false);
            HoldoverGraphics.addElement(Pushed);
            HoldoverGraphics.addElement(Pushlabel);


        }
        repaint();
    }
    //       stack[stacktop++] = value;

    //       GElement arraynode = (GElement) shapes.get(stacktop - 1);
    //       arraynode.setLabel(String.valueOf(value));
    //       toparrow.move(50, 0);
    //       toplabel.move(50,0);







    public void pop() {

        GElement Poplabel = createLabel("Popping: ", 100, 40, false);
          GElement Popped;
          int i;
          Vector2D path[];
          Vector2D path2[];


        if (stacktop > 0) {
            stacktop--;
            Popped = createLabel(stack[stacktop].getLabel(),stack[stacktop].getPositionX(),stack[stacktop].getPositionY(),false);
            path = createPath(Popped.getPosition(), new Vector2D(150,40),NUMSTEPS);
            for (i=0; i<NUMSTEPS;i++) {
                Popped.setPosition(path[i]);
                repaintwaitmin();
            }
            GElement lst[] = {Poplabel,Popped};
            LineupHorizontal(lst);

            if (stacktop == 0) {
                repaintwait();
                nullptr = createNullPointer(Xpos[0], Ypos[0] + 65, 20, 20);
                removeAny(toparrow);
        //        setRootElement(nullptr);
                repaintwait();
                removeShape(first);

          //      toplabel = createLabel("top", Xpos[0], Ypos[0] + 80);


            } else {
                repaintwait();
                      Vector2D endposlab = new Vector2D(toplabel.getPositionX() + Xpos[stacktop-1] - Xpos[stacktop],
                                                        toplabel.getPositionY() + Ypos[stacktop-1] - Ypos[stacktop]);
                      Vector2D endposarrow = new Vector2D(toparrow.getPositionX() + Xpos[stacktop-1] - Xpos[stacktop],
                                                        toparrow.getPositionY() + Ypos[stacktop-1] - Ypos[stacktop]);
                      path = createPath(toplabel.getPosition(), endposlab, NUMSTEPS);
                      path2 =  createPath(toparrow.getPosition(), endposarrow, NUMSTEPS);
                      toparrow.setTarget(Xpos[stacktop-1],Ypos[stacktop-1] + 25);
                      for (i=0; i<NUMSTEPS;i++) {
                          toplabel.setPosition(path[i]);
                          toparrow.setPosition(path2[i]);
                          toparrow.setTarget(Xpos[stacktop-1],Ypos[stacktop-1] + 25);
                          repaintwaitmin();
                      }
                repaintwait();
                removeLink(stack[stacktop], stack[stacktop - 1]);
                removeShape(stack[stacktop]);
            }

        }  else {
           Popped = createLabel("Stack is empty", 180, 40, false);
        }
        HoldoverGraphics.addElement(Poplabel);
        HoldoverGraphics.addElement(Popped);

        repaint();
    }
}
