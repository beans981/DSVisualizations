package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewCountSort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelCountSort extends DSPanel {



    protected boolean smallList = true;
    protected DSViewCountSort sortView;
    protected JButton randomizeButton;
    protected JButton switchViewButton;
    protected JButton toggleIndexButton;
    protected JButton sortButton;
    protected JButton sizeButton;

    public DSPanelCountSort(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        sortButton = new JButton("Counting Sort");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.COUNTINGSORT);
                changeDone();
            }
        });
        box.add(sortButton);


        randomizeButton = new JButton("Randomize");
        randomizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sortView.Randomize();
                changeDone();
            }
        });
        box.add(randomizeButton);

        sizeButton = new JButton("Change to Large List");
        sizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (smallList) {
                    sizeButton.setText("Change to Small List");
                    Animate(sortView.CHANGETOLARGE);
                    smallList = false;
                    changeDone();

                } else {
                    sizeButton.setText("Change to Large List");
                    Animate(sortView.CHANGETOSMALL);
                    smallList = true;
                    changeDone();


                }
            }
        });
        box.add(sizeButton);


        this.add(box, BorderLayout.NORTH);
        this.add(view = sortView = new DSViewCountSort(), BorderLayout.CENTER);
        SetupAnimationPanel(sortView);

    }


    public void DisableSpecific() {
        randomizeButton.setEnabled(false);
        sortButton.setEnabled(false);
        sizeButton.setEnabled(false);
    }


    public void EnableSpecific() {
        randomizeButton.setEnabled(true);
        sortButton.setEnabled(true);
        sizeButton.setEnabled(true);


    }

    // Persistence

    public void setData(Object data) {
        sortView.setData(data);
    }

    public Object getData() {
        return sortView.getData();
    }

}
