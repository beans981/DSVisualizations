package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;

import java.awt.*;


public class DSViewDijkstra extends DSViewGraph {


    protected final int START_TABLE_X_SMALL = 50;
    protected final int START_TABLE_Y_SMALL = 100;
    protected final int VERTEX_BOX_WIDTH_SMALL = 50;
    protected final int KNOWN_BOX_WIDTH_SMALL = 50;
    protected final int COST_BOX_WIDTH_SMALL = 50;
    protected final int PATH_BOX_WIDTH_SMALL = 50;
    protected final int BOX_HEIGHT_SMALL = 30;

    protected final int START_TABLE_X_LARGE = 50;
    protected final int START_TABLE_Y_LARGE = 70;
    protected final int VERTEX_BOX_WIDTH_LARGE = 50;
    protected final int KNOWN_BOX_WIDTH_LARGE = 50;
    protected final int COST_BOX_WIDTH_LARGE = 50;
    protected final int PATH_BOX_WIDTH_LARGE = 50;
    protected final int BOX_HEIGHT_LARGE = 20;


    protected int StartTableX = START_TABLE_X_SMALL;
    protected int StartTableY = START_TABLE_Y_SMALL;
    protected int VertexBoxWidth = VERTEX_BOX_WIDTH_SMALL;
    protected int KnownBoxWidth = KNOWN_BOX_WIDTH_SMALL;
    protected int CostBoxWidth = COST_BOX_WIDTH_SMALL;
    protected int PathBoxWidth = PATH_BOX_WIDTH_SMALL;
    protected int BoxHeight = BOX_HEIGHT_SMALL;

    protected GElement[] VertexBox;
    protected GElement[] KnownBox;
    protected GElement[] CostBox;
    protected GElement[] PathBox;
    protected int Dcost[];

    public final static int DIJKSTRA = 5;

    public DSViewDijkstra() {
        super();


    }


