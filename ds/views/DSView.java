package edu.usfca.ds.views;

import edu.usfca.ds.shapes.*;
import edu.usfca.ds.utils.DSAction;
import edu.usfca.xj.appkit.gview.GView;
import edu.usfca.xj.appkit.gview.base.Vector2D;
import edu.usfca.xj.appkit.gview.object.GElement;
import edu.usfca.xj.appkit.gview.object.GElementArrow;
import edu.usfca.xj.appkit.gview.object.GElementLabel;
import edu.usfca.xj.appkit.gview.object.GLink;

import java.util.*;

public class DSView extends GView
{

    protected List shapes = new ArrayList();
    protected List links = new ArrayList();
    long sleeptime;
    boolean paused;
    int steps;
    protected boolean skipAnimation;
    Vector HoldoverGraphics;
    protected int waitscalefactor = 20;
    protected double minwaitscalefactor = 1.0;

    public DSView()
    {
        super();
        HoldoverGraphics = new Vector();
    }


    protected void AnimatePath(GElement element, Vector2D start, Vector2D end, int steps)
    {
        int j;
        if (skipAnimation)
        {
            element.moveToPosition(end);
            return;
        }
        Vector2D path[] = createPath(start, end, steps);
        for (j = 0; j < steps; j++)
        {
            element.moveToPosition(path[j]);
            repaintwaitmin();
        }

    }


    protected void AnimateToSameLocation(Vector elementVec, Vector2D end)
    {
        if (elementVec.size() > 0)
        {
            GElement elem = (GElement) elementVec.elementAt(0);

            int pathLength = (int) (Math.sqrt((elem.getPositionX() - end.getX()) * (elem.getPositionX() - end.getX()) +
                                             (elem.getPositionY() - end.getY()) * (elem.getPositionY() - end.getY()))) / 3;
            Vector2D paths[][] = createPaths(elementVec, end, pathLength);
            for (int i = 0; i < pathLength; i++)
            {
                for (int j = 0; j < elementVec.size(); j++)
                {
                    GElement e = (GElement) elementVec.elementAt(j);
                    e.moveToPosition(paths[j][i]);
                }
                repaintwaitmin();
            }
        }

    }


    protected Vector2D[][] createPaths(Vector elementVec, Vector2D end)
    {
        if (elementVec.size() > 0)
        {
            GElement elem = (GElement) elementVec.elementAt(0);

            int pathLength = (int) Math.sqrt((elem.getPositionX() - end.getX()) * (elem.getPositionX() - end.getX()) +
                                             (elem.getPositionY() - end.getY()) * (elem.getPositionY() - end.getY()));
            return createPaths(elementVec, end, pathLength);
        }
        return createPaths(elementVec, end, 1);

    }


    protected Vector2D[][] createPaths(Vector elementVec, Vector2D end, int steps)
    {
        Vector2D path[][] = new Vector2D[elementVec.size()][steps];
        for (int i = 0; i < elementVec.size(); i++)
        {
            GElement elem = (GElement) elementVec.elementAt(i);

            double X = elem.getPositionX();
            double Y = elem.getPositionY();
            double Xinc = (end.getX() - X) / steps;
            double Yinc = (end.getY() - Y) / steps;
            for (int j = 0; j < steps; j++)
            {
                X += Xinc;
                Y += Yinc;
                path[i][j] = new Vector2D(X, Y);
            }
            if (steps > 0)
            {
                path[i][steps - 1] = end;
            }
        }
        return path;
    }

    protected void AnimatePath(GElement element1, Vector2D start1, Vector2D end1,
                               GElement element2, Vector2D start2, Vector2D end2, int steps)
    {
        int j;
        if (skipAnimation)
        {
            element1.moveToPosition(end1);
            element2.moveToPosition(end2);
            return;
        }
        Vector2D path1[] = createPath(start1, end1, steps);
        Vector2D path2[] = createPath(start2, end2, steps);
        for (j = 0; j < steps; j++)
        {
            element1.moveToPosition(path1[j]);
            element2.moveToPosition(path2[j]);
            repaintwaitmin();
        }

    }

    protected void AnimatePath(GElement element1, Vector2D start1, Vector2D end1,
                               GElement element2, Vector2D start2, Vector2D end2,
                               GElement element3, Vector2D start3, Vector2D end3, int steps)
    {
        int j;
        if (skipAnimation)
        {
            element1.moveToPosition(end1);
            element2.moveToPosition(end2);
            element3.moveToPosition(end3);
            return;
        }
        Vector2D path1[] = createPath(start1, end1, steps);
        Vector2D path2[] = createPath(start2, end2, steps);
        Vector2D path3[] = createPath(start3, end3, steps);
        for (j = 0; j < steps; j++)
        {
            element1.moveToPosition(path1[j]);
            element2.moveToPosition(path2[j]);
            element3.moveToPosition(path3[j]);
            repaintwaitmin();
        }

    }

