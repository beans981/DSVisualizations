package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;


public class DSViewFloyd extends DSViewGraph {


    public int SMALL_EXPLAIN_STRING_X = 500;
    public int SMALL_EXPLAIN_STRING_Y = 400;

    public int LARGE_EXPLAIN_STRING_X = 700;
    public int LARGE_EXPLAIN_STRING_Y = 450;

    public int explain_string_x = SMALL_EXPLAIN_STRING_X;
    public int explain_string_y = SMALL_EXPLAIN_STRING_Y;

    public int D_SMALL_MATRIX_WIDTH = 35;
    public int D_SMALL_MATRIX_HEIGHT = 35;
    public int D_SMALL_MATRIX_INITIAL_X = 20;
    public int D_SMALL_MATRIX_INITIAL_Y = 50;

    public int D_LARGE_MATRIX_WIDTH = 20;
    public int D_LARGE_MATRIX_HEIGHT = 20;
    public int D_LARGE_MATRIX_INITIAL_X = 10;
    public int D_LARGE_MATRIX_INITIAL_Y = 30;

    public int D_SMALL_PATH_WIDTH = 35;
    public int D_SMALL_PATH_HEIGHT = 35;
    public int D_SMALL_PATH_INITIAL_X = 280;
    public int D_SMALL_PATH_INITIAL_Y = 50;

    public int D_LARGE_PATH_WIDTH = 20;
    public int D_LARGE_PATH_HEIGHT = 20;
    public int D_LARGE_PATH_INITIAL_X = 500;
    public int D_LARGE_PATH_INITIAL_Y = 30;




    protected int LARGE_NODE_I_X = 350;
    protected int LARGE_NODE_I_Y = 450;
    protected int LARGE_NODE_J_X = 550;
    protected int LARGE_NODE_J_Y = 450;
    protected int LARGE_NODE_K_X = 450;
    protected int LARGE_NODE_K_Y = 420;

    protected int SMALL_NODE_I_X = 100;
    protected int SMALL_NODE_I_Y = 430;
    protected int SMALL_NODE_J_X = 300;
    protected int SMALL_NODE_J_Y = 430;
    protected int SMALL_NODE_K_X = 200;
    protected int SMALL_NODE_K_Y = 360;

    protected int node_i_x = SMALL_NODE_I_X;
    protected int node_i_y = SMALL_NODE_I_Y;
    protected int node_j_x = SMALL_NODE_J_X;
    protected int node_j_y = SMALL_NODE_J_Y;
    protected int node_k_x = SMALL_NODE_K_X;
    protected int node_k_y = SMALL_NODE_K_Y;


    public int D_matrix_width = D_SMALL_MATRIX_WIDTH;
    public int D_matrix_height = D_SMALL_MATRIX_HEIGHT;
    public int D_matrix_initial_x = D_SMALL_MATRIX_INITIAL_X;
    public int D_matrix_initial_y = D_SMALL_MATRIX_INITIAL_Y;

    public int D_path_width = D_SMALL_PATH_WIDTH;
    public int D_path_height = D_SMALL_PATH_HEIGHT;
    public int D_path_initial_x = D_SMALL_PATH_INITIAL_X;
    public int D_path_initial_y = D_SMALL_PATH_INITIAL_Y;


    protected GElement[] VertexBox;
    protected GElement[] KnownBox;
    protected GElement[] CostBox;
    protected GElement[] PathBox;
    protected int Dcost[];

    protected GElement inode;
    protected GElement knode;
    protected GElement jnode;
    protected GElement ikedge;
    protected GElement kjedge;
    protected GElement ijedge;

    public final static int FLOYD = 9;

