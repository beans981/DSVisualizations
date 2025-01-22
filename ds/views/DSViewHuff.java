package edu.usfca.ds.views;

import edu.usfca.ds.shapes.DSShapeCircle;
import edu.usfca.ds.shapes.DSShapeColoredLabel;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.awt.*;
import java.util.ArrayList;

public class DSViewHuff extends DSView {

    public final int ENCODE = 1;

    protected final int WIDTHDELTA = 34;
    protected final int YPOS = 450;
    protected final int YDELTA = 45;
    protected final int NUMSTEPS = 40;

    protected int maxheight;
    protected DSShapeColoredLabel inputlabel;


    protected int frequency[];
    protected char frequencyChar[];
    protected HuffTree leaf[];
    protected int numChars;
    int numtrees;
    protected GElementLabel encodeArray[];
    GElementLabel[] encodeHeader;


    public DSViewHuff() {
        frequencyChar = new char[256];
        frequency = new int[256];
        leaf = new HuffTree[256];

        createLabel("",10,10); // Root object


    }


    DSShapeCircle createFreqCircle(String label, String freq, double x, double y) {
        DSShapeCircle c = createCircle(label, x, y);
        c.setRadius(16);
        c.setdoublelabel(true);
        c.setSecondLabelColor(Color.BLUE);
        c.setSecondLabel(freq);
        return c;
    }


    protected void CallFunction(int function, Object param1) {
        switch (function) {
            case ENCODE:
                encode((String) param1);
                break;
        }
    }

    void SetPaths(HuffTree root, int pathlength) {
        if (root != null) {
            root.path = createPath(root.circle.getPosition(), new Vector2D(root.newX, root.newY), pathlength);
            SetPaths(root.left, pathlength);
            SetPaths(root.right, pathlength);
        }
    }

    void MoveAlongPath(HuffTree root, int step) {
        if (root != null) {
            root.circle.setPosition(root.path[step]);
            MoveAlongPath(root.left, step);
            MoveAlongPath(root.right, step);
        }
    }


