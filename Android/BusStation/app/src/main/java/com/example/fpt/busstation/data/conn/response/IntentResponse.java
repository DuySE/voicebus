package com.example.fpt.busstation.data.conn.response;

import com.example.fpt.busstation.data.conn.ApiContansts;
import com.example.fpt.busstation.data.db.IntentDTO;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by cuong on 9/27/2017.
 */

public class IntentResponse {
    public static final String TAG = IntentResponse.class.getSimpleName();
    private static IntentDTO intentDTO;
    public static IntentDTO convertData(String datas){
        if(datas!=null){
            intentDTO = new IntentDTO();
            JSONObject jsonObject = null;
            try{
                jsonObject = new JSONObject(datas);
                intentDTO.setType(jsonObject.getInt(ApiContansts.KEY_TYPE));
                switch (intentDTO.getType()){
                    case 1:
                        intentDTO.setMess(jsonObject.getString(ApiContansts.KEY_MESS));
                        break;
                    case 2:
                        intentDTO.setMess(jsonObject.getString(ApiContansts.KEY_MESS));
                        break;
                    case 3:
                        intentDTO.setMess(jsonObject.getString(ApiContansts.KEY_MESS));
                        intentDTO.setBusnum(jsonObject.getString(ApiContansts.KEY_BUSNUM));
                        break;
                    case 4:
                        intentDTO.setMess(jsonObject.getString(ApiContansts.KEY_MESS));
                        intentDTO.setBusnum(jsonObject.getString(ApiContansts.KEY_BUSNUM));
                        break;
                    case 5:
                        intentDTO.setMess(jsonObject.getString(ApiContansts.KEY_MESS));
                        intentDTO.setBegin(jsonObject.getString(ApiContansts.KEY_BEGIN));
                        intentDTO.setEnd(jsonObject.getString(ApiContansts.KEY_END));
                        break;
                    case 6:
                        intentDTO.setMess(jsonObject.getString(ApiContansts.KEY_MESS));

                        intentDTO.setEnd(jsonObject.getString(ApiContansts.KEY_END));
                        break;
                    case 7:
                        intentDTO.setMess(jsonObject.getString(ApiContansts.KEY_MESS));
                        break;
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }
        return intentDTO;

    }
}