    public DSViewFloyd() {
        super();

/* Set new values for location parameters */


        LARGE_LOGICAL_X_SHIFT = 0;
        SMALL_MATRIX_WIDTH = 40;
        SMALL_MATRIX_HEIGHT = 40;
        SMALL_MATRIX_INITIAL_X = 580;
        SMALL_MATRIX_INITIAL_Y = 70;

        LARGE_MATRIX_WIDTH = 25;
        LARGE_MATRIX_HEIGHT = 25;
        LARGE_MATRIX_INITIAL_X = 500;
        LARGE_MATRIX_INITIAL_Y = 10;

        matrix_width = SMALL_MATRIX_WIDTH;
        matrix_height = SMALL_MATRIX_HEIGHT;
        matrix_initial_x = SMALL_MATRIX_INITIAL_X;
        matrix_initial_y = SMALL_MATRIX_INITIAL_Y;


        SMALLSIZE = 6;
        LARGESIZE = 18;


        DELTA_X_L_SMALL = 75;
        DELTA_Y_L_SMALL = 40;

        ELEM_WIDTH_SMALL = DELTA_X_L_SMALL - 20;
        ELEM_HEIGHT_SMALL = DELTA_Y_L_SMALL - 5;


        INITIAL_X_L_SMALL = 640; /* 600?  640? */
        INITIAL_Y_L_SMALL = 70;

        DELTA_X_L_LARGE = 65;
        DELTA_Y_L_LARGE = 24;

        ELEM_WIDTH_LARGE = DELTA_X_L_LARGE - 20;
        ELEM_HEIGHT_LARGE = DELTA_Y_L_LARGE - 4;


        deltaXL = DELTA_X_L_SMALL;
        deltaYL = DELTA_Y_L_SMALL;

        elem_width = ELEM_WIDTH_SMALL;
        elem_height = ELEM_HEIGHT_SMALL;

        INITIAL_Y_L_LARGE = 20;
        INITIAL_X_L_LARGE = 550;
        removeOld();
        create_small_graph();

    }


    protected void CallFunction(int function) {
        switch (function) {
            case FLOYD:
                floyd();
                break;
            case RANDOMIZE:
                randomize();
                break;
            case VIEWLOGICAL:
                switch_to_logical();
                break;
            case VIEWINTERNALLIST:
                switch_to_internal_list();
                break;
            case VIEWINTERNALARRAY:
                switch_to_internal_array();
                break;
            case CHANGETOLARGE:
                switch_to_large_graph();
                break;
            case CHANGETOSMALL:
                switch_to_small_graph();
        }
    }