    protected GElement[] BuildLineup(GElement g)
    {
        GElement returnLineup[] = new GElement[1];
        returnLineup[0] = g;
        return returnLineup;
    }

    protected GElement[] BuildLineup(GElement g1, GElement g2)
    {
        GElement returnLineup[] = new GElement[2];
        returnLineup[0] = g1;
        returnLineup[1] = g2;
        return returnLineup;
    }

    protected GElement[] BuildLineup(GElement g1, GElement g2, GElement g3)
    {
        GElement returnLineup[] = new GElement[3];
        returnLineup[0] = g1;
        returnLineup[1] = g2;
        returnLineup[2] = g3;
        return returnLineup;
    }

    protected GElement[] BuildLineup(GElement g1, GElement g2, GElement g3, GElement g4)
    {
        GElement returnLineup[] = new GElement[4];
        returnLineup[0] = g1;
        returnLineup[1] = g2;
        returnLineup[2] = g3;
        returnLineup[3] = g4;
        return returnLineup;
    }

    protected GElement[] BuildLineup(GElement g1, GElement g2, GElement g3, GElement g4, GElement g5)
    {
        GElement returnLineup[] = new GElement[5];
        returnLineup[0] = g1;
        returnLineup[1] = g2;
        returnLineup[2] = g3;
        returnLineup[3] = g4;
        returnLineup[4] = g5;
        return returnLineup;
    }

    protected GElement[] BuildLineup(GElement g1, GElement g2, GElement g3, GElement g4, GElement g5, GElement g6)
    {
        GElement returnLineup[] = new GElement[6];
        returnLineup[0] = g1;
        returnLineup[1] = g2;
        returnLineup[2] = g3;
        returnLineup[3] = g4;
        returnLineup[4] = g5;
        returnLineup[5] = g6;
        return returnLineup;
    }


    Vector2D[] createPath(Vector2D start, Vector2D finish)
    {
        int pathLength = (int) Math.sqrt((start.getX() - finish.getX()) * (start.getX() - finish.getX()) +
                                         (start.getY() - finish.getY()) * (start.getY() - finish.getY()));
        return createPath(start, finish, pathLength);
    }

    Vector2D[] createPath(Vector2D start, Vector2D finish, int numelements)
    {
        int i;
        Vector2D path[] = new Vector2D[numelements];
        double X = start.getX();
        double Y = start.getY();
        double Xinc = (finish.getX() - start.getX()) / numelements;
        double Yinc = (finish.getY() - start.getY()) / numelements;
        for (i = 0; i < numelements; i++)
        {
            X += Xinc;
            Y += Yinc;
            path[i] = new Vector2D(X, Y);
        }
        if (numelements > 0)
        {
            path[numelements - 1] = finish;
        }
        return path;
    }


    protected void CallFunction(int Function)
    {
        /* Override this function!! */

    }

    protected void CallFunction(int Function, Object param1)
    {
        /* Override this function!! */

    }

    protected void CallFunction(int Function, Object param1, Object param2)
    {
        /* Override this function!! */

    }


    protected void LineupHorizontal(GElement elems[])
    {
        if (elems.length != 0)
        {
            while (elems[0].getLabel().length() != 0 && elems[0].getFrame().r.width == 0)
            {
                repaintwaitmin(1);
            }
            LineupHorizontal(new Vector2D(elems[0].getPositionX() - elems[0].getFrame().r.width / 2,
                                          elems[0].getPositionY()), elems);
        }

    }

    protected void LineupHorizontal(Vector2D startposition, GElement elems[])
    {

        int i;


        for (i = 0; i < elems.length; i++)
        {
            while (elems[i].getLabel().length() != 0 && elems[i].getFrame().r.width == 0)
            {
                repaintwaitmin(1);
            }
            startposition.shift(elems[i].getFrame().r.width / 2, 0);
            elems[i].moveToPosition(startposition);
            startposition.shift(elems[i].getFrame().r.width / 2, 0);
        }

    }

    protected void LineupHorizontal(Vector2D startposition, GElement g)
    {
        LineupHorizontal(new Vector2D(startposition.getX(), startposition.getY()), BuildLineup(g));
    }

