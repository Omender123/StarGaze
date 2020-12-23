package star.example.stargaze.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import star.example.stargaze.R;
import star.example.stargaze.adapters.DrawerAdapter;
import star.example.stargaze.authentication.ForgotActivity;
import star.example.stargaze.authentication.LoginActivity;
import star.example.stargaze.databinding.ActivityMainBinding;
import star.example.stargaze.fragments.FragmentArtist;
import star.example.stargaze.fragments.FragmentBlog;
import star.example.stargaze.fragments.FragmentHelpFAQ;
import star.example.stargaze.fragments.FragmentMyEvent;
import star.example.stargaze.fragments.FragmentOrders;
import star.example.stargaze.fragments.FragmentHome;
import star.example.stargaze.fragments.FragmentWallet;
import star.example.stargaze.models.response.ProfileImageResp;
import star.example.stargaze.sharedPref.MyPreferences;
import star.example.stargaze.sharedPref.PrefConf;
import star.example.stargaze.utils.AppUtils;
import star.example.stargaze.utils.Constants;
import star.example.stargaze.utils.ImagePath;
import star.example.stargaze.view_presenter.MainActivityPresenter;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener ,
        DrawerAdapter.DrawerMenuItemClickListener, View.OnClickListener, MainActivityPresenter.MainView {
        private ActivityMainBinding binding;
        private Context context;
        private FragmentHome fragmentHome;
        private DrawerAdapter drawerAdapter;
        private Dialog dialog;
        private String profileImage;
        public  boolean permissionStatus;
        private int PICK_PHOTO_FOR_AVATAR = 1;
        private View view;

        private MainActivityPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        context = MainActivity.this;
        AppUtils.checkAndRequestPermissions(this);
        dialog = AppUtils.hideShowProgress(context);
        view = binding.getRoot();
        fragmentHome = new FragmentHome();
        presenter = new MainActivityPresenter(this);
        drawerAdapter = new DrawerAdapter(this,context);
        AppUtils.setUpToolbar((AppCompatActivity)context,binding.toolbar,false,false,"");
        AppUtils.setUpDrawer((AppCompatActivity)context, binding.drawerLayout, binding.toolbar,false);
        binding.bottomNav.setOnNavigationItemSelectedListener(this);
        binding.head.drawerMenuRecycler.setLayoutManager(new GridLayoutManager(context, 1));
        binding.head.drawerMenuRecycler.setAdapter(drawerAdapter);

        setFrameLayout(fragmentHome);

         binding.head.imgCameraPicker.setOnClickListener(this);
         binding.head.editProfileBtn.setOnClickListener(this);
        setProfileName();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setProfileName();
    }

    private void setProfileName(){

        if (MyPreferences.getInstance(context).getBoolean(PrefConf.KEY_IS_LOGGED_IN,false) &&  MyPreferences.getInstance(context).getBoolean(PrefConf.KEY_IS_PROFILE_UPDATED,false)){
            String userNameChar = MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_NAME, "");
            profileImage = MyPreferences.getInstance(context).getString(PrefConf.PROFILEPIC, "");
            binding.head.tvHeadNavUserName.setText(userNameChar.toUpperCase());
            binding.head.tvHeadNavNumEmail.setText(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_EMAIL, ""));
            Glide.with(context).load(Constants.IMG_URL+""+profileImage).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.ic_profile_pic).into(binding.head.imgProfilePic);
        }else {
            profileImage = MyPreferences.getInstance(context).getString(PrefConf.PROFILEPIC, "");
            String userNameChar = MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_NAME, "");
            binding.head.tvHeadNavUserName.setText(userNameChar.toUpperCase());
            binding.head.tvHeadNavNumEmail.setText(MyPreferences.getInstance(context).getString(PrefConf.KEY_USER_EMAIL, ""));
            Glide.with(context).load(Constants.IMG_URL+""+profileImage).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.ic_profile_pic).into(binding.head.imgProfilePic);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_home:
                binding.toolbar.setVisibility(View.VISIBLE);
                setFrameLayout(fragmentHome);
                break;
            case R.id.action_artist:
                binding.toolbar.setVisibility(View.GONE);
                FragmentArtist artist = new FragmentArtist();
                setFrameLayout(artist);
                break;
            case R.id.action_events:
