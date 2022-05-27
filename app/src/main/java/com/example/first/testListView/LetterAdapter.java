package com.example.first.testListView;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.first.R;
import com.example.first.letters.Letters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/*
        定制ListView界面
        步骤：
            1. 新建一个适配器类。(Letters)
            2. 给List View指定一个自定义的布局。(letters_item.xml)
            3. 自定义一个适配器(LetterAdapter)。
            4. 在活动中把适配器类和自定义的适配器结合起来。
 */
public class LetterAdapter extends ArrayAdapter<Letters> {
    private int resourceId;

    //重写getView 这个方法在每个子项被滚动到屏幕内时被调用（需要优化）
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        //获取当前项的letter实例
        Letters letters = getItem(position);
        /*
            LayoutInflater.from()创建一个LayoutInflater对象
            inflate()：为子项加载布局，false表示只让在父布局中声明的layout属性生效，但不会为这个View添加父布局
                        因为View一旦有了父布局，就不能再添加到ListView？
         */
        //View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        /*
            提升ListView的运行效率
            方法：
                1. 使用getView()方法中的convertView参数将之前加载好的布局进行缓存。view.setTag(viewHolder)
                2. 使用一个内部类来存控件的实例，这样不用让每次加载都要对控件的实例进行调用。
         */
        //convertView将之前加载好的布局进行缓存
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.letterImage = (ImageView) view.findViewById(R.id.letter_image);
            viewHolder.letterName = (TextView) view.findViewById(R.id.letter_name);
            viewHolder.letterCount = (TextView) view.findViewById(R.id.letter_count);
            //将viewHoulder存在vie中
            view.setTag(viewHolder);

        } else {
            view = convertView;
            //重新获取viewHoler
            viewHolder = (ViewHolder) view.getTag();
        }
/*
        ImageView imageView = (ImageView) view.findViewById(R.id.letter_image);
        TextView textView = (TextView) view.findViewById(R.id.letter_name);
        TextView textView2 = (TextView) view.findViewById(R.id.letter_count);
        //设置显示
        imageView.setImageResource(letters.getImageId());
        textView.setText(letters.getName());
        textView2.setText(letters.getCount());

 */
        viewHolder.letterImage.setImageResource(letters.getImageId());
        viewHolder.letterName.setText(letters.getName());
        viewHolder.letterCount.setText(letters.getCount());

        return view;
        //return super.getView(position, convertView, parent);
    }

    //内部类，对控件的实例进行缓存
    class ViewHolder {
        ImageView letterImage;
        TextView letterName;
        TextView letterCount;
    }

    /*
        重写构造函数，传入的参数为：context，ListView子项布局的id，数据
     */
    public LetterAdapter(Context context, int textViewResouceId, List<Letters> objects) {
        super(context, textViewResouceId, objects);

        resourceId = textViewResouceId;


    }

}
