package com.yq.http;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.yq.model.Cbj;
import com.yq.yqwater.MeApplcition;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by mac on 16/11/20.
 */

public class downloadHttp {

    public static void download_all(String id,String fbh) {
        String url = "http://219.139.44.245:8086/webServiceXSD1/CbjServlet.action?method=get_cbsj&";
        final String ids = id;
        System.out.println(ids);
        final String fbhs = fbh;
        System.out.println(fbhs);
        StringRequest reqstr = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            public void onResponse(String response) {
                System.out.println(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("DATA");
                    for (int i = 0; i < jsonArray.length(); i++){
                        JSONObject js = jsonArray.getJSONObject(i);
                        Cbj cbj = new Cbj();
                        cbj.setHm(js.getString("hm"));
                        cbj.setDz(js.getString("Dz"));
                        cbj.setCmds0(js.getString("cmds0"));
                        cbj.setSysl0(js.getString("sysl0"));
                        cbj.setCmds1(js.getString("cmds1"));
                        cbj.setSysl1(js.getString("sysl1"));
                        System.out.println(i);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        }){
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> hasmap = new HashMap<String, String>();
                String cs = "{\"id\":"+"\""+ids+"\""+","+"\"fbh\":"+"\""+fbhs+"\""+"}";
                System.out.println(cs);
                hasmap.put("DATA", cs);
                return hasmap;
            }
        };
        //reqstr.setTag("one");
        //MeApplcition.getHttpQueue().add(reqstr);
    }
}
