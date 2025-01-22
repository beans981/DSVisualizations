package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewListArray;
import edu.usfca.ds.views.DSViewListLL;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelListLL extends DSPanel {

    protected final int MAXITERATOR = 2;


    protected DSViewListLL listView;
    protected JButton lengthButton;
    protected JButton getIteratorButton;
    protected JTextField add;
    protected JButton addButton;


    protected JTextField insert[];
    protected JButton insertButton[];
    protected JTextField setCurrent[];
    protected JButton setCurrentButton[];


    protected JButton inListButton[];
    protected JButton deleteButton[];
    protected JButton nextButton[];
    protected JButton currentButton[];
    protected JButton firstButton[];
    protected int activeIterators = 0;

    protected Box iterator[];
    protected Box box;


    public DSPanelListLL(DSWindow window) {
        super(window);
        int i;

        Color iteratorColors[] = DSViewListArray.iteratorColors;
        iterator = new Box[MAXITERATOR];

        insert = new JTextField[MAXITERATOR];
        setCurrent = new JTextField[MAXITERATOR];

        insertButton = new JButton[MAXITERATOR];
        deleteButton = new JButton[MAXITERATOR];

        setCurrentButton = new JButton[MAXITERATOR];
        nextButton = new JButton[MAXITERATOR];
        currentButton = new JButton[MAXITERATOR];
        inListButton = new JButton[MAXITERATOR];
        firstButton = new JButton[MAXITERATOR];


        Box topbox = Box.createVerticalBox();
        Box box = Box.createHorizontalBox();

        for (i = 0; i < MAXITERATOR; i++) {
            iterator[i] = Box.createHorizontalBox();

            firstButton[i] = new JButton("first");
            AddActionListenButton(firstButton[i], i, listView.FIRST);
//            firstButton[i].setForeground(iteratorColors[i]);
            firstButton[i].setBackground(iteratorColors[i]);
            //           firstButton[i].setBorder(BorderFactory.createLineBorder(iteratorColors[i]));
            iterator[i].add(firstButton[i]);
            firstButton[i].setVisible(false);


            nextButton[i] = new JButton("next");
             AddActionListenButton(nextButton[i], i, listView.NEXT);
//             nextButton[i].setForeground(iteratorColors[i]);
             nextButton[i].setBackground(iteratorColors[i]);
//            nextButton[i].setBorder(BorderFactory.createLineBorder(iteratorColors[i]));
             iterator[i].add(nextButton[i]);
             nextButton[i].setVisible(false);

            currentButton[i] = new JButton("current");
             AddActionListenButton(currentButton[i], i, listView.CURRENT);
//             currentButton[i].setForeground(iteratorColors[i]);
             currentButton[i].setBackground(iteratorColors[i]);
//            nextButton[i].setBorder(BorderFactory.createLineBorder(iteratorColors[i]));
             iterator[i].add(currentButton[i]);
             currentButton[i].setVisible(false);


            inListButton[i] = new JButton("inList");
            AddActionListenButton(inListButton[i], i, listView.INLIST);
//            inListButton[i].setForeground(iteratorColors[i]);
            inListButton[i].setBackground(iteratorColors[i]);
//            nextButton[i].setBorder(BorderFactory.createLineBorder(iteratorColors[i]));
            iterator[i].add(inListButton[i]);
            inListButton[i].setVisible(false);


            setCurrent[i] = new JTextField("");
            setCurrent[i].setMaximumSize(new Dimension(50, 30));
            AddActionListenField2(setCurrent[i], i, listView.SETCURRENT);
//            insertBefore[i].setForeground(iteratorColors[i]);
            setCurrent[i].setBackground(iteratorColors[i]);
            iterator[i].add(setCurrent[i]);
            setCurrent[i].setVisible(false);

            setCurrentButton[i] = new JButton("Set Current");
            AddActionListenButtonField2(setCurrentButton[i], i, listView.SETCURRENT, setCurrent[i]);
//            setCurrentButton[i].setForeground(iteratorColors[i]);
            setCurrentButton[i].setBackground(iteratorColors[i]);
            iterator[i].add(setCurrentButton[i]);
            setCurrentButton[i].setVisible(false);


            insert[i] = new JTextField("");
            insert[i].setMaximumSize(new Dimension(55, 30));
            AddActionListenField(insert[i], i, listView.INSERTBEFORE);
//            insertBefore[i].setForeground(iteratorColors[i]);
            insert[i].setBackground(iteratorColors[i]);
            iterator[i].add(insert[i]);
            insert[i].setVisible(false);

            insertButton[i] = new JButton("Insert");
            AddActionListenButtonField(insertButton[i], i, listView.INSERTBEFORE, insert[i]);
//            insertBeforeButton[i].setForeground(iteratorColors[i]);
            insertButton[i].setBackground(iteratorColors[i]);
            iterator[i].add(insertButton[i]);
            insertButton[i].setVisible(false);

            deleteButton[i] = new JButton("Delete");
            AddActionListenButton(deleteButton[i], i, listView.DELETE);
            deleteButton[i].setBackground(iteratorColors[i]);
            iterator[i].add(deleteButton[i]);
            deleteButton[i].setVisible(false);




        }


        lengthButton = new JButton("length");
        lengthButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(listView.LENGTH);

                changeDone();
            }
        });

        box.add(lengthButton);

        getIteratorButton = new JButton("Create Iterator");
        getIteratorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (activeIterators < (MAXITERATOR)) {
                    firstButton[activeIterators].setVisible(true);
                    nextButton[activeIterators].setVisible(true);
                    currentButton[activeIterators].setVisible(true);
                    inListButton[activeIterators].setVisible(true);
                    setCurrent[activeIterators].setVisible(true);
                    setCurrentButton[activeIterators].setVisible(true);
                    insert[activeIterators].setVisible(true);
                    insertButton[activeIterators].setVisible(true);
                    deleteButton[activeIterators].setVisible(true);
                    activeIterators++;
                    if (activeIterators == MAXITERATOR)
                        DSPanelListLL.this.getIteratorButton.setEnabled(false);
                    Animate(listView.ADDITERATOR);
                }
                changeDone();
            }
        });
        box.add(getIteratorButton);




        add = new JTextField("");
        add.setMaximumSize(new Dimension(55, 30));
        //      field.setMaximumSize(new Dimension(50, 30));
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (add.getText().length() != 0) {
                        Animate(listView.ADD, ExtractString(add.getText(),6));
                    add.setText("");
                    changeDone();
                }
            }
        });

        box.add(add);


        addButton = new JButton("add");
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (add.getText().length() != 0) {
                        Animate(listView.ADD, ExtractString(add.getText(),6));
                    add.setText("");
                    changeDone();
                }
            }
        });

       box.add(addButton);


        topbox.add(box);
        for (i = 0; i < MAXITERATOR; i++)
            topbox.add(iterator[i]);

        this.add(topbox, BorderLayout.NORTH);
