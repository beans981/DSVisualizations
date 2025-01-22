package edu.usfca.ds.panels;

import edu.usfca.ds.DSWindow;
import edu.usfca.ds.views.DSViewCC;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DSPanelCC extends DSPanelGraph {


    protected DSViewCC graphView;
    protected JButton dfsButton;



    protected boolean showDF = false;

    public DSPanelCC(DSWindow window) {
        super(window);

        Box box = Box.createHorizontalBox();

        dfsButton = new JButton("Run Connected Components");
        dfsButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                    Animate(DSViewCC.CC);
            }
        });
        box.add(dfsButton);






        AddGraphControls(box);



        this.add(box, BorderLayout.NORTH);
        this.add(view = graphView = new DSViewCC(), BorderLayout.CENTER);
        SetupAnimationPanel(graphView);

    }


    public void DisableSpecific() {
        super.DisableSpecific();
        dfsButton.setEnabled(false);
    }



    public void EnableSpecific() {
        super.EnableSpecific();
        dfsButton.setEnabled(true);

    }

    // Persistence

    public void setData(Object data) {
        graphView.setData(data);
    }

    public Object getData() {
        return graphView.getData();
    }

}
