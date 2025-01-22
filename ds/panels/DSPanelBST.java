package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewBST;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelBST extends DSPanel {

    protected DSViewBST BSTView;
    protected JTextField insertfield;
    protected JButton insertButton;
    protected JTextField findfield;
    protected JButton findButton;
    protected JTextField deletefield;
    protected JButton deleteButton;

    public DSPanelBST(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        insertfield = new JTextField("");
        insertfield.setMaximumSize(new Dimension(40, 30));
        insertfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (insertfield.getText().length() != 0) {
                        Animate(BSTView.INSERT,ExtractString(insertfield.getText(),4));
                    insertfield.setText("");
                    changeDone();
                }
            }
        });
        box.add(insertfield);

        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (insertfield.getText().length() != 0) {
                Animate(BSTView.INSERT,ExtractString(insertfield.getText(),4));
                  insertfield.setText("");
                changeDone();
              }
            }
        });
        box.add(insertButton);



        findfield = new JTextField("");
        findfield.setMaximumSize(new Dimension(40, 30));
        findfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (findfield.getText().length() != 0) {
                        Animate(BSTView.FIND,ExtractString(findfield.getText(),4));
                    findfield.setText("");

                    changeDone();
                }
            }
        });
        box.add(findfield);

        findButton = new JButton("Find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (findfield.getText().length() != 0) {
                        Animate(BSTView.FIND,ExtractString(findfield.getText(),4));
                    findfield.setText("");
                changeDone();
              }
            }
        });
        box.add(findButton);



        deletefield = new JTextField("");
        deletefield.setMaximumSize(new Dimension(40, 30));
        deletefield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (deletefield.getText().length() != 0) {
                        Animate(BSTView.DELETE, ExtractString(deletefield.getText(),4));
                    deletefield.setText("");
                    changeDone();
                }
            }
        });
        box.add(deletefield);

        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (deletefield.getText().length() != 0) {
                Animate(BSTView.DELETE, ExtractString(deletefield.getText(),4));
                deletefield.setText("");
                changeDone();
                }
              }
        });
        box.add(deleteButton);



        this.add(box, BorderLayout.NORTH);
        this.add(view = BSTView = new DSViewBST(), BorderLayout.CENTER);
        SetupAnimationPanel(BSTView);

    }

    public void DisableSpecific() {
        insertfield.setEnabled(false);
        insertButton.setEnabled(false);
        findfield.setEnabled(false);
        findButton.setEnabled(false);
        deletefield.setEnabled(false);
        deleteButton.setEnabled(false);
    }



    public void EnableSpecific() {
        insertfield.setEnabled(true);
        insertButton.setEnabled(true);
        findfield.setEnabled(true);
        findButton.setEnabled(true);
        deletefield.setEnabled(true);
        deleteButton.setEnabled(true);

    }







    // Persistence

    public void setData(Object data) {
        BSTView.setData(data);
    }

    public Object getData() {
        return BSTView.getData();
    }

}
