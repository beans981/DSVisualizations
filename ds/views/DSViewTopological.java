package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;
import edu.usfca.xj.appkit.gview.base.Vector2D;

import java.awt.*;


public class DSViewTopological extends DSViewGraphDirected {


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


    private GElement IndegreLabels[] = new GElementLabel[LARGESIZE];
    private GElement IndegreeBoxes[] = new GElement[LARGESIZE];
    private GElementLabel TopologicalOrder[] = new GElementLabel[LARGESIZE];
    private int numTopological = 0;
    private GElementLabel dLabels[] = new GElementLabel[30];
    private GElementLabel fLabels[] = new GElementLabel[30];
    private int indegree[];
    private GElement indegreeGraphic[];



    private final int WHITE = 0;
    private final int GREY = 1;
    private final int BLACK = 2;
    private GElement DFSLabels[] = new GElement[LARGESIZE*4];


    public final static int TOPOLOGICAL_INDEGREE = 8;
    public final static int TOPOLOGICAL_DFS = 9;

    protected boolean DFScompleted=false;

    private int yoffset = 0;
    private static final int INDEGREE_XPOS = 60;
    private static final int INDEGREE_YSTART = 70;
    private static final double INDEGREE_HEIGHT = 20 ;
    private static final double INDEGREE_WIDTH = 50;

    private static final int STACK_X_START = 150;
    private static final int STACK_X_WIDTH = 20;
    private static final int TOPOLOGICAL_XPOS = 250;

    public DSViewTopological() {
        super();

        DAG = true;
        randomize();
        indegree = new int[LARGESIZE];
        indegreeGraphic = new GElement[LARGESIZE];
        dXpos = new int[]{550, 700, 850, 505, 655, 900, 510, 655, 900, 550, 700, 850};
        dYpos = new int[]{16, 16, 16, 161, 161, 161, 291, 291, 291, 438, 438, 438};

        fXpos = new int[]{550, 700, 850, 505, 655, 900, 510, 655, 900, 550, 700, 850};
        fYpos = new int[]{30, 30, 30, 176, 176, 176, 306, 306, 306, 452, 452, 452};


    }


    protected void CallFunction(int function) {
        switch (function) {
            case TOPOLOGICAL_DFS:
                clearLabels();
                TopologicalDFS();
                break;
            case TOPOLOGICAL_INDEGREE:
                clearLabels();
                TopologicalIndegree();
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


        }
    }

    private void TopologicalIndegree() {
        int i,j;

        GElementLabel indicies[] = new GElementLabel[size];
        GElementLabel stackGraphic[] = new GElementLabel[size];
        int stack[] = new int[size];

        int stacktop = 0;


        labelindex = 0;
        DFSLabels[labelindex++] = createLabel("Indegree",INDEGREE_XPOS,INDEGREE_YSTART-INDEGREE_HEIGHT-4);
        for (i=0; i<size; i++) {

            indegree[i] = 0;
            indegreeGraphic[i]  = createRectangle("0",INDEGREE_XPOS,INDEGREE_YSTART+i*INDEGREE_HEIGHT,INDEGREE_WIDTH,INDEGREE_HEIGHT);
            DFSLabels[labelindex++] = indegreeGraphic[i];
            DFSLabels[labelindex] = createLabel(Integer.toString(i) ,INDEGREE_XPOS-INDEGREE_WIDTH/2-10,INDEGREE_YSTART+i*INDEGREE_HEIGHT);
            DFSLabels[labelindex].setLabelColor(Color.BLUE);
            indicies[i] = (GElementLabel) DFSLabels[labelindex];
            labelindex++;
        }
        for (i=0;i<size;i++) {
            setVertexColor(i,Color.RED);
            for (j=0; j<size;j++) {
                if (cost[i][j] < Integer.MAX_VALUE) {
                    setEdgeColor(i,j,Color.RED);
                    repaintwait();
                    indegree[j]++;
                    indegreeGraphic[j].setLabel(Integer.toString(indegree[j]));
                    indegreeGraphic[j].setLabelColor(Color.RED);
                    indicies[j].setLabelColor(Color.RED);
                    repaintwait();
                    indicies[j].setLabelColor(Color.BLUE);
                    indegreeGraphic[j].setLabelColor(Color.BLACK);
                    setEdgeColor(i,j,Color.BLACK);
                }

            }
            setVertexColor(i,Color.BLACK);
        }
        DFSLabels[labelindex] = createLabel("Zero Degree Nodes:",INDEGREE_XPOS+20,INDEGREE_YSTART+size*INDEGREE_HEIGHT);
        labelindex++;

        DFSLabels[labelindex++] = createLabel("Topological Order",TOPOLOGICAL_XPOS,INDEGREE_YSTART-INDEGREE_HEIGHT-4);

        for(i=0;i<size;i++) {
            indegreeGraphic[i].setLabelColor(Color.RED);
            repaintwait();
            if (indegree[i] == 0) {
                stackGraphic[stacktop] = createLabel(Integer.toString(i),INDEGREE_XPOS-INDEGREE_WIDTH/2-10,INDEGREE_YSTART+i*INDEGREE_HEIGHT);
                DFSLabels[labelindex++] = stackGraphic[stacktop];
                AnimatePath(stackGraphic[stacktop],stackGraphic[stacktop].getPosition(),
                            new Vector2D(STACK_X_START+stacktop*STACK_X_WIDTH,INDEGREE_YSTART+size*INDEGREE_HEIGHT),30);
                stack[stacktop] = i;
                stacktop++;
               }
            indegreeGraphic[i].setLabelColor(Color.BLACK);
        }

        for(i=0;i<size;i++) {
            stacktop--;
            int vertex = stack[stacktop];
            GElement top = stackGraphic[stacktop];
            top.setLabelColor(Color.RED);
            repaintwait();
            AnimatePath(stackGraphic[stacktop],stackGraphic[stacktop].getPosition(),
                        new Vector2D(TOPOLOGICAL_XPOS,INDEGREE_YSTART+i*INDEGREE_HEIGHT),30);
            setVertexColor(vertex,Color.RED);
            for(j=0;j<size;j++) {
                if (cost[vertex][j] < Integer.MAX_VALUE) {
                    setEdgeColor(vertex,j,Color.RED);
                    repaintwait();
                    indegree[j]--;
                    indegreeGraphic[j].setLabel(Integer.toString(indegree[j]));
                    indegreeGraphic[j].setLabelColor(Color.RED);
                    indicies[j].setLabelColor(Color.RED);
                    repaintwait();
                    indicies[j].setLabelColor(Color.BLUE);
                    indegreeGraphic[j].setLabelColor(Color.BLACK);
                    if (indegree[j] == 0) {
                        stackGraphic[stacktop] = createLabel(Integer.toString(j),INDEGREE_XPOS-INDEGREE_WIDTH/2-10,INDEGREE_YSTART+j*INDEGREE_HEIGHT);
                        DFSLabels[labelindex++] = stackGraphic[stacktop];
                        AnimatePath(stackGraphic[stacktop],stackGraphic[stacktop].getPosition(),
                                    new Vector2D(STACK_X_START+stacktop*STACK_X_WIDTH,INDEGREE_YSTART+size*INDEGREE_HEIGHT),30);
                        stack[stacktop] = j;
                        stacktop++;

                    }
                    setEdgeColor(vertex,j,Color.BLACK);
                }
            }
            top.setLabelColor(Color.BLACK);
            setVertexColor(vertex,Color.BLACK);



        }



    }

    private void TopologicalDFS() {


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
        DFS(0, 0);
        for (i = 1; i < size; i++) {
            if (VertexColor[i] == WHITE) {
                DFSLabels[labelindex] = createRectangle("", DFS_INITIAL_X + 100, DFS_INITIAL_Y + yoffset * DFS_DELTA_Y + DFS_yoffset_correct - 3, 200, 1);
                DFSLabels[labelindex].setColor(Color.BLUE);
                DFS_yoffset_correct += 5;
                labelindex++;
                DFS(i, 0);
            }
        }

    }

    public void DFS(int startvertex, int xoffset) {
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
                    setEdgeColor(startvertex, i, Color.RED);
                    repaintwait();
                    if (VertexColor[i] == WHITE) {
                        setEdgeColor(startvertex, i, Color.BLUE);
                        setVertexColor(startvertex, Color.BLACK);
                        parent[i] = startvertex;
                        DFS(i, xoffset + 1);
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


        finish[startvertex] = time;
        if (showDF) {
            fLabels[startvertex] = createLabel("f=" + time, fXpos[startvertex], fYpos[startvertex]);
            repaintwait();
        }
        addToStack(startvertex);
        setVertexColor(startvertex, Color.BLACK);
        time++;

        VertexColor[startvertex] = BLACK;
    }

    private void addToStack(int vertex) {
        int i;
        for (i=numTopological; i>0; i--);
            TopologicalOrder[i] = TopologicalOrder[i-1];


    }


    protected void setEdgeColor(int i, int j, Color clr) {
        if (viewingType == VIEWLOGICAL) {
            edges[i][j].setColor(clr);
            edges[i][j].setLabelColor(clr);

        } else if (viewingType == VIEWINTERNALLIST) {
            EdgesL[i][j].setColor(clr);
            if (clr.equals(Color.BLACK))
                EdgesL[i][j].setLabelColor(Color.BLUE);
            else
                EdgesL[i][j].setLabelColor(clr);
        //    EdgesL[i][j].setLabel2Color(clr);
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
