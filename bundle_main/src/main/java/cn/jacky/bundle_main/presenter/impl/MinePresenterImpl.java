package cn.jacky.bundle_main.presenter.impl;

import com.alibaba.fastjson.JSON;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cn.jacky.bundle_main.bean.PCABean;
import cn.jacky.bundle_main.model.impl.ContextBehaviorImpl;
import cn.jacky.bundle_main.presenter.contract.MineContract;
import cn.jacky.bundle_main.ui.basepresenter.BasePresenterImpl;

/**
 * Created by Hzj on 2017/8/28.
 * 我的界面,相关接口
 */

public class MinePresenterImpl extends BasePresenterImpl implements MineContract.Presenter{
    private MineContract.View iMineView;

    public MinePresenterImpl(MineContract.View iMineView) {
        super(iMineView);
        this.iMineView = iMineView;
    }

    @Override
    public void getPCAList() {
        final List<PCABean> pcaBeanList = new ArrayList<>();
        //解析本地省市数据
        new Thread(new Runnable() {
            @Override
            public void run() {
                String pca = readPCA();
                pca = pca.replace("\n", "");
                try {
                    JSONArray jsonArray = new JSONArray(pca);
                    if (null != jsonArray && jsonArray.length() > 0) {
                        List<PCABean> pcaList = JSON.parseArray(jsonArray.toString(), PCABean.class);
                        pcaBeanList.addAll(pcaList);
                    }
                    if (null != pcaBeanList && null != iMineView)
                        iMineView.onGetPCASuccess(pcaBeanList);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }


    /**
     * 从文本中读取 省市区城市列表
     *
     * @return
     */
    private String readPCA() {
        InputStream in = null;
        ByteArrayOutputStream out = null;
        try {
            in = ContextBehaviorImpl.getApplicationContext().getAssets().open("PCA.json");
            out = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int length = -1;
            while ((length = in.read(b)) != -1) {
                out.write(b, 0, length);
            }
            return new String(out.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void unbindView() {
        iMineView=null;
    }
}
