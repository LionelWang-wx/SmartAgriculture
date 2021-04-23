package com.tfu.smartagriculture.wx;

import android.Manifest;
import android.os.Bundle;

import com.tfu.framework.base.BaseActivity;
import com.tfu.framework.baseInterface.OnPermissionsResult;
import com.tfu.smartagriculture.R;

import java.util.List;

import static com.tfu.framework.utils.PermissionUtils.checkWindowPermission;
import static com.tfu.framework.utils.PermissionUtils.requestWindowPermission;
import static com.tfu.framework.utils.ToastUtils.showToast;

/**
 * 逻辑
 * 1.一个公司或者一个用户就是一个账户
 * 2.每个用户可以将自己的大棚和农田里的传感器通过扫描的方式进行添加创建场景
 * 3.App把收集到的数据进行多角度整理分析形成直观可视化数据
 * 4.直观可视化数据
 * (1).基本数据:温度、湿度、风力、土壤酸碱度等(图表化)
 * (2).对基础数据进行多维度分析形成新的实用数据,通过这些实用数据分析具体情况
 * 举例：比如土壤酸碱度和湿度对农作物的成长的影响
 * (3)提供交流平台让每个人都可以看到数据进行分析，得出解决方案
 * <p>
 * 核心：就是将传感器的数据收集到，然后将数据共享,供所以人交流讨论
 */

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkWindowPermission(this)) {
            requestWindowPermission(this);
        }
        //申明所需权限
        String[] mStrPermission = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE,

        };

        requestPermissionsResult(new OnPermissionsResult() {
            @Override
            public void success() {
                showToast(MainActivity.this, "权限申请成功");
            }

            @Override
            public void failed(List<String> failedPermissions) {
                if (mStrPermission.length == failedPermissions.size()) {
                    showToast(MainActivity.this, "权限申请失败");
                } else if (mStrPermission.length > failedPermissions.size()) {
                    showToast(MainActivity.this, "部分权限申请失败");
                }
            }
        }, mStrPermission);
    }

}