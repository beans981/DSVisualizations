package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewBFS;
import edu.usfca.ds.views.DSViewDFS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelBFS extends DSPanelGraph {


    protected DSViewBFS graphView;
    protected JButton dfsButton;
    protected JButton changeDirectedButton;
    protected JButton showDFButton;
    protected JTextField startfield;
    protected JLabel startLabel;


    protected boolean directed = false;

    public DSPanelBFS(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


              startLabel = new JLabel("Start:");
        box.add(startLabel);

        startfield = new JTextField("");
        startfield.setMaximumSize(new Dimension(50, 30));
        startfield.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (startfield.getText().length() != 0) {
                    int elem = ExtractInt(startfield.getText(),3);
                    if (elem < 1000) {
                        Animate(DSViewBFS.BFS, new Integer(elem));
                    }
                    startfield.setText("");
                }
            }
        });
        box.add(startfield);

        dfsButton = new JButton("BFS");
        dfsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                int startelem = ExtractInt(startfield.getText(),3);
                if (startelem < 1000)
                    Animate(DSViewBFS.BFS, new Integer(startelem));
                startfield.setText("");
            }
        });
        box.add(dfsButton);


        changeDirectedButton = new JButton("Directed Graph");
        changeDirectedButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (directed) {
                    Animate(DSViewDFS.CHANGETOUNDIRECTED);
                    changeDirectedButton.setText("Directed Graph");
                    directed = false;
                } else {
                    Animate(DSViewDFS.CHANGETODIRECTED);
                    changeDirectedButton.setText("Undirected Graph");
                    directed = true;
                }
            }
        });
        box.add(changeDirectedButton);





        AddGraphControls(box);



        this.add(box, BorderLayout.NORTH);
        this.add(view = graphView = new DSViewBFS(), BorderLayout.CENTER);
        SetupAnimationPanel(graphView);

    }


    public void DisableSpecific() {
        super.DisableSpecific();
        dfsButton.setEnabled(false);
        changeDirectedButton.setEnabled(false);
        startfield.setEnabled(false);
    }



    public void EnableSpecific() {
        super.EnableSpecific();
        dfsButton.setEnabled(true);
        changeDirectedButton.setEnabled(true);
        startfield.setEnabled(true);

    }

    // Persistence

    public void setData(Object data) {
        graphView.setData(data);
    }

    public Object getData() {
        return graphView.getData();
    }

}
