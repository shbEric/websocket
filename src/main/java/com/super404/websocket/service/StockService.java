package com.super404.websocket.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.super404.websocket.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口服务，调用股票行情接口
 */
public class StockService {
    public static Map<String,String> getStockInfo(){
        String host = "https://finance.api51.cn";
        String path = "/real/v4/";
        String method = "GET";
        String appcode = "748ffa05c4c54cf391ebe231b3b2bbab";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("fields", "prod_code,prod_name,update_time,open_px,high_px,low_px,last_px,preclose_px,bid_grp,offer_grp,week_52_low,week_52_high,px_change,px_change_rate,market_value,circulation_value,dyn_pb_rate,dyn_pe,turnover_ratio,turnover_volume,turnover_value,amplitude,trade_status,business_amount_in,business_amount_out,bps");
        querys.put("prod_code", "000001.SZ");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);

            JSONObject obj = JSONObject.parseObject(EntityUtils.toString(response.getEntity()));

            if (obj == null){
                return null;
            }

            if (obj.containsKey("data")) {
                JSONObject data = obj.getJSONObject("data");
                if (data.containsKey("snapshot")){
                    JSONArray szArray = data.getJSONObject("snapshot").getJSONArray("000001.SZ");
                    JSONArray fields = data.getJSONArray("fields");
                    Map<String, String> mapInfo = new HashMap<>();
                    for (int i = 0; i < szArray.size(); i++) {
                        mapInfo.put(fields.getString(i), szArray.getString(i));
                    }
                    System.out.println(mapInfo);
                    return mapInfo;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
