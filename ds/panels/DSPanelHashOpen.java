package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewHashOpen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelHashOpen extends DSPanel {

    protected final int MAXITERATOR = 2;


    protected DSViewHashOpen hashView;
    protected JButton insertButton;
    protected JTextField insertField;
    protected JButton deleteButton;
    protected JTextField deleteField;
    protected JTextField findField;
    protected JButton findButton;
    protected JButton swapinputButton;
    JLabel hashtype;

    protected boolean hashingIntegers = true;


    public DSPanelHashOpen(DSWindow window) {
        super(window);


        Box box = Box.createHorizontalBox();


        insertField = new JTextField("");
        insertField.setMaximumSize(new Dimension(100, 30));
        insertField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashOpen.INSERT, insertField);
            }
        });

        box.add(insertField);


        insertButton = new JButton("insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashOpen.INSERT, insertField);

            }
        });

        box.add(insertButton);


        findField = new JTextField("");
        findField.setMaximumSize(new Dimension(100, 30));
        findField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashOpen.FIND, findField);

            }
        });

        box.add(findField);


        findButton = new JButton("find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashOpen.FIND, findField);

            }
        });

        box.add(findButton);

        deleteField = new JTextField("");
        deleteField.setMaximumSize(new Dimension(100, 30));
        deleteField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashOpen.DELETE, deleteField);
            }
        });

        box.add(deleteField);


        deleteButton = new JButton("delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashOpen.DELETE, deleteField);
            }
        });

        box.add(deleteButton);


        hashtype = new JLabel(" Hashing Integers ");
        hashtype.setForeground(Color.BLUE);

        box.add(hashtype);

        swapinputButton = new JButton("Hash Strings");
        swapinputButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (hashingIntegers) {
                    Animate(DSViewHashOpen.HASHSTRING);
                    hashingIntegers = false;
                    hashtype.setText(" Hashing Strings ");
                    swapinputButton.setText("Hash Integers");
                } else {
                    Animate(DSViewHashOpen.HASHINTEGER);
                    hashingIntegers = true;
                    hashtype.setText(" Hashing Integers ");
                    swapinputButton.setText("Hash Strings");
                }
                changeDone();
            }
        });

        box.add(swapinputButton);


        this.add(box, BorderLayout.NORTH);
        this.add(view = hashView = new DSViewHashOpen(), BorderLayout.CENTER);
        SetupAnimationPanel(hashView);

    }


    protected void ListenerBody(int action, JTextField field) {
        if (field.getText().length() != 0) {
            if (hashingIntegers) {
                int elem = ExtractInt(field.getText());
                if (elem < Integer.MAX_VALUE) {
                    Animate(action, new Integer(elem));
                }
            } else {
                if (field.getText().length() > 0) {
                    Animate(action, ExtractString(field.getText(),20));
                }
            }
            field.setText("");
            changeDone();
        }


    }


    public void DisableSpecific() {

        insertButton.setEnabled(false);
        insertField.setEnabled(false);
        findButton.setEnabled(false);
        findField.setEnabled(false);
        deleteButton.setEnabled(false);
        deleteField.setEnabled(false);
        swapinputButton.setEnabled(false);

    }


    public void EnableSpecific() {

        insertButton.setEnabled(true);
        insertField.setEnabled(true);
        findButton.setEnabled(true);
        findField.setEnabled(true);
        deleteButton.setEnabled(true);
        deleteField.setEnabled(true);
        swapinputButton.setEnabled(true);


    }

    private int ExtractInt(String text) {
        int extracted;

        try {
            extracted = Integer.parseInt(text);
            if (extracted == Integer.MAX_VALUE) extracted = Integer.MAX_VALUE-1;
        } catch (Exception e) {
            extracted = Integer.MAX_VALUE;
        }
        return extracted;
    }

    // Persistence

    public void setData(Object data) {
        hashView.setData(data);
    }

    public Object getData() {
        return hashView.getData();
    }

}