    protected void floyd() {
        int i, j, k;
        String middlelab;

        if (size == LARGESIZE)
            removeOld();
        GElementLabel DistanceLabel = createLabel("Distance:", D_matrix_initial_x + 50,
                                                  D_matrix_initial_y - 20);
        GElementLabel PathLabel = createLabel("Path:", D_path_initial_x + 50,
                                              D_path_initial_y - 20);
        GElementLabel explainString = createLabel("", explain_string_x, explain_string_y);

        GElement Dmatrix[][];
        int DMatrixVal[][];
        GElement Dmatrixindex[][];

        GElement Dpath[][];
        GElement Dpathindex[][];

        Dmatrix = new GElement[size][];
        DMatrixVal = new int[size][];
        Dmatrixindex = new GElement[size][];
        Dpath = new GElement[size][];
        Dpathindex = new GElement[size][];
        for (i = 0; i < size; i++) {
            Dmatrix[i] = new GElement[size];
            Dpath[i] = new GElement[size];
            Dpathindex[i] = new GElement[size];
            DMatrixVal[i] = new int[size];
            Dmatrixindex[i] = new GElementLabel[2];
        }

        for (i = 0; i < size; i++) {
            Dmatrixindex[i][0] = createLabel(String.valueOf(i), D_matrix_initial_x, D_matrix_height * (i + 1) + D_matrix_initial_y, false);
            Dmatrixindex[i][0].setLabelColor(Color.BLUE);
            Dmatrixindex[i][1] = createLabel(String.valueOf(i), D_matrix_initial_x + D_matrix_width * (i + 1), D_matrix_initial_y, false);
            Dmatrixindex[i][1].setLabelColor(Color.BLUE);

            Dpathindex[i][0] = createLabel(String.valueOf(i), D_path_initial_x, D_path_height * (i + 1) + D_path_initial_y, false);
            Dpathindex[i][0].setLabelColor(Color.BLUE);
            Dpathindex[i][1] = createLabel(String.valueOf(i), D_path_initial_x + D_path_width * (i + 1), D_path_initial_y, false);
            Dpathindex[i][1].setLabelColor(Color.BLUE);
            for (j = 0; j < size; j++) {
                if (i == j)
                    middlelab = "0";
                else if (cost[i][j] == Integer.MAX_VALUE)
                    middlelab = "inf";
                else
                    middlelab = String.valueOf(cost[i][j]);
                DMatrixVal[i][j] = cost[i][j];
                if (i == j)
                    DMatrixVal[i][j] = 0;
                Dmatrix[i][j] = createRectangle(middlelab, D_matrix_width * (j + 1) + D_matrix_initial_x,
                                                D_matrix_height * (i + 1) + D_matrix_initial_y, D_matrix_width, D_matrix_height, false);
                if (cost[i][j] < Integer.MAX_VALUE)
                    middlelab = Integer.toString(i);
                else
                    middlelab = "-1";
                Dpath[i][j] = createRectangle(middlelab, D_path_width * (j + 1) + D_path_initial_x,
                                              D_path_height * (i + 1) + D_path_initial_y, D_path_width, D_path_height, false);
            }
        }
        inode = createCircle("i", node_i_x, node_i_y);
        jnode = createCircle("j", node_j_x, node_j_y);
        knode = createCircle("k", node_k_x, node_k_y);
        ikedge = createLink(inode, knode, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER,
                            "", 10);
        kjedge = createLink(knode, jnode, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER,
                            "", 10);
        if (size==LARGESIZE)
            ijedge = createLink(inode, jnode, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER,
                            "", -1);
        else
            ijedge = createLink(inode, jnode, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER,
                            "", 10);

        for (k = 0; k < size; k++) {
            knode.setLabel(Integer.toString(k));
            for (i = 0; i < size; i++) {
                inode.setLabel(Integer.toString(i));
                for (j = 0; j < size; j++) {
                    jnode.setLabel(Integer.toString(j));
                    ikedge.setLabel(Dmatrix[i][k].getLabel());
                    ikedge.setLabelColor(Color.GREEN);
                    Dmatrix[i][k].setLabelColor(Color.GREEN);
                    kjedge.setLabel(Dmatrix[k][j].getLabel());

                    kjedge.setLabelColor(Color.ORANGE);
                    Dmatrix[k][j].setLabelColor(Color.ORANGE);

                    ijedge.setLabel(Dmatrix[i][j].getLabel());

                    ijedge.setLabelColor(Color.RED);
                    Dmatrix[i][j].setLabelColor(Color.RED);

                    if ((DMatrixVal[i][k] < Integer.MAX_VALUE) &&
                        (DMatrixVal[k][j] < Integer.MAX_VALUE) &&
                        (DMatrixVal[i][k] + DMatrixVal[k][j] < DMatrixVal[i][j])) {
                        explainString.setLabel(Dmatrix[i][k].getLabel() + " + " +
                                               Dmatrix[k][j].getLabel() + " < " +
                                               Dmatrix[i][j].getLabel());
                        repaintwait();

                        GElementLabel costlabel = createLabel(Integer.toString(DMatrixVal[i][k] + DMatrixVal[k][j]),
                                                              explainString.getPosition());
                        GElementLabel nodeLabel = createLabel(Dpath[k][j].getLabel(), Dpath[k][j].getPosition());
                        AnimatePath(costlabel, costlabel.getPosition(), Dmatrix[i][j].getPosition(),
                                    nodeLabel, nodeLabel.getPosition(), Dpath[i][j].getPosition(), 30);
                        removeAny(costlabel);
                        removeAny(nodeLabel);
                        DMatrixVal[i][j] = DMatrixVal[i][k] + DMatrixVal[k][j];
                        Dmatrix[i][j].setLabel(Integer.toString(DMatrixVal[i][j]));
                        Dpath[i][j].setLabel(Dpath[k][j].getLabel());
                    } else {
                        explainString.setLabel("!(" + Dmatrix[i][k].getLabel() + " + " +
                                               Dmatrix[k][j].getLabel() + " < " +
                                               Dmatrix[i][j].getLabel() + ")");
                        repaintwait();

                    }

                    Dmatrix[i][k].setLabelColor(Color.BLACK);
                    Dmatrix[k][j].setLabelColor(Color.BLACK);
                    Dmatrix[i][j].setLabelColor(Color.BLACK);


                }
            }

        }
        explainString.setLabel("");
        ijedge.setLabelColor(Color.BLACK);
        ikedge.setLabelColor(Color.BLACK);
        kjedge.setLabelColor(Color.BLACK);
        for (i = 0; i < size; i++) {
            HoldoverGraphics.add(explainString);
            HoldoverGraphics.add(Dmatrixindex[i][0]);
            HoldoverGraphics.add(Dmatrixindex[i][1]);
            HoldoverGraphics.add(Dpathindex[i][0]);
            HoldoverGraphics.add(Dpathindex[i][1]);
            for (j = 0; j < size; j++) {
                HoldoverGraphics.add(Dmatrix[i][j]);
                HoldoverGraphics.add(Dpath[i][j]);

            }
        }
        HoldoverGraphics.add(ijedge);
        HoldoverGraphics.add(ikedge);
        HoldoverGraphics.add(kjedge);
        HoldoverGraphics.add(inode);
        HoldoverGraphics.add(jnode);
        HoldoverGraphics.add(knode);
        HoldoverGraphics.add(DistanceLabel);
        HoldoverGraphics.add(PathLabel);

    }