    protected void LineupHorizontal(Vector2D startposition, GElement g1, GElement g2)
    {
        LineupHorizontal(startposition, BuildLineup(g1, g2));
    }

    protected void LineupHorizontal(Vector2D startposition, GElement g1, GElement g2, GElement g3)
    {
        LineupHorizontal(startposition, BuildLineup(g1, g2, g3));
    }

    protected void LineupHorizontal(Vector2D startposition, GElement g1, GElement g2, GElement g3, GElement g4)
    {
        LineupHorizontal(startposition, BuildLineup(g1, g2, g3, g4));
    }

    protected void LineupHorizontal(Vector2D startposition, GElement g1, GElement g2, GElement g3, GElement g4, GElement g5)
    {
        LineupHorizontal(startposition, BuildLineup(g1, g2, g3, g4, g5));
    }

    protected void LineupHorizontal(Vector2D startposition, GElement g1, GElement g2, GElement g3, GElement g4, GElement g5, GElement g6)
    {
        LineupHorizontal(startposition, BuildLineup(g1, g2, g3, g4, g5, g6));
    }

    protected void LineupHorizontal(GElement g)
    {
        LineupHorizontal(BuildLineup(g));
    }

    protected void LineupHorizontal(GElement g1, GElement g2)
    {
        LineupHorizontal(BuildLineup(g1, g2));
    }

    protected void LineupHorizontal(GElement g1, GElement g2, GElement g3)
    {
        LineupHorizontal(BuildLineup(g1, g2, g3));
    }

    protected void LineupHorizontal(GElement g1, GElement g2, GElement g3, GElement g4)
    {
        LineupHorizontal(BuildLineup(g1, g2, g3, g4));
    }


    public void Animate(int function, Object param1, Object param2)
    {
        StartingAnimation();
        CallFunction(function, param1, param2);
        EndingAnimation();
        repaint();
    }

    public void Animate(int function, Object param1)
    {
        StartingAnimation();
        CallFunction(function, param1);
        EndingAnimation();
        repaint();
    }


    public void Animate(int function)
    {
        StartingAnimation();
        CallFunction(function);
        EndingAnimation();
    }


    public DSShapeSingleLLL createSingleLinkedListRectL(String label, double x, double y, double w, double h)
    {
        return createSingleLinkedListRectL(label, x, y, w, h, true);
    }

    public DSShapeSingleLLL createSingleLinkedListRectL(String label, double x, double y, double w, double h, boolean draggable)
    {
        DSShapeSingleLLL c = new DSShapeSingleLLL();
        c.setLabel(label);
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);

        shapes.add(c);
        addElement(c);

