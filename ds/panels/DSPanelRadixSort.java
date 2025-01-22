package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewRadixSort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelRadixSort extends DSPanel {



    protected boolean viewingGraphic = true;
    protected boolean viewingIndices = true;
    protected DSViewRadixSort sortView;
    protected JButton randomizeButton;
    protected JButton switchViewButton;
    protected JButton toggleIndexButton;
    protected JButton sortButton;
    protected JButton sort2Button;
    protected JButton sort3Button;
    protected JButton sort4Button;
    protected JButton sort5Button;
    protected JButton sort6Button;

    public DSPanelRadixSort(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        sortButton = new JButton("Radix Sort");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.RADIXSORT);
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
        this.add(view = sortView = new DSViewRadixSort(), BorderLayout.CENTER);
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
