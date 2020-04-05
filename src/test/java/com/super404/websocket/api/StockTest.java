package com.super404.websocket.api;

import com.super404.websocket.utils.HttpUtils;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class StockTest {

    @Test
    public void testStock(){
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
            System.out.println(response.toString());
            //获取response的body
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
