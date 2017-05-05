package com.effone.hostismeandroid.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/***
 * Clase para manipular las operaciones simples con db local como son insert,delete,update,etc
 */
public class JsonDataToSend {
    private JSONObject jsonData;

    public JsonDataToSend() {
        this.jsonData = new JSONObject();
    }

   public void setRequest(String request){
       try {
           jsonData.put("request", request);
       } catch (JSONException e) {
           e.printStackTrace();
           throw new RuntimeException(e);
       }
   }

    public void setMessageJsonArray(JSONArray messageArray){
        try {
            jsonData.put("messageArray", messageArray);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
    public void setMessage(String message){
        try {
            jsonData.put("message", message);
        } catch (JSONException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public JSONObject getOurJson(){
        return jsonData;
    }


}
