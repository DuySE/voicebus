package com.example.fpt.busstation.ui.main;

import android.Manifest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.fpt.busstation.data.conn.RestClient;
import com.example.fpt.busstation.data.conn.request.DirectionRequest;
import com.example.fpt.busstation.data.conn.request.IntentRequest;
import com.example.fpt.busstation.data.conn.request.RouteRequest;
import com.example.fpt.busstation.data.conn.request.StationRequest;
import com.example.fpt.busstation.data.conn.response.DirectionResponse;
import com.example.fpt.busstation.data.conn.response.IntentResponse;
import com.example.fpt.busstation.data.conn.response.RouteResponse;
import com.example.fpt.busstation.data.conn.response.StationResponse;
import com.example.fpt.busstation.data.db.DirectionDTO;
import com.example.fpt.busstation.data.db.IntentDTO;
import com.example.fpt.busstation.service.OnResponseStringListener;
import com.example.fpt.busstation.ui.base.BasePresenter;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;
import com.example.fpt.busstation.data.db.StationDto;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

/**
 * Created by cuong on 9/13/2017.
 */

public class MainPresenter<T extends MainMvpView> extends BasePresenter<T> implements MainMvpPresenter<T> {
    MediaPlayer mediaPlayer;
    private MediaRecorder mediaRecorder;
    private static final int PERMISSION_AUDIO = 2;
    private static final String AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/voiceRecord.3gp";
    private boolean flag_block_media=false;
    public MainPresenter() {


        super();

    }


    @Override
    public void sendDetectRequest(final Double lat, final Double lng, String text) {
        Log.d("Send request", "test");
        getMvpView().showLoading();
        IntentRequest.sendGetRequest(text, new OnResponseStringListener() {
            @Override
            public void onResponse(String data) {
                try {
                    IntentDTO dto = IntentResponse.convertData(data);


                    Log.d("Type",dto.getType()+"");
                    switch (dto.getType()) {
                        case 1:
                            sendStationRequest(lng, lat, "", dto.getType());
                            break;
                        case 2:
                            sendStationRequest(lng, lat, "", dto.getType());
                            break;
                        case 3:
                            sendStationRequest(lng, lat, dto.getBusnum(), dto.getType());
                            break;
                        case 4:
                            sendStationRequest(lng, lat, dto.getBusnum(), dto.getType());
                            break;
                        case 5:
                            sendRouteRequest(lng, lat, dto.getBegin(), dto.getEnd(), dto.getType());
                            break;
                        case 6:
                            sendRouteRequest(lng, lat, "", dto.getEnd(), dto.getType());
                            break;
                        case 7:
                            break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
    }

    @Override
    public void sendRouteRequest(Double lng, Double lat, String begin, String end, final int type) {

        RouteRequest.sendGetRequest(lng, lat, begin, end, type, new OnResponseStringListener() {
            @Override
            public void onResponse(String data) {
                List<RecommendRoutesDto> result = RouteResponse.convertData(data);
                if(result.size()!=0) {

                    getMvpView().removeAllMarkerAndPolyline();
                    getMvpView().showRouteInstruction(result, type == 6);
                }
                else{
                    sendTTSRequest("Không tìm thấy đường đi");
                }
                getMvpView().hideLoading();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
    }

    @Override
    public void sendStationRequest(Double lng, Double lat, String number, int type) {

        StationRequest.sendGetRequest(lng, lat, number, type, new OnResponseStringListener() {
            @Override
            public void onResponse(String data) {
                List<StationDto> result = StationResponse.convertData(data);
                if(result.size()!=0) {
                    getMvpView().removeAllMarkerAndPolyline();
                    for (int i = 0; i < result.size(); i++) {
                        StationDto dto = result.get(i);
                        getMvpView().placeStation(dto.getLng(), dto.getLat(), dto.getStationAddress(), dto.getStationName(), i);
                    }
                    getMvpView().showBusAndStation(result);
                }else{
                    sendTTSRequest("Không tìm thấy trạm");
                }
                getMvpView().hideLoading();
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("error", error.toString());
            }
        });
    }
    @Override
    public void sendDirectionWalkingRequest(LatLng from, LatLng to){
        DirectionRequest.sendGetRequest(from, to, new OnResponseStringListener() {
            @Override
            public void onResponse(String data) {
                List<DirectionDTO> result = DirectionResponse.convertData(data);
                for(DirectionDTO dto : result){
                    getMvpView().drawWalkingRoute(dto.getPoints());
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });
    }
    @Override
    public void sendTTSRequest(String text) {
        Log.d("String text", text);

        RestClient.getInstance().postAudioRequest("http://api.openfpt.vn/text2speech/v4", text, new OnResponseStringListener() {
            @Override
            public void onResponse(String data) {
                try {

                    JSONObject jsonObject = new JSONObject(data);
                    int success = jsonObject.getInt("error");
                    if (success == 0) {
                        String url = jsonObject.getString("async");

                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                mediaPlayer.stop();
                                mediaPlayer.release();

                            }
                        });

                        try {
                            mediaPlayer.setDataSource(url);
                            mediaPlayer.prepare();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        mediaPlayer.start();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        });

    }

    @Override
    public void startRecordAudio() {
        if (checkPermissionAudio()) {
            mediaRecorderReady();
            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            getMvpView().requestPermissionsSafely(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO}, PERMISSION_AUDIO);
        }
    }

    public void mediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }

    public boolean checkPermissionAudio() {
        return getMvpView().hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) && getMvpView().hasPermission(Manifest.permission.RECORD_AUDIO);
    }

    @Override
    public void stopRecordAudio() {
        if (!checkPermissionAudio()) return;
        getMvpView().showLoading();
        mediaRecorder.stop();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mediaPlayer.stop();
                mediaPlayer.release();
                File file = new File(AudioSavePathInDevice);
                file.delete();
                getMvpView().hideLoading();
            }
        });
        try {
            mediaPlayer.setDataSource(AudioSavePathInDevice);
            mediaPlayer.prepare();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();
    }
}
