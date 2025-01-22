package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewHeapSort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelHeapSort extends DSPanel {



    protected boolean smallList = true;
    protected DSViewHeapSort sortView;
    protected JButton randomizeButton;
    protected JButton switchViewButton;
    protected JButton toggleIndexButton;
    protected JButton sortButton;
    protected JButton sizeButton;

    public DSPanelHeapSort(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        sortButton = new JButton("Heap Sort");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.HEAPSORT);
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


        this.add(box, BorderLayout.NORTH);
        this.add(view = sortView = new DSViewHeapSort(), BorderLayout.CENTER);
        SetupAnimationPanel(sortView);

    }


    public void DisableSpecific() {
        randomizeButton.setEnabled(false);
        sortButton.setEnabled(false);
    }


    public void EnableSpecific() {
        randomizeButton.setEnabled(true);
        sortButton.setEnabled(true);


    }

    // Persistence

    public void setData(Object data) {
        sortView.setData(data);
    }

    public Object getData() {
        return sortView.getData();
    }

}
