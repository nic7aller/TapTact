package com.hackingbuckeyes.taptact;

import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private NfcAdapter nfcChip;
    private final CharSequence NO_NFC_TEXT = "Please turn on NFC and Android Beam in Settings";

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

    public NdefMessage createNdefMessage (NfcEvent event) {
        return new NdefMessage((new ContactPicker()).getContact());
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void pickContact(View view){
        ContactPicker contact = new ContactPicker();
        contact.chooseContact();
    }
}
