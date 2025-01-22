package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewHuff;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelHuff extends DSPanel {

    protected DSViewHuff huffView;
    protected JTextField encodefield;
    protected JButton encodeButton;

    public DSPanelHuff(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        encodefield = new JTextField("");
        encodefield.setMinimumSize(new Dimension(100,200));
//        encodefield.setMaximumSize(new Dimension(100, 200));
        //      field.setMaximumSize(new Dimension(50, 30));
        encodefield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (encodefield.getText().length() != 0) {
                    Animate(huffView.ENCODE, encodefield.getText());
                    encodefield.setText("");
                    changeDone();
                }
            }
        });

        box.add(encodefield);


        encodeButton = new JButton("Encode");
        encodeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (encodefield.getText().length() != 0) {
                    Animate(huffView.ENCODE, encodefield.getText());
                    encodefield.setText("");
                    changeDone();
                }
            }
        });

        box.add(encodeButton);


        this.add(box, BorderLayout.NORTH);
        this.add(view =huffView = new DSViewHuff(), BorderLayout.CENTER);
        SetupAnimationPanel(huffView);

    }


    public void DisableSpecific() {
        encodeButton.setEnabled(false);
        encodefield.setEnabled(false);
    }


    public void EnableSpecific() {
        encodeButton.setEnabled(true);
        encodefield.setEnabled(true);

    }



    // Persistence

    public void setData(Object data) {
        huffView.setData(data);
    }

    public Object getData() {
        return huffView.getData();
    }

}
