package com.sky.txtReader.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.sky.txtReader.activity.FullscreenActivity;
import com.sky.txtReader.activity.R;
import com.sky.txtReader.utils.BookBean;
import com.sky.txtReader.utils.TxtReaderApplication;

import java.io.File;
import java.util.List;

/**
 * Created by shaojunjie on 2014/11/6.
 */
public class BookShelfFragment extends Fragment {

    private GridView bookShelf;

    private List<BookBean> books;

    final ShlefAdapter adapter=new ShlefAdapter();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View contentView=inflater.inflate(R.layout.bookshelf_fragment,container,false);

        books=((TxtReaderApplication)getActivity().getApplication()).getBooks();

        bookShelf = (GridView) contentView.findViewById(R.id.bookShelf);
        bookShelf.setAdapter(adapter);
        bookShelf.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int index,
                                    long arg3) {
                // TODO Auto-generated method stub
                BookBean book=books.get(index);
                Intent intent=new Intent(getActivity(),FullscreenActivity.class);
                Uri uri= Uri.parse(book.getPath());
                intent.setData(uri);
                intent.putExtra("book",book);
                startActivity(intent);
            }
        });

        bookShelf.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                Vibrator vibrator = (Vibrator)getActivity().getSystemService(Context.VIBRATOR_SERVICE);
                long [] pattern = {100,200};
                vibrator.vibrate(pattern,-1);

                final BookBean b= books.get(position);

                CharSequence[] items={"从书架中删除","从存储空间删除"};

                AlertDialog optionDialog = new AlertDialog.Builder(getActivity()).setTitle(R.string.option_title).setIcon(android.R.drawable.btn_star).setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                         if (which == 1) {
                            File file = new File(b.getPath());
                            file.deleteOnExit();
                        }

                        ((TxtReaderApplication) getActivity().getApplication()).removeBook(b.getPath());
                        books.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                }).create();
                optionDialog.show();

                return true;
            }
        });

        return contentView;
    }

    class ShlefAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return books.size()+5;
        }

        @Override
        public Object getItem(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            // TODO Auto-generated method stub
            return arg0;
        }

        @Override
        public View getView(int position, View contentView, ViewGroup arg2) {
            // TODO Auto-generated method stub

            contentView=LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.item1, null);

            TextView view=(TextView) contentView.findViewById(R.id.imageView1);
            if(books.size()>position){
                String[] ss=books.get(position).getPath().split("/");
                view.setText(ss[ss.length-1]);
                view.setBackgroundResource(R.drawable.cover_txt);
            }else{
                view.setBackgroundResource(R.drawable.cover_txt);
                view.setClickable(false);
                view.setVisibility(View.INVISIBLE);
            }
            return contentView;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        books=((TxtReaderApplication)getActivity().getApplication()).getBooks();
        adapter.notifyDataSetChanged();
    }
}
