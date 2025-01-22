package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewHeap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelHeap extends DSPanel {

    protected DSViewHeap heapView;
    protected JTextField insertfield;
    protected JButton insertbutton;
    protected JButton removesmallestbutton;
    protected JButton buildheapbutton;
    protected JButton deletebutton;

    public DSPanelHeap(DSWindow window) {
        super(window);

        paused = false;
        running = false;
        Box box = Box.createHorizontalBox();

        insertfield = new JTextField("");
        insertfield.setMaximumSize(new Dimension(50, 30));
        //      field.setMaximumSize(new Dimension(50, 30));
        insertfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (insertfield.getText().length() != 0) {
                    int insertelem = ExtractInt(insertfield.getText(),3);
                    if (insertelem < 1000) {
                        Animate(heapView.INSERT, new Integer(insertelem));
                    }
                    insertfield.setText("");
                    changeDone();
                }
            }
        });
        box.add(insertfield);

        insertbutton = new JButton("Insert");
        insertbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int insertelem = ExtractInt(insertfield.getText(),3);
                if (insertelem < 1000)
                    Animate(heapView.INSERT, new Integer(insertelem));
                insertfield.setText("");
                changeDone();
            }
        });
        box.add(insertbutton);

        removesmallestbutton = new JButton("Remove Smallest");
        removesmallestbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(heapView.REMOVESMALLEST);
                changeDone();
            }
        });
        box.add(removesmallestbutton);

        deletebutton = new JButton("Delete Heap");
        deletebutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(heapView.DELETEHEAP);
                changeDone();
            }
        });
        box.add(deletebutton);

        buildheapbutton = new JButton("Build Heap");
        buildheapbutton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                Animate(heapView.BUILDHEAP);
                changeDone();
            }
        });
        box.add(buildheapbutton);

        this.add(box, BorderLayout.NORTH);
        this.add(view = heapView = new DSViewHeap(), BorderLayout.CENTER);
       SetupAnimationPanel(heapView);


    }


    public void DisableSpecific() {
        buildheapbutton.setEnabled(false);
        deletebutton.setEnabled(false);
        insertbutton.setEnabled(false);
        removesmallestbutton.setEnabled(false);
        insertfield.setEnabled(false);
    }



    public void EnableSpecific() {
        buildheapbutton.setEnabled(true);
        deletebutton.setEnabled(true);
        insertbutton.setEnabled(true);
        removesmallestbutton.setEnabled(true);
        insertfield.setEnabled(true);
        insertfield.setRequestFocusEnabled(true);
    }



    // Persistence

    public void setData(Object data) {
        heapView.setData(data);
    }

    public Object getData() {
        return heapView.getData();
    }

}
