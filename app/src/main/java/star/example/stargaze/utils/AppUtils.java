package star.example.stargaze.utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import star.example.stargaze.R;
import star.example.stargaze.activities.ConfirmActivity;
import star.example.stargaze.api.ApiManager;
import star.example.stargaze.api.ApiService;
import star.example.stargaze.authentication.LoginActivity;
import star.example.stargaze.models.PastData;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;

import com.google.android.material.badge.BadgeDrawable;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class AppUtils {

    public static final int PERMISSION_REQUEST_CODE = 200;


    public static ApiService getApi(Context context) {
        return ApiManager.getRetrofit(context).create(ApiService.class);
    }



    public static AlertDialog showDialogMessage(Context context, String title, String message) {
        AlertDialog alertDialog = new AlertDialog.Builder(context).setTitle(title).setMessage(message).show();
        if (alertDialog.isShowing()) {
            alertDialog.cancel();
        }
        return alertDialog;
    }

;
    public static Dialog hideShowProgress(Context context) {
        Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialogView = inflater.inflate(R.layout.progress_dialog, null);
        dialog.setContentView(dialogView);
        dialog.setCancelable(false);

        return dialog;
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradient(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            activity.requestWindowFeature (Window.FEATURE_NO_TITLE);
            Window window = activity.getWindow();
            window.setFlags (WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.
                    LayoutParams.FLAG_FULLSCREEN);
            window.setNavigationBarColor(activity.getResources().getColor(R.color.colorPrimary));
        }
    }


    public  static  void setUpToolbar(AppCompatActivity activity, Toolbar toolbar, boolean isTitleEnable, boolean isBackEnable, String title){
           activity.setSupportActionBar(toolbar);

            Objects.requireNonNull(activity.getSupportActionBar()).setDisplayShowTitleEnabled(isTitleEnable);
            activity.getSupportActionBar().setTitle(title);
             activity.getSupportActionBar().setDisplayHomeAsUpEnabled(isBackEnable);
             activity.getSupportActionBar().setDisplayShowHomeEnabled(isBackEnable);

             toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     activity.onBackPressed();
                 }
             });
    }

    public static void setUpDrawer(Activity activity, DrawerLayout drawer, Toolbar toolbar,boolean isNotWhite){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity,drawer,toolbar, R.string.navigation_drawer_open,R.string.navigation_drawer_close);
       if (isNotWhite){
           toggle.getDrawerArrowDrawable().setColor(activity.getResources().getColor(R.color.colorPrimaryDark));
       }else {
           toggle.getDrawerArrowDrawable().setColor(activity.getResources().getColor(R.color.white));

       }
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public static String getPostalCodeByCoordinates(Context context, double lat, double lon) throws IOException {

        Geocoder mGeo_coder = new Geocoder(context, Locale.getDefault());
        String zip_code=null;
        String add_details = " ";
        Address address;

        if (mGeo_coder != null) {
            List<Address> addresses = mGeo_coder.getFromLocation(lat, lon, 2);

            if (addresses != null && addresses.size() > 0) {

                for (int i = 0; i < addresses.size(); i++) {
                    address = addresses.get(1);
                    if (address.getPostalCode() != null) {
                        zip_code = address.getAddressLine(0);
                        System.out.println("zipcode "+zip_code);
//                        String [] details = zip_code.split(",");
//                        add_details = details[1]
//                       add_details= add_details+address.getLocality()+" "+address.getFeatureName()+" "
//                               +address.getSubAdminArea()+" "+address.getSubLocality()+" "+address.getPremises()+" "+address.getLocale()
//                                +" "+address.getAddressLine(0);
//
                        break;
                    }

                }
                return zip_code;
            }
        }
        return null;
    }

    public static String getPostalCode(Context context, double lat, double lon) throws IOException {

        Geocoder mGeo_coder = new Geocoder(context, Locale.getDefault());
        String zip_code=null;
        String add_details = " ";
        Address address;

        if (mGeo_coder != null) {
            List<Address> addresses = mGeo_coder.getFromLocation(lat, lon, 2);

            if (addresses != null && addresses.size() > 0) {

                for (int i = 0; i < addresses.size(); i++) {
                    address = addresses.get(1);
                    if (address.getPostalCode() != null) {
                        zip_code = address.getPostalCode();

                    break;
                    }

                }
                return zip_code;
            }
        }
        return null;
    }

    public static void setBadgeCount(Context context, LayerDrawable icon, int count) {

        BadgeDrawableIcon badge;

        // Reuse drawable if possible
        if(icon!=null) {
            Drawable reuse = icon.findDrawableByLayerId(R.id.ic_badge);
            if (reuse instanceof BadgeDrawable) {
                badge = (BadgeDrawableIcon) reuse;
            } else {
                badge = new BadgeDrawableIcon(context);
            }
            badge.setCount(""+count);
            icon.mutate();
            icon.setDrawableByLayerId(R.id.ic_badge, badge);
        }


    }

    public static boolean checkPermission(Activity context) {
        int result = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        int result1 = ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION);

        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
    }

    public static void requestPermission(Activity activity) {

        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
    }

    public static void showMessageOKCancel(String message,Activity activity, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton("OKAY", okListener)
                .create()
                .show();
    }


    public static String getTime(String dateTime){
       String times ="";
        try {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date d = f.parse(dateTime);

            DateFormat date = new SimpleDateFormat("MM/dd/yyyy");
            DateFormat time = new SimpleDateFormat("hh:mm:ss a");
            System.out.println("Date: " + date.format(d));
            System.out.println("Time: " + time.format(d));//09:00:00 am

            String []word= time.format(d).split(":");
            times=times+word[0]+":"+word[1]+" "+word[2].split(" ")[1];

        } catch (ParseException e) {
            e.printStackTrace();
        }
    return times;
    }

    public static String getDate(String dateTime){
        String dateStr="";
        try {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            Date d = f.parse(dateTime);

            DateFormat date = new SimpleDateFormat("EEE, d MMM yyyy");
//            System.out.println("Date: " + date.format(d));
            dateStr = date.format(d);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

public static String getDateTime(String timeStamp){
        String dateTime ="";
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
            Date date = sdf.parse(timeStamp);

            SimpleDateFormat sdf2 = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
            sdf2.setTimeZone(TimeZone.getTimeZone("IST"));
            dateTime = sdf2.format(date);
            System.out.println("ssss"+dateTime);
            return dateTime;
        }catch (ParseException e){
            e.printStackTrace();
        }
    System.out.println("ssss"+dateTime);

    return dateTime;
}
//    public static  boolean isNetworkConnected(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//
//        return cm.getActiveNetworkInfo() != null;
//    }

    public static void avoidDoubleClick(long mLastClickTime){
        // Preventing multiple clicks, using threshold of 1 second
        if (SystemClock.elapsedRealtime() - mLastClickTime < 1500) {
            return;
        }
        mLastClickTime = SystemClock.elapsedRealtime();
    }
    static AlertDialog alertDialog;

    public static void alertMessage(final AppCompatActivity context,String msg) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context,R.style.CustomDialog);
        LayoutInflater inflater =context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.login_dialog_layout, null);
        TextView textView = dialogView.findViewById(R.id.txt_alert_msg);
        textView.setText(msg);
        dialogBuilder.setView(dialogView);



        Button okay = dialogView.findViewById(R.id.okay_btn);

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                logout(context);

            }
        });


        alertDialog = dialogBuilder.create();
        alertDialog.setCancelable(false);
