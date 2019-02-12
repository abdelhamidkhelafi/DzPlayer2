package fr.univavignon.dzplayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import  android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class albums extends Fragment {
    public ListView lv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.albums,container,false);
        ArrayList<String> data = new ArrayList<>();
        data.add("hamid");
        data.add("usma");
        data.add("hamisd");
        data.add("hamidsss");
        data.add("hamisazed");
        data.add("hamidseeeeess");
        data.add("hamid");
        data.add("usma");
        data.add("hamisd");
        data.add("hamidsss");
        data.add("hamisazed");
        data.add("hamidseeeeess");
        data.add("hamid");
        data.add("usma");
        data.add("hamisd");
        data.add("hamidsss");
        data.add("hamisazed");
        data.add("hamidseeeeess");

        ArrayAdapter<String> adapter  = new ArrayAdapter<String>(
                getContext(),
                android.R.layout.simple_list_item_1,
                data
        );
       lv =(ListView) view.findViewById(R.id.albumsListView);
        lv.setAdapter(adapter);

        return view;
    }
    public ListView getLv()
    {
        return lv;
    }
}