        return c;
    }


    public DSShapeSingleLLU createSingleLinkedListRectU(String label, double x, double y, double w, double h)
    {
        return createSingleLinkedListRectU(label, x, y, w, h, true);
    }

    public DSShapeSingleLLU createSingleLinkedListRectU(String label, double x, double y, double w, double h, boolean draggable)
    {
        DSShapeSingleLLU c = new DSShapeSingleLLU();
        c.setLabel(label);
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);

        shapes.add(c);
        addElement(c);

        return c;
    }


    public DSShapeNullPointer createNullPointer(double x, double y, double w, double h)
    {
        return createNullPointer(x, y, w, h, true);
    }

    public DSShapeNullPointer createNullPointer(double x, double y, double w, double h, boolean draggable)
    {
        DSShapeNullPointer c = new DSShapeNullPointer();
        c.setLabel("");
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);

        shapes.add(c);
        addElement(c);

        return c;
    }


    public DSShapeSingleLL2R createSingleLinkedListRec2R(String label, String label2, double x, double y, double w, double h)
    {
        return createSingleLinkedListRect2R(label, label2, x, y, w, h, true);
    }

    public DSShapeSingleLL2R createSingleLinkedListRect2R(String label, String label2, double x, double y, double w, double h, boolean draggable)
    {
        DSShapeSingleLL2R c = new DSShapeSingleLL2R();
        c.setLabel(label);
        c.setLabel2(label2);
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);
        c.setpercentLink(.25);

        shapes.add(c);
        addElement(c);

        return c;
    }


    public DSShapeSingleLLR createSingleLinkedListRecR(String label, double x, double y, double w, double h)
    {
        return createSingleLinkedListRectR(label, x, y, w, h, true);
    }

    public DSShapeSingleLLR createSingleLinkedListRectR(String label, double x, double y, double w, double h, boolean draggable)
    {
        DSShapeSingleLLR c = new DSShapeSingleLLR();
        c.setLabel(label);
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);
        c.setpercentLink(.25);

        shapes.add(c);
        addElement(c);

        return c;
    }


    public DSShapeDoubleLLR createDoubleLinkedListRect(String label, double x, double y, double w, double h)
    {
        return createDoubleLinkedListRect(label, x, y, w, h, true);
    }

    public DSShapeDoubleLLR createDoubleLinkedListRect(String label, double x, double y, double w, double h, boolean draggable)
    {
        DSShapeDoubleLLR c = new DSShapeDoubleLLR();
        c.setLabel(label);
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);

        shapes.add(c);
        addElement(c);

        return c;
    }


    public DSShapeRectMultiLabel createMultiLabelRectange(String label, double x, double y, double w, double h)
    {
        return createMultiLabelRectange(label, x, y, w, h, true);
    }


    public DSShapeRectMultiLabel createMultiLabelRectange(String label, double x, double y, double w, double h, boolean draggable)
    {
        DSShapeRectMultiLabel c = new DSShapeRectMultiLabel();
        c.insertLabel(label);
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);
        shapes.add(c);
        addElement(c);
        return c;
    }


    public DSShapeRect createRectangle(String label, double x, double y, double w, double h)
    {
        return createRectangle(label, x, y, w, h, true);
    }

    public DSShapeRect createRectangle(String label, double x, double y, double w, double h, boolean draggable)
    {
        DSShapeRect c = new DSShapeRect();
        c.setLabel(label);
        c.setPosition(x, y);
        c.setSize(w, h);
        c.setDraggable(draggable);

        shapes.add(c);
        addElement(c);

        return c;
    }

    public DSShapeCircle createCircle(String label, double x, double y)
    {
        return createCircle(label, x, y, true);
    }

    public DSShapeCircle createCircle(String label, double x, double y, boolean draggable)
    {
        DSShapeCircle c = new DSShapeCircle();
        c.setLabel(label);
        c.setPosition(x, y);
        c.setDraggable(draggable);

        shapes.add(c);
        addElement(c);

        return c;
    }

    public GElementLabel createLabel(String label, double x, double y)
    {
        return createLabel(label, x, y, true);
    }


    public GElementLabel createLabel(String label, Vector2D position)
    {
        return createLabel(label, position, true);
    }

    public GElementLabel createLabel(String label, Vector2D position, boolean draggable)
    {
        return createLabel(label, position.getX(), position.getY(), draggable);
    }


    public GElementLabel createLabel(String label, double x, double y, boolean draggable)
    {
        GElementLabel l = new GElementLabel();
        l.setPosition(x, y);
        l.setLabel(label);
        l.setDraggable(draggable);
        addElement(l);
        return l;
    }

    public DSShapeColoredLabel createColoredLabel(String label, double x, double y)
    {
        return createColoredLabel(label, x, y, true);
    }

    public DSShapeColoredLabel createColoredLabel(String label, double x, double y, boolean draggable)
    {
        DSShapeColoredLabel l = new DSShapeColoredLabel();
        l.setPosition(x, y);
        l.setLabel(label);
        l.setDraggable(draggable);
        addElement(l);
        return l;
    }


    public GElementArrow createArrow(double x1, double y1, double x2, double y2, double arrowLength)
    {
        return createArrow(x1, y1, x2, y2, arrowLength, true);
    }

    public GElementArrow createArrow(double x1, double y1, double x2, double y2, double arrowLength, boolean draggable)
    {
        GElementArrow l = new GElementArrow();
        l.setArrowLength(arrowLength);
        l.setSource(x1, y1);
        l.setTarget(x2, y2);
        l.setDraggable(draggable);
        addElement(l);
        return l;
    }

    public DSShapeLink createLink(int source, int target, int shape, String sourceAnchor, String targetAnchor, String label, float flateness)
    {
        DSShapeLink l = new DSShapeLink((GElement) shapes.get(source), sourceAnchor,
                                        (GElement) shapes.get(target), targetAnchor,
                                        shape, label, flateness);
        links.add(l);
        addElement(l);
        return l;
    }

    public DSShapeLink createLink(GElement source, GElement target, int shape, String sourceAnchor, String targetAnchor, String label, float flateness)
    {
        DSShapeLink l = new DSShapeLink(source, sourceAnchor,
                                        target, targetAnchor,
                                        shape, label, flateness);
        links.add(l);
        addElement(l);
        return l;
    }

    public void performActionOnAllElements(DSAction action)
    {

        for (int i = 0; i < shapes.size(); i++)
        {
            GElement element = (GElement) shapes.get(i);
            action.perform(element, i);
        }

        for (int i = 0; i < links.size(); i++)
        {
            GElement element = (GElement) links.get(i);
            action.perform(element, i);
        }
    }

    public int numberOfShapes()
    {
        return shapes.size();
    }

    public void removeLastShape()
    {
        removeShape(shapes.size() - 1);
    }

    public void removeShape(int index)
    {
        if (index >= 0 && index < shapes.size())
        {
            getRootElement().removeElement((GElement) shapes.get(index));
            shapes.remove(index);
        }
    }

    public void removeLink(int e1, int e2)
    {
        if (e1 >= 0 && e2 >= 0 && e1 < shapes.size() && e2 < shapes.size())
        {
            removeLink((GElement) shapes.get(e1), (GElement) shapes.get(e2));
        }
    }

    public void removeLink(GElement e1, GElement e2)
    {

        for (int i = links.size() - 1; i >= 0; i--)
        {
            GLink link = (GLink) links.get(i);
            if (link.source == e1 && link.target == e2)
            {
                getRootElement().removeElement(link);
                links.remove(i);
            }
        }
    }

    public GLink getLink(GElement e1, GElement e2)
    {
        for (int i = links.size() - 1; i >= 0; i--)
        {
            GLink link = (GLink) links.get(i);
            if (link.source == e1 && link.target == e2)
            {
                return link;
            }

        }
        return null;
    }

    public void removeAny(GElement e)
    {
        int i;
        if (e == null)
        {
            return;
        }
        for (i = links.size() - 1; i >= 0; i--)
        {
            if (links.get(i) == e)
            {
                links.remove(i);
            }
        }
        for (i = 0; i < shapes.size(); i++)
        {
            if (shapes.get(i) == e)
            {
                shapes.remove(i);
            }
        }
        getRootElement().removeElement(e);
    }

    public void removeShape(GElement element)
    {
        int i;
        for (i = 0; i < shapes.size(); i++)
        {
            if (shapes.get(i) == element)
            {
                removeShape(i);
            }
        }

    }

    private void addElement(GElement element)
    {
        GElement root = getRootElement();
        if (root == null)
        {
            setRootElement(element);
        }
        else
        {
            root.addElement(element);
        }
    }


    public void setDelay(int delay)
    {
        sleeptime = delay;
    }


    public void pause()
    {
        steps = 0;
        paused = true;
    }

    public void step()
    {
        steps++;
    }


    public void go()
    {
        paused = false;
        steps = 0;
    }

    public void skip()
    {
        skipAnimation = true;
    }

    protected void repaintwaitmin(long sleep)
    {

        repaint();
        try
        {
            Thread.sleep((long) Math.max(((sleep) * minwaitscalefactor), 1));
        }
        catch (InterruptedException e)
        {

        }
    }

    protected void repaintwaitmin()
    {
        if (skipAnimation || sleeptime == 0)
        {
            return;
        }
        repaintwaitmin(sleeptime);
    }


    protected void repaintwait(long sleep)
    {
        int i;
        repaint();
        while (paused && steps == 0)
        {
            try
            {
                Thread.sleep(50);
                if (skipAnimation)
                {
                    return;
                }
            }
            catch (InterruptedException e)
            {

            }
        }
        if (paused && steps > 0)
        {
            steps--;
        }
        else
        {

            try
            {
                for (i = 0; i < sleep * waitscalefactor; i += 10)
                {
                    Thread.sleep(10);
                }
                if (skipAnimation)
                {
                    return;
                }
            }
            catch (InterruptedException e)
            {

            }
        }
    }

    protected void repaintwait()
    {
        if (skipAnimation || (sleeptime == 0 && !paused))
        {
            return;
        }

        repaintwait(sleeptime);
    }

    // Persistence

    public void setData(Object data)
    {
        if (data == null)
        {
            return;
        }

        Map map = (Map) data;
        setRootElement((GElement) map.get("root"));

        shapes = (List) map.get("shapes");
        links = (List) map.get("links");

        repaint();
    }

    public Object getData()
    {
        Map data = new HashMap();
        data.put("root", getRootElement());
        data.put("shapes", shapes);
        data.put("links", links);
        return data;
    }


    public void StartingAnimation()
    {
        int i;
        GElement e;
        if (HoldoverGraphics != null)
        {
            for (i = 0; i < HoldoverGraphics.size(); i++)
            {
                e = (GElement) HoldoverGraphics.elementAt(i);
                removeAny(e);
            }
        }

    }


    public void EndingAnimation()
    {
        skipAnimation = false;
    }


}
