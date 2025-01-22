package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewSort;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelSort extends DSPanel {

    public final int RANDOMIZE = 1;
    public final int SWITCH_TO_NUMERIC = 2;
    public final int SWITCH_TO_GRAPHIC = 3;


    protected boolean viewingGraphic = true;
    protected boolean viewingIndices = true;
    protected DSViewSort sortView;
    protected JButton randomizeButton;
    protected JButton switchViewButton;
    protected JButton toggleIndexButton;
    protected JButton sortButton;
    protected JButton sort2Button;
    protected JButton sort3Button;
    protected JButton sort4Button;
    protected JButton sort5Button;
    protected JButton sort6Button;

    public DSPanelSort(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        sortButton = new JButton("Bubble Sort");
        sortButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.BUBBLESORT);
                changeDone();
            }
        });
        box.add(sortButton);

        sort2Button = new JButton("Selection Sort");
        sort2Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.SELECTIONSORT);
                changeDone();
            }
        });
        box.add(sort2Button);

        sort3Button = new JButton("Insertion Sort");
        sort3Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.INSERTIONSORT);
                changeDone();
            }
        });
        box.add(sort3Button);


        sort4Button = new JButton("Quick Sort");
        sort4Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.QUICKSORT);
                changeDone();
            }
        });
        box.add(sort4Button);

        sort5Button = new JButton("Merge Sort");
        sort5Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.MERGESORT);
                changeDone();
            }
        });
        box.add(sort5Button);

        sort6Button = new JButton("Shell Sort");
        sort6Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(sortView.SHELLSORT);
                changeDone();
            }
        });
        box.add(sort6Button);


        randomizeButton = new JButton("Randomize");
        randomizeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                sortView.Randomize();
                changeDone();
            }
        });
        box.add(randomizeButton);


        switchViewButton = new JButton("Hide Bars");
        switchViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (viewingGraphic) {
                    sortView.SwitchToNumeric();
                    viewingGraphic = false;
                    switchViewButton.setText("View Bars");

                } else {
                    sortView.SwitchToGraphic();
                    viewingGraphic = true;
                    switchViewButton.setText("Hide Bars");
                }
                changeDone();
            }
        });
        box.add(switchViewButton);

        toggleIndexButton = new JButton("Hide Indices");
        toggleIndexButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (viewingIndices) {
                    sortView.ToggleIndicesViewable();
                    viewingIndices = false;
                    toggleIndexButton.setText("View Indices");

                } else {
                    sortView.ToggleIndicesViewable();
                    viewingIndices = true;
                    toggleIndexButton.setText("Hide Indices");
                }
                changeDone();
            }
        });
        box.add(toggleIndexButton);

        this.add(box, BorderLayout.NORTH);
        this.add(view = sortView = new DSViewSort(), BorderLayout.CENTER);
        SetupAnimationPanel(sortView);

    }


    public void DisableSpecific() {
        randomizeButton.setEnabled(false);
        switchViewButton.setEnabled(false);
        toggleIndexButton.setEnabled(false);
        sortButton.setEnabled(false);
        sort2Button.setEnabled(false);
        sort3Button.setEnabled(false);
        sort4Button.setEnabled(false);
        sort5Button.setEnabled(false);
        sort6Button.setEnabled(false);
    }


    public void EnableSpecific() {
        randomizeButton.setEnabled(true);
        switchViewButton.setEnabled(true);
        toggleIndexButton.setEnabled(true);
        sortButton.setEnabled(true);
        sort2Button.setEnabled(true);
        sort3Button.setEnabled(true);
        sort4Button.setEnabled(true);
        sort5Button.setEnabled(true);
        sort6Button.setEnabled(true);


    }

    // Persistence

    public void setData(Object data) {
        sortView.setData(data);
    }

    public Object getData() {
        return sortView.getData();
    }

}