    protected void encode(String input) {
        int i;
        int j;
        numChars = 0;
        numtrees = 0;
        GElementLabel inputlabelheader = createLabel("Input:  ",-10,-10,false);
        inputlabelheader.setLabelColor(Color.BLUE);
        inputlabel = createColoredLabel(input, -10, -10, false);
        LineupHorizontal(new Vector2D(5,10),inputlabelheader,inputlabel);


        int freq[] = new int[256];
        int deref[] = new int[256];

        for (i = 0; i < 256; i++) {
            freq[i] = 0;
        }

        for (i = 0; i < input.length(); i++) {
            freq[(int) (input.charAt(i))]++;
        }

        for (i = 0; i < 256; i++) {
            if (freq[i] > 0) {
                frequencyChar[numChars] = (char) i;
                frequency[numChars] = freq[i];
                deref[i] = numChars;
                numChars++;
            }
        }
        numtrees = numChars;
        encodeArray = new GElementLabel[numChars];
        encodeHeader = new GElementLabel[numChars];


        for (j = 0; j < numtrees; j++) {
            leaf[j] = new HuffTree(createFreqCircle("'" + frequencyChar[j] + "'", "0", j * 33 + 33, 100));
        }

        resetAllPositions(1);




        int indx;
        waitscalefactor /= 4;
        for (i = 0; i < input.length(); i++) {
            inputlabel.setLabelColorIndex(Color.RED, i);
            indx = deref[(int) input.charAt(i)];
            leaf[indx].circle.setLabelColor(Color.RED);
            repaintwait();
            leaf[indx].frequency++;
            leaf[indx].circle.setSecondLabel(String.valueOf(leaf[indx].frequency));
            repaintwait();
            leaf[indx].circle.setLabelColor(Color.BLACK);
            inputlabel.setLabelColorIndex(Color.BLACK, i);
        }
        waitscalefactor *= 4;

        repaintwait();


        boolean change = SortByFrequency();
        if (change) {
                resetAllPositions(40);
                repaintwait();
        } else {
            resetAllPositions(1);
        }
        while (numtrees > 1) {


            int nextheight = Math.max(leaf[0].height, leaf[1].height) + 1;
            HuffTree tmp = new HuffTree(createFreqCircle("", String.valueOf(leaf[0].frequency + leaf[1].frequency),
                                                         (maxheight+4)* 8 + leaf[0].leftwidth + leaf[0].rightwidth, YPOS - YDELTA * (nextheight-1)));
            tmp.leftlink = createLink(tmp.circle, leaf[0].circle, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "0", -1);
            tmp.rightlink = createLink(tmp.circle, leaf[1].circle, GLink.SHAPE_ARC, GElement.ANCHOR_CENTER, GElement.ANCHOR_CENTER, "1", 1);
            tmp.left = leaf[0];
            tmp.right = leaf[1];
            leaf[0] = tmp;
            for (i = 1; i < numtrees - 1; i++)
                leaf[i] = leaf[i + 1];
            numtrees--;
            change = SortByFrequency();
            if (change) {
                repaintwait();
                resetAllPositions(40);
            } else {
                resetAllPositions(1);
            }
            repaintwait();
        }

        for (i=0; i<numChars;i++) {
            encodeHeader[i] = createLabel(String.valueOf(frequencyChar[i]),10,i*15+30);
            encodeHeader[i].setLabelColor(Color.BLUE);
            encodeArray[i] = createLabel("",25,i*15+30,false);
        }
        GElement frameRec = createRectangle("",4*(maxheight+2)+1,numChars*15/2+27,8*(maxheight+2),numChars*15+2);
        frameRec.setColor(Color.BLUE);

 //       inputlabelheader.move(8*(maxheight+4)-8*(4+5),0);
  //      inputlabel.move(0,10);
        GElementLabel codeheader = createLabel("Building Code:",300,40);
        codeheader.setLabelColor(Color.BLUE);
        GElementLabel code = createLabel("",-10,-10);
        LineupHorizontal(codeheader,code);
        BuildCodes(leaf[0],code,codeheader,deref);
        removeAny(codeheader);
        removeAny(code);
        ArrayList output = new ArrayList();
        int charsprinted = 0;

        GElementLabel lab = createLabel("Encoded Output",-10,-10);
        LineupHorizontal(new Vector2D((maxheight+4)*8,26),lab);
        lab.setLabelColor(Color.BLUE);
        GElementLabel moveLab;
        int currentline;
        for (currentline = 0; charsprinted < inputlabel.getLabel().length();currentline++) {
            output.add(createColoredLabel("",-10,-10));

            for (int currlinesize = 0; charsprinted < inputlabel.getLabel().length() && currlinesize < 100;) {
                GElementLabel curline = (GElementLabel) output.get(currentline);
                LineupHorizontal(new Vector2D((maxheight + 4) * 8,41+currentline*12),curline);
                int indx2 = deref[inputlabel.getLabel().charAt(charsprinted)];
                inputlabel.setLabelColorIndex(Color.RED,charsprinted);
                encodeHeader[indx2].setLabelColor(Color.RED);
                repaintwait();
                moveLab = createLabel(encodeArray[indx2].getLabel(),-10,-10);
                moveLab.setLabelVisible(false);
                LineupHorizontal(curline,moveLab);
                moveLab.setLabelVisible(true);
                AnimatePath(moveLab,encodeArray[indx2].getPosition(),moveLab.getPosition(),NUMSTEPS/2);
                removeAny(moveLab);
                curline.setLabel(curline.getLabel()+encodeArray[indx2].getLabel());
                currlinesize += encodeArray[indx2].getLabel().length();
                LineupHorizontal(new Vector2D((maxheight + 4) * 8,41+currentline*12),curline);
                repaintwait();
                inputlabel.setLabelColorIndex(Color.BLACK,charsprinted);
                encodeHeader[indx2].setLabelColor(Color.BLUE);
                charsprinted++;
            }
        }
        removeAny(inputlabelheader);
        removeAny(inputlabel);


        for (j=0; j<20;j++) {
            for (i=0; i<currentline;i++) {
                GElement nextline = (GElement) output.get(i);
                nextline.move(0,-1);
            }
            lab.move(0,-1);
            repaintwaitmin();
        }
        GElementLabel finaloutlab = createLabel("Decoded output: ",-10,-10);
        finaloutlab.setLabelColor(Color.BLUE);
        GElementLabel finalout = createLabel("",-10,-10);
        LineupHorizontal(new Vector2D((maxheight+4)*8,output.size()*12+23),finaloutlab,finalout);
        Vector2D outputposition = new Vector2D(finalout.getPositionX(),finalout.getPositionY());
        for (i=0;i<currentline;i++) {
            DSShapeColoredLabel curline = (DSShapeColoredLabel) output.get(i);
            j = 0;
            while (j < curline.getLabel().length()) {
                j = decode(leaf[0],curline,j,finalout,outputposition);

            }
        }



        AddtoHoldover(leaf[0]);
        for (i=0; i<numChars; i++) {
            HoldoverGraphics.add(encodeArray[i]);
            HoldoverGraphics.add(encodeHeader[i]);
        }
        for (i=0; i<currentline; i++)
            HoldoverGraphics.add(output.get(i));
        HoldoverGraphics.add(inputlabelheader);
        HoldoverGraphics.add(inputlabel);
        HoldoverGraphics.add(lab);
        HoldoverGraphics.add(frameRec);
        HoldoverGraphics.add(finalout);
        HoldoverGraphics.add(finaloutlab);

    }

