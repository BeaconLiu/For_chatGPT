package com.example.readingnotes;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder> {

    private Context context;
    private List<Book> bookList;

    // 构造函数，传入上下文和书籍列表
    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
    }

    // 创建ViewHolder对象
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_info_layout, parent, false);
        return new BookViewHolder(view);
    }

    // 绑定ViewHolder对象，设置显示内容
    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = bookList.get(position);
        holder.bookTitleTextView.setText(book.getTitle());
        holder.bookAuthorTextView.setText(book.getAuthor());
        holder.publisherTextView.setText(book.getPublisher());
        holder.pubDateTextView.setText(book.getPubDate());
        // TODO: 从书籍中获取封面图片并设置
    }

    // 返回数据列表长度
    @Override
    public int getItemCount() {
        return bookList.size();
    }

    // 定义ViewHolder类
    public class BookViewHolder extends RecyclerView.ViewHolder {
        public ImageView bookCoverImageView;
        public TextView bookTitleTextView;
        public TextView bookAuthorTextView;
        public TextView publisherTextView;
        public TextView pubDateTextView;

        // ViewHolder构造函数，获取布局中的控件
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookCoverImageView = itemView.findViewById(R.id.book_cover_image_view);
            bookTitleTextView = itemView.findViewById(R.id.book_title_text_view);
            bookAuthorTextView = itemView.findViewById(R.id.book_author_text_view);
            publisherTextView = itemView.findViewById(R.id.publisher_text_view);
            pubDateTextView = itemView.findViewById(R.id.pub_date_text_view);
        }
    }
}

//这里留一个疑问，这个地方需要添加书籍信息，然后在recyclerview中显示，似乎要获取页面2的intent和book
