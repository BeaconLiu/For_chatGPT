package com.example.readingnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import com.example.readingnotes.Book;
public class MainActivity2 extends AppCompatActivity {

    private EditText mIsbnEditText;
    private ProgressBar mProgressBar;
    private ImageView mBookCoverImageView;
    private TextView mBookTitleTextView;
    private TextView mAuthorTextView;
    private TextView mPublisherTextView;
    private TextView mPubDateTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        调用 super.onCreate(savedInstanceState) 方法来执行父类的初始化操作
        super.onCreate(savedInstanceState);
//        setContentView 方法将该活动与布局文件 activity_main2.xml关联起来，以便我们可以使用布局文件中定义的所有视图
        setContentView(R.layout.activity_main2);
//        找到布局文件中的 Toolbar 视图，使用它来替代默认的应用程序栏，并将其设置为 ActionBar
        Toolbar toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
//        设置 ActionBar 的标题为空，以便我们可以在工具栏中显示自定义的标题
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
//        在工具栏中添加 TextView，以显示我们希望用户搜索书籍的消息
        TextView titleTextView = toolbar.findViewById(R.id.titleTextView);
        titleTextView.setText("搜索书籍");
//        在布局文件中找到 EditText、ImageView、TextView 视图，以便我们可以通过它们显示搜索结果
        mIsbnEditText = findViewById(R.id.search_view);
        mBookCoverImageView = findViewById(R.id.book_cover_image_view);
        mBookTitleTextView = findViewById(R.id.book_title_text_view);
        mAuthorTextView = findViewById(R.id.book_author_text_view);
        mPublisherTextView = findViewById(R.id.publisher_text_view);
        mPubDateTextView = findViewById(R.id.pub_date_text_view);
//        找到了“查询”按钮、返回按钮和“新建”按钮，并将它们与适当的视图关联起来
        Button searchButton = findViewById(R.id.searchButton);
//        对于“查询”按钮，我们为其设置一个单击监听器
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {    //在单击时执行以下操作：首先，我们获取 EditText 中输入的 ISBN 号。如果 EditText 中没有输入，则显示一个 Toast 消息提示用户输入 ISBN
                String isbn = mIsbnEditText.getText().toString();
                if (isbn.isEmpty()) {
                    Toast.makeText(MainActivity2.this, "请输入ISBN", Toast.LENGTH_SHORT).show();
                    return;
                }
                new FetchBookTask().execute(isbn); //创建一个新的异步任务 FetchBookTask，并传入 ISBN 作为参数来获取相应的书籍信息
            }
        });
        //对于”返回“按钮，为其设置一个单击监听器，以便在单击时调用 onBackPressed() 方法返回上一个活动
        Button backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //对于“新建”按钮，为其设置一个单击监听器，以便在单击时创建一个新的意图，意图内容大概是是创建自定义书籍，暂未实现
        Button newButton = findViewById(R.id.newButton);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // 找到图片控件
        ImageView bookCoverImageView = findViewById(R.id.book_cover_image_view);
        // 为其设置点击事件监听器
        bookCoverImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 获取书籍信息
                String bookTitle = mBookTitleTextView.getText().toString();
                String bookAuthor = mAuthorTextView.getText().toString();
                String bookPublisher = mPublisherTextView.getText().toString();
                String bookPubDate = mPubDateTextView.getText().toString();

                // 创建一个Book对象，保存书籍信息，已导入import com.example.readingnotes.Book;包
                Book book = new Book(bookTitle, bookAuthor, bookPublisher, bookPubDate);

                // 使用Intent在页面间传递数据
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                /* putExtra()是Intent类的一个方法，它可以传递数据给其他组件，第一个参数是键值对的键，这里的"book"就是键的名称，第二个参数是要
                传递的数据，这里是book对象。在MainActivity中，可以使用getIntent()方法获取到这个Intent，然后通过getStringExtra()、getIntExtra()等方法获取到传递的数据*/
                intent.putExtra("book", book);
                //启动一个新的Activity（即MainActivity）
                startActivity(intent);
            }
        });
    }




    //这段代码是一个异步任务类 FetchBookTask，它继承自 AsyncTask<String, Void, String>，表示它需要传入一个 String 类型的参数、不需要指定进度、并返回一个 String 类型的结果
    private class FetchBookTask extends AsyncTask<String, Void, String> {


        private static final String TAG = "FetchBookTask";

        @Override
        //进度条， 已经被隐藏了
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.GONE);
            mBookCoverImageView.setVisibility(View.GONE);
            mBookTitleTextView.setVisibility(View.GONE);
            mAuthorTextView.setVisibility(View.GONE);
            mPublisherTextView.setVisibility(View.GONE);
            mPubDateTextView.setVisibility(View.GONE);
        }

        @Override
//        FetchBookTask 有一个 doInBackground 方法，它在后台线程中执行。这个方法接收一个 String 类型的参数 params，表示查询的 ISBN 号码，然后通过豆瓣 API 查询相关的书籍信息。这部分的实现涉及到网络请求、解析 JSON 等操作
        protected String doInBackground(String... params) {
            String isbn = params[0]; //获取isbn码
            String apiUrl = "https://api.douban.com/v2/book/isbn/" + isbn; //构建豆瓣api的url
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(apiUrl);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuilder buffer = new StringBuilder();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) { //逐行读取json数据
                    buffer.append(line).append("\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }

                return buffer.toString(); //返回json数据

            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
    }
}
}  




