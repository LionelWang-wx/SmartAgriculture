package com.tfu.framework.baseInterface;

import java.util.List;

/**
 * 权限处理接口
 */
public interface OnPermissionsResult {
    void success();

    void failed(List<String> failedPermissions);
}