    protected void CallFunction(int function, Object param) {
        switch (function) {
            case DIJKSTRA:
                dijkstra(((Integer) param).intValue());
                break;
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

    protected int getNext() {
        int smallest = Integer.MAX_VALUE;
        int nextindex = -1;

        for (int i = 0; i < size; i++) {
            if ((KnownBox[i].getLabel().compareTo("true") != 0) &&
                (Dcost[i] < smallest)) {
                smallest = Dcost[i];
                nextindex = i;
            }
        }

        return nextindex;
    }


    protected void switch_to_large_graph() {
        RemoveTable();
        super.switch_to_large_graph();
        StartTableX = START_TABLE_X_LARGE;
          StartTableY = START_TABLE_Y_LARGE;
          VertexBoxWidth = VERTEX_BOX_WIDTH_LARGE;
          KnownBoxWidth = KNOWN_BOX_WIDTH_LARGE;
          CostBoxWidth = COST_BOX_WIDTH_LARGE;
          PathBoxWidth = PATH_BOX_WIDTH_LARGE;
          BoxHeight = BOX_HEIGHT_LARGE;

    }

    protected void switch_to_small_graph() {
        RemoveTable();
          super.switch_to_small_graph();
        StartTableX = START_TABLE_X_SMALL;
          StartTableY = START_TABLE_Y_SMALL;
          VertexBoxWidth = VERTEX_BOX_WIDTH_SMALL;
          KnownBoxWidth = KNOWN_BOX_WIDTH_SMALL;
          CostBoxWidth = COST_BOX_WIDTH_SMALL;
          PathBoxWidth = PATH_BOX_WIDTH_SMALL;
          BoxHeight = BOX_HEIGHT_SMALL;

    }


    protected void dijkstra(int startvertex) {
        int i, j;
        int next;

        VertexBox = new GElement[size + 1];
        KnownBox = new GElement[size + 1];
        CostBox = new GElement[size + 1];
        PathBox = new GElement[size + 1];
        Dcost = new int[size];


        BuildTable();
        CostBox[startvertex].setLabel("0");
        Dcost[startvertex] = 0;

        for (i = 0; i < size; i++) {
            next = getNext();
            if (next == -1) break;
            GElementArrow arrow = createArrow(StartTableX-20,StartTableY + (next + 1) * BoxHeight,StartTableX,StartTableY + (next + 1) * BoxHeight,10,false);
            KnownBox[next].setLabel("true");
            setVertexColor(next, Color.RED);
            repaintwait();
            for (j = 0; j < size; j++) {

                if (cost[next][j] < Integer.MAX_VALUE) {
                    GElementLabel l;

                    CostBox[j].setLabelColor(Color.RED);
                    CostBox[next].setLabelColor(Color.RED);
                    if (Dcost[j] > Dcost[next] + cost[next][j]) {
                        l = createLabel(Dcost[next] + " + " + cost[next][j] + " < "+ CostBox[j].getLabel() ,-10,-10);
                    } else {
                        l = createLabel("!("+Dcost[next] + " + " + cost[next][j] + " < "+ CostBox[j].getLabel()+")" ,-10,-10);
                    }
                    LineupHorizontal(new Vector2D(StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth + PathBoxWidth+4,
                                                  StartTableY + (j + 1) * BoxHeight),l);

                    setEdgeColor(next, j, Color.RED);
                    repaintwait();
                    if (Dcost[j] > Dcost[next] + cost[next][j]) {
                        Dcost[j] = Dcost[next] + cost[next][j];
                        CostBox[j].setLabel(String.valueOf(Dcost[j]));
                        PathBox[j].setLabel(String.valueOf(next));
                        repaintwait();
                    }
                    setEdgeColor(next, j, Color.BLACK);
                    removeAny(l);
                    CostBox[j].setLabelColor(Color.BLACK);
                    CostBox[next].setLabelColor(Color.BLACK);
                } else if (viewingType == VIEWINTERNALARRAY) {
                    setEdgeColor(next,j,Color.RED);
                    repaintwait();
                    setEdgeColor(next,j,Color.BLACK);
                }
            }
            setVertexColor(next, Color.BLACK);
            removeAny(arrow);

        }


        GElementLabel paths[] = new GElementLabel[size];
        GElementArrow arrow = createArrow(StartTableX-20,StartTableY + (0) * BoxHeight,StartTableX,StartTableY + (0) * BoxHeight,10,false);
        GElementLabel tmp;
        for (i=0; i<size; i++) {
            paths[i] = createLabel("Path: ",-10,-10);
            LineupHorizontal(new Vector2D(StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth + PathBoxWidth+15,
                                          StartTableY + (i + 1) * BoxHeight),paths[i]);
            AnimatePath(arrow,arrow.getPosition(),new Vector2D(StartTableX-20,StartTableY + (i+1) * BoxHeight),10);
            tmp = createLabel(String.valueOf(i),VertexBox[i].getPositionX(),VertexBox[i].getPositionY(),false);
            AnimatePath(tmp,tmp.getPosition(), new Vector2D(StartTableX + VertexBoxWidth+KnownBoxWidth+CostBoxWidth+PathBoxWidth,StartTableY + (i+1)*BoxHeight),15);
            paths[i].setLabel("Path: " + String.valueOf(i) + paths[i].getLabel().substring(6,paths[i].getLabel().length()));
            LineupHorizontal(new Vector2D(StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth + PathBoxWidth+15,
                                          StartTableY + (i + 1) * BoxHeight),paths[i]);
            next = i;
            removeAny(tmp);
            repaintwait();
            PathBox[next].setLabelColor(Color.RED);
            AnimatePath(arrow,arrow.getPosition(),new Vector2D(StartTableX-20,StartTableY + (next+1) * BoxHeight),10);
            repaintwait();
            while (Integer.parseInt(PathBox[next].getLabel()) != -1) {
                PathBox[next].setLabelColor(Color.BLACK);
                tmp = createLabel(PathBox[next].getLabel(),PathBox[next].getPositionX(), PathBox[next].getPositionY(),false);
                AnimatePath(tmp,tmp.getPosition(), new Vector2D(StartTableX + VertexBoxWidth+KnownBoxWidth+CostBoxWidth+PathBoxWidth+45,StartTableY + (i+1)*BoxHeight),15);
                paths[i].setLabel("Path: " + tmp.getLabel() + " " + paths[i].getLabel().substring(6,paths[i].getLabel().length()));
                LineupHorizontal(new Vector2D(StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth + PathBoxWidth+15,
                                              StartTableY + (i + 1) * BoxHeight),paths[i]);
                next = Integer.parseInt(PathBox[next].getLabel());
                removeAny(tmp);
                PathBox[next].setLabelColor(Color.RED);
                AnimatePath(arrow,arrow.getPosition(),new Vector2D(StartTableX-20,StartTableY + (next+1) * BoxHeight),10);
                repaintwait();
                PathBox[next].setLabelColor(Color.BLACK);
            }
            PathBox[next].setLabelColor(Color.BLACK);


        }
        removeAny(arrow);

        for(i=0; i<size;i++)
            HoldoverGraphics.add(paths[i]);



        for (i = 0; i < size + 1; i++) {
            HoldoverGraphics.add(VertexBox[i]);
            HoldoverGraphics.add(KnownBox[i]);
            HoldoverGraphics.add(CostBox[i]);
            HoldoverGraphics.add(PathBox[i]);
        }

    }


    protected void RemoveTable() {
        int i;
        if (VertexBox == null)
            return;
        for (i = 0; i < size + 1; i++) {
            removeAny(VertexBox[i]);
            removeAny(KnownBox[i]);
            removeAny(CostBox[i]);
            removeAny(PathBox[i]);
        }
        VertexBox = null;

    }


    protected void setEdgeColor(int i, int j, Color clr) {
        if (viewingType == VIEWLOGICAL) {
            edges[i][j].setColor(clr);
            edges[i][j].setLabelColor(clr);

        } else if (viewingType == VIEWINTERNALLIST) {
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

    protected void BuildTable() {
        int i;
        for (i = 0; i < size; i++) {
            VertexBox[i] = createRectangle(String.valueOf(i), StartTableX + VertexBoxWidth / 2, StartTableY + (i + 1) * BoxHeight, VertexBoxWidth, BoxHeight);
            VertexBox[i].setLabelColor(Color.BLUE);
            KnownBox[i] = createRectangle("false", StartTableX + VertexBoxWidth + KnownBoxWidth / 2,
                                          StartTableY + (i + 1) * BoxHeight, KnownBoxWidth, BoxHeight);
            CostBox[i] = createRectangle("inf", StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth / 2,
                                         StartTableY + (i + 1) * BoxHeight, CostBoxWidth, BoxHeight);
            PathBox[i] = createRectangle("-1", StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth + PathBoxWidth / 2,
                                         StartTableY + (i + 1) * BoxHeight, CostBoxWidth, BoxHeight);
            Dcost[i] = Integer.MAX_VALUE;

        }
        VertexBox[size] = createRectangle("Vertex", StartTableX + VertexBoxWidth / 2, StartTableY, VertexBoxWidth, BoxHeight);
        KnownBox[size] = createRectangle("Known", StartTableX + VertexBoxWidth + KnownBoxWidth / 2, StartTableY, KnownBoxWidth, BoxHeight);
        CostBox[size] = createRectangle("Cost", StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth / 2, StartTableY, CostBoxWidth, BoxHeight);
        PathBox[size] = createRectangle("Path", StartTableX + VertexBoxWidth + KnownBoxWidth + CostBoxWidth + PathBoxWidth / 2, StartTableY, CostBoxWidth, BoxHeight);
    }


}
