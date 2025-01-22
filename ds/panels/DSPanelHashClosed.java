package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewHashClosed;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelHashClosed extends DSPanel {


    protected DSViewHashClosed hashView;
    protected JButton insertButton;
    protected JTextField insertField;
    protected JButton deleteButton;
    protected JTextField deleteField;
    protected JTextField findField;
    protected JButton findButton;
    protected JButton swapinputButton;
    protected JRadioButton linearButton;
    protected JRadioButton quadraticButton;
    protected JRadioButton doublehashButton;
    JLabel hashtype;

    protected boolean hashingIntegers = true;
    protected int currentMethod = DSViewHashClosed.LINEARPROBING;


    public DSPanelHashClosed(DSWindow window) {
        super(window);


        Box box = Box.createHorizontalBox();
        Box box3 = Box.createVerticalBox();


        insertField = new JTextField("");
        insertField.setMaximumSize(new Dimension(100, 30));
        insertField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashClosed.INSERT, insertField);
            }
        });

        box.add(insertField);


        insertButton = new JButton("insert");
        insertButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashClosed.INSERT, insertField);

            }
        });

        box.add(insertButton);


        findField = new JTextField("");
        findField.setMaximumSize(new Dimension(100, 30));
        findField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashClosed.FIND, findField);

            }
        });

        box.add(findField);


        findButton = new JButton("find");
        findButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashClosed.FIND, findField);

            }
        });

        box.add(findButton);

        deleteField = new JTextField("");
        deleteField.setMaximumSize(new Dimension(100, 30));
        deleteField.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashClosed.DELETE, deleteField);
            }
        });

        box.add(deleteField);


        deleteButton = new JButton("delete");
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                ListenerBody(DSViewHashClosed.DELETE, deleteField);
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
                    Animate(DSViewHashClosed.HASHSTRING);
                    hashingIntegers = false;
                    hashtype.setText(" Hashing Strings ");
                    swapinputButton.setText("Hash Integers");
                } else {
                    Animate(DSViewHashClosed.HASHINTEGER);
                    hashingIntegers = true;
                    hashtype.setText(" Hashing Integers ");
                    swapinputButton.setText("Hash Strings");
                }
                changeDone();
            }
        });

        box.add(swapinputButton);

        Box box2 = Box.createHorizontalBox();

        linearButton = new JRadioButton("Linear Probing [hash(key) + i]");
        linearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (currentMethod != DSViewHashClosed.LINEARPROBING) {
                    Animate(hashView.LINEARPROBING);
                    currentMethod = DSViewHashClosed.LINEARPROBING;
                }
            }
        });
        linearButton.setSelected(true);
        box2.add(linearButton);


        quadraticButton = new JRadioButton("Quadradic Probing [hash(key) + i*i]");

        quadraticButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (currentMethod != DSViewHashClosed.QUADRATICPROBING) {

                    Animate(DSViewHashClosed.QUADRATICPROBING);
                    currentMethod = DSViewHashClosed.QUADRATICPROBING;

                }
            }
        });
        box2.add(quadraticButton);

        doublehashButton = new JRadioButton("Double Hashing [hash(key) + i*hash2(key)]");
        doublehashButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (currentMethod != DSViewHashClosed.DOUBLEHASHING) {
                    Animate(hashView.DOUBLEHASHING);
                    currentMethod = DSViewHashClosed.DOUBLEHASHING;
                }
            }
        });

        box2.add(doublehashButton);

        ButtonGroup group = new ButtonGroup();
        group.add(linearButton);
        group.add(quadraticButton);
        group.add(doublehashButton);


        box3.add(box);
        box3.add(box2);

        this.add(box3, BorderLayout.NORTH);
        this.add(view = hashView = new DSViewHashClosed(), BorderLayout.CENTER);
        SetupAnimationPanel(hashView);

    }


    protected void ListenerBody(int action, JTextField field) {
        if (field.getText().length() != 0) {
            if (hashingIntegers) {
                int elem = ExtractInt(field.getText(),10);
                if (elem < Integer.MAX_VALUE) {
                    Animate(action, new Integer(elem));
                }
            } else {
                if (field.getText().length() > 0) {
                    Animate(action, ExtractString(field.getText(), 20));
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


    // Persistence

    public void setData(Object data) {
        hashView.setData(data);
    }

    public Object getData() {
        return hashView.getData();
    }

}
