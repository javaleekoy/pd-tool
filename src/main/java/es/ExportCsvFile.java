package es;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import file.cvs.CvsFileUtils;
import io.searchbox.core.SearchResult;

import java.util.ArrayList;
import java.util.List;

/**
 * @author peramdy on 2018/9/19.
 */
public class ExportCsvFile {


    public static void exportCsv() {
        List<String> fieldNames = EsUtils.fieldNames("http://192.168.10.241", 9200, "logstash-sensenow-2018.08", null);
        SearchResult result = (SearchResult) EsUtils.searchAll("http://192.168.10.241", 9200, "logstash-sensenow-2018.08", 0, 100);
        JSONObject firstHits = JSONObject.parseObject(result.getJsonString()).getJSONObject("hits");
        JSONArray secondHits = firstHits.getJSONArray("hits");
        String[] headers = new String[fieldNames.size()];
        fieldNames.toArray(headers);
        List<String[]> list = new ArrayList<>();
        list.add(headers);
        for (Object object : secondHits) {
            String[] content = new String[fieldNames.size()];
            JSONObject source = JSONObject.parseObject(object.toString()).getJSONObject("_source");
            System.out.println(source);
            for (int i = 0; i < fieldNames.size(); i++) {
                String fieldName = fieldNames.get(i);
                String value = null;
                if (fieldName.indexOf(".") > -1) {
                    String[] secondFields = fieldName.split("\\.");
                    JSONObject secondSource = source.getJSONObject(secondFields[0]);
                    if (secondSource != null) {
                        value = secondSource.getString(secondFields[1]);
                    }
                } else {
                    value = source.getString(fieldName);
                }
                if (value == null) {
                    value = "";
                }
                System.out.println(fieldNames.get(i) + ":" + value);
                content[i] = value;
            }
            list.add(content);
        }
        CvsFileUtils.writeCsvFile("D:\\tmp.csv", list);
    }

    public static void main(String[] args) {
        exportCsv();
    }


}
