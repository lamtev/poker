package com.lamtev.poker.android;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class RoomListFragment extends Fragment {

    private RecyclerView recyclerView;
    private RoomListItemAdapter adapter;

    private List<RoomInfo> roomsInfo = new ArrayList<RoomInfo>() {{
        add(new RoomInfo("World cup", 7, 100_000, true));
        add(new RoomInfo("Europe cup", 5, 75_000, false));
        add(new RoomInfo("America cup", 6, 25_000, true));
        add(new RoomInfo("Asia cup", 9, 25_000, false));
        add(new RoomInfo("Russia cup", 5, 25_000, true));
    }};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_room_card_list, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.room_card_list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.i(getClass().getSimpleName(), "View created");
        updateUI();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        if (adapter == null) {
            adapter = new RoomListItemAdapter(roomsInfo);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private class RoomListItemHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView playersNumber;
        private TextView stack;
        private TextView status;
        private TextView playersNumberText;
        private TextView stackText;
        private TextView statusText;

        public RoomListItemHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i(RoomListFragment.class.getSimpleName(), "card view clicked");
                }
            });
            name = (TextView) itemView.findViewById(R.id.room_card_room_name);
            playersNumber = (TextView) itemView.findViewById(R.id.room_card_players_number);
            stack = (TextView) itemView.findViewById(R.id.room_card_stack_size);
            status = (TextView) itemView.findViewById(R.id.room_card_room_status);

            playersNumberText = (TextView) itemView.findViewById(R.id.room_card_players_number_text);
            stackText = (TextView) itemView.findViewById(R.id.room_card_stack_size_text);
            statusText = (TextView) itemView.findViewById(R.id.room_card_room_status_text);
        }

        public void bindRoom(final RoomInfo roomInfo) {
            name.setText(roomInfo.getName());
            playersNumber.setText(roomInfo.getPlayersNumber() + "");
            stack.setText(roomInfo.getStack() + "");
            status.setText(roomInfo.isFree() ? "free" : "in progress");

            playersNumberText.setText(getString(R.string.players_number_text));
            stackText.setText(getString(R.string.stack_size_text));
            statusText.setText(getString(R.string.game_status_text));
        }
    }


    private class RoomListItemAdapter extends RecyclerView.Adapter<RoomListItemHolder> {
        private List<RoomInfo> rooms;

        public RoomListItemAdapter(List<RoomInfo> rooms) {
            this.rooms = rooms;
        }

        @Override
        public RoomListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.room_card, parent, false);
            return new RoomListItemHolder(view);
        }

        @Override
        public void onBindViewHolder(RoomListItemHolder holder, int position) {
            RoomInfo room = rooms.get(position);
            holder.bindRoom(room);
        }


        @Override
        public int getItemCount() {
            return roomsInfo.size();
        }
    }

}
