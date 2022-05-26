package com.example.first;
/**
 * 碎片状态：运行 暂停 停止 销毁
 * 1.  运行状态：当一个碎片是可见的，并且它所关联的活动正处于运行状态时，该碎片也处于运行状态。
 * 2.  暂停状态：当一个活动进入暂停状态（另一个未占满屏幕的活动add到了栈顶）时，与它相关联的可见碎片就会进入到暂停状态。
 * 3.  停止状态：当一个活动进入停止状态，与它相关联的碎片就会进入到停止状态。
 * 4.  销毁状态：当活动被销毁时，与它相关联的碎片就会进入到销毁状态。
 */

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class LeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.left_fragment, container, false);
        /**
         * 得到和当前碎片相关的活动实例
         */
       // MainActivity activity = (MainActivity) getActivity();
        /**
         * 若要碎片和碎片之间通信，则碎片A先获取活动，活动再获取碎片B的实例
         */
        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
