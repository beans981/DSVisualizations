package edu.usfca.ds.views;

import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.ds.shapes.DSShapeRect;

import java.awt.*;
import java.util.Vector;


public class DSViewDynamicProg2 extends DSView
{


    protected int amount_table_size = 4;
    protected int d[] = new int[amount_table_size];

    protected final int MAXCHANGE = 150;
    protected final int TABLE_X_OFFSET = 140;
    protected final int TABLE_X_LIMIT = 40;

    protected int TableX[] = new int[MAXCHANGE + 1];
    protected int TableY[] = new int[MAXCHANGE + 1];


    private static int XDIFF_TABLE = 22;
    private static int XSTART_TABLE = 75;
    private static int YSTART_TABLE = 200;
    private static int YDIFF_TABLE = 20;
    private static int DENOM_OFFSET = 40;

    private static int XDIFF_TABLE_MEM = 22;
    private static int XSTART_TABLE_MEM = 430;
    private static int YSTART_TABLE_MEM = 10;
    private static int YDIFF_TABLE_MEM = 20;
    protected final int TABLE_X_LIMIT_MEM = 20;
    // private static int DENOM_OFFSET_MEM = 40;


    private static int XDIFF = 20;
    private static int XSTART = 100;
    private static int YSTART = 130;
    private static int YDIFF = 14;
    private static int PLUSOFFSET = 40;


    protected GElementLabel[] code = new GElementLabel[14];
    protected GElement Labels[] = new GElement[1000];
    protected int labelindex = 0;


    public final static int CHANGE_RECURSIVE = 8;
    public final static int CHANGE_TABLE = 9;
    public final static int CHANGE_MEMOIZED = 10;
    public final static int CHANGE_ERROR = 11;


    private int yoffset = 0;

    public DSViewDynamicProg2()
    {
        super();
        labelindex = 0;
        code[0] = createLabel("int change(int n, int h)", -10, -10, false);
        code[1] = createLabel("if (h == 0)", -10, -10, false);
        code[2] = createLabel("return n / d[0]", -10, -10, false);
        code[3] = createLabel("else", -10, -10, false);
        code[4] = createLabel("best = infinity", -10, -10, false);
        code[5] = createLabel("for i = 0 to n / d[h]", -10, -10, false);
        code[6] = createLabel("best = MIN(best, i + change(n - i * d[h], h - 1))", -10, -10, false);
        code[7] = createLabel("return best", -10, -10, false);

        d[0] = 1;
        d[1] = 4;
        d[2] = 6;
        d[3] = 15;


    }


    protected void ArrangeCode()
    {
        LineupHorizontal(new Vector2D(30, 10), code[0]);
        LineupHorizontal(new Vector2D(40, 24), code[1]);
        LineupHorizontal(new Vector2D(50, 38), code[2]);
        LineupHorizontal(new Vector2D(40, 52), code[3]);
        LineupHorizontal(new Vector2D(50, 66), code[4]);
        LineupHorizontal(new Vector2D(50, 80), code[5]);
        LineupHorizontal(new Vector2D(60, 94), code[6]);
        LineupHorizontal(new Vector2D(50, 108), code[7]);

    }


    protected void CallFunction(int function, Object param)
    {
        switch (function)
        {
            case CHANGE_RECURSIVE:
                clearLabels();
                run_change_recursive(((Integer) param).intValue());
                break;
            case CHANGE_TABLE:
                clearLabels();
                change_table(((Integer) param).intValue());
                break;
            case CHANGE_MEMOIZED:
                clearLabels();
                change_memoized(((Integer) param).intValue());
                break;

        }

    }

    protected void CallFunction(int function)
    {
        switch (function)
        {


            case CHANGE_ERROR:
                clearLabels();
                Labels[labelindex++] = createLabel("Use Buttons <Change: Table> <Change: Recursuve> to start visualziation",
                                                   500, 20);
                Labels[labelindex - 1].setLabelColor(Color.blue);
                break;
        }

    }

