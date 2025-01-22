package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewBinomialQueue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelBinomialQueue extends DSPanel {


    protected JTextField insertfield;
    protected DSViewBinomialQueue queueView;
    protected JRadioButton viewLogicalButton;
    protected JRadioButton viewInternalButton;
    protected int viewingCurrent = DSViewBinomialQueue.VIEW_LOGICAL;


    protected JButton insertButton;
    protected JButton removeSmallestButton;

    public DSPanelBinomialQueue(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        insertfield = new JTextField("");
        insertfield.setMaximumSize(new Dimension(50, 30));
        insertfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (insertfield.getText().length() != 0) {

                        Animate(DSViewBinomialQueue.INSERT, ExtractString(insertfield.getText(),4));

                    insertfield.setText("");
                }
            }
        });
        box.add(insertfield);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    Animate(DSViewBinomialQueue.INSERT, ExtractString(insertfield.getText(),4));
                insertfield.setText("");
            }
        });
        box.add(insertButton);

        removeSmallestButton = new JButton("Remove Smallest");
        removeSmallestButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    Animate(DSViewBinomialQueue.REMOVE_SMALLEST);
            }
        });
        box.add(removeSmallestButton);



        viewLogicalButton = new JRadioButton("View Logical Representation");
           viewLogicalButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent event) {
                   if (viewingCurrent != DSViewBinomialQueue.VIEW_LOGICAL) {
                       Animate(DSViewBinomialQueue.VIEW_LOGICAL);
                       viewingCurrent = DSViewBinomialQueue.VIEW_LOGICAL;
                   }
               }
           });
        viewLogicalButton.setSelected(true);

        box.add(viewLogicalButton);

        viewInternalButton = new JRadioButton("View Internal Represntation");
           viewInternalButton.addActionListener(new ActionListener() {
               public void actionPerformed(ActionEvent event) {
                   if (viewingCurrent != DSViewBinomialQueue.VIEW_INTERNAL) {
                       Animate(DSViewBinomialQueue.VIEW_INTERNAL);
                       viewingCurrent = DSViewBinomialQueue.VIEW_INTERNAL;
                   }
               }
           });
           box.add(viewInternalButton);


        ButtonGroup group = new ButtonGroup();
        group.add(viewLogicalButton);
        group.add(viewInternalButton);




        this.add(box, BorderLayout.NORTH);
        this.add(view = queueView = new DSViewBinomialQueue(), BorderLayout.CENTER);
        SetupAnimationPanel(queueView);

    }


    public void DisableSpecific() {
        super.DisableSpecific();
        insertfield.setEnabled(false);
        insertButton.setEnabled(false);
        removeSmallestButton.setEnabled(false);
        viewInternalButton.setEnabled(false);
        viewLogicalButton.setEnabled(false);
    }



    public void EnableSpecific() {
        super.EnableSpecific();
        insertfield.setEnabled(true);
        insertButton.setEnabled(true);
        removeSmallestButton.setEnabled(true);
        viewInternalButton.setEnabled(true);
        viewLogicalButton.setEnabled(true);
    }

    // Persistence, NOT USED!

    public void setData(Object data) {
    }

    public Object getData() {
        return null;
    }

}
