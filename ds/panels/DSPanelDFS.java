package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewDFS;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelDFS extends DSPanelGraph {


    protected DSViewDFS graphView;
    protected JButton dfsButton;
    protected JButton changeDirectedButton;
    protected JButton showDFButton;


    protected boolean directed = true;
    protected boolean showDF = false;

    public DSPanelDFS(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();

        dfsButton = new JButton("Run DFS");
        dfsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    Animate(DSViewDFS.DFS);
            }
        });
        box.add(dfsButton);

        changeDirectedButton = new JButton("Undirected Graph");
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


        showDFButton = new JButton("Show d/f");
        showDFButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                if (showDF) {
                    Animate(DSViewDFS.HIDEDF);
                    showDFButton.setText("Show d/f");
                    showDF = false;
                } else {
                    Animate(DSViewDFS.SHOWDF);
                    showDFButton.setText("Hide d/f");
                    showDF = true;
                }
            }
        });
        box.add(showDFButton);




        AddGraphControls(box);



        this.add(box, BorderLayout.NORTH);
        this.add(view = graphView = new DSViewDFS(), BorderLayout.CENTER);
        SetupAnimationPanel(graphView);

    }


    public void DisableSpecific() {
        super.DisableSpecific();
        dfsButton.setEnabled(false);
        changeDirectedButton.setEnabled(false);
        showDFButton.setEnabled(false);
    }



    public void EnableSpecific() {
        super.EnableSpecific();
        dfsButton.setEnabled(true);
        changeDirectedButton.setEnabled(true);
        showDFButton.setEnabled(true);

    }

    // Persistence

    public void setData(Object data) {
        graphView.setData(data);
    }

    public Object getData() {
        return graphView.getData();
    }

}
