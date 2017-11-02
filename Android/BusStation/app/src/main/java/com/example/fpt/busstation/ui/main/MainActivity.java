package com.example.fpt.busstation.ui.main;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.fpt.busstation.R;
import com.example.fpt.busstation.data.db.BusTransferInstructionDto;
import com.example.fpt.busstation.service.AnchorSheetBehavior;
import com.example.fpt.busstation.service.MainActivityCallbacks;
import com.example.fpt.busstation.ui.base.BaseActivity;
import com.example.fpt.busstation.ui.stationBusFragment.StationBusFragment;
import com.example.fpt.busstation.ui.routeInstructionFragment.RouteInstructionFragment;
import com.example.fpt.busstation.data.db.BusRouteInstructionDto;
import com.example.fpt.busstation.data.db.PointDto;
import com.example.fpt.busstation.data.db.RecommendRoutesDto;
import com.example.fpt.busstation.data.db.StationDto;
import com.example.fpt.busstation.data.db.WalkInstructionDto;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Gap;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class MainActivity extends BaseActivity implements
        MainMvpView, OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener,
        MainActivityCallbacks {

    private AnchorSheetBehavior mBottomSheetBehavior;
    private MainMvpPresenter<MainMvpView> mPresenter;
    private FloatingActionButton recordImgView;
    private EditText etTest;
    private long mLastClickTime = 0;
    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private Marker mCurrLocationMarker;
    private LocationRequest mLocationRequest;
    private FloatingActionButton mFab;
    private static final int PERMISSION_LOCATION = 1;
    private static final int PERMISSION_AUDIO = 2;
    private static final int REQUEST_LOCATION = 1;
    private static final int REQUEST_SPEECH_INPUT = 2;
    private static final String BUS_STATION_REQ = "case12";
    private static final String ROUTE_INS_REQ = "case3";
    private List<Marker> listMarker;
    private List<Polyline> listPolyline;
    private StationBusFragment stationFragment;
    private RouteInstructionFragment routeFragment;
    private Projection mLastProjectionMarker;
    private boolean flag_routeInstrution = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("OnCreate", "Fire");
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getContentViewResource() {
        return R.layout.activity_main;
    }

    @Override
    protected void onInit() {
        Log.d("OnInit", "Fire");
        mPresenter = new MainPresenter<>();
        mPresenter.onAttach(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        listMarker = new ArrayList<>();
        listPolyline = new ArrayList<>();
        recordImgView = (FloatingActionButton) findViewById(R.id.recordImgView);
        recordImgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mLastLocation != null)
                   // mPresenter.sendRouteRequest(mLastLocation.getLongitude(), mLastLocation.getLatitude(), "", "", 5);
//                    mPresenter.sendStationRequest(mLastLocation.getLongitude(), mLastLocation.getLatitude(), "", 5);
                    startRecognizeSpeech();
            }

        });
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mBottomSheetBehavior = AnchorSheetBehavior.from(findViewById(R.id.bottom_sheet));
        mBottomSheetBehavior.setHideable(true);
        mBottomSheetBehavior.setPeekHeight(240);
        mBottomSheetBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);
        mBottomSheetBehavior.setAnchorSheetCallback(new AnchorSheetBehavior.AnchorSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {

                if (newState == AnchorSheetBehavior.STATE_EXPANDED) {
                    mFab.setVisibility(View.GONE);
                    recordImgView.setVisibility(View.GONE);
                } else {
                    mFab.setVisibility(View.VISIBLE);
                    recordImgView.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (routeFragment != null || stationFragment != null)
                    if (mBottomSheetBehavior.getState() == AnchorSheetBehavior.STATE_ANCHOR)
                        hideBottomSheet();
                    else showBottomSheet();
            }
        });
        /*btTest.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    mLastClickTime = SystemClock.elapsedRealtime();

                    mPresenter.startRecordAudio();

                    return true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    //handle mutiple click make media crash
                    if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                        btTest.setEnabled(false);
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            public void run() {
                                // Actions to do after 10 seconds
                                mPresenter.stopRecordAudio();
                                btTest.setEnabled(true);
                            }
                        }, 1000);
                    }
                    else {
                        mPresenter.stopRecordAudio();
                        return true;
                    }
                }
                return false;
            }
        });*/

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("OnMapReady", "Fire");
        mMap = googleMap;
        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_map_json));

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("===========>Prepare", "BuildGoogleApiClient");
            buildGoogleApiClient();
        } else {
            Log.d("===========>Prepare", "RequestPermissionSafely");
            requestPermissionsSafely(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_LOCATION);
        }
        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                String[] stringsplit = marker.getTitle().split(",");
                if (stringsplit.length == 1) return null;
                View myContentView = null;
                int type = Integer.parseInt(stringsplit[0]);
                String titleString = stringsplit[2];
                switch (type) {
                    case 1:
                        myContentView = getLayoutInflater().inflate(R.layout.custom_marker_station, null);
                        TextView title = (TextView) myContentView.findViewById(R.id.title);
                        title.setText(titleString);
                        TextView snippet = (TextView) myContentView.findViewById(R.id.snippet);
                        snippet.setText(marker.getSnippet());
                        break;
                    case 2:
                        myContentView = getLayoutInflater().inflate(R.layout.custom_marker_station, null);
                        TextView route = (TextView) myContentView.findViewById(R.id.title);
                        route.setText(titleString);
                        TextView snippetRoute = (TextView) myContentView.findViewById(R.id.snippet);
                        snippetRoute.setText(marker.getSnippet());
                        break;
                    case 4:
                        myContentView = getLayoutInflater().inflate(R.layout.custom_marker_station_just_info, null);
                        TextView station = (TextView) myContentView.findViewById(R.id.stationName);
                        station.setText("Trạm " + titleString);
                        break;
                }
                return myContentView;
            }
        });
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {
                String[] stringsplit = marker.getTitle().split(",");
                if (stringsplit.length == 1) return;
                int type = Integer.parseInt(stringsplit[0]);
                int position = Integer.parseInt(stringsplit[1]);
                LatLng markerPosition, targetPosition;
                Point markerPoint, targetPoint;
                switch (type) {
                    case 1:
                        markerPosition = marker.getPosition();
                        markerPoint = mLastProjectionMarker.toScreenLocation(markerPosition);
                        targetPoint = new Point(markerPoint.x, (int) (markerPoint.y + (findViewById(R.id.map).getHeight() * 0.2)));
                        targetPosition = mLastProjectionMarker.fromScreenLocation(targetPoint);
                        moveMapCamera(targetPosition);
                        stationFragment.changeListBusCross(position);
                        showBottomSheet();
                        break;
                    case 2:
                        markerPosition = marker.getPosition();
                        markerPoint = mLastProjectionMarker.toScreenLocation(markerPosition);
                        targetPoint = new Point(markerPoint.x, (int) (markerPoint.y + (findViewById(R.id.map).getHeight() * 0.2)));
                        targetPosition = mLastProjectionMarker.fromScreenLocation(targetPoint);
                        moveMapCamera(targetPosition);
                        routeFragment.changeToInstructionTab();
                        showBottomSheet();
                        break;
                }
            }
        });
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                moveMapCameraMarker(marker.getPosition());
                if (mBottomSheetBehavior.getState() == AnchorSheetBehavior.STATE_ANCHOR) {
                    hideBottomSheet();
                }
                return false;
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("OnRequestPermission", "Fire");
        switch (requestCode) {
            case PERMISSION_LOCATION:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        Log.d("=======>RequestLocation", "Granted");
                        Log.d("=============>Prepare", "BuildGoogleApiClient");
                        buildGoogleApiClient();
                    }
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showMessage("Permission Denied");
                    Log.d("=======>RequestLocation", "Denied");
                    finish();
                }
                break;
            case PERMISSION_AUDIO:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay!
                    Log.d("=======>RequestAudio", "Granted");
                    showMessage("Vui lòng ấn nút để ghi âm");
                } else {
                    showMessage("Permission Denied");
                    Log.d("=======>RequestAudio", "Denied");
                }
        }
    }

    protected synchronized void buildGoogleApiClient() {
        Log.d("BuildGoogleApiClient", "Fire");
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();

    }

    protected void startLocationUpdate() {
        Log.d("StartLocationUpdate", "Fire");
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            Log.d("======>", "RequestLocationUpdates");
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        }
    }

    protected void stopLocationUpdate() {
        Log.d("StopLocationUpdate", "Fire");
        LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
    }

    public void startRecognizeSpeech() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, getString(R.string.prompt_speech_input));
        try {
            startActivityForResult(intent, REQUEST_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            showMessage("Not Support Speech");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("OnStart", "Fire");
        if (mGoogleApiClient != null) {
            Log.d("======>", "PrepareConnectGoogleAPIClient");
            mGoogleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("OnStop", "Fire");
        if (mGoogleApiClient != null) {
            Log.d("======>", "PrepareDisconnectGoogleAPIClient");
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("OnPause", "Fire");
        if (mGoogleApiClient != null)
            if (mGoogleApiClient.isConnected()) {
                Log.d("======>", "PrepareStopLocationUpdate");
                stopLocationUpdate();
            }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("OnResume", "Fire");
        if (mGoogleApiClient != null)
            if (mGoogleApiClient.isConnected()) {
                Log.d("======>", "PrepareStartLocationUpdate");
                startLocationUpdate();
            }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d("OnConnected", "Fire");
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(20000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //request user to open gps
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest);
        builder.setAlwaysShow(true);
        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.d("======>", "Enabled GPS");
                        Log.d("===========>", "PrepareStartLocationUpdate");
                        startLocationUpdate();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.d("======>", "Not enable GPS. Request GPS");
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(MainActivity.this, REQUEST_LOCATION);

                        } catch (IntentSender.SendIntentException e) {
                            Log.d("======>", "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.d("======>", "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });

    }

    @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("onActivityResult()", Integer.toString(resultCode));
        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        switch (requestCode) {
            case REQUEST_LOCATION:
                switch (resultCode) {
                    case Activity.RESULT_OK: {
                        // All required changes were successfully made
                        break;
                    }
                    case Activity.RESULT_CANCELED: {
                        // The user was asked to change settings, but chose not to
                        finish();
                        break;
                    }
                    default: {
                        break;
                    }
                }
                break;
            case REQUEST_SPEECH_INPUT:
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // showMessage(result.get(0));
                    mPresenter.sendDetectRequest(mLastLocation.getLatitude(),mLastLocation.getLongitude(),result.get(0));
                }
                break;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d("OnLocationChanged", "Fire");

        //Place current location marker
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if(!flag_routeInstrution) {
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
            if (mCurrLocationMarker != null) {
                mCurrLocationMarker.remove();
            } else {
                moveMapCamera(latLng);
            }
            mCurrLocationMarker = mMap.addMarker(markerOptions);
            mLastLocation = location;
        }else{
            mCurrLocationMarker.setPosition(latLng);

            mLastLocation = location;
        }
        //move map camera
    }

    @Override
    public void onBackPressed() {
        if (mBottomSheetBehavior.getState() != AnchorSheetBehavior.STATE_HIDDEN && mBottomSheetBehavior.getState() != AnchorSheetBehavior.STATE_COLLAPSED) {
            hideBottomSheet();
        } else {
            moveTaskToBack(true);
        }
    }

    /* @Override
     public boolean dispatchTouchEvent(MotionEvent event){
         if (event.getAction() == MotionEvent.ACTION_DOWN) {
             if (mBottomSheetBehavior.getState()==AnchorSheetBehavior.STATE_ANCHOR) {

                 Rect outRect = new Rect();
                 findViewById(R.id.bottom_sheet).getGlobalVisibleRect(outRect);

                 if(!outRect.contains((int)event.getRawX(), (int)event.getRawY()))
                     mBottomSheetBehavior.setState(AnchorSheetBehavior.STATE_COLLAPSED);
                     moveMapCamera(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()));
             }
         }

         return super.dispatchTouchEvent(event);
     }*/
    public void placeCurrentLocation(){
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()));
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker());
        if (mCurrLocationMarker != null) {
            mCurrLocationMarker.remove();
        } else {
            moveMapCamera(new LatLng(mLastLocation.getLatitude(),mLastLocation.getLongitude()));
        }
        mCurrLocationMarker = mMap.addMarker(markerOptions);

    }
    @Override
    public void placeStation(double lng, double lat, String address, String name, int position) {
        LatLng latLng = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("1," + position + "," + name);
        markerOptions.snippet(address);
        markerOptions.icon(bitmapDescriptorFromVector(getBaseContext(), R.drawable.ic_marker_station));
        listMarker.add(mMap.addMarker(markerOptions));
    }

    @Override
    public void showBottomSheet() {
        findViewById(R.id.bottom_sheet).post(new Runnable() {
            @Override
            public void run() {
                mBottomSheetBehavior.setState(AnchorSheetBehavior.STATE_ANCHOR);
            }
        });
    }

    @Override
    public void hideBottomSheet() {
        findViewById(R.id.bottom_sheet).post(new Runnable() {
            @Override
            public void run() {
                mBottomSheetBehavior.setState(AnchorSheetBehavior.STATE_HIDDEN);
            }
        });
    }

    @Override
    public void showBusAndStation(List<StationDto> list) {
        flag_routeInstrution=false;

        stationFragment = new StationBusFragment(list);
        stationFragment.setCallbacks(this);
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .replace(R.id.bottom_sheet, stationFragment)
                .commit();
        showBottomSheet();
        moveMapCameraTopMarker(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        mCurrLocationMarker.showInfoWindow();
    }

    @Override
    public void showRouteInstruction(List<RecommendRoutesDto> list,boolean flag) {
        flag_routeInstrution=flag;
        if(!flag) placeCurrentLocation();
        routeFragment = new RouteInstructionFragment(list);
        routeFragment.setCallbacks(this);
        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(null)
                .replace(R.id.bottom_sheet, routeFragment)
                .commit();
        showBottomSheet();
        moveMapCameraTopMarker(new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude()));
        mCurrLocationMarker.showInfoWindow();
    }

    public void moveMapCamera(final LatLng latLng) {
        findViewById(R.id.map).post(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
            }
        });
    }

    public void moveMapCameraTopMarker(final LatLng latLng) {
        findViewById(R.id.map).post(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        mLastProjectionMarker = mMap.getProjection();
                        Point markerPoint = mLastProjectionMarker.toScreenLocation(latLng);
                        Point targetPoint = new Point(markerPoint.x, (int) (markerPoint.y + (findViewById(R.id.map).getHeight() * 0.2)));
                        LatLng targetPosition = mLastProjectionMarker.fromScreenLocation(targetPoint);
                        moveMapCamera(targetPosition);
                    }

                    @Override
                    public void onCancel() {

                    }
                });

            }
        });
    }

    public void moveMapCameraMarker(final LatLng latLng) {
        findViewById(R.id.map).post(new Runnable() {
            @Override
            public void run() {
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16), new GoogleMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        mLastProjectionMarker = mMap.getProjection();
                    }

                    @Override
                    public void onCancel() {

                    }
                });

            }
        });

    }
    @Override
    public void showBottomSheetCB() {
        findViewById(R.id.bottom_sheet).post(new Runnable() {
            @Override
            public void run() {
                mBottomSheetBehavior.setState(AnchorSheetBehavior.STATE_ANCHOR);
            }
        });
    }
    @Override
    public void drawWalkingRouteCB(int position) {

        LatLng from = new LatLng(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        LatLng to =  listMarker.get(position).getPosition();
        removeAllPolyline();
        mPresenter.sendDirectionWalkingRequest(from,to);
    }
    @Override
    public void drawRouteCB(List<Object> instruction) {
        removeAllMarkerAndPolyline();

        for (int i = 0; i < instruction.size(); i++) {
            if (instruction.get(i) instanceof BusRouteInstructionDto) {
                BusRouteInstructionDto dto = (BusRouteInstructionDto) instruction.get(i);
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.color(Color.parseColor(dto.getColor()));

                polylineOptions.width(10);
                polylineOptions.clickable(true);
                polylineOptions.jointType(JointType.ROUND);
                polylineOptions.endCap(new RoundCap());
                polylineOptions.startCap(new RoundCap());
                for (int j = 1; j < dto.getStations().size() - 1; j++) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .title("4," + j + "," + dto.getStations().get(j).getName())
                            .icon(bitmapDescriptorFromVector(getBaseContext(), R.drawable.ic_marker_station))
                            .position(new LatLng(dto.getStations().get(j).getLat(), dto.getStations().get(j).getLng()));
                    listMarker.add(mMap.addMarker(markerOptions));

                }
                for (int j = 0; j < dto.getPath().size(); j++) {
                    PointDto pointDto = dto.getPath().get(j);
                    polylineOptions.add(new LatLng(pointDto.getLat(), pointDto.getLng()));
                }
                listPolyline.add(mMap.addPolyline(polylineOptions));
            } else if (instruction.get(i) instanceof BusTransferInstructionDto) {
                BusTransferInstructionDto transferDto = (BusTransferInstructionDto) instruction.get(i);
                MarkerOptions markerOptions = new MarkerOptions()
                        .title("2," + i + "," + "Trạm " + transferDto.getChangeCoord().getName())
                        .snippet("Chuyển sang tuyến số " + transferDto.getToBus())
                        .icon(bitmapDescriptorFromVector(getBaseContext(), R.drawable.ic_marker_station))
                        .position(new LatLng(transferDto.getChangeCoord().getLat(), transferDto.getChangeCoord().getLng()));
                listMarker.add(mMap.addMarker(markerOptions));

            } else {
                WalkInstructionDto dto = (WalkInstructionDto) instruction.get(i);


                if (dto.getBeginType() == 1 && dto.getEndType() == 2) {
                    LatLng from = new LatLng(dto.getBeginCoord().getLat(), dto.getBeginCoord().getLng());
                    LatLng to = new LatLng(dto.getEndCoord().getLat(), dto.getEndCoord().getLng());
                    MarkerOptions markerOptions = new MarkerOptions()
                            .title("2," + i + ", Vị trí đang đứng")
                            .icon(bitmapDescriptorFromVector(getBaseContext(), R.drawable.ic_marker_current_pos))
                            .snippet("Đi bộ đến trạm " + dto.getEndCoord().getName())
                            .position(from);
                    mCurrLocationMarker.remove();
                    mCurrLocationMarker = mMap.addMarker(markerOptions);
                    listMarker.add(mCurrLocationMarker);
                    moveMapCameraTopMarker(markerOptions.getPosition());
                    mPresenter.sendDirectionWalkingRequest(from,to);
                    markerOptions = new MarkerOptions()
                            .title("2," + i + ", Trạm " + dto.getEndCoord().getName())
                            .icon(bitmapDescriptorFromVector(getBaseContext(), R.drawable.ic_marker_station))
                            .snippet("Đón tuyến số: " + dto.getToBus())
                            .position(to);
                    listMarker.add(mMap.addMarker(markerOptions));
                } else if (dto.getBeginType() == 2 && dto.getEndType() == 3) {
                    MarkerOptions markerOptions = new MarkerOptions()
                            .title("2," + i + ", Trạm " + dto.getBeginCoord().getName())
                            .icon(bitmapDescriptorFromVector(getBaseContext(), R.drawable.ic_marker_station))
                            .snippet("Xuống trạm, đi bộ đến " + dto.getEndCoord().getName())
                            .position(new LatLng(dto.getBeginCoord().getLat(), dto.getBeginCoord().getLng()));
                    listMarker.add(mMap.addMarker(markerOptions));
                    LatLng from = new LatLng(dto.getBeginCoord().getLat(), dto.getBeginCoord().getLng());
                    LatLng to = new LatLng(dto.getEndCoord().getLat(), dto.getEndCoord().getLng());
                    mPresenter.sendDirectionWalkingRequest(from,to);
                    markerOptions = new MarkerOptions()
                            .title("2," + i + ",Điểm cần đến")
                            .snippet(dto.getEndCoord().getName())
                            .icon(bitmapDescriptorFromVector(getBaseContext(), R.drawable.ic_marker_finish))
                            .position(new LatLng(dto.getEndCoord().getLat(), dto.getEndCoord().getLng()));
                    listMarker.add(mMap.addMarker(markerOptions));
                }
            }
        }
    }


    @Override
    public void moveToMarkerAndShowInfo(int position) {

        listMarker.get(position).showInfoWindow();
        moveMapCameraTopMarker(listMarker.get(position).getPosition());
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
        vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
        Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    @Override
    public void removeAllMarkerAndPolyline() {
        for (Marker m : listMarker) {
            m.remove();
        }
        listMarker = new ArrayList<>();
        for (Polyline p : listPolyline) {
            p.remove();
        }
        listPolyline = new ArrayList<>();
    }
    public void removeAllPolyline(){
        for (Polyline p : listPolyline) {
            p.remove();
        }
        listPolyline = new ArrayList<>();
    }
    @Override
    public void drawWalkingRoute(List<LatLng> list) {

        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.color(Color.GRAY);
        polylineOptions.width(10);
        List<PatternItem> pattern = Arrays.<PatternItem>asList(
                new Dash(30), new Gap(20));
        polylineOptions.pattern(pattern);
        for(LatLng latLng: list){
            polylineOptions.add(latLng);
        }
        listPolyline.add(mMap.addPolyline(polylineOptions));
    }
}
