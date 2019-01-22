package cn.jacky.bundle_main.model;

import android.support.annotation.NonNull;

import cn.jacky.bundle_main.model.callback.CopyDBCallback;


/**
 * 作    者： Huangzj on 2017/9/9/009.
 * 描    述：
 * 修订记录：
 */

public interface FileModel {

     void copyDBToApp(@NonNull final CopyDBCallback callback) ;
}
