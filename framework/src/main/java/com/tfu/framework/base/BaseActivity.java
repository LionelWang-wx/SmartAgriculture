package com.tfu.framework.base;

import android.content.pm.PackageManager;
import android.os.Bundle;

import com.tfu.framework.baseInterface.OnPermissionsResult;
import com.tfu.framework.utils.PermissionUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import static com.tfu.framework.entity.Constants.REQUESTCODE;
import static com.tfu.framework.utils.PermissionUtils.failedPermissions;


public class BaseActivity extends AppCompatActivity {

    private OnPermissionsResult onPermissionsResult;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    /**
     * 请求权限的回调
     *
     * @param onPermissionsResult
     * @param permissions
     */
    protected void requestPermissionsResult(OnPermissionsResult onPermissionsResult, String... permissions) {
        this.onPermissionsResult = onPermissionsResult;
        PermissionUtils.getInstance().requestPermissions(this, permissions);
    }


    /**
     * 请求权限的回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 授予结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        failedPermissions.clear();
        switch (requestCode) {
            case REQUESTCODE:
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            //请求失败权限
                            failedPermissions.add(permissions[i]);
                        }
                    }
                    //将接口返回到调用处
                    if (onPermissionsResult != null) {
                        if (failedPermissions.size() > 0) {
                            onPermissionsResult.failed(failedPermissions);
                        } else {
                            onPermissionsResult.success();
                        }
                    }
                }
                break;
        }
    }
}