    private int decode(HuffTree huffTree, DSShapeColoredLabel curline, int index, GElementLabel finalout,Vector2D position) {
        if (huffTree != null) {
            huffTree.circle.setColor(Color.RED);
            repaintwait();
            if (huffTree.left == null) {
                GElementLabel movelab = createLabel(String.valueOf(huffTree.character),-10,-10);
                movelab.setLabelVisible(false);
                LineupHorizontal(finalout,movelab);
                movelab.setLabelVisible(true);
                AnimatePath(movelab,huffTree.circle.getPosition(),movelab.getPosition(),30);
                removeAny(movelab);
                finalout.setLabel(finalout.getLabel() + huffTree.character);
                LineupHorizontal(position, finalout);
                huffTree.circle.setColor(Color.BLACK);
                return index;
            }
            curline.setLabelColorIndex(Color.RED,index);
            if (curline.getLabel().charAt(index) == '0') {
                huffTree.leftlink.setColor(Color.RED);
                huffTree.leftlink.setLabelColor(Color.RED);
                repaintwait();
                curline.setLabelColorIndex(Color.LIGHT_GRAY,index);
                huffTree.leftlink.setColor(Color.BLACK);
                huffTree.leftlink.setLabelColor(Color.BLACK);
                huffTree.circle.setColor(Color.BLACK);
                return decode(huffTree.left,curline,index+1,finalout,position);
            }   else {
                huffTree.rightlink.setColor(Color.RED);
                huffTree.rightlink.setLabelColor(Color.RED);
                repaintwait();
                curline.setLabelColorIndex(Color.LIGHT_GRAY,index);
                huffTree.rightlink.setColor(Color.BLACK);
                huffTree.rightlink.setLabelColor(Color.BLACK);
                huffTree.circle.setColor(Color.BLACK);
                return decode(huffTree.right,curline,index+1,finalout,position);
            }
        }   else {
            return index;
        }

    }

    private void AddtoHoldover(HuffTree tree) {
        if (tree != null) {
            if (tree.left != null) {
                HoldoverGraphics.add(tree.leftlink);
                AddtoHoldover(tree.left);
            }
            if (tree.right != null) {
                HoldoverGraphics.add(tree.rightlink);
                AddtoHoldover(tree.right);
            }
            HoldoverGraphics.add(tree.circle);
        }
    }

    protected void BuildCodes(HuffTree huffTree, GElementLabel code, GElement codeheader,int[] deref) {
        Vector2D path[];
        int j;
        GElementLabel moveLabel;
        if (huffTree != null) {
            huffTree.circle.setColor(Color.RED);
            repaintwait();
            if (huffTree.left == null & huffTree.right == null) {
                int indx = deref[huffTree.character];
                path = createPath(code.getPosition(), new Vector2D(45,indx*15+30),NUMSTEPS);
                moveLabel = createLabel(code.getLabel(), code.getPositionX(),code.getPositionY());
                for (j=0; j<NUMSTEPS;j++) {
                    moveLabel.setPosition(path[j]);
                    repaintwaitmin();
                }
                removeAny(moveLabel);
                encodeArray[indx].setLabel(code.getLabel());
                LineupHorizontal(new Vector2D(20,indx*15+30),encodeArray[indx]);
            }   else {
                huffTree.circle.setColor(Color.BLACK);
                code.setLabel(code.getLabel()+"0");
                LineupHorizontal(codeheader,code);
                huffTree.leftlink.setColor(Color.RED);
                huffTree.leftlink.setLabelColor(Color.RED);
                repaintwait();
                huffTree.leftlink.setColor(Color.BLACK);
                huffTree.leftlink.setLabelColor(Color.BLACK);
                BuildCodes(huffTree.left,code,codeheader,deref);
                huffTree.circle.setColor(Color.RED);
                code.setLabel(code.getLabel().substring(0,code.getLabel().length()-1));
                LineupHorizontal(codeheader,code);
                repaintwait();
                huffTree.circle.setColor(Color.BLACK);
                code.setLabel(code.getLabel()+"1");
                LineupHorizontal(codeheader,code);
                huffTree.rightlink.setColor(Color.RED);
                huffTree.rightlink.setLabelColor(Color.RED);
                repaintwait();
                huffTree.rightlink.setColor(Color.BLACK);
                huffTree.rightlink.setLabelColor(Color.BLACK);
                BuildCodes(huffTree.right,code,codeheader,deref);
                code.setLabel(code.getLabel().substring(0,code.getLabel().length()-1) );
                LineupHorizontal(codeheader,code);
            }

            huffTree.circle.setColor(Color.BLACK);

        }
    }

