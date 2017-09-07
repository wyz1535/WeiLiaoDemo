package com.leyifu.weiliaodemo.interf;

import java.io.File;

/**
 * Created by hahaha on 2017/6/16 0016.
 */
public interface OnDownloadListeren {

    void onDownloadSuccessed(File file);

    void onDownloadFailed();
}
