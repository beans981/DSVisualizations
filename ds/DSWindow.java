package edu.usfca.ds;

import edu.usfca.ds.panels.*;
import edu.usfca.xj.appkit.frame.XJWindow;
import edu.usfca.xj.appkit.menu.XJMainMenuBar;
import edu.usfca.xj.appkit.menu.XJMenu;
import edu.usfca.xj.appkit.menu.XJMenuItem;
import edu.usfca.xj.appkit.menu.XJMenuItemDelegate;
import edu.usfca.xj.appkit.utils.XJAlert;
import edu.usfca.xj.appkit.utils.XJFileChooser;
import edu.usfca.xj.foundation.XJUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DSWindow extends XJWindow implements XJMenuItemDelegate {

    protected JTabbedPane viewTabbedPane;

    protected DSPanelHeap heapPanel;
    protected DSPanelBST BSTPanel;
    protected DSPanelStackArray stackPanel;
    protected DSPanelStackLL stackPanelLL;
    protected DSPanelQueueArray queuePanel;
    protected DSPanelSort sortPanel;
    protected DSPanelQueueLL queuePanelLL;
    protected DSPanelListArray listPanel;
    protected DSPanelCountSort countSortPanel;
    protected DSPanelRadixSort radixSortPanel;
    protected DSPanelListLL listPanel2;
    protected DSPanelHashOpen hashOpenPanel;
    protected DSPanelHashClosed hashClosedPanel;
    protected DSPanelBucketSort bucketPanel;
    protected DSPanelHeapSort heapsortPanel;
    protected DSPanelHuff huffPanel;
    protected DSPanelDijkstra dijkstraPanel;
    protected DSPanelDFS dfsPanel;
    protected DSPanelTopological topoPanel;
    protected DSPanelBFS bfsPanel;
    protected DSPanelCC ccPanel;
    protected DSPanelPrim primPanel;
    protected DSPanelKruskal kruskalPanel;
    protected DSPanelAVLTree AVLPanel;
    protected DSPanelBTree BTreePanel;
    protected DSPanelBTree2 BTreePanel2;
    protected DSPanelDynamicProg DynamicProgPanel;
    protected DSPanelFloyd FloydPanel;
    protected DSPanelBinomialQueue BinomialQueuePanel;
    protected DSPanelDynamicProg2 DynamicProgPanel2;

    public DSWindow() {
        Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        r.width *= 0.75f;
        r.height *= 0.75f;
        getRootPane().setPreferredSize(r.getSize());

        viewTabbedPane = new JTabbedPane();
        viewTabbedPane.setTabPlacement(JTabbedPane.TOP);

        stackPanel = new DSPanelStackArray(this);

        stackPanel = new DSPanelStackArray(this);
        stackPanelLL = new DSPanelStackLL(this);
        queuePanel = new DSPanelQueueArray(this);
        queuePanelLL = new DSPanelQueueLL(this);

        listPanel = new DSPanelListArray(this);
         listPanel2 = new DSPanelListLL(this);

        BSTPanel = new DSPanelBST(this);
        AVLPanel = new DSPanelAVLTree(this);
        BTreePanel2 = new DSPanelBTree2(this);
        BTreePanel = new DSPanelBTree(this);


        heapPanel = new DSPanelHeap(this);
        BinomialQueuePanel = new DSPanelBinomialQueue(this);

        sortPanel = new DSPanelSort(this);
         bucketPanel = new DSPanelBucketSort(this);
         countSortPanel = new DSPanelCountSort(this);
         radixSortPanel = new DSPanelRadixSort(this);
         heapsortPanel = new DSPanelHeapSort(this);

         hashOpenPanel = new DSPanelHashOpen(this);
         hashClosedPanel = new DSPanelHashClosed(this);

         huffPanel = new DSPanelHuff(this);


         topoPanel = new DSPanelTopological(this);
         bfsPanel = new DSPanelBFS(this);
         dfsPanel = new DSPanelDFS(this);
         dijkstraPanel = new DSPanelDijkstra(this);
        FloydPanel = new DSPanelFloyd(this);
        kruskalPanel = new DSPanelKruskal(this);
         primPanel = new DSPanelPrim(this);

         ccPanel = new DSPanelCC(this);

        DynamicProgPanel = new DSPanelDynamicProg(this);
        DynamicProgPanel2 = new DSPanelDynamicProg2(this);

        viewTabbedPane.add("Stack-Array", stackPanel);
        viewTabbedPane.add("Stack-Linked List", stackPanelLL );
        viewTabbedPane.add("Queue-Array", queuePanel);
        viewTabbedPane.add("Queue-Linked List", queuePanelLL );
        viewTabbedPane.add("List-Array", listPanel);
        viewTabbedPane.add("List-Linked List", listPanel2);


        getContentPane().add(viewTabbedPane);
        pack();
    }

    public void setData(Object data) {
    }

    public Object getData() {
        return null;
    }

    public static final int MI_EXPORT_AS_IMAGE = 100;
    public static final int MI_EXPORT_AS_EPS = 101;
    public static final int MI_CHECK_UPDATES = 102;
    public static final int MI_ALGORITHMS_LIST = 200;
    public static final int MI_ALGORITHMS_SORT = 201;
    public static final int MI_ALGORITHMS_GRAPH = 202;
    public static final int MI_ALGORITHMS_TREES = 203;
    public static final int MI_ALGORITHMS_HASHING = 204;
    public static final int MI_ALGORITHMS_DYNAMIC = 205;
    public static final int MI_ALGORITHMS_HEAPS = 206;
    public static final int MI_ALGORITHMS_HUFFMAN = 207;

    public void customizeFileMenu(XJMenu menu) {

        XJMenu exportMenu = new XJMenu();
        exportMenu.setTitle("Export");
        exportMenu.addItem(new XJMenuItem("As EPS...", MI_EXPORT_AS_EPS, this));
        exportMenu.addItem(new XJMenuItem("As Bitmap Image...", MI_EXPORT_AS_IMAGE, this));

        menu.insertItemAfter(exportMenu, XJMainMenuBar.MI_CLOSE);
        menu.insertSeparatorAfter(XJMainMenuBar.MI_CLOSE);

        XJMenu algorithmMenu = new XJMenu();
        algorithmMenu.setTitle("Algorithms");
        algorithmMenu.addItem(new XJMenuItem("Lists/Stacks/Queues",MI_ALGORITHMS_LIST,this));
        algorithmMenu.addItem(new XJMenuItem("Sorting Algorithms",MI_ALGORITHMS_SORT,this));
        algorithmMenu.addItem(new XJMenuItem("Trees (BST/AVL/B-Trees)",MI_ALGORITHMS_TREES,this));
        algorithmMenu.addItem(new XJMenuItem("Heaps / Binomial Queues",MI_ALGORITHMS_HEAPS,this));
        algorithmMenu.addItem(new XJMenuItem("Graph Algorithms",MI_ALGORITHMS_GRAPH,this));
        algorithmMenu.addItem(new XJMenuItem("Hashing",MI_ALGORITHMS_HASHING,this));
        algorithmMenu.addItem(new XJMenuItem("Huffman Coding",MI_ALGORITHMS_HUFFMAN,this));
        algorithmMenu.addItem(new XJMenuItem("Dynamic Programming",MI_ALGORITHMS_DYNAMIC,this));


    }

    public void customizeMenuBar(XJMainMenuBar menubar) {
        XJMenu menu = new XJMenu();
        menu.setTitle("Algorithms");
        menu.addItem(new XJMenuItem("Lists/Stacks/Queues", KeyEvent.VK_1,  MI_ALGORITHMS_LIST, this));
        menu.addItem(new XJMenuItem("Sorting Algortihms", KeyEvent.VK_2,  MI_ALGORITHMS_SORT, this));
        menu.addItem(new XJMenuItem("Trees (BST/AVL/B-Tree)", KeyEvent.VK_3,  MI_ALGORITHMS_TREES, this));
        menu.addItem(new XJMenuItem("Heaps / Binomial Queues", KeyEvent.VK_4,  MI_ALGORITHMS_HEAPS, this));
        menu.addItem(new XJMenuItem("Graph Algortihms", KeyEvent.VK_5,  MI_ALGORITHMS_GRAPH, this));
        menu.addItem(new XJMenuItem("Hashing", KeyEvent.VK_6,  MI_ALGORITHMS_HASHING, this));
        menu.addItem(new XJMenuItem("Huffman Coding", KeyEvent.VK_7,  MI_ALGORITHMS_HUFFMAN, this));
        menu.addItem(new XJMenuItem("Dynamic Programming", KeyEvent.VK_8,  MI_ALGORITHMS_DYNAMIC, this));
    // This line adds a menu separator
    //menu.addSeparator();

        menubar.addCustomMenu(menu);
    }

    public void customizeHelpMenu(XJMenu menu) {
        menu.insertItemAfter(new XJMenuItem("Check for Updates", MI_CHECK_UPDATES, this), XJMainMenuBar.MI_HELP);
        menu.insertSeparatorAfter(XJMainMenuBar.MI_HELP);
    }

    public void handleMenuEvent(XJMenu menu, XJMenuItem item) {
        super.handleMenuEvent(menu, item);
        switch(item.getTag()) {
            case MI_EXPORT_AS_EPS:
                exportPanelToEPS((DSPanel)viewTabbedPane.getSelectedComponent());
                break;
            case MI_EXPORT_AS_IMAGE:
                exportPanelToImage((DSPanel)viewTabbedPane.getSelectedComponent());
                break;
            case MI_CHECK_UPDATES:
                DSApplication.checkForUpdates(false);
                break;
            case MI_ALGORITHMS_LIST:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Stack-Array", stackPanel);
                viewTabbedPane.add("Stack-Linked List", stackPanelLL );
                viewTabbedPane.add("Queue-Array", queuePanel);
                viewTabbedPane.add("Queue-Linked List", queuePanelLL );
                viewTabbedPane.add("List-Array", listPanel);
                viewTabbedPane.add("List-Linked List", listPanel2);
                break;
            case MI_ALGORITHMS_SORT:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Comparison Sorts", sortPanel );
                viewTabbedPane.add("Bucket Sort", bucketPanel );
                viewTabbedPane.add("Counting Sort", countSortPanel );
                viewTabbedPane.add("Radix Sort", radixSortPanel);
                viewTabbedPane.add("Heap Sort", heapsortPanel);

                break;
            case MI_ALGORITHMS_TREES:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Binary Search Tree", BSTPanel);
                viewTabbedPane.add("AVL Tree", AVLPanel);
       //         viewTabbedPane.add("Red/Black Tree", RedBlackPanel);
                viewTabbedPane.add("2-3 Tree", BTreePanel2);
                viewTabbedPane.add("B-Tree (Proactive Split/Merge)", BTreePanel);

                break;
            case MI_ALGORITHMS_HEAPS:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Heap", heapPanel);
                viewTabbedPane.add("Binomial Queues", BinomialQueuePanel);

                break;
            case MI_ALGORITHMS_GRAPH:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Topological Sort", topoPanel);
                viewTabbedPane.add("Breadth First Search", bfsPanel);
                viewTabbedPane.add("Depth First Search", dfsPanel);
                viewTabbedPane.add("Dijkstra's Algorithm", dijkstraPanel);
                viewTabbedPane.add("Floyd-Warshall", FloydPanel);
                viewTabbedPane.add("Kruskal's Algorithm", kruskalPanel);
                viewTabbedPane.add("Prim's Algorithm", primPanel);
                viewTabbedPane.add("Connected Components", ccPanel);

                break;
            case MI_ALGORITHMS_HUFFMAN:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Huffman Coding", huffPanel);
                break;
            case MI_ALGORITHMS_DYNAMIC:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Dynamic Programming: Fibonacci", DynamicProgPanel);
                viewTabbedPane.add("Dynamic Programming: Change", DynamicProgPanel2);
                break;
            case MI_ALGORITHMS_HASHING:
                viewTabbedPane.removeAll();
                viewTabbedPane.add("Open Hashing", hashOpenPanel);
                viewTabbedPane.add("Closed Hashing", hashClosedPanel);

                break;


        }
    }

    public void exportPanelToImage(DSPanel panel) {

        // Fetch the list of available image format
        List extensions = new ArrayList();
        for (int i = 0; i < ImageIO.getWriterFormatNames().length; i++) {
            String ext = ImageIO.getWriterFormatNames()[i].toLowerCase();
            if(!extensions.contains(ext))
                extensions.add(ext);
        }

        // Save the panel content to disk
        if(XJFileChooser.shared().displaySaveDialog(this.getJavaContainer(), extensions, extensions, false)) {
            String file = XJFileChooser.shared().getSelectedFilePath();
            try {
                ImageIO.write(panel.getImage(), file.substring(file.lastIndexOf(".")+1), new File(file));
            } catch (IOException e) {
                XJAlert.display(this.getJavaContainer(), "Error", "Image \""+file+"\" cannot be saved because:\n"+e);
            }
        }
    }

    public void exportPanelToEPS(DSPanel panel) {
        if(!XJFileChooser.shared().displaySaveDialog(this.getJavaContainer(), "eps", "EPS file", false))
            return;

        String file = XJFileChooser.shared().getSelectedFilePath();
        if(file == null)
            return;

        try {
            XJUtils.writeStringToFile(panel.getEPS(), file);
        } catch (Exception e) {
            XJAlert.display(this.getJavaContainer(), "Error", "Cannot export to EPS file: "+file+"\nError: "+e);
        }
    }

}
