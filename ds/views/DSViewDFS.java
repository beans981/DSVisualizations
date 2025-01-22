package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;
import edu.usfca.xj.appkit.gview.base.Vector2D;

import java.awt.*;


public class DSViewDFS extends DSViewGraphDirected {


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


    private int dXpos[];
    private int dYpos[];
    private int fXpos[];
    private int fYpos[];
    int parent[] = new int[30];


    private GElementLabel dLabels[] = new GElementLabel[30];
    private GElementLabel fLabels[] = new GElementLabel[30];

    private final int WHITE = 0;
    private final int GREY = 1;
    private final int BLACK = 2;
    private GElement DFSLabels[] = new GElement[50];


    public final static int DFS = 8;
    public final static int CHANGETODIRECTED = 9;
    public final static int CHANGETOUNDIRECTED = 10;
    public final static int SHOWDF=11;
    public final static int HIDEDF=12;

    protected boolean DFScompleted=false;


    protected GElement HighlightCircle;

    private int yoffset = 0;

    public DSViewDFS() {
        super();

        dXpos = new int[]{550, 700, 850, 505, 655, 900, 510, 655, 900, 550, 700, 850};
        dYpos = new int[]{16, 16, 16, 161, 161, 161, 291, 291, 291, 438, 438, 438};

        fXpos = new int[]{550, 700, 850, 505, 655, 900, 510, 655, 900, 550, 700, 850};
        fYpos = new int[]{30, 30, 30, 176, 176, 176, 306, 306, 306, 452, 452, 452};


    }


    protected void CallFunction(int function) {
        switch (function) {
            case DFS:
                clearLabels();
                dfs();
                DFScompleted = true;
                break;
            case RANDOMIZE:
                clearLabels();
                randomize();
                DFScompleted = false;
                break;
            case VIEWLOGICAL:
                clearLabels();
                switch_to_logical();
                DFScompleted = false;
                break;
            case VIEWINTERNALLIST:
                clearLabels();
                switch_to_internal_list();
                DFScompleted = false;
                break;
            case VIEWINTERNALARRAY:
                clearLabels();
                switch_to_internal_array();
                DFScompleted = false;
                break;
            case CHANGETOLARGE:
                clearLabels();
                switch_to_large_graph();
                DFScompleted = false;
                break;
            case CHANGETOSMALL:
                clearLabels();
                switch_to_small_graph();
                DFScompleted = false;
                break;
            case CHANGETODIRECTED:
                clearLabels();
                switch_to_directed();
                DFScompleted = false;
                break;
            case CHANGETOUNDIRECTED:
                clearLabels();
                switch_to_undirected();
                DFScompleted = false;
                break;
            case SHOWDF:
                showDF = true;
                addDFLabels();
                break;

           case HIDEDF:
                showDF = false;
                removeDFLabels();
                break;


        }
    }


    protected void addDFLabels() {
        int i;
        if (DFScompleted) {
            for (i = 0; i < size; i++) {
                dLabels[i] = createLabel("d=" + discover[i], dXpos[i], dYpos[i]);
                fLabels[i] = createLabel("f=" + finish[i], fXpos[i], fYpos[i]);

            }

        }


    }

    protected void removeDFLabels() {
        int i;
        if (DFScompleted) {
            for (i = 0; i < size; i++) {
                removeAny(dLabels[i]);
                dLabels[i] = null;
                removeAny(fLabels[i]);
                fLabels[i] = null;
            }

        }
    }

    protected void switch_to_internal_array() {
        super.switch_to_internal_array();
        Build_d_f();


    }


    protected void switch_to_internal_list() {
        super.switch_to_internal_list();
        Build_d_f();
    }


    protected void switch_to_logical() {
        super.switch_to_logical();
        Build_d_f();

    }

    void clearLabels() {
        int i;
        for (i = 0; i < labelindex; i++)
            if (DFSLabels[i] != null) {
                removeAny(DFSLabels[i]);
                DFSLabels[i] = null;
            }
        for (i = 0; i < size; i++) {
            removeAny(dLabels[i]);
            dLabels[i] = null;
            removeAny(fLabels[i]);
            fLabels[i] = null;

        }
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
        Build_d_f();
    }