    private void run_change_recursive(int input)
    {
        ArrangeCode();
        Labels[labelindex++] = createLabel("change(" + input + ") = ",
                                           XSTART, YSTART - 8, false);
        Labels[labelindex++] = createRectangle("", XSTART, YSTART + 7, 100, 1, false);
        change_recursive(input, amount_table_size - 1, 0, 1);
        repaintwait(1);
        AnimatePath(Labels[2], Labels[2].getPosition(), new Vector2D(XSTART + Labels[0].getFrame().r.width / 2
                                                                     + Labels[2].getFrame().r.width / 2, YSTART - 8), 10);
        removeAny(Labels[1]);
        Labels[1] = Labels[2];
        labelindex--;
    }

    protected void change_memoized(int n)
    {
        ArrangeCode();
        if (n > 99)
        {
            n = 99;
        }

        int currentY = YSTART_TABLE_MEM;


        int i = 0;
        while (i <= MAXCHANGE)
        {
            for (int j = 0; j < TABLE_X_LIMIT_MEM && i <= MAXCHANGE; j++)
            {
                TableX[i] = XSTART_TABLE_MEM + j * XDIFF_TABLE_MEM;
                TableY[i] = currentY;
                i++;
            }
            currentY += YDIFF_TABLE_MEM * 5;
        }


        int Table[][] = new int[amount_table_size][];
        DSShapeRect TableL[][] = new DSShapeRect[amount_table_size][];
        for (i = 0; i < amount_table_size; i++)
        {
            TableL[i] = new DSShapeRect[n + 1];
            Table[i] = new int[n + 1];
        }
        GElementLabel indicies[] = new GElementLabel[n + 1 + amount_table_size];
        setupTables(TableL, indicies, Table, n);

        Labels[labelindex++] = createLabel("change(" + n + ") = ",
                                           XSTART, YSTART - 8, false);
        Labels[labelindex++] = createRectangle("", XSTART, YSTART + 7, 100, 1, false);


        change_recursive_mem(n, amount_table_size - 1, 0, 1, Table, TableL);

        for (i = 0; i <= n; i++)
        {
            for (int j = 0; j < amount_table_size; j++)
            {
                Labels[labelindex++] = TableL[j][i];
            }
            Labels[labelindex++] = indicies[i];
        }
        for (i = 0; i < amount_table_size; i++)
        {
            Labels[labelindex++] = indicies[i + n + 1];
        }


    }

    protected void setupTables(DSShapeRect TableL[][], GElementLabel indicies[], int Table[][], int n)
    {


        for (int j = 0; j <= n; j++)
        {
            indicies[j] = createLabel(Integer.toString(j), TableX[j], TableY[j], false);
            indicies[j].setLabelColor(Color.BLUE);
        }
        for (int i = 0; i < amount_table_size; i++)
        {
            indicies[n + 1 + i] = createLabel("d[" + i + "] = " + d[i], TableX[0] - DENOM_OFFSET, TableY[0] + (i + 1) * YDIFF_TABLE, false);
            indicies[n + 1 + i].setLabelColor(Color.BLUE);
            for (int j = 0; j <= n; j++)
            {
                TableL[i][j] = createRectangle("", TableX[j], TableY[j] + (i + 1) * YDIFF_TABLE,
                                               XDIFF_TABLE, YDIFF_TABLE, false);
                Table[i][j] = -1;


            }
        }

    }

