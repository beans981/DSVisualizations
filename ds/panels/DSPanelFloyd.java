package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewDijkstra;
import edu.usfca.ds.views.DSViewFloyd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelFloyd extends DSPanelGraph {


    protected DSViewFloyd graphView;
    protected JButton floydButton;

    public DSPanelFloyd(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


       floydButton = new JButton("Run Floyd-Warshall");
        floydButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    Animate(DSViewFloyd.FLOYD);
                changeDone();
            }
        });
        box.add(floydButton);



        AddGraphControls(box);



        this.add(box, BorderLayout.NORTH);
        this.add(view = graphView = new DSViewFloyd(), BorderLayout.CENTER);
        SetupAnimationPanel(graphView);

    }


    public void DisableSpecific() {
        super.DisableSpecific();
        floydButton.setEnabled(false);
    }



    public void EnableSpecific() {
        super.EnableSpecific();
        floydButton.setEnabled(true);
    }

    // Persistence

    public void setData(Object data) {
        /* graphView.setData(data);*/
    }

    public Object getData() {
        return graphView.getData();
    }

}
