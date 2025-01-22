package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeCircle;
import edu.usfca.ds.shapes.DSShapeLink;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;


public class DSViewKruskal extends DSViewGraph {


    protected final int EDGE_WIDTH = 60;

    protected final int FIRST_ROW_X = 150;
    protected final int ROW_WIDTH = 90;

    protected final int FIRST_ROW_Y = 70;
    protected final int ROW_HEIGHT = 39;


    public final int SMALL_INDEP_SET_WIDTH = 40;
    public final int SMALL_INDEP_SET_HEIGHT = 40;
    public final int SMALL_INDEP_SET_INITIAL_X = 67;
    public final int SMALL_INDEP_SET_INITIAL_Y = 70;
    public final int SMALL_INDEP_SET_INDEX_X = 35;

    public final int LARGE_INDEP_SET_WIDTH = 25;
    public final int LARGE_INDEP_SET_HEIGHT = 25;
    public final int LARGE_INDEP_SET_INITIAL_X = 60;
    public final int LARGE_INDEP_SET_INITIAL_Y = 35;
    public final int LARGE_INDEP_SET_INDEX_X = 35;

    protected int indep_set_width = SMALL_INDEP_SET_WIDTH;
    protected int indep_set_height = SMALL_INDEP_SET_HEIGHT;
    protected int indep_set_initial_x = SMALL_INDEP_SET_INITIAL_X;
    protected int indep_set_initial_y = SMALL_INDEP_SET_INITIAL_Y;
    protected int indep_set_index_x = SMALL_INDEP_SET_INDEX_X;


    protected GElementLabel ISindex[];
    protected GElement IndepSet[];




    protected int Xpos[];
    protected int Ypos[];
    protected int YposIS[];

    protected EdgeClass EdgesList[];
    protected int numedges;

    public final static int KRUSKAL = 7;

    public DSViewKruskal() {
        super();

        // big_edge_percent = 1;

        int i, j;
        int currentX;

        IndepSet = new GElement[LARGESIZE];
        ISindex = new GElementLabel[LARGESIZE];
        YposIS = new int[LARGESIZE];
        Xpos = new int[70];
        Ypos = new int[70];
        EdgesList = new EdgeClass[70];


        currentX = FIRST_ROW_X;
        for (j = 0; j < 5; j++) {
            for (i = 0; i < 11; i++) {
                Xpos[i + 11 * j] = currentX;
                Ypos[i + 11 * j] = FIRST_ROW_Y + ROW_HEIGHT * i;
            }
            currentX += ROW_WIDTH;
        }

    }

    protected void CallFunction(int function) {
        switch (function) {
            case KRUSKAL:
                kruskal();
                break;
            default:
                super.CallFunction(function);
        }
    }


    boolean Less(String n1, String n2) {
        if (n2.compareTo("inf") == 0) {
            if (n1.compareTo("inf") == 0)
                return false;
            else
                return true;
        }
        return (Integer.parseInt(n1) < Integer.parseInt(n2));
    }

    protected void sortEdges(EdgeClass[] edges, int length) {
        int i,j, smallindex;
        EdgeClass tmp;

        for (i=0;i<length-1;i++) {
            smallindex = i;
            for (j=i+1; j<length;j++)
                if (edges[j].cost < edges[smallindex].cost)
                    smallindex = j;
            tmp = edges[i];
            edges[i] = edges[smallindex];
            edges[smallindex] = tmp;
        }

    }

    protected void setPath(EdgeClass edges[], int length, int numsteps) {
        int i;
        for (i=0; i<length;i++) {
            edges[i].path = createPath(edges[i].U.getPosition(), new Vector2D(Xpos[i] - EDGE_WIDTH / 2, Ypos[i]),numsteps);
        }
    }

    protected void moveAlongPath(EdgeClass edges[],int length, int numsteps) {
        int i,j;
        for (j=0; j<numsteps;j++) {
            for (i=0; i<length;i++){
                edges[i].U.moveToPosition(edges[i].path[j]);
                edges[i].V.moveToPosition(edges[i].path[j]);
                edges[i].V.move(EDGE_WIDTH,0);
            }
            repaintwaitmin();
        }
    }


