package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeRect;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;

import java.awt.*;


public class DSViewDynamicProg extends DSView {


    protected final int MAXFIB = 46;
    protected final int TABLE_X_OFFSET = 140;
    protected final int TABLE_Y_LIMIT = 20;

    protected int TableX[] = new int[MAXFIB+1];
    protected int TableY[] = new int[MAXFIB+1];


    private static int XDIFF_TABLE = 80;
    private static int XSTART_TABLE = 550;
    private static int YSTART_TABLE = 20;
    private static int YDIFF_TABLE = 20;


    private static int XDIFF = 14;
    private static int XSTART = 100;
    private static int YSTART = 100;
    private static int YDIFF = 14;


    protected GElementLabel[] code = new GElementLabel[10];
    protected GElement Labels[] = new GElement[200];
    protected int labelindex = 0;


    public final static int FIB_RECURSIVE = 8;
    public final static int FIB_TABLE = 9;
    public final static int FIB_MEMOIZED = 10;
    public final static int FIB_ERROR = 11;



    private int yoffset = 0;

    public DSViewDynamicProg() {
        super();
        int i;
        int currentX;
        labelindex=0;
        code[0] = createLabel("int fibonacci(int n)",-10,-10, false);
        code[1] = createLabel("if (n < 2)", -10,-10, false);
        code[2] = createLabel("return 1", -10,-10, false);
        code[3] = createLabel("else",-10,-10, false);
        code[4] = createLabel("return ",-10,-10, false);
        code[5] = createLabel("fibonacci(n-1)",-10,-10, false);
        code[6] = createLabel(" + ",-10,-10, false);
        code[7] = createLabel("fibonacci(n-2)",-10,-10, false);

        currentX = XSTART_TABLE;

        i = 0;
        while (i<=MAXFIB) {
            for (int j=0; j < TABLE_Y_LIMIT && i <= MAXFIB;j++) {
                TableX[i] = currentX;
                TableY[i] = YSTART_TABLE + j*YDIFF_TABLE;
                i++;
            }
            currentX += TABLE_X_OFFSET;

        }

    }


    protected void ArrangeCode() {
        LineupHorizontal(new Vector2D(30,10),code[0]);
        LineupHorizontal(new Vector2D(40,24),code[1]);
        LineupHorizontal(new Vector2D(50,38),code[2]);
        LineupHorizontal(new Vector2D(40,52),code[3]);
        LineupHorizontal(new Vector2D(50,66),code[4],code[5],code[6],code[7]);
    }


    protected void CallFunction(int function, Object param) {
        switch (function) {
            case FIB_RECURSIVE:
                clearLabels();
                run_fib_recursive(((Integer) param).intValue());
                break;
                case FIB_TABLE:
                clearLabels();
                fib_table(((Integer) param).intValue());
                break;
                case FIB_MEMOIZED:
                clearLabels();
                fib_memoized(((Integer) param).intValue());
                break;

        }

    }

     protected void CallFunction(int function) {
        switch (function) {


                case FIB_ERROR:
                clearLabels();
                Labels[labelindex++] = createLabel("Use Buttons <Fibonacci: Table> <Fibonacci: Memoized> <Fibonacci: Recursuve> to start visualziation",
                                                   500,20);
                break;
        }

    }

    private void run_fib_recursive(int input) {
        ArrangeCode();
        Labels[labelindex++] = createLabel("fibonacci("+input+") = ",
                                            XSTART,YSTART-8, false);
        Labels[labelindex++] = createRectangle("",XSTART,YSTART+7,100,1, false);
        fib_recursive(input,0,1);
        repaintwait(1);
        AnimatePath(Labels[2],Labels[2].getPosition(),new Vector2D(XSTART+Labels[0].getFrame().r.width/2
                                                                   +Labels[2].getFrame().r.width/2,YSTART-8),10);
        removeAny(Labels[1]);
        Labels[1] = Labels[2];
        labelindex--;
    }

