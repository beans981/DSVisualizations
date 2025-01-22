package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewHeap;
import edu.usfca.ds.views.DSViewDynamicProg;
import edu.usfca.ds.views.DSViewDynamicProg2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelDynamicProg2 extends DSPanel {

    protected DSViewDynamicProg2 DPView;
    protected JTextField inputfield;
    protected JButton recursivebutton;
    protected JButton tablebutton;
    protected JButton memoizedbutton;
    protected JLabel noteLabel;

    public DSPanelDynamicProg2(DSWindow window) {
        super(window);

        paused = false;
        running = false;
        Box box = Box.createHorizontalBox();

        noteLabel = new JLabel("Make change for");
        box.add(noteLabel);
        inputfield = new JTextField("");
        inputfield.setMaximumSize(new Dimension(50, 30));
       inputfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {

                        Animate(DPView.CHANGE_ERROR);


            }
        });
        box.add(inputfield);

        tablebutton = new JButton("Change: Table");
        tablebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int inputelem = ExtractInt(inputfield.getText(),3);
                if (inputelem < 150)
                    Animate(DPView.CHANGE_TABLE, new Integer(inputelem));
                else {
                    inputfield.setText("150");
                    Animate(DPView.CHANGE_TABLE, new Integer(150));
                }
                inputfield.setText("");
            }
        });
        box.add(tablebutton);

        memoizedbutton = new JButton("Change: Memoized");
        memoizedbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int inputelem = ExtractInt(inputfield.getText(),3);
                if (inputelem < 150)
                    Animate(DPView.CHANGE_MEMOIZED, new Integer(inputelem));
                else {
                    inputfield.setText("150");
                    Animate(DPView.CHANGE_MEMOIZED, new Integer(150));
                }

                inputfield.setText("");
            }
        });
        box.add(memoizedbutton);

        recursivebutton = new JButton("Change: Recurisve");
        recursivebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int inputelem = ExtractInt(inputfield.getText(),3);
                if (inputelem < 150)
                    Animate(DPView.CHANGE_RECURSIVE, new Integer(inputelem));
                else {
                    inputfield.setText("150");
                    Animate(DPView.CHANGE_RECURSIVE, new Integer(150));
                }

                inputfield.setText("");
            }
        });
        box.add(recursivebutton);
        this.add(box, BorderLayout.NORTH);
        this.add(view = DPView = new DSViewDynamicProg2(), BorderLayout.CENTER);
       SetupAnimationPanel(DPView);

    }


    public void DisableSpecific() {
        recursivebutton.setEnabled(false);
        memoizedbutton.setEnabled(false);
        tablebutton.setEnabled(false);
    }



    public void EnableSpecific() {
        recursivebutton.setEnabled(true);
        memoizedbutton.setEnabled(true);
        tablebutton.setEnabled(true);

    }



    // Persistence

    public void setData(Object data) {

    }

    public Object getData() {
         return null;
    }

}
