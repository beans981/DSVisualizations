package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewTree;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelTree extends DSPanel {

    protected DSViewTree treeView;

    public DSPanelTree(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();

        JTextField field = new JTextField("88");
        field.setMaximumSize(new Dimension(50, 30));
        box.add(field);

        JButton add = new JButton("Add");
        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                treeView.addLeaf();
                changeDone();
            }
        });

        box.add(add);

        JButton remove = new JButton("Remove");
        remove.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                treeView.removeLeaf();
                changeDone();
            }
        });

        box.add(remove);

        //box.add(Box.createHorizontalGlue());
        box.add(Box.createHorizontalStrut(10));

        JButton button = new JButton("Rename Labels");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                treeView.renameLabels();
                changeDone();
            }
        });
        box.add(button);

        button = new JButton("Colorize Labels");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                treeView.colorizeLabels();
                changeDone();
            }
        });
        box.add(button);

        button = new JButton("Show/hide Labels");
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                treeView.toggleLabelsVisibility();
                changeDone();
            }
        });
        box.add(button);

        this.add(box, BorderLayout.NORTH);
        this.add(treeView = new DSViewTree(), BorderLayout.CENTER);
    }

    // Persistence

    public void setData(Object data) {
        treeView.setData(data);
    }

    public Object getData() {
        return treeView.getData();
    }

}