    protected void fib_memoized(int n) {
        ArrangeCode();

        int i;

        DSShapeRect TableL[] = new DSShapeRect[n+1];
        GElementLabel indicies[] = new GElementLabel[n+1];
        long Table[] = new long[n+1];
        setupTables(TableL, indicies, Table, n);
        Labels[labelindex++] = createLabel("fibonacci("+n+") = ",
                                            XSTART,YSTART-8, false);
        Labels[labelindex++] = createRectangle("",XSTART,YSTART+7,100,1, false);
        fib_recursive_m(n,0,1,TableL, indicies,Table);
        repaintwait(1);
        AnimatePath(Labels[2],Labels[2].getPosition(),new Vector2D(XSTART+Labels[0].getFrame().r.width/2
                                                                   +Labels[2].getFrame().r.width/2,YSTART-8),10);
        removeAny(Labels[1]);
        Labels[1] = Labels[2];
        labelindex--;

        for (i=0; i<=n; i++) {
            Labels[labelindex++] = TableL[i];
            Labels[labelindex++] = indicies[i];
        }


    }

    protected void setupTables(DSShapeRect TableL[], GElementLabel indicies[], long Table[], int n) {
        int i;

        for (i=0; i<=n; i++) {
            TableL[i] = createRectangle("",TableX[i], TableY[i],XDIFF_TABLE,
                                        YDIFF_TABLE, false);
            indicies[i] = createLabel(Integer.toString(i),TableX[i]- XDIFF_TABLE/2 - 20,
                                     TableY[i], false);
            indicies[i].setLabelColor(Color.BLUE);
            Table[i] = -1;
        }

    }

    protected void fib_table(int n) {
        ArrangeCode();
        Labels[labelindex++] = createLabel("fibonacci("+n+") = ",
                                            XSTART,YSTART-8, false);



        DSShapeRect TableL[] = new DSShapeRect[n+1];
        GElementLabel indicies[] = new GElementLabel[n+1];
        long Table[] = new long[n+1];
        setupTables(TableL, indicies, Table, n);
        int i;

        Table[0] = 1;
        TableL[0].setLabel("1");
        repaintwait();
        Table[1] = 1;
        TableL[1].setLabel("1");
        repaintwait();
        for (i=2; i<=n; i++) {
            TableL[i-1].setLabelColor(Color.RED);
            TableL[i-2].setLabelColor(Color.RED);
            repaintwait();
            TableL[i-1].setLabelColor(Color.BLACK);
            TableL[i-2].setLabelColor(Color.BLACK);
            Table[i] = Table[i-1] + Table[i-2];
            TableL[i].setLabel(Long.toString(Table[i]));
            repaintwait();
        }
        GElementLabel result = createLabel(Long.toString(Table[n]), TableX[n], TableY[n], false);
        Labels[labelindex++] = result;
        while(result.getFrame().r.width == 0)
            repaintwaitmin(1);
        AnimatePath(result,result.getPosition(),new Vector2D(XSTART+Labels[0].getFrame().r.width/2
                                                                   +result.getFrame().r.width/2,YSTART-8),25);
        for (i=0; i<=n; i++) {
            Labels[labelindex++] = TableL[i];
            Labels[labelindex++] = indicies[i];
        }



    }