    protected void switch_to_large_graph() {
        super.switch_to_large_graph();
        indep_set_width = LARGE_INDEP_SET_WIDTH;
        indep_set_height = LARGE_INDEP_SET_HEIGHT;
        indep_set_initial_x = LARGE_INDEP_SET_INITIAL_X;
        indep_set_initial_y = LARGE_INDEP_SET_INITIAL_Y;
        indep_set_index_x = LARGE_INDEP_SET_INDEX_X;



    }

    protected void switch_to_small_graph() {
        super.switch_to_small_graph();
        indep_set_width = SMALL_INDEP_SET_WIDTH;
        indep_set_height = SMALL_INDEP_SET_HEIGHT;
        indep_set_initial_x = SMALL_INDEP_SET_INITIAL_X;
        indep_set_initial_y = SMALL_INDEP_SET_INITIAL_Y;
        indep_set_index_x = SMALL_INDEP_SET_INDEX_X;




    }


    protected void kruskal() {
        int i, j;
        numedges = 0;

        for (i=0;i<size;i++) {
            IndepSet[i] = createRectangle("-1",indep_set_initial_x,indep_set_initial_y + i*indep_set_height,indep_set_width,indep_set_height,false);
            ISindex[i] = createLabel(String.valueOf(i),indep_set_index_x, indep_set_initial_y + i*indep_set_height,false);
            ISindex[i].setLabelColor(Color.BLUE);
            YposIS[i] =  indep_set_initial_y + i*indep_set_height;
        }
        for (i=0;i<size;i++)
            for (j=0;j<size;j++)
                setEdgeColor(i,j,Color.LIGHT_GRAY);



        for (i = 0; i < size; i++) {
            for (j = i + 1; j < size; j++) {
                if (cost[i][j] < Integer.MAX_VALUE) {
                    EdgesList[numedges] = new EdgeClass();
                    EdgesList[numedges].u = i;
                    EdgesList[numedges].v = j;
                    EdgesList[numedges].U = createCircle(String.valueOf(i), Xpos[numedges] - EDGE_WIDTH / 2, Ypos[numedges]);
                    EdgesList[numedges].U.setRadius(10);
                    EdgesList[numedges].U.setColor(Color.WHITE);
                    EdgesList[numedges].V = createCircle(String.valueOf(j), Xpos[numedges] + EDGE_WIDTH / 2, Ypos[numedges]);
                    EdgesList[numedges].V.setRadius(10);
                    EdgesList[numedges].V.setColor(Color.WHITE);
                    EdgesList[numedges].edge = createLink(EdgesList[numedges].U, EdgesList[numedges].V, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER,
                                                          String.valueOf(cost[i][j]), 0);
                    EdgesList[numedges].edge.setArrowVisible(false);
                    EdgesList[numedges].cost = cost[i][j];
                    numedges++;
                }

            }
        }
        sortEdges(EdgesList,numedges);
        setPath(EdgesList,numedges,40);
        moveAlongPath(EdgesList,numedges,40);



        int treesize = 0;
        while(numedges > 0 && treesize < size-1) {
            GElementLabel animateLabel;
            EdgesList[0].U.setLabelColor(Color.RED);
            EdgesList[0].V.setLabelColor(Color.RED);
            EdgesList[0].edge.setLabelColor(Color.RED);
            EdgesList[0].edge.setColor(Color.RED);
            setEdgeColor(EdgesList[0].u,EdgesList[0].v,Color.RED);
            GElementLabel ulab = createLabel("Set("+EdgesList[0].u+")=",-10,-10);
            LineupHorizontal(new Vector2D(110,20),ulab);
            int uset = find(IndepSet,EdgesList[0].u);
            animateLabel = createLabel(String.valueOf(uset),ISindex[uset].getPositionX(),ISindex[uset].getPositionY(),false);
            AnimatePath(animateLabel, animateLabel.getPosition(), new Vector2D(155,20),20);
            removeAny(animateLabel);
            ulab.setLabel(ulab.getLabel() + uset);
            LineupHorizontal(new Vector2D(110,20),ulab);
            repaintwait();
            GElementLabel vlab = createLabel("Set("+EdgesList[0].v+")=",-10,-10);
            LineupHorizontal(new Vector2D(200,20),vlab);
            int vset = find(IndepSet,EdgesList[0].v);
            animateLabel = createLabel(String.valueOf(vset),ISindex[vset].getPositionX(),ISindex[vset].getPositionY(),false);
            AnimatePath(animateLabel, animateLabel.getPosition(), new Vector2D(245,20),20);
            removeAny(animateLabel);

            vlab.setLabel(vlab.getLabel() + vset);
            LineupHorizontal(new Vector2D(200,20),vlab);
            repaintwait();
            if (uset == vset) {
                setEdgeColor(EdgesList[0].u,EdgesList[0].v,Color.LIGHT_GRAY);
            }   else {
                setEdgeColor(EdgesList[0].u,EdgesList[0].v,Color.BLACK);
                int usize = Integer.parseInt(IndepSet[uset].getLabel());
                int vsize = Integer.parseInt(IndepSet[vset].getLabel());
                if (usize < vsize) {
                    animateLabel = createLabel(IndepSet[vset].getLabel(), IndepSet[vset].getPositionX(), IndepSet[vset].getPositionY(),false);
                    IndepSet[vset].setLabel("");
                    AnimatePath(animateLabel,animateLabel.getPosition(), IndepSet[uset].getPosition(),20);
                    removeAny(animateLabel);
                    IndepSet[uset].setLabel(String.valueOf(usize+vsize));
                    repaintwait();
                    IndepSet[vset].setLabel(String.valueOf(uset));
                }   else {
                    animateLabel = createLabel(IndepSet[uset].getLabel(), IndepSet[uset].getPositionX(), IndepSet[uset].getPositionY(),false);
                    IndepSet[uset].setLabel("");
                    AnimatePath(animateLabel,animateLabel.getPosition(), IndepSet[vset].getPosition(),20);
                    removeAny(animateLabel);
                    IndepSet[vset].setLabel(String.valueOf(usize+vsize));
                    repaintwait();
                    IndepSet[uset].setLabel(String.valueOf(vset));

                }
                treesize++;
            }
            removeAny(ulab);
            removeAny(vlab);
            removeAny(EdgesList[0].edge);
            removeAny(EdgesList[0].U);
            removeAny(EdgesList[0].V);
            for (i=1; i<numedges;i++)
                EdgesList[i-1] =EdgesList[i];
            numedges--;
            sortEdges(EdgesList,numedges);
            setPath(EdgesList,numedges,40);
            moveAlongPath(EdgesList,numedges,40);
        }

        for (i=0;i<size;i++) {
            HoldoverGraphics.add(IndepSet[i]);
            HoldoverGraphics.add(ISindex[i]);
        }
        for (i=0;i<numedges;i++) {
            HoldoverGraphics.add(EdgesList[i].edge);
            HoldoverGraphics.add(EdgesList[i].U);
            HoldoverGraphics.add(EdgesList[i].V);

        }



    }

