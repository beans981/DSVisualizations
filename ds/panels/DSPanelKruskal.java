package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewKruskal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelKruskal extends DSPanelGraph {


    protected DSViewKruskal graphView;
    protected JButton kruskalButton;

    public DSPanelKruskal(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();


        kruskalButton = new JButton("Run Kruskal");
        kruskalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    Animate(DSViewKruskal.KRUSKAL);
                changeDone();
            }
        });
        box.add(kruskalButton);



        AddGraphControls(box);



        this.add(box, BorderLayout.NORTH);
        this.add(view = graphView = new DSViewKruskal(), BorderLayout.CENTER);
        SetupAnimationPanel(graphView);

    }


    public void DisableSpecific() {
        super.DisableSpecific();
        kruskalButton.setEnabled(false);
    }



    public void EnableSpecific() {
        super.EnableSpecific();
        kruskalButton.setEnabled(true);
    }

    // Persistence

    public void setData(Object data) {
        graphView.setData(data);
    }

    public Object getData() {
        return graphView.getData();
    }

}
