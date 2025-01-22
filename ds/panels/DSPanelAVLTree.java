package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewAVLTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelAVLTree extends DSPanel {

    protected DSViewAVLTree AVLView;
    protected JTextField insertfield;
    protected JButton insertButton;
    protected JTextField findfield;
    protected JButton findButton;
    protected JTextField deletefield;
    protected JButton deleteButton;
    protected JButton swapViewButton;

    protected boolean viewingNPL = true;


    public DSPanelAVLTree(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        insertfield = new JTextField("");
        insertfield.setMaximumSize(new Dimension(40, 30));
        insertfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (insertfield.getText().length() != 0) {
                        Animate(AVLView.INSERT,ExtractString(insertfield.getText(),4));
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
                Animate(AVLView.INSERT,ExtractString(insertfield.getText(),4));
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
                        Animate(AVLView.FIND,ExtractString(findfield.getText(),4));
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
                        Animate(AVLView.FIND,ExtractString(findfield.getText(),4));
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
                        Animate(AVLView.DELETE, ExtractString(deletefield.getText(),4));
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
                Animate(AVLView.DELETE, ExtractString(deletefield.getText(),4));
                deletefield.setText("");
                changeDone();
                }
              }
        });
        box.add(deleteButton);

        swapViewButton = new JButton("Hide Heights");
        swapViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (viewingNPL) {
                    Animate(AVLView.HIDE_HEIGHT);
                    viewingNPL = false;
                    swapViewButton.setText("Show Heights");


                }   else {
                    Animate(AVLView.SHOW_HEIGHT);
                    viewingNPL = true;
                    swapViewButton.setText("Hide Heights");

                }

              }
        });
        box.add(swapViewButton);



        this.add(box, BorderLayout.NORTH);
        this.add(view = AVLView = new DSViewAVLTree(), BorderLayout.CENTER);
        SetupAnimationPanel(AVLView);

    }

    public void DisableSpecific() {
        insertfield.setEnabled(false);
        insertButton.setEnabled(false);
        findfield.setEnabled(false);
        findButton.setEnabled(false);
        deletefield.setEnabled(false);
        deleteButton.setEnabled(false);
        swapViewButton.setEnabled(false);
    }



    public void EnableSpecific() {
        insertfield.setEnabled(true);
        insertButton.setEnabled(true);
        findfield.setEnabled(true);
        findButton.setEnabled(true);
        deletefield.setEnabled(true);
        deleteButton.setEnabled(true);
        swapViewButton.setEnabled(true);

    }







    // Persistence

    public void setData(Object data) {
        AVLView.setData(data);
    }

    public Object getData() {
        return AVLView.getData();
    }

}