    protected void change_table(int n)
    {
        ArrangeCode();

        int currentY = YSTART_TABLE;


        int i = 0;
        while (i <= MAXCHANGE)
        {
            for (int j = 0; j < TABLE_X_LIMIT && i <= MAXCHANGE; j++)
            {
                TableX[i] = XSTART_TABLE + j * XDIFF_TABLE;
                TableY[i] = currentY;
                i++;
            }
            currentY += YDIFF_TABLE * 5;
        }


        Labels[labelindex++] = createLabel("change(" + n + ") = ",
                                           XSTART, YSTART - 8, false);


        GElementLabel description = createLabel("", TableX[20], TableY[0] - 3 * YDIFF_TABLE, false);
        DSShapeRect TableL[][] = new DSShapeRect[amount_table_size][];
        int Table[][] = new int[amount_table_size][];
        for (i = 0; i < amount_table_size; i++)
        {
            TableL[i] = new DSShapeRect[n + 1];
            Table[i] = new int[n + 1];
        }
        GElementLabel indicies[] = new GElementLabel[n + 1 + amount_table_size];
        setupTables(TableL, indicies, Table, n);

        for (i = 0; i <= n; i++)
        {
            if (i % d[0] == 0)
            {
                Table[0][i] = i / d[0];
                TableL[0][i].setLabel(Integer.toString(Table[0][i]));
                repaintwait();
            }
        }
        for (i = 1; i < amount_table_size; i++)
        {
            for (int j = 0; j <= n; j++)
            {
                int best = Integer.MAX_VALUE;
                for (int k = 0; k <= j / d[i]; k++)
                {
                    int current = k + Table[i - 1][j - k * d[i]];
                    TableL[i - 1][j - k * d[i]].setLabelColor(Color.RED);
                    if (current < best)
                    {
                        best = current;
                    }
                    description.setLabel(Integer.toString(k) + " + " + Table[i - 1][j - k * d[i]] + " = " + current + ", best = " + best);
                    repaintwait();
                    TableL[i - 1][j - k * d[i]].setLabelColor(Color.BLACK);
                }
                Table[i][j] = best;
                TableL[i][j].setLabel(Integer.toString(best));
                repaintwait();
            }
        }
        removeAny(description);

        GElementLabel result = createLabel(Integer.toString(Table[amount_table_size - 1][n]),
                                           TableX[n],
                                           TableY[n] + amount_table_size * YDIFF_TABLE,
                                           false);
        Labels[labelindex++] = result;
        while (result.getFrame().r.width == 0)
        {
            repaintwaitmin(1);
        }
        AnimatePath(result, result.getPosition(), new Vector2D(XSTART + Labels[0].getFrame().r.width / 2
                                                               + result.getFrame().r.width / 2, YSTART - 8), 25);
        for (i = 0; i <= n; i++)
        {
            for (int j = 0; j < amount_table_size; j++)
            {
                Labels[labelindex++] = TableL[j][i];
            }
            Labels[labelindex++] = indicies[i];
        }
        for (i = 0; i < amount_table_size; i++)
        {
            Labels[labelindex++] = indicies[i + n + 1];
        }
    }


    protected long change_recursive_mem(int n, int d, int xoffset, int yoffset, int Table[][], DSShapeRect TableL[][])
    {
        Labels[labelindex++] = createLabel("change(" + n + ", " + d + ")",
                                           xoffset * XDIFF + XSTART,
                                           yoffset * YDIFF + YSTART,
                                           false);
        if (Table[d][n] > 0)
        {
            GElementLabel moveLabel = createLabel(TableL[d][n].getLabel(), TableL[d][n].getPosition(), false);
            AnimatePath(moveLabel, moveLabel.getPosition(), Labels[labelindex - 1].getPosition(), 20);
            removeAny(moveLabel);
            Labels[labelindex - 1].setLabel(Long.toString(Table[d][n]));
            return Table[d][n];

        }

        if (d == 0)
        {
            if (n % this.d[0] == 0)
            {
                removeAny(Labels[--labelindex]);
                Labels[labelindex++] = createLabel(Integer.toString(n / this.d[0]),
                                                   xoffset * XDIFF + XSTART,
                                                   yoffset * YDIFF + YSTART, false);
                Table[d][n] = n / this.d[0];
                GElementLabel moveLabel = createLabel(TableL[d][n].getLabel(), TableL[d][n].getPosition(), false);
                AnimatePath(moveLabel, Labels[labelindex - 1].getPosition(), TableL[d][n].getPosition(), 20);
                removeAny(moveLabel);
                TableL[d][n].setLabel(Integer.toString(Table[d][n]));


                repaintwait();
                return n / this.d[0];
            }
            else
            {
                return Integer.MAX_VALUE;
            }

        }

        int best = Integer.MAX_VALUE;
        for (int i = 0; i <= n / this.d[d]; i++)
        {
            int current = i;
            Labels[labelindex++] = createLabel(Integer.toString(i) + " +",
                                               (xoffset + 1) * XDIFF + XSTART - PLUSOFFSET,
                                               (yoffset + 1 + i) * YDIFF + YSTART,
                                               false);
            current += change_recursive_mem(n - i * this.d[d], d - 1, xoffset + 2, yoffset + 1 + i, Table, TableL);
            if (current < best)
            {
                best = current;
            }
        }

        for (int i = 0; i <= 2 * (n / this.d[d]); i++)
        {
            removeAny(Labels[--labelindex]);
        }
        removeAny(Labels[--labelindex]);
        removeAny(Labels[--labelindex]);
        Labels[labelindex++] = createLabel(Integer.toString(best),
                                           xoffset * XDIFF + XSTART,
                                           yoffset * YDIFF + YSTART,
                                           false);
        repaintwait();


        Table[d][n] = best;
        TableL[d][n].setLabel(Integer.toString(best));
        return best;
    }


