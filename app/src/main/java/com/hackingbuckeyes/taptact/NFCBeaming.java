package com.hackingbuckeyes.taptact;
import android.app.Activity;
import android.content.Context;
import android.nfc.*;
import android.widget.Toast;

/**
 * This class handles the NFC side of the app. It will make sure the user has the proper hardware
 * and NFC turned on. This will allow the user to send a contact to another phone.
 */

public class NFCBeaming extends Activity {

    private NfcAdapter nfcChip;
    private final CharSequence NO_NFC_TEXT = "Please turn on NFC and Android Beam in Settings";

    public NFCBeaming() {
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
        }
    }

    public boolean doWhenTapButtonPressed() {
        boolean nfcEnabled = !isNFCNotEnabled();
        if (nfcEnabled) {

        }
        return nfcEnabled;
    }
}