package com.hackingbuckeyes.taptact;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcAdapter.OnNdefPushCompleteCallback;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * This class handles the NFC side of the app. It will make sure the user has the proper hardware
 * and NFC turned on. This will allow the user to send a contact to another phone.
 *
 * @author Nic Siebenaller
 * @author Ryan Gleske
 *
 * Much code help from Android Developers and Github user dideler
 */

public class MainActivity extends AppCompatActivity implements
        CreateNdefMessageCallback, OnNdefPushCompleteCallback {

    private ContactPicker contact = new ContactPicker();
    private NfcAdapter nfcChip;

    private final CharSequence NO_NFC_TEXT = "Please turn on NFC and Android Beam in Settings";
    private final CharSequence SENT_TEXT = "Your selected contact information has been transferred";
    private final int BEAM_SUCCESS = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nfcChip = NfcAdapter.getDefaultAdapter(this);
        toastIfNotEnabled();
    }

    private boolean isNFCNotEnabled() {
        return !nfcChip.isEnabled() || !nfcChip.isNdefPushEnabled();
    }

    private void toastIfNotEnabled() {
        if (isNFCNotEnabled()) {
            Toast toast = Toast.makeText(getApplicationContext(), NO_NFC_TEXT, Toast.LENGTH_LONG);
            toast.show();
            startActivity(new Intent(Settings.ACTION_NFCSHARING_SETTINGS));
        }
    }

    @Override
    public NdefMessage createNdefMessage (NfcEvent event) {
        return new NdefMessage((new ContactPicker()).getContact());
    }

    @Override
    public void onNdefPushComplete(NfcEvent arg0)
    {
        handler.obtainMessage(BEAM_SUCCESS).sendToTarget();
    }

    private final Handler handler = new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case BEAM_SUCCESS:
                    Toast toast = Toast.makeText(getApplicationContext(), SENT_TEXT, Toast.LENGTH_LONG);
                    toast.show();
                    Vibrator vibe = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE) ;
                    if (vibe.hasVibrator()) {
                        vibe.vibrate(500);
                    }
                    break;
            }
        }
    };

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void pickContact(View view){
        contact.chooseContact();
    }
}
