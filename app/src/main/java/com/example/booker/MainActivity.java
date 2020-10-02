package com.example.booker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.booker.data.Books;
import com.example.booker.listBooks.BookAdapter;
import com.example.booker.listBooks.ListBooksContract;
import com.example.booker.listBooks.ListBooksError;
import com.example.booker.listBooks.ListBooksModelImpl;
import com.example.booker.listBooks.ListBooksPresenterImpl;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListBooksContract.ListBooksView {

    private LinearLayout layoutTextFailed;
    private TextView textViewFailed;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*TextView textView = findViewById(R.id.testUsername);

        SharedPreferences settings = getSharedPreferences("UserData", MODE_PRIVATE);
        String userMail = settings.getString("Username", "Erreur");
        textView.setText(userMail);*/

        listView = findViewById(R.id.listBooks);

        layoutTextFailed = findViewById(R.id.layoutTextFailed);
        textViewFailed = findViewById(R.id.textFailed);

        ListBooksPresenterImpl presenter = new ListBooksPresenterImpl(new ListBooksModelImpl(), this);

        presenter.loadData();

    }

    @Override
    public void showViewError(@NotNull ListBooksError error) {
        layoutTextFailed.setVisibility(View.VISIBLE);
        switch (error){
            case BOOKS_EMPTY:
                textViewFailed.setText(getString(R.string.list_books_load_empty));
                break;
            case LOAD_FAILED:
                textViewFailed.setText(getString(R.string.list_books_load_failed));
                break;
        }
    }

    @Override
    public void showViewSuccess(@NotNull ArrayList<Books> listBooks) {
        BookAdapter bookAdapter = new BookAdapter(this, listBooks);
        listView.setAdapter(bookAdapter);
    }
}