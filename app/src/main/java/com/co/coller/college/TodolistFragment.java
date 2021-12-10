package com.co.coller.college;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.co.coller.R;
import com.co.coller.adapter.todolistAdapter;
import com.co.coller.api.api;
import com.co.coller.api.apiClient;
import com.co.coller.api.sharedPref;
import com.co.coller.model.todolist;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link TodolistFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TodolistFragment extends Fragment {

    public static final String ID_TODOLIST = "id_todolist";
    public static final String DETAIL_TODOLIST = "nama_todolist";

    RecyclerView rvTodolist;
    ArrayList<todolist> listTodolist;
    api api;
    sharedPref sharedPref;
    todolistAdapter adapter;
    TextView addTodolist;

    private View.OnClickListener onItemClicklistener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) view.getTag();
            int position = viewHolder.getAdapterPosition();
            todolist thisitem = listTodolist.get(position);

            //Toast.makeText(getContext(), "clicked" + thisitem.getNamaTodolist(), Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getContext(), AddTodolistActivity.class);
            intent.putExtra(ID_TODOLIST, thisitem.getIdTodolist());
            intent.putExtra(DETAIL_TODOLIST, thisitem.getNamaTodolist());
            startActivity(intent);
        }
    };

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TodolistFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TodolistFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TodolistFragment newInstance(String param1, String param2) {
        TodolistFragment fragment = new TodolistFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_todolist, container, false);

        sharedPref = new sharedPref(view.getContext());
        rvTodolist = view.findViewById(R.id.rvTodolist);
        addTodolist = view.findViewById(R.id.add_todolist);

        getTodolist();

        addTodolist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), AddTodolistActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        return view;
    }

    private void getTodolist() {
        final String uid = sharedPref.getUid();
        api = apiClient.getClient().create(api.class);

        Call<List<todolist>> getTodolist = api.getTodolist(uid);

        getTodolist.enqueue(new Callback<List<todolist>>() {
            @Override
            public void onResponse(Call<List<todolist>> call, Response<List<todolist>> response) {
                listTodolist = new ArrayList<>(response.body());
                adapter = new todolistAdapter(listTodolist);
                rvTodolist.setAdapter(adapter);
                rvTodolist.setLayoutManager(new LinearLayoutManager(getContext()));
                adapter.setOnItemClicklistener(onItemClicklistener);
            }

            @Override
            public void onFailure(Call<List<todolist>> call, Throwable t) {

            }
        });
    }
}