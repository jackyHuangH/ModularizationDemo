package cn.jacky.bundle_main.ui.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.zenchn.support.utils.StringUtils;

import cn.jacky.bundle_main.R;


/**
 * @author:Hzj
 * @date :2018/4/3/003
 * desc  ：替代Dialog 的优化方案，官方推荐
 * record：
 */

public class PromptDialogFragment extends DialogFragment {
    private String mContent;

    public PromptDialogFragment() {
    }

    public PromptDialogFragment setContent(String content) {
        this.mContent = content;
        return this;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.fragment_prompt, container);
        TextView tvPrompt = (TextView) view.findViewById(R.id.tv_prompt);
        tvPrompt.setText(StringUtils.isEmpty(mContent) ? "" : mContent);

        return view;
    }
}
