package com.abdosalm.parks.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {
    public static final String PARKS_URL = "https://developer.nps.gov/api/v1/parks?parkCode=&api_key=dT57RYnMIRcCWrPY19fFV3UFdlzc2FPSxjCL32vo";
    public static String getParksUrl(String stateCode){
       return  "https://developer.nps.gov/api/v1/parks?parkCode="+stateCode+"&api_key=dT57RYnMIRcCWrPY19fFV3UFdlzc2FPSxjCL32vo";
    }
    public static void hideSoftKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken() , 0);
    }
}