    private boolean SortByFrequency() {
        boolean change = false;
        int i, j;
        int smallindex;
        for (i = 0; i < numtrees - 1; i++) {
            smallindex = i;
            for (j = i + 1; j < numtrees; j++) {
                if (leaf[j].frequency < leaf[smallindex].frequency)
                    smallindex = j;
            }
            if (i != smallindex) {
                HuffTree tmp = leaf[i];
            leaf[i] = leaf[smallindex];
            leaf[smallindex] = tmp;
                change = true;
            }
        }
        return change;
    }


    private void resetAllPositions(int numSteps) {
        int i, j;
        int leftdelta;
        maxheight = 0;
        for (i = 0; i < numtrees; i++) {
            ResetWidths(leaf[i], 1);
            maxheight = Math.max(maxheight, leaf[i].height);
        }
        leftdelta = (maxheight + 4) * 8;
        for (i = 0; i < numtrees; i++) {
            leftdelta += leaf[i].leftwidth;
            if (leaf[i] != null)
                SetNewPositions(leaf[i], leftdelta, 0, leaf[i].height);
            leftdelta += leaf[i].rightwidth;
        }
        for (i = 0; i < numtrees; i++) {
            SetPaths(leaf[i], numSteps);
        }
        for (j = 0; j < numSteps; j++) {
            for (i = 0; i < numtrees; i++)
                MoveAlongPath(leaf[i], j);
            repaintwaitmin();
        }

    }

    private void SetNewPositions(HuffTree tree, int Xposition, int position, int height) {
        if (tree != null) {
            if (position == -1) {
                Xposition = Xposition - tree.rightwidth;
            } else if (position == 1) {
                Xposition = Xposition + tree.leftwidth;
            }
            tree.newX = Xposition;
            tree.newY = YPOS - YDELTA * (height - tree.depth);
            SetNewPositions(tree.left, Xposition, -1, height);
            SetNewPositions(tree.right, Xposition, 1, height);
        }
    }

    private int ResetWidths(HuffTree root, int depth) {
        int lefth, righth;
        if (root == null)
            return 0;
        root.depth = depth;
        root.leftwidth = Math.max(ResetWidths(root.left, depth + 1), WIDTHDELTA / 2);
        root.rightwidth = Math.max(ResetWidths(root.right, depth + 1), WIDTHDELTA / 2);
        if (root.left == null)
            lefth = 0;
        else
            lefth = root.left.height;
        if (root.right == null)
            righth = 0;
        else
            righth = root.right.height;
        root.height = 1 + Math.max(lefth, righth);

        //      return Math.max(root.leftwidth, widthdelta/2) +
        //              Math.max(root.rightwidth, widthdelta/2);
        return root.leftwidth + root.rightwidth;
    }


    protected class HuffTree {
        int frequency;
        char character;
        int leftwidth;
        int rightwidth;
        int height;
        int depth;
        int newX;
        int newY;
        Vector2D path[];
        DSShapeCircle circle;
        GLink leftlink, rightlink;
        HuffTree left, right;
        int width;

        HuffTree(DSShapeCircle circ) {

            frequency = Integer.parseInt(circ.getSecondLabel());
            if (circ.getLabel().length() > 1)
                character = circ.getLabel().charAt(1);
            else
                character = '\0';
            circle = circ;
            left = right = null;
            leftlink = rightlink = null;
            width = 32;
        }
    }


}