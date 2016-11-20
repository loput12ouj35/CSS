package com.example.loput.css;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loput.css.fragments.CamFragment;
import com.example.loput.css.fragments.ConnectedFragment;
import com.example.loput.css.fragments.DisconnectedFragment;
import com.example.loput.css.fragments.MicFragment;

public class MainActivity extends AppCompatActivity {

    private Setting setting;        //설정값 저장
    private Fragment dcFragment;        //연결 전 화면
    private Fragment cFragment;         //연결 후 화면
    private Fragment camFragment;       //캠 화면
    private Fragment micFragment;       //마이크 화면

    FloatingActionButton fab;       //하단 우측 버튼

    private boolean connected = false;      //PC와 연결 되었는가?


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Xeno : requesting permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getApplicationContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO},
                        MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            } else {
                Log.d("Home", "Already granted access");
                //initializeView(v);
            }
        }

        //기본 메뉴바, 버튼 세팅
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "연결하는 방법: ???", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //변수 초기화
        setting = Setting.getSetting();
        dcFragment = new DisconnectedFragment();
        cFragment = new ConnectedFragment();
        camFragment = new CamFragment();
        micFragment = new MicFragment();

        //화면 초기화
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
        ft.replace(R.id.frame_layout, dcFragment);

        ft.commit();


    }

    // Xeno : requesting permission
    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 29;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("Home", "Permission Granted");
                    //initializeView(v);
                } else {
                    Log.d("Home", "Permission Failed");
                    //Toast.makeText(getActivity().getBaseContext(), "You must allow permission record audio to your mobile device.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            // Add additional cases for other permissions you may have asked for
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showSettingDialog();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        changeScreen(Screen.c);
        fab.setVisibility(View.GONE);
    }

    //화면 전환
    public enum Screen {dc, c, cam, mic}

    private void micSetting()
    {
    }

    public void changeScreen(Screen sc) {
        Fragment f = dcFragment;

        switch (sc){
            case dc:
                break;
            case c:
                f = cFragment;
                break;
            case cam:
                f = camFragment;
                break;
            case mic:
                f = micFragment;
                break;
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.setCustomAnimations(android.R.animator.fade_in,android.R.animator.fade_out);
        ft.replace(R.id.frame_layout, f);

        ft.commit();

    }


    //설정 다이얼로그 호출
    private void showSettingDialog() {
        //다이얼로그 생성 시작
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final LayoutInflater inflater =
                (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        final View Viewlayout = inflater.inflate(R.layout.dialog_setting,
                (ViewGroup) findViewById(R.id.layout_setting_dialog));

        //각종 뷰 로드
        final TextView tvSound = (TextView) Viewlayout.findViewById(R.id.textViewSound);
        final SeekBar sb = (SeekBar) Viewlayout.findViewById(R.id.seekBar1);

        final Button btSoundLow = (Button) Viewlayout.findViewById(R.id.buttonLowSound);
        final Button btSoundMedium = (Button) Viewlayout.findViewById(R.id.buttonMediumSound);
        final Button btSoundHigh = (Button) Viewlayout.findViewById(R.id.buttonHighSound);

        final Button btVideoLow = (Button) Viewlayout.findViewById(R.id.buttonLowVideo);
        final Button btVideoMedium = (Button) Viewlayout.findViewById(R.id.buttonMediumVideo);
        final Button btVideoHigh = (Button) Viewlayout.findViewById(R.id.buttonHighVideo);

        //버튼 리스너
        View.OnClickListener l1 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btSoundLow.setTextColor(Color.BLACK);
                btSoundMedium.setTextColor(Color.BLACK);
                btSoundHigh.setTextColor(Color.BLACK);

                switch (v.getId()){
                    case R.id.buttonLowSound:
                        setting.soundQuality = Setting.Quality.low;
                        btSoundLow.setTextColor(Color.RED);
                        break;
                    case R.id.buttonMediumSound:
                        setting.soundQuality = Setting.Quality.medium;
                        btSoundMedium.setTextColor(Color.RED);
                        break;
                    case R.id.buttonHighSound:
                        setting.soundQuality = Setting.Quality.high;
                        btSoundHigh.setTextColor(Color.RED);
                        break;
                }
            }
        };

        View.OnClickListener l2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btVideoLow.setTextColor(Color.BLACK);
                btVideoMedium.setTextColor(Color.BLACK);
                btVideoHigh.setTextColor(Color.BLACK);

                switch (v.getId()){
                    case R.id.buttonLowVideo:
                        setting.videoQuality = Setting.Quality.low;
                        btVideoLow.setTextColor(Color.RED);
                        break;
                    case R.id.buttonMediumVideo:
                        setting.videoQuality = Setting.Quality.medium;
                        btVideoMedium.setTextColor(Color.RED);
                        break;
                    case R.id.buttonHighVideo:
                        setting.videoQuality = Setting.Quality.high;
                        btVideoHigh.setTextColor(Color.RED);
                        break;

                }
            }
        };

        btSoundLow.setOnClickListener(l1);
        btSoundMedium.setOnClickListener(l1);
        btSoundHigh.setOnClickListener(l1);

        btVideoLow.setOnClickListener(l2);
        btVideoMedium.setOnClickListener(l2);
        btVideoHigh.setOnClickListener(l2);


        //버튼 색깔 로드
        if(setting.soundQuality == Setting.Quality.low)
            btSoundLow.setTextColor(Color.RED);
        else if (setting.soundQuality == Setting.Quality.medium)
            btSoundMedium.setTextColor(Color.RED);
        else
            btSoundHigh.setTextColor(Color.RED);

        if(setting.videoQuality == Setting.Quality.low)
            btVideoLow.setTextColor(Color.RED);
        else if(setting.videoQuality == Setting.Quality.medium)
            btVideoMedium.setTextColor(Color.RED);
        else
            btVideoHigh.setTextColor(Color.RED);


        //텍스트 뷰 로드
        tvSound.setText("음량: " + setting.sound);

        //seekbar 로드
        sb.setMax(100);
        sb.setProgress(setting.sound);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvSound.setText("음량: " + progress);
                setting.sound = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });






        //다이얼로그 띄우기
        builder.setTitle("설정").setView(Viewlayout).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}