//        this.add(iter1, BorderLayout.NORTH);
        this.add(view = listView = new DSViewListLL(), BorderLayout.CENTER);
        SetupAnimationPanel(listView);

    }


    private void AddActionListenButton(final JButton button, final int iteratornum, final int action) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(action, new Integer(iteratornum));
                changeDone();
            }
        });


    }

    private void AddActionListenButtonField(final JButton button, final int iteratornum, final int action,
                                            final JTextField field) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (field.getText().length() != 0) {
                        Animate(action, new Integer(iteratornum), ExtractString(field.getText(),6));
                    field.setText("");
                    changeDone();
                }
            }
        });


    }

    private void AddActionListenField(final JTextField field, final int iteratornum, final int action) {
        field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (field.getText().length() != 0) {
                        Animate(action, new Integer(iteratornum), ExtractString(field.getText(),6));
                    field.setText("");
                    changeDone();
                }
            }
        });

    }

    private void AddActionListenButtonField2(final JButton button, final int iteratornum, final int action,
                                            final JTextField field) {
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (field.getText().length() != 0) {
                    int insertelem = ExtractInt(field.getText(),3);
                    if (insertelem < 1000) {
                        Animate(action, new Integer(iteratornum), new Integer(insertelem));
                    }
                    field.setText("");
                    changeDone();
                }
            }
        });


    }

    private void AddActionListenField2(final JTextField field, final int iteratornum, final int action) {
        field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (field.getText().length() != 0) {
                    int insertelem = ExtractInt(field.getText(),3);
                    if (insertelem < 1000) {
                        Animate(action, new Integer(iteratornum), new Integer(insertelem));
                    }
                    field.setText("");
                    changeDone();
                }
            }
        });

    }




    public void DisableSpecific() {
        int i;
        lengthButton.setEnabled(false);
        getIteratorButton.setEnabled(false);
        add.setEnabled(false);
        addButton.setEnabled(false);


        for (i = 0; i < activeIterators; i++) {
            firstButton[i].setEnabled(false);
            nextButton[i].setEnabled(false);
            currentButton[i].setEnabled(false);
            inListButton[i].setEnabled(false);
            setCurrent[i].setEnabled(false);
            setCurrentButton[i].setEnabled(false);
            insert[i].setEnabled(false);
            insertButton[i].setEnabled(false);
            inListButton[i].setEnabled(false);
            deleteButton[i].setEnabled(false);
        }

    }


    public void EnableSpecific() {
        int i;
        lengthButton.setEnabled(true);
        if (activeIterators < (MAXITERATOR))
            getIteratorButton.setEnabled(true);
        add.setEnabled(true);
           addButton.setEnabled(true);


        for (i = 0; i < MAXITERATOR; i++) {
            firstButton[i].setEnabled(true);
            nextButton[i].setEnabled(true);
            currentButton[i].setEnabled(true);
            inListButton[i].setEnabled(true);
            setCurrent[i].setEnabled(true);
            setCurrentButton[i].setEnabled(true);
            insert[i].setEnabled(true);
            insertButton[i].setEnabled(true);
           inListButton[i].setEnabled(true);
            deleteButton[i].setEnabled(true);
        }

    }


    // Persistence

    public void setData(Object data) {
        listView.setData(data);
    }

    public Object getData() {
        return listView.getData();
    }

}