//                binding.toolbar.setVisibility(View.GONE);
                FragmentMyEvent fragmentMyEvent = new FragmentMyEvent();
                setFrameLayout(fragmentMyEvent);
                break;
            case R.id.action_wallet:
                binding.toolbar.setVisibility(View.GONE);
                FragmentWallet wallet = new FragmentWallet();
                setFrameLayout(wallet);
//                Intent intent = new Intent(context,MyWalletActivity.class);
//                startActivity(intent);
                break;
            case R.id.action_profile:
                Intent intent1 = new Intent(context,UpdateProfileActivity.class);
                startActivity(intent1);
                break;
        }
        return true;
    }

    private void setFrameLayout(Fragment fragment) {

        FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();

        ft.replace(R.id.fragment_container, fragment);
        ft.commit();

    }

    @Override
    public void onDrawerItemClick(int position) {
        Intent intent = null;
        switch (position){

            case 0:
                binding.toolbar.setVisibility(View.GONE);
                FragmentOrders fragmentOrders = new FragmentOrders();
                setFrameLayout(fragmentOrders);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 1:
                binding.toolbar.setVisibility(View.GONE);
                FragmentWallet fragmentWallet = new FragmentWallet();
                setFrameLayout(fragmentWallet);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 2:
                intent = new Intent(context, MyReferralActivity.class);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
         /*   case 3:
                AppUtils.shareApp(context);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
        */    case 3:
                binding.toolbar.setVisibility(View.GONE);
                FragmentHelpFAQ fragmentSupport = new FragmentHelpFAQ();
                setFrameLayout(fragmentSupport);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
/*
            case 5:

                binding.toolbar.setVisibility(View.GONE);
                FragmentBlog fragmentBlog = new FragmentBlog();
                setFrameLayout(fragmentBlog);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;*/
           /* case 6:
                intent = new Intent(context, ForgotActivity.class);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;*/
            case 4:
                dialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        logout();
                        dialog.dismiss();
                    }
                }, 500);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                finish();
                break;

        }
        if(intent !=null){
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.edit_profile_btn:
                Intent intent = new Intent(context, UpdateProfileActivity.class);
                startActivity(intent);
                binding.drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case R.id.img_camera_picker:
                galleryPicker();
                break;
        }

    }


    private void logout() {
        MyPreferences.getInstance(context).clearPreferences();
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

   /* @Override
    public void onBackPressed() {

      // AppUtils.exitDialog(this);


    }*/

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case AppUtils.PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    permissionStatus = true;

                } else {
                    permissionStatus = false;
                    String msg = "Please Allow Permission to share.";
                    customAlert(msg);

                }
                return;
        }
    }

    private void customAlert(String msg) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        }).show();
    }

    private void galleryPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_PHOTO_FOR_AVATAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO_FOR_AVATAR && resultCode == RESULT_OK) {
            if (data == null)
                return;
            Uri uri = data.getData();
            System.out.println("urii  "+uri +" "+uri.getPath());
            String path  = ImagePath.getPath(context,uri);
            System.out.println("urii path "+path );
            if(path!=null && !path.equals("")) {
                File file = new File(path);
                uploadImage(file);
            }

        }
    }

    @Override
    public void showHideProgress(boolean isShow) {
            if(isShow){
                dialog.show();
            }else {
                dialog.dismiss();
            }
    }

    @Override
    public void onError(String message) {
        if(message.equalsIgnoreCase("Token Expired")){
            AppUtils.alertMessage(this,"Your account is logged in to new device or your session is expired!");
        }else {
            Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onProfileUpload(ProfileImageResp imageResp) {
        MyPreferences.getInstance(context).putString(PrefConf.PROFILEPIC,imageResp.getProfileImage());

        Glide.with(context).load(Constants.IMG_URL+""+imageResp.getProfileImage()).apply(new RequestOptions().circleCrop()).placeholder(R.drawable.ic_profile_pic).into(binding.head.imgProfilePic);


//        AppUtils.showMessageOKCancel("Your Profile Updated Successfully!",this, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                finish();
//            }
//        });
    }

    @Override
    public void onFailure(Throwable t) {
        Snackbar.make(view, t.getMessage(), Snackbar.LENGTH_SHORT).show();
    }

    private void uploadImage( File file){
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part image =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
        presenter.uploadImage(image);
    }
}
