package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewStackLL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelStackLL extends DSPanel {

    protected DSViewStackLL stackView;
    protected JTextField pushfield;
    protected JButton pushButton;
    protected JButton popButton;

    public DSPanelStackLL(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        pushfield = new JTextField("");
        pushfield.setMaximumSize(new Dimension(55, 30));
        //      field.setMaximumSize(new Dimension(50, 30));
        pushfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (pushfield.getText().length() != 0) {
                    Animate(stackView.PUSH, ExtractString(pushfield.getText(),6));
                    pushfield.setText("");
                    changeDone();
                }
            }
        });

        box.add(pushfield);


        pushButton = new JButton("Push");
        pushButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (pushfield.getText().length() != 0) {
                    Animate(stackView.PUSH, ExtractString(pushfield.getText(),6));
                    pushfield.setText("");
                    changeDone();
                }
            }
        });

        box.add(pushButton);

        popButton = new JButton("Pop");
        popButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(stackView.POP);
                changeDone();
            }
        });
        box.add(popButton);

        this.add(box, BorderLayout.NORTH);
        this.add(view = stackView = new DSViewStackLL(), BorderLayout.CENTER);
        SetupAnimationPanel(stackView);

    }


    public void DisableSpecific() {
        pushButton.setEnabled(false);
        popButton.setEnabled(false);
        pushfield.setEnabled(false);
    }


    public void EnableSpecific() {
        pushButton.setEnabled(true);
        popButton.setEnabled(true);
        pushfield.setEnabled(true);

    }

    private int ExtractInt(String text) {
        int extracted;

        try {
            extracted = Integer.parseInt(text);
            if (extracted > 999) extracted = 999;
            if (extracted < -99) extracted = -99;
        } catch (Exception e) {
            extracted = 1000;
        }
        return extracted;
    }

    // Persistence

    public void setData(Object data) {
        stackView.setData(data);
    }

    public Object getData() {
        return stackView.getData();
    }

}