    protected long change_recursive(int n, int d, int xoffset, int yoffset)
    {
        code[0].setLabelColor(Color.RED);
        Labels[labelindex++] = createLabel("change(" + n + ", " + d + ")",
                                           xoffset * XDIFF + XSTART,
                                           yoffset * YDIFF + YSTART,
                                           false);
        repaintwait();
        code[0].setLabelColor(Color.BLACK);
        code[1].setLabelColor(Color.RED);
        repaintwait();
        code[1].setLabelColor(Color.BLACK);


        if (d == 0)
        {
            code[2].setLabelColor(Color.RED);
            repaintwait();

            if (n % this.d[0] == 0)
            {
                removeAny(Labels[--labelindex]);
                Labels[labelindex++] = createLabel(Integer.toString(n / this.d[0]),
                                                   xoffset * XDIFF + XSTART,
                                                   yoffset * YDIFF + YSTART, false);
                repaintwait();

                code[2].setLabelColor(Color.BLACK);
                return n / this.d[0];
            }
            else
            {
                code[2].setLabelColor(Color.BLACK);
                return Integer.MAX_VALUE;
            }

        }

        code[4].setLabelColor(Color.RED);
        int best = Integer.MAX_VALUE;
        repaintwait();
        code[4].setLabelColor(Color.BLACK);

        code[5].setLabelColor(Color.RED);
        repaintwait();
        code[5].setLabelColor(Color.BLACK);


        for (int i = 0; i <= n / this.d[d]; i++)
        {
            int current = i;
            Labels[labelindex++] = createLabel(Integer.toString(i) + " +",
                                               (xoffset + 1) * XDIFF + XSTART - PLUSOFFSET,
                                               (yoffset + 1 + i) * YDIFF + YSTART,
                                               false);

            code[6].setLabelColor(Color.RED);
            repaintwait();
            code[6].setLabelColor(Color.BLACK);


            current += change_recursive(n - i * this.d[d], d - 1, xoffset + 2, yoffset + 1 + i);
            if (current < best)
            {
                best = current;
            }
        }
        Vector moveVector = new Vector();
        for (int i = 0; i <= 2 * (1 + (n / this.d[d])); i++)
        {
            moveVector.add(Labels[labelindex - i - 1]);
        }
        AnimateToSameLocation(moveVector, Labels[labelindex - 2 * (n / this.d[d]) - 3].getPosition());


        for (int i = 0; i <= 2 * (n / this.d[d]); i++)
        {
            removeAny(Labels[--labelindex]);
        }
        removeAny(Labels[--labelindex]);
        removeAny(Labels[--labelindex]);
        Labels[labelindex++] = createLabel(Integer.toString(best),
                                           xoffset * XDIFF + XSTART,
                                           yoffset * YDIFF + YSTART,
                                           false);

        code[7].setLabelColor(Color.RED);
        Labels[labelindex-1].setLabelColor(Color.RED);
        repaintwait();

        Labels[labelindex-1].setLabelColor(Color.BLACK);
        code[7].setLabelColor(Color.BLACK);

        return best;
    }

    void clearLabels()
    {
        int i;
        for (i = 0; i < labelindex; i++)
        {
            if (Labels[i] != null)
            {
                removeAny(Labels[i]);
                Labels[i] = null;
            }
        }
        labelindex = 0;
    }


}