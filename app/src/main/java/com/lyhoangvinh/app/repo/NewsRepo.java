package com.lyhoangvinh.app.repo;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.lyhoangvinh.app.model.Newspaper;
import com.lyhoangvinh.app.utils.XMLDOMParser;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Single;

public class NewsRepo {

    public Single<List<Newspaper>> getData(Context context) {
        return Single.create(emitter -> {
            String url = "https://vnexpress.net/rss/tin-moi-nhat.rss";
            Volley.newRequestQueue(context).add(new StringRequest(Request.Method.GET, url, response -> {
                Log.d("NewsPresenter", "LOAD:" + response);
                XMLDOMParser parser = new XMLDOMParser();
                Document doc = parser.getDocument(response);
                NodeList nodeList = doc.getElementsByTagName("item");
                NodeList nodeListDescription = doc.getElementsByTagName("description");
                List<Newspaper> data = new ArrayList<>();
                for (int i = 0; i < nodeList.getLength(); i++) {
                    String cData = nodeListDescription.item(i + 1).getTextContent();
                    Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");
                    Matcher matcher = p.matcher(cData);
                    String imageUrl = "";
                    String content = "";
                    if (matcher.find()) {
                        imageUrl = matcher.group(1);
                    }
                    Element e = (Element) nodeList.item(i);
                    String title = parser.getValue(e, "title");
                    String[] contents = cData.split("</br>");
                    if (contents.length > 1) {
                        content = contents[1];
                    } else {
                        content = title;
                    }
                    String link = parser.getValue(e, "link");
                    data.add(new Newspaper(imageUrl, title, link, content));
                }
                emitter.onSuccess(data);
            }, error -> {
                Log.d("NewsPresenter", "ERROR: " + error.getMessage());
                emitter.onError(error);
            }));
        });
    }


}
