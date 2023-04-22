package com.example.readingnotes;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar.LayoutParams;import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar.LayoutParams;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;import com.example.readingnotes.BookAdapter;

public class MainActivity extends AppCompatActivity {
    private List<Book> bookList = new ArrayList<>(); //书籍列表
    private RecyclerView recyclerView;//循环视图
    private BookAdapter bookAdapter;//适配器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main); //关联布局文件

        // 找到 Toolbar 控件
        Toolbar toolbar = findViewById(R.id.toolbar);

        // 将 Toolbar 设置为 ActionBar
        setSupportActionBar(toolbar);

        // 找到 Toolbar 的 TextView 对象
        TextView titleTextView = findViewById(R.id.toolbar_title);

        // 设置标题为 "书架"
        toolbar.setTitle("");

        // 设置标题的对齐方式为居中
        titleTextView.setText("我的书架");
        titleTextView.setGravity(Gravity.CENTER_VERTICAL);
        titleTextView.setGravity(Gravity.CENTER_HORIZONTAL);

        // 设置标题的宽度为 match_parent
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        titleTextView.setLayoutParams(params);

        // 找到“添加”按钮控件
        Button addButton = findViewById(R.id.addButton);
        // 设置其点击事件
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击按钮后跳转到下一个页面
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        //有一个“菜单”按钮暂时未定义功能，搁置于此
        //还有“查询”功能未实现，搁置于此



//        此处留下一个疑问，这里是否需要添加循环书籍的展示 逻辑代码

        // 初始化书籍列表适配器
        // 创建 BookAdapter 实例
        bookAdapter = new BookAdapter(MainActivity.this, bookList);


        // 初始化书籍列表
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(bookAdapter);

        //从页面2中获取book实例的信息
        Intent intent = getIntent();
        Book book = intent.getParcelableExtra("book");
        // 如果获取到了 book 对象，则添加到书籍列表中
        if (book != null) {
            bookList.add(book);
            bookAdapter.notifyDataSetChanged();
        }
    }

    // 添加书籍到书籍列表中
    public void addBook(Book book) {
        bookList.add(book);
        bookAdapter.notifyDataSetChanged();
    }

}




