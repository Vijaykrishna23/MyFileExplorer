package com.example.myfileexplorer.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfileexplorer.utils.Constants;
import com.example.myfileexplorer.utils.PermissionUtils;

import java.lang.reflect.Method;

import butterknife.ButterKnife;

/**
 * Abstract Activity class with common features to keep the code DRY.
 * Also binds ButterKnife to the view, so no need to bind in the
 * child classes.
 */
public abstract class AbstractAppCompatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getActivityLayout());
        ButterKnife.bind(this);

        PermissionUtils.requestPermissions(this);


        //Disables FileUriExposedException by overriding the method on the class.
        if (Build.VERSION.SDK_INT >= 24) {
            try {
                Method disableDeathOnFileUriExposureMethod = StrictMode.class.getMethod(Constants.FORCE_OVERRIDE_DISABLE_FILE_URI_EXCEPTION_METHOD_NAME);
                disableDeathOnFileUriExposureMethod.invoke(null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * Returns the activity to bind with the Ui class.
     */
    public abstract int getActivityLayout();


    /**
     * Given the classname, navigates to the activity and pushes the current
     * activity on backstack.
     */
    public void moveToActivity(Class<?> navigateToClassName) {
        startActivity(new Intent(this, navigateToClassName));
    }

    /**
     * Given the classname, navigates to the activity and destroys
     * the current activity.
     */
    public void moveToActivityAndFinishCurrentActivity(Class<?> navigateToClassName) {
        moveToActivity(navigateToClassName);
        finish();
    }


}
