package datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Support;

import android.content.Context;
import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.DAO.QuanAoDAO;
import datdvph44632.fpoly.duan1_appbanhang_dinhvandat.Entity.QuanAo;

public class DownloadQuanAo {
    List<QuanAo> listLap = new ArrayList<>();
    QuanAo quanAo;
    String TAG = "QuanAoLoader_____";
    String textContent;
    QuanAoDAO quanAoDAO;
    int listSize;

    public void getListLaptop(InputStream inputStream, Context context) {
        quanAoDAO = new QuanAoDAO(context);
        try {

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);

            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                ArrayList<QuanAo> list = quanAoDAO.selectQuanAo(null, null, null, null);
                if (list != null){
                    listSize = list.size();
                } else {
                    listSize = 0;
                }

                Log.d(TAG, "getListNews: eventType != END_DOC - ET= " + eventType);

                String tagName = parser.getName();
                Log.d(TAG, "Tag name =  " + tagName + ", Độ sâu của thẻ = "
                        + parser.getDepth());

                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        Log.d(TAG, "getListNews: case START_TAG");

                        if (tagName.equalsIgnoreCase("item")) {
                            Log.d(TAG, "getListNews: Start Tag <Item>");
                            quanAo = new QuanAo();
                            quanAo.setMaQuanAo("LAP" + listSize);
                        }
                        break;

                    case XmlPullParser.TEXT:
                        Log.d(TAG, "getListNews: case TEXT");
                        textContent = parser.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        Log.d(TAG, "getListNews: case END_TAG");
                        if(quanAo != null){
                            Log.d(TAG, "getListNews: news != null");
                            if(tagName.equalsIgnoreCase("item")){
                                Log.d(TAG, "getListNews: End Tag <Item>");
                                quanAoDAO.insertQuanAo(quanAo);
                            }

                            if (tagName.equalsIgnoreCase("title")){
                                Log.d(TAG, "getListNews: End Tag <Title>");
                                quanAo.setTenQuanAo( textContent );
                            }

                            if (tagName.equalsIgnoreCase("link")){
                                Log.d(TAG, "getListNews: End Tag <Link>");
                                quanAo.setMaQuanAo( textContent );
                            }
                        }
                        break;
                    default:
                        Log.d(TAG, "getListNews: default ET: " + eventType + ", Tag name = " + tagName);
                        break;
                }
                eventType = parser.next();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
