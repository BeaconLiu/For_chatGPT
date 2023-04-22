package com.example.readingnotes;


import android.os.Parcel;
import android.os.Parcelable;
//保存书籍信息的自定义book类
public class Book implements Parcelable  {
    private String title;
    private String author;
    private String publisher;
    private String pubDate;

    public Book(String title, String author, String publisher, String pubDate) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPubDate() {
        return pubDate;
    }

    // 实现Parcelable接口的方法
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(publisher);
        dest.writeString(pubDate);
    }

    public static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    private Book(Parcel in) {
        title = in.readString();
        author = in.readString();
        publisher = in.readString();
        pubDate = in.readString();
    }
}