//        alertDialog.getWindow().setGravity(Gravity.BOTTOM);
        alertDialog.show();

    }

    private static void logout(AppCompatActivity context) {
        MyPreferences.getInstance(context).clearPreferences();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        context.finish();
    }

    public static void alert(final Context context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context,R.style.CustomDialog);
        LayoutInflater inflater =((AppCompatActivity)context).getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.book_ticket_layout, null);

        dialogBuilder.setView(dialogView);

        TextView save = dialogView.findViewById(R.id.tv_book_more_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                Intent intent = new Intent(context, ConfirmActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK);
                context.startActivity(intent);

            }
        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                alertDialog.dismiss();
//            }
//        });

        alertDialog = dialogBuilder.create();

        alertDialog.show();

    }

    public static void exitDialog(final AppCompatActivity context) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
        LayoutInflater inflater =context.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_exit_dialog, null);
        dialogBuilder.setView(dialogView);

        Button cancel =  dialogView.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    alertDialog.dismiss();
                } catch (Exception e) {
                }
            }
        });
        Button rateButton =  dialogView.findViewById(R.id.rate_button);
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                rate(context);
            }
        });
        Button yesButton =  dialogView.findViewById(R.id.exit_button);
        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
                context.finish();
            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.show();


    }


    public static  boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

    public static void shareApp(Context context) {
        String code = MyPreferences.getInstance(context).getString(PrefConf.KEY_MY_REFERRAL_CODE,"");

        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String sAux = "Hey,\n Its amazing install stargaze and use this\n Referral code : "+code +"\n Download "+ context.getResources().getString(R.string.app_name) + "\n";

           /* String sAux = "Download\n" + context.getResources().getString(R.string.app_name) + "\n";
           */ sAux = sAux + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n";
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void shareVideo(Context context,String link) {
        try {
            Intent i = new Intent(Intent.ACTION_SEND);
            i.setType("text/plain");
            String sAux = "Download\n" + context.getResources().getString(R.string.app_name) + "\n";
            sAux = sAux + "https://play.google.com/store/apps/details?id=" + context.getPackageName() + "\n"+"Video Link : "+link;
            i.putExtra(Intent.EXTRA_TEXT, sAux);
            context.startActivity(Intent.createChooser(i, "choose one"));
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    public static void galleryPicker(AppCompatActivity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        activity.startActivityForResult(intent, Constants.PICK_PHOTO_FOR_AVATAR);
    }

    public static boolean checkAndRequestPermissions(Activity context) {
        if (context != null) {

            int storagePermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE);
            int storageWritePermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE);

            List<String> listPermissionsNeeded = new ArrayList<>();


            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }

            if (storageWritePermission != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }

            if (!listPermissionsNeeded.isEmpty()) {
                ActivityCompat.requestPermissions(context,
                        listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), PERMISSION_REQUEST_CODE);
                return false;
            }
        } else {
            return false;
        }

        return true;
    }



    public static List<PastData> getPastData(){
        List<PastData>data = new ArrayList<>();
        int [] images ={R.drawable.concert,R.drawable.concert2,R.drawable.concert3,R.drawable.concert4,R.drawable.concert,R.drawable.concert2,R.drawable.concert3,R.drawable.concert4,R.drawable.concert,R.drawable.concert2};
        String[]eventNames ={"comedy show","Shaha-Jan-Night-drama","Shane_E_Bihar","College Fun Festive","comedy show","Shaha-Jan-Night-drama","Shane_E_Bihar","College Fun Festive","comedy show","Shaha-Jan-Night-drama"};

        for(int i=0;i<images.length;i++){
            PastData pastData = new PastData(eventNames[i],"Sunday 09:00 PM","District 9- Vancouver,India",images[i],"District Presents");
            data.add(pastData);
        }
        return data;
    }
}
