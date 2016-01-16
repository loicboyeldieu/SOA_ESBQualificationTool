package com.esbqualificationtool.model;

import com.rabbitmq.client.ConnectionFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.json.JSONObject;

public class ElasticsearchUtils {

    private static final String ES_HOST = "192.168.0.103";
    private static final String INDEX = "loictest";
    private static int requestID = 1;

    public static void indexToES(String jsonString, String scenarioName) {

        JSONObject jsonObj = new JSONObject(jsonString);

        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSZ");
        df.setTimeZone(tz);
        String nowAsISO = df.format(new Date());
        jsonObj.put("postDate", nowAsISO);
        jsonObj.put("requestID", requestID);
        requestID++;

        System.out.println("[ElasticsearchUtils] Ready to send :\n " + jsonObj.toString());

        Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(ES_HOST, 9300));
        IndexResponse indexResponse = (IndexResponse) client.prepareIndex(INDEX, scenarioName).setSource(jsonObj.toString()).execute().actionGet();

        client.close();
        System.out.println("[ElasticsearchUtils] Indexed to Elasticsearch !");
    }
}
