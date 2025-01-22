package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;


public class DSViewBFS extends DSViewGraphDirected {


    protected final int DFS_INITIAL_X = 50;
    protected final int DFS_INITIAL_Y = 50;
    protected final int DFS_DELTA_X = 20;
    protected final int DFS_DELTA_Y = 20;

    private int DFS_yoffset_correct = 0;

    protected int VertexColor[] = new int[20];
    protected int discover[] = new int[20];
    protected int finish[] = new int[20];
    protected int time = 0;
    private int labelindex = 0;
    protected boolean showDF = false;


    int parent[] = new int[30];

    int QXLoc[];
    int QYLoc[];

    private final int WHITE = 0;
    private final int GREY = 1;
    private final int BLACK = 2;


    public final static int BFS = 8;
    public final static int CHANGETODIRECTED = 9;
    public final static int CHANGETOUNDIRECTED = 10;


    public DSViewBFS() {
        super();
        undirected=true;
        removeOld();
        randomize();
        int i;

        QXLoc = new int[LARGESIZE];
        QYLoc = new int[LARGESIZE];

        for (i=0; i<LARGESIZE;i++) {
            QXLoc[i] = 30 + i*20;
            QYLoc[i] = 80;
        }

        createLabel("BFS QUEUE",70,57);
        createRectangle("",80,70,100,1);


    }


    protected void CallFunction(int function, Object param) {
        switch (function) {
            case BFS:
                clearLabels();
                bfs(((Integer) param).intValue());
                break;
        }

    }


    protected void CallFunction(int function) {
        switch (function) {
            case RANDOMIZE:
                clearLabels();
                randomize();
                break;
            case VIEWLOGICAL:
                clearLabels();
                switch_to_logical();
                break;
            case VIEWINTERNALLIST:
                clearLabels();
                switch_to_internal_list();
                break;
            case VIEWINTERNALARRAY:
                clearLabels();
                switch_to_internal_array();
                break;
            case CHANGETOLARGE:
                clearLabels();
                switch_to_large_graph();
                break;
            case CHANGETOSMALL:
                clearLabels();
                switch_to_small_graph();
                break;
            case CHANGETODIRECTED:
                clearLabels();
                switch_to_directed();
                break;
            case CHANGETOUNDIRECTED:
                clearLabels();
                switch_to_undirected();
                break;


        }
    }



    protected void switch_to_internal_array() {
        super.switch_to_internal_array();

    }


    protected void switch_to_internal_list() {
        super.switch_to_internal_list();
    }


    protected void switch_to_logical() {
        super.switch_to_logical();

    }

