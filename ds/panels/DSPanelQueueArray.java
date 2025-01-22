package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewQueueArray;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelQueueArray extends DSPanel {

    protected DSViewQueueArray queueView;
    protected JTextField enqueuefield;
    protected JButton enqueueButton;
    protected JButton dequeueButton;

    public DSPanelQueueArray(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        enqueuefield = new JTextField("");
        enqueuefield.setMaximumSize(new Dimension(55, 30));
        //      field.setMaximumSize(new Dimension(50, 30));
        enqueuefield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (enqueuefield.getText().length() != 0) {
                        Animate(queueView.ENQUEUE, ExtractString(enqueuefield.getText(),6) );
                    enqueuefield.setText("");
                    changeDone();
                }
            }
        });

        box.add(enqueuefield);


        enqueueButton = new JButton("Enqueue");
        enqueueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (enqueuefield.getText().length() != 0) {
                    Animate(queueView.ENQUEUE, ExtractString(enqueuefield.getText(),6) );

                    enqueuefield.setText("");
                    changeDone();
                }
            }
        });

        box.add(enqueueButton);

        dequeueButton = new JButton("Dequeue");
        dequeueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(queueView.DEQUEUE);
                changeDone();
            }
        });
        box.add(dequeueButton);

        this.add(box, BorderLayout.NORTH);
        this.add(view = queueView = new DSViewQueueArray(), BorderLayout.CENTER);
        SetupAnimationPanel(queueView);

    }


    public void DisableSpecific() {
        enqueueButton.setEnabled(false);
        dequeueButton.setEnabled(false);
        enqueuefield.setEnabled(false);
    }


    public void EnableSpecific() {
        enqueueButton.setEnabled(true);
        dequeueButton.setEnabled(true);
        enqueuefield.setEnabled(true);

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
        queueView.setData(data);
    }

    public Object getData() {
        return queueView.getData();
    }

}
