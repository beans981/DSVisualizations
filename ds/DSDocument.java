package edu.usfca.ds;

import edu.usfca.xj.appkit.document.XJDataXML;
import edu.usfca.xj.appkit.document.XJDocument;

public class DSDocument extends XJDocument {

    protected static final String DATA_KEY = "data";

    public void documentWillWriteData() {
        DSWindow w = (DSWindow)getWindow();
        XJDataXML data = (XJDataXML)getDocumentData();
        data.setDataForKey(this, DATA_KEY, w.getData());
    }

    public void documentDidReadData() {
        DSWindow w = (DSWindow)getWindow();
        XJDataXML data = (XJDataXML)getDocumentData();
        w.setData(data.getDataForKey(DATA_KEY));
    }

}
