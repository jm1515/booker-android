package com.example.booker.listBooks;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booker.R;
import com.example.booker.data.Books;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.annotation.NonNull;

public class BookAdapter extends ArrayAdapter<Books> {

    public BookAdapter(@NonNull Context context, @NonNull List<Books> objects) {
        super(context, 0, objects);
    }

    @Override
    public View getView(int position, View convertView, @NotNull ViewGroup parent) {
        Books book = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_book_list, parent, false);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert book != null;
                Toast.makeText(getContext(), book.getTitle(), Toast.LENGTH_LONG).show();
            }
        });

        TextView titleBook = convertView.findViewById(R.id.bookTitle);
        assert book != null;
        titleBook.setText(book.getTitle());

        TextView authorBook = convertView.findViewById(R.id.author);
        authorBook.setText(book.getAuthor());

        TextView yearBook = convertView.findViewById(R.id.year);
        yearBook.setText(book.getYear());

        //TODO Image à mettre dans la base Mongo
        ImageView iconBook = convertView.findViewById(R.id.iconBook);

        return convertView;
    }
}