    protected long fib_recursive_m(int n, int xoffset, int yoffset,DSShapeRect TableL[], GElementLabel indicies[], long Table[]) {
            code[0].setLabelColor(Color.RED);
            Labels[labelindex++] = createLabel("fibonacci("+n+")",xoffset*XDIFF+XSTART,yoffset*YDIFF+YSTART, false);
            indicies[n].setLabelColor(Color.RED);
            TableL[n].setColor(Color.RED);
            repaintwait();
        indicies[n].setLabelColor(Color.BLUE);
        TableL[n].setColor(Color.BLACK);
        code[0].setLabelColor(Color.BLACK);
            if (Table[n] > 0) {
                GElementLabel moveLabel = createLabel(TableL[n].getLabel(), TableL[n].getPosition(), false);
                AnimatePath(moveLabel,moveLabel.getPosition(),Labels[labelindex-1].getPosition(),20);
                removeAny(moveLabel);
                Labels[labelindex-1].setLabel(Long.toString(Table[n]));
                return Table[n];

            }
            code[1].setLabelColor(Color.RED);
            repaintwait();
            code[1].setLabelColor(Color.BLACK);
/*            if (skipAnimation) {
                long result = getfib(n);
                removeAny(Labels[--labelindex]);
                Labels[labelindex++] = createLabel(Long.toString(result),xoffset*XDIFF+XSTART,yoffset*YDIFF+YSTART);
                return result;
            } */
            if (n<2) {
                code[2].setLabelColor(Color.RED);
                removeAny(Labels[--labelindex]);
                Labels[labelindex++] = createLabel("1",xoffset*XDIFF+XSTART,yoffset*YDIFF+YSTART, false);
                repaintwait();
                code[2].setLabelColor(Color.BLACK);
                GElementLabel moveLabel = createLabel("1",Labels[labelindex-1].getPosition(), false);
                AnimatePath(moveLabel,moveLabel.getPosition(),TableL[n].getPosition(),20);
                TableL[n].setLabel("1");
                removeAny(moveLabel);
                Table[n] = 1;
                return 1;
            } else {
                code[5].setLabelColor(Color.RED);
                repaintwait();
                code[5].setLabelColor(Color.BLACK);
                long prev = fib_recursive_m(n-1,xoffset+1,yoffset+1,TableL, indicies,Table);
                code[7].setLabelColor(Color.RED);
                repaintwait();
                code[7].setLabelColor(Color.BLACK);
                long prevprev = fib_recursive_m(n-2,xoffset+1,yoffset+2, TableL, indicies, Table);
                code[6].setLabelColor(Color.RED);
                repaintwait();
                Labels[labelindex-3].setLabel("");
                AnimatePath(Labels[labelindex-1],Labels[labelindex-1].getPosition(),Labels[labelindex-3].getPosition(),
                            Labels[labelindex-2],Labels[labelindex-2].getPosition(),Labels[labelindex-3].getPosition(),20);
                removeAny(Labels[--labelindex]);
                removeAny(Labels[--labelindex]);
                Labels[labelindex-1].setLabel(Long.toString(prev+prevprev));
                code[6].setLabelColor(Color.BLACK);
                GElementLabel moveLabel = createLabel(Long.toString(prev+prevprev),Labels[labelindex-1].getPosition(), false);
                AnimatePath(moveLabel,moveLabel.getPosition(),TableL[n].getPosition(),20);
                Table[n] = prev+prevprev;
                TableL[n].setLabel(moveLabel.getLabel());
                removeAny(moveLabel);
                return prev + prevprev;
            }
        }

    protected long fib_recursive(int n, int xoffset, int yoffset) {
            code[0].setLabelColor(Color.RED);
            Labels[labelindex++] = createLabel("fibonacci("+n+")",xoffset*XDIFF+XSTART,yoffset*YDIFF+YSTART, false);
            repaintwait();
            code[0].setLabelColor(Color.BLACK);
            code[1].setLabelColor(Color.RED);
            repaintwait();
            code[1].setLabelColor(Color.BLACK);
            if (skipAnimation) {
                long result = getfib(n);
                removeAny(Labels[--labelindex]);
                Labels[labelindex++] = createLabel(Long.toString(result),xoffset*XDIFF+XSTART,yoffset*YDIFF+YSTART, false);
                return result;
            }
            if (n<2) {
                code[2].setLabelColor(Color.RED);
                removeAny(Labels[--labelindex]);
                Labels[labelindex++] = createLabel("1",xoffset*XDIFF+XSTART,yoffset*YDIFF+YSTART, false);
                repaintwait();
                code[2].setLabelColor(Color.BLACK);
                return 1;
            } else {
                code[5].setLabelColor(Color.RED);
                repaintwait();
                code[5].setLabelColor(Color.BLACK);
                long prev = fib_recursive(n-1,xoffset+1,yoffset+1);
                code[7].setLabelColor(Color.RED);
                repaintwait();
                code[7].setLabelColor(Color.BLACK);
                long prevprev = fib_recursive(n-2,xoffset+1,yoffset+2);
                code[6].setLabelColor(Color.RED);
                repaintwait();
                Labels[labelindex-3].setLabel("");
                AnimatePath(Labels[labelindex-1],Labels[labelindex-1].getPosition(),Labels[labelindex-3].getPosition(),
                            Labels[labelindex-2],Labels[labelindex-2].getPosition(),Labels[labelindex-3].getPosition(),20);
                removeAny(Labels[--labelindex]);
                removeAny(Labels[--labelindex]);
                Labels[labelindex-1].setLabel(Long.toString(prev+prevprev));
                code[6].setLabelColor(Color.BLACK);
                return prev + prevprev;
            }
        }



    private long getfib(int n) {
        if (n < 2)
            return 1;
        int prev = 1;
        int prevprev = 1;
        int tmp;
        for (int i=n; i>1;i--) {
            tmp = prev;
            prev = prev + prevprev;
            prevprev = tmp;
        }
        return prev;
    }


    void clearLabels() {
        int i;
        for (i = 0; i < labelindex; i++)
            if (Labels[i] != null) {
                removeAny(Labels[i]);
                Labels[i] = null;
            }
        labelindex = 0;
    }



}