    void clearLabels() {
        int i;

        if (undirected && viewingType == VIEWLOGICAL) {
            removeOld();
            BuildLogical();
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


    protected void switch_to_large_graph() {
        super.switch_to_large_graph();
    }


    protected void switch_to_small_graph() {
        super.switch_to_small_graph();
    }


    protected void bfs(int sv) {
        int queue[] = new int[30];
        GElementLabel queuelabels[] = new GElementLabel[30];
        int queuetop = 0;
        int i,j;
        int next;


        for (i = 0; i < size; i++)
            for (j = 0; j < size; j++)
                if (cost[i][j] > 0 && cost[i][j] < Integer.MAX_VALUE)
                    setEdgeColor(i, j, Color.BLACK);



        queue[queuetop] = sv;
        queuelabels[queuetop] = createLabel(Integer.toString(sv),QXLoc[queuetop],QYLoc[queuetop]);
        queuetop++;

        for (i = 0; i < size; i++) {
            parent[i] = -1;
            VertexColor[i] = WHITE;
        }


        while (queuetop > 0) {
            next = queue[0];
            queuelabels[0].setLabelColor(Color.RED);
            setVertexColor(next, Color.RED);
            repaintwait();
            VertexColor[next] = BLACK;
            for (i = 0; i < size; i++) {
                if (cost[next][i] > 0 && cost[next][i] < Integer.MAX_VALUE) {
                    if (undirected) {
                        if (parent[next] != i) {
                            setEdgeColor(next, i, Color.RED);
                            repaintwait();
                            if (VertexColor[i] == WHITE) {
                                if (viewingType == VIEWLOGICAL) {
                                    removeAny(edges[next][i]);
                                    int flat = flatness[next][i];
                                    if (flat == 0) flat = -flatness[i][next];
                                    edges[next][i] = createLink(nodes[next], nodes[i], GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER,
                                                                "", flat);
                                    setEdgeColor(next, i, Color.BLUE);
                                    edges[i][next] = edges[next][i];

                                    parent[i] = next;
                                    queue[queuetop] = i;
                                    queuelabels[queuetop]  = createLabel(Integer.toString(i),nodes[i].getPosition());
                                    AnimatePath(queuelabels[queuetop],queuelabels[queuetop].getPosition(),new Vector2D(QXLoc[queuetop],QYLoc[queuetop]),20);
                                    queuetop++;
                                    VertexColor[i] = GREY;
                                    repaintwait();

                                } else {
                                    setEdgeColor(next, i, Color.BLUE);
                                    parent[i] = next;
                                    queue[queuetop] = i;
                                    queuelabels[queuetop] =  createLabel(Integer.toString(i),QXLoc[queuetop],QYLoc[queuetop]);
                                    queuetop++;
                                    VertexColor[i] = GREY;

                                    repaintwait();
                                }
                            } else {
                               // GElementLabel l = createLabel("Vertex " + i + " already visited", DFS_INITIAL_X + xoffset * DFS_DELTA_X + 75,
                               //                               DFS_INITIAL_Y + yoffset * DFS_DELTA_Y + DFS_yoffset_correct);
                               // repaintwait();
                                //removeAny(l);

                                setEdgeColor(next, i, Color.BLACK);

                            }
                        }
                    } else {
                        setEdgeColor(next, i, Color.RED);
                        repaintwait();
                        if (VertexColor[i] == WHITE) {
                            setEdgeColor(next, i, Color.BLUE);
                            parent[i] = next;
                            queue[queuetop] = i;
                            queuelabels[queuetop]  = createLabel(Integer.toString(i),nodes[i].getPosition());
                            AnimatePath(queuelabels[queuetop],queuelabels[queuetop].getPosition(),new Vector2D(QXLoc[queuetop],QYLoc[queuetop]),20);
                            queuetop++;
                            VertexColor[i] = GREY;

                            repaintwait();
                        } else {
                            // GElementLabel l = createLabel("Vertex " + i + " already visited", DFS_INITIAL_X + xoffset * DFS_DELTA_X + 75,
                             //                             DFS_INITIAL_Y + yoffset * DFS_DELTA_Y + DFS_yoffset_correct);
                            // repaintwait();
                            // removeAny(l);

                            setEdgeColor(next, i, Color.BLACK);

                        }
                    }
                }

            }

            setVertexColor(next, Color.BLACK);
            removeAny(queuelabels[0]);
            for (i = 0; i < queuetop; i++) {
                queue[i] = queue[i + 1];
                queuelabels[i] = queuelabels[i+1];
            }
            queuetop--;
            moveQueue(queuelabels,queuetop);



        }


    }

    private void moveQueue(GElementLabel[] queuelabels, int queuetop) {
        int i,j;
        Vector2D path[][] = new Vector2D[queuetop][];
        for (i=0; i<queuetop;i++)
            path[i] = createPath(queuelabels[i].getPosition(), new Vector2D(QXLoc[i],QYLoc[i]),20);

         for (j=0; j<20;j++) {
                  for (i=0; i<queuetop;i++){
                      queuelabels[i].moveToPosition(path[i][j]);
                  }
                  repaintwaitmin();
         }
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


}
