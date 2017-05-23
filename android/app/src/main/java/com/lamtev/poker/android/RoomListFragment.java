package com.lamtev.poker.android;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RoomListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<RoomInfo> roomsInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.room_card_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.room_card_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i(getClass().getSimpleName(), "View created");
        return view;
    }

}