    protected int find(GElement IndepSet[],int elem) {
        GElementArrow emph = createArrow(5,YposIS[elem],indep_set_index_x -15,YposIS[elem],10);
        int newelem;
        IndepSet[elem].setLabelColor(Color.RED);
        repaintwait();
        while (Integer.parseInt(IndepSet[elem].getLabel()) > 0) {
            newelem = Integer.parseInt(IndepSet[elem].getLabel());
            AnimatePath(emph, emph.getPosition(), new Vector2D(5,YposIS[newelem]),20);
            IndepSet[elem].setLabelColor(Color.BLACK);
            elem = newelem;
            IndepSet[elem].setLabelColor(Color.RED);
            repaintwait();
        }
        IndepSet[elem].setLabelColor(Color.BLACK);
        removeAny(emph);
        return elem;

    }


    protected void setEdgeColor(int i, int j, Color clr) {
        if (viewingType == VIEWLOGICAL && edges[i][j] != null) {
            edges[i][j].setColor(clr);
            edges[i][j].setLabelColor(clr);

        } else if (viewingType == VIEWINTERNALLIST && EdgesL[i][j] != null) {
            EdgesL[i][j].setColor(clr);
            EdgesL[i][j].setLabel2Color(clr);
        } else if (viewingType == VIEWINTERNALARRAY) {
            matrix[i][j].setLabelColor(clr);
        }

    }

    protected void setVertexColor(int v, Color clr) {
        if (viewingType == VIEWLOGICAL) {
            nodes[v].setColor(clr);

        } else if (viewingType == VIEWINTERNALLIST) {
            listH[v].setColor(clr);
        } else if (viewingType == VIEWINTERNALARRAY) {

        }


    }

    protected class EdgeClass {
        int u, v;
        int cost;
        DSShapeCircle U, V;
        DSShapeLink edge;
        int newX,newY;
        Vector2D path[];
    }


}
