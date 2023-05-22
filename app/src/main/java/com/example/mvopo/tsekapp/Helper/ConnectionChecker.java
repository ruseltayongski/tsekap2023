package com.example.mvopo.tsekapp.Helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by mvopo on 12/21/2017.
 */

public class ConnectionChecker {
    private Context context;

    public ConnectionChecker(Context context) {
        super();
        this.context = context;
    }
    public boolean isConnectedToInternet(){

        ConnectivityManager conn = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        if(conn!=null)
        {
            NetworkInfo[] info = conn.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;

    }
}