    protected void switch_to_large_graph() {

        super.switch_to_large_graph();

        D_matrix_width = D_LARGE_MATRIX_WIDTH;
        D_matrix_height = D_LARGE_MATRIX_HEIGHT;
        D_matrix_initial_x = D_LARGE_MATRIX_INITIAL_X;
        D_matrix_initial_y = D_LARGE_MATRIX_INITIAL_Y;

        D_path_width = D_LARGE_PATH_WIDTH;
        D_path_height = D_LARGE_PATH_HEIGHT;
        D_path_initial_x = D_LARGE_PATH_INITIAL_X;
        D_path_initial_y = D_LARGE_PATH_INITIAL_Y;


        node_i_x = LARGE_NODE_I_X;
        node_i_y = LARGE_NODE_I_Y;
        node_j_x = LARGE_NODE_J_X;
        node_j_y = LARGE_NODE_J_Y;
        node_k_x = LARGE_NODE_K_X;
        node_k_y = LARGE_NODE_K_Y;

        explain_string_x = LARGE_EXPLAIN_STRING_X;
        explain_string_y = LARGE_EXPLAIN_STRING_Y;


    }

    protected void switch_to_small_graph() {
        super.switch_to_small_graph();


        D_matrix_width = D_SMALL_MATRIX_WIDTH;
        D_matrix_height = D_SMALL_MATRIX_HEIGHT;
        D_matrix_initial_x = D_SMALL_MATRIX_INITIAL_X;
        D_matrix_initial_y = D_SMALL_MATRIX_INITIAL_Y;

        D_path_width = D_SMALL_PATH_WIDTH;
        D_path_height = D_SMALL_PATH_HEIGHT;
        D_path_initial_x = D_SMALL_PATH_INITIAL_X;
        D_path_initial_y = D_SMALL_PATH_INITIAL_Y;


        node_i_x = SMALL_NODE_I_X;
        node_i_y = SMALL_NODE_I_Y;
        node_j_x = SMALL_NODE_J_X;
        node_j_y = SMALL_NODE_J_Y;
        node_k_x = SMALL_NODE_K_X;
        node_k_y = SMALL_NODE_K_Y;

        explain_string_x = SMALL_EXPLAIN_STRING_X;
        explain_string_y = SMALL_EXPLAIN_STRING_Y;


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
