package edu.usf.nmtierneymail.futureself;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.content.Context;
import android.telephony.CellLocation;
import android.telephony.gsm.GsmCellLocation;
import android.telephony.cdma.CdmaCellLocation;
import android.support.v4.content.ContextCompat;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;
import android.Manifest;

/**
 * Created by nicoletierney on 10/7/17.
 */

class TelephonyState extends AppCompatActivity {

    void gatherPermissions(){
        //GATHER ALL PERMISSIONS NEEDED TO RUN APPLICATION
        if ( (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this,
                        Manifest.permission.READ_PHONE_STATE)
                        != PackageManager.PERMISSION_GRANTED) ){

            ActivityCompat.requestPermissions(//REQUEST PERMISSIONS NEEDED FOR APP
                    this,
                    new String[]{
                            Manifest.permission.ACCESS_COARSE_LOCATION,
                            Manifest.permission.READ_PHONE_STATE
                    }, 123);
        }//END IF
    }//END CHECK

    @Override
    public void onStart() {
        super.onStart();
        // TelephonyManager
        gatherPermissions();
        final TelephonyManager telMgr;
        telMgr = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        Toast telMgrOutput = null;
        telMgrOutput.setText(telMgr.toString());
        String telephonyOverview = getTelephonyOverview(telMgr);
        telMgrOutput.setText(telephonyOverview);
    }
    private String getTelephonyOverview(final TelephonyManager telMgr) {
        // Post: telMgr in human-readable form has been returned

        // callState = state of the call (idle, ringing, off the hook etc.)
        gatherPermissions();
        int callState = telMgr.getCallState();
        String callStateString = "NA";
        switch (callState) {
            case TelephonyManager.CALL_STATE_IDLE:
                callStateString = "IDLE";
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                callStateString = "OFFHOOK";
                break;
            case TelephonyManager.CALL_STATE_RINGING:
                callStateString = "RINGING";
                break;
        }

        // cellLocation = location of cell in latitude/longitude
        CellLocation cellLocation = telMgr.getCellLocation();
        String cellLocationString = null;
        if (cellLocation instanceof GsmCellLocation)
        {
            cellLocationString = ((GsmCellLocation)cellLocation).getLac()
                    + " " + ((GsmCellLocation)cellLocation).getCid();
        }
        else if (cellLocation instanceof CdmaCellLocation)
        {
            cellLocationString = ((CdmaCellLocation)cellLocation).
                    getBaseStationLatitude() + " " +
                    ((CdmaCellLocation)cellLocation).getBaseStationLongitude();
        }

        // deviceId, deviceSoftwareVersion, line1Number, networkCountryIso,
        // networkOperator, networkOperatorName
        String deviceId = telMgr.getDeviceId();
        String deviceSoftwareVersion = telMgr.getDeviceSoftwareVersion();
        String line1Number = telMgr.getLine1Number();
        String networkCountryIso = telMgr.getNetworkCountryIso();
        String networkOperator = telMgr.getNetworkOperator();
        String networkOperatorName = telMgr.getNetworkOperatorName();

        // phoneType (GSM, CDMA, etc.)
        int phoneType = telMgr.getPhoneType();
        String phoneTypeString = "NA";
        switch (phoneType) {
            case TelephonyManager.PHONE_TYPE_GSM:
                phoneTypeString = "GSM";
                break;
            case TelephonyManager.PHONE_TYPE_CDMA:
                phoneTypeString = "CDMA";
                break;
            case TelephonyManager.PHONE_TYPE_NONE:
                phoneTypeString = "NONE";
                break;
        }

        // simCountryIso, simOperator, simOperatorName, simSerialNumber, simSubscriberId
        String simCountryIso = telMgr.getSimCountryIso();
        String simOperator = telMgr.getSimOperator();
        String simOperatorName = telMgr.getSimOperatorName();
        String simSerialNumber = telMgr.getSimSerialNumber();
        String simSubscriberId = telMgr.getSubscriberId();

        // simState = state of sim card (pin requires, ready, etc.)
        int simState = telMgr.getSimState();
        String simStateString = "NA";
        switch (simState) {
            case TelephonyManager.SIM_STATE_ABSENT:
                simStateString = "ABSENT";
                break;
            case TelephonyManager.SIM_STATE_NETWORK_LOCKED:
                simStateString = "NETWORK_LOCKED";
                break;
            case TelephonyManager.SIM_STATE_PIN_REQUIRED:
                simStateString = "PIN_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_PUK_REQUIRED:
                simStateString = "PUK_REQUIRED";
                break;
            case TelephonyManager.SIM_STATE_READY:
                simStateString = "STATE_READY";
                break;
            case TelephonyManager.SIM_STATE_UNKNOWN:
                simStateString = "STATE_UNKNOWN";
                break;
        }

        // return concatenation of the above data, with labels
        return "Telephone Manager:  " +
                "  \ncallState = " + callStateString +
                "  \ncellLocationString = " + cellLocationString +
                "  \ndeviceId = " + deviceId;

    }

}