    private void Build_d_f() {
        int i;
        if (viewingType == VIEWINTERNALARRAY) {
            if (size == SMALLSIZE) {

                dXpos = new int[SMALLSIZE];
                dYpos = new int[SMALLSIZE];
                fXpos = new int[SMALLSIZE];
                fYpos = new int[SMALLSIZE];
                for (i = 0; i < size; i++) {
                    dXpos[i] = SMALL_MATRIX_INITIAL_X - 40;
                    dYpos[i] = SMALL_MATRIX_INITIAL_Y + (i + 1) * SMALL_MATRIX_HEIGHT - 7;
                    fXpos[i] = SMALL_MATRIX_INITIAL_X - 40;
                    fYpos[i] = SMALL_MATRIX_INITIAL_Y + (i + 1) * SMALL_MATRIX_HEIGHT + 7;
                }
            } else {   /* size == LARGESIZE */
                dXpos = new int[LARGESIZE];
                dYpos = new int[LARGESIZE];
                fXpos = new int[LARGESIZE];
                fYpos = new int[LARGESIZE];
                for (i = 0; i < size; i++) {
                    dXpos[i] = LARGE_MATRIX_INITIAL_X - 60;
                    dYpos[i] = LARGE_MATRIX_INITIAL_Y + (i + 1) * LARGE_MATRIX_HEIGHT;
                    fXpos[i] = LARGE_MATRIX_INITIAL_X - 25;
                    fYpos[i] = LARGE_MATRIX_INITIAL_Y + (i + 1) * LARGE_MATRIX_HEIGHT;
                }


            }
        } else if (viewingType == VIEWINTERNALLIST) {
            if (size == SMALLSIZE) {
                dXpos = new int[SMALLSIZE];
                dYpos = new int[SMALLSIZE];
                fXpos = new int[SMALLSIZE];
                fYpos = new int[SMALLSIZE];

                for (i = 0; i < size; i++) {
                    dXpos[i] = INITIAL_X_L_SMALL - 130;
                    dYpos[i] = INITIAL_Y_L_SMALL + i * DELTA_Y_L_SMALL - 7;
                    fXpos[i] = INITIAL_X_L_SMALL - 130;
                    fYpos[i] = INITIAL_Y_L_SMALL + i * DELTA_Y_L_SMALL + 7;
                }
            } else {   /* size == LARGESIZE */

                dXpos = new int[LARGESIZE];
                dYpos = new int[LARGESIZE];
                fXpos = new int[LARGESIZE];
                fYpos = new int[LARGESIZE];
                for (i = 0; i < size; i++) {
                    dXpos[i] = INITIAL_X_L_LARGE - 146;
                    dYpos[i] = INITIAL_Y_L_LARGE + i * DELTA_Y_L_LARGE;
                    fXpos[i] = INITIAL_X_L_LARGE - 108;
                    fYpos[i] = INITIAL_Y_L_LARGE + i * DELTA_Y_L_LARGE;
                }
            }


        } else {  /* viewingType == VIEWLOGICAL */
            if (size == SMALLSIZE) {

                dXpos = new int[]{550, 700, 850, 505, 655, 900, 510, 655, 900, 550, 700, 850};
                dYpos = new int[]{16, 16, 16, 161, 161, 161, 291, 291, 291, 438, 438, 438};

                fXpos = new int[]{550, 700, 850, 505, 655, 900, 510, 655, 900, 550, 700, 850};
                fYpos = new int[]{30, 30, 30, 176, 176, 176, 306, 306, 306, 452, 452, 452};
            } else { /* size == LARGESIZE */

                dXpos = new int[]{510, 610, 710, 890,
                                  560, 660, 760,
                                  510, 615, 710, 890,
                                  560, 660, 760,
                                  510, 610, 710, 890};
                dYpos = new int[]{90, 90, 90, 90,
                                  165, 165, 165,
                                  240, 240, 240, 240,
                                  315, 315, 315,
                                  390, 390, 390, 390};
                fXpos = new int[]{510, 610, 710, 890,
                                  560, 660, 760,
                                  510, 615, 710, 890,
                                  560, 660, 760,
                                  510, 610, 710, 890};
                fYpos = new int[]{107, 107, 107, 107,
                                  182, 182, 182,
                                  257, 257, 257, 257,
                                  332, 332, 332,
                                  407, 407, 407, 407};


            }


        }


    }

    protected void switch_to_small_graph() {
        super.switch_to_small_graph();
        Build_d_f();
    }


    protected void dfs() {
        int i, j;

        DFS_yoffset_correct = 0;
        for (i = 0; i < size; i++)
            for (j = 0; j < size; j++)
                if (cost[i][j] > 0 && cost[i][j] < Integer.MAX_VALUE)
                    setEdgeColor(i, j, Color.BLACK);

        yoffset = 0;
        labelindex = 0;

        for (i = 0; i < size; i++) {
            parent[i] = -1;
            VertexColor[i] = WHITE;
            discover[i] = -1;
            finish[i] = -1;
            time = 0;

        }
        if (viewingType == VIEWLOGICAL) {
            HighlightCircle = createCircle("",Xpos[0],Ypos[0]);
            HighlightCircle.setColor(Color.RED);
            setVertexColor(0,Color.RED);
        }
        DFS(0, 0);

        for (i = 1; i < size; i++) {
            if (VertexColor[i] == WHITE) {
                DFSLabels[labelindex] = createRectangle("", DFS_INITIAL_X + 100, DFS_INITIAL_Y + yoffset * DFS_DELTA_Y + DFS_yoffset_correct - 3, 200, 1);
                DFSLabels[labelindex].setColor(Color.BLUE);
                DFS_yoffset_correct += 5;
                labelindex++;
                if (viewingType == VIEWLOGICAL) {
                    HighlightCircle.setPosition(Xpos[i],Ypos[i]);
                    setVertexColor(i,Color.RED);
                }
                DFS(i, 0);
            }
        }
        if (viewingType == VIEWLOGICAL) {

            removeAny(HighlightCircle);
        }


    }

    public void DFS(int startvertex, int xoffset) {
        if (viewingType == VIEWLOGICAL) {
            AnimatePath(HighlightCircle,
                        HighlightCircle.getPosition(),
                        new Vector2D(Xpos[startvertex],Ypos[startvertex]),20);
        }

        int adj_x_offset = DFS_INITIAL_X + xoffset * DFS_DELTA_X + (startvertex / 10) * 4;
        DFSLabels[labelindex] = createLabel("DFS(" + startvertex + ")", adj_x_offset, DFS_INITIAL_Y + yoffset * DFS_DELTA_Y + DFS_yoffset_correct);
        yoffset = yoffset + 1;
        labelindex++;
        int i;
        setVertexColor(startvertex, Color.RED);
        if (showDF)
            dLabels[startvertex] = createLabel("d=" + time, dXpos[startvertex], dYpos[startvertex]);
        repaintwait();
        VertexColor[startvertex] = GREY;
        discover[startvertex] = time;
        time++;

        for (i = 0; i < size; i++) {
            if (cost[startvertex][i] > 0 && cost[startvertex][i] < Integer.MAX_VALUE) {
                if (undirected) {
                    if (parent[startvertex] != i) {
                        setEdgeColor(startvertex, i, Color.RED);
                        repaintwait();
                        if (VertexColor[i] == WHITE) {
                            if (viewingType == VIEWLOGICAL) {
                               removeAny(edges[startvertex][i]);
                                int flat = flatness[startvertex][i];
                                if (flat ==0) flat = -flatness[i][startvertex];
                               edges[startvertex][i] = createLink(nodes[startvertex],nodes[i],GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER,
                                                     "", flat);
                               setEdgeColor(startvertex,i,Color.BLUE);
                                edges[i][startvertex] = edges[startvertex][i];
                                setVertexColor(startvertex, Color.BLACK);
                                parent[i] = startvertex;
                                DFS(i, xoffset + 1);
                                if (viewingType == VIEWLOGICAL) {
                                    AnimatePath(HighlightCircle,HighlightCircle.getPosition(), new Vector2D(Xpos[startvertex],Ypos[startvertex]),20);
                                }
                                setVertexColor(startvertex, Color.RED);
                                repaintwait();

                            } else {
                                setEdgeColor(startvertex, i, Color.BLUE);
                                setVertexColor(startvertex, Color.BLACK);
                                parent[i] = startvertex;
                                DFS(i, xoffset + 1);
                                if (viewingType == VIEWLOGICAL) {
                                    AnimatePath(HighlightCircle,HighlightCircle.getPosition(), new Vector2D(Xpos[startvertex],Ypos[startvertex]),20);
                                }
                                setVertexColor(startvertex, Color.RED);
                                repaintwait();
                            }
                        } else {
                            GElementLabel l = createLabel("Vertex " + i + " already visited", DFS_INITIAL_X + xoffset * DFS_DELTA_X + 75,
                                                          DFS_INITIAL_Y + yoffset * DFS_DELTA_Y + DFS_yoffset_correct);
                            repaintwait();
                            removeAny(l);

                            setEdgeColor(startvertex, i, Color.BLACK);

                        }
                    }
                } else {
                    setEdgeColor(startvertex, i, Color.RED);
                    repaintwait();
                    if (VertexColor[i] == WHITE) {
                        setEdgeColor(startvertex, i, Color.BLUE);
                        setVertexColor(startvertex, Color.BLACK);
                        parent[i] = startvertex;
                        DFS(i, xoffset + 1);
                        if (viewingType == VIEWLOGICAL) {
                            AnimatePath(HighlightCircle,HighlightCircle.getPosition(), new Vector2D(Xpos[startvertex],Ypos[startvertex]),20);
                        }

                        setVertexColor(startvertex, Color.RED);
                        repaintwait();
                    } else {
                        GElementLabel l = createLabel("Vertex " + i + " already visited", DFS_INITIAL_X + xoffset * DFS_DELTA_X + 75,
                                                      DFS_INITIAL_Y + yoffset * DFS_DELTA_Y + DFS_yoffset_correct);
                        repaintwait();
                        removeAny(l);

                        setEdgeColor(startvertex, i, Color.BLACK);

                    }
                }

            }
        }


        finish[startvertex] = time;
        if (showDF) {
            fLabels[startvertex] = createLabel("f=" + time, fXpos[startvertex], fYpos[startvertex]);
            repaintwait();
        }
        setVertexColor(startvertex, Color.BLACK);
        time++;

        VertexColor[startvertex] = BLACK;
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
