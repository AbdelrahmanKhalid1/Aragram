package com.example.aragram.ui.searchprofile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aragram.R;
import com.example.aragram.model.User;
import com.example.aragram.ui.searchprofile.UserActivity;
import com.example.aragram.ui.searchprofile.adapter.SearchAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {
    EditText profileName;
    RecyclerView profiles;
    SearchAdapter searchAdapter;
    SearchViewModel viewModel;
    List<User> finalFiltredUsers=new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v= inflater.inflate(R.layout.search_fragment,container,false);
        Log.d("bi", "onCreateView: ");
        initAll(v);
        viewModel.usersResult.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                //Toast.makeText(getContext(), "fuckk"+users.get(0).getUsername(), Toast.LENGTH_SHORT).show();
                Log.d("what?", "onChanged:  lol???");
                finalFiltredUsers.clear();
                finalFiltredUsers.addAll(users);
                searchAdapter.notifyDataSetChanged();


                //searchAdapter=new SearchAdapter(finalFiltredUsers);

            }
        });
        profileName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewModel.getFilterProfiles(s.toString());

            }
        });
        viewModel.filterdUsers.observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                Log.d("what?", "onChanged:  lol???");
                finalFiltredUsers.clear();
                finalFiltredUsers.addAll(users);
                searchAdapter.notifyDataSetChanged();

            }
        });
        searchAdapter.setmListner(new SearchAdapter.OnItemClickListner() {
            @Override
            public void onClick(int position) {
                Intent intent= new Intent(getActivity(), UserActivity.class);
                intent.putExtra("user",finalFiltredUsers.get(position));
                startActivity(intent);
            }
        });


       return v;
    }

    private void initAll(View v) {

        profiles=v.findViewById(R.id.users_recyclerview);
        profileName=v.findViewById(R.id.search_user);
        profiles.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel= new ViewModelProvider(this).get(SearchViewModel.class);
        viewModel.getProfiles();
        searchAdapter=new SearchAdapter(finalFiltredUsers,requireContext());
        profiles.setAdapter(searchAdapter);

    }
}
