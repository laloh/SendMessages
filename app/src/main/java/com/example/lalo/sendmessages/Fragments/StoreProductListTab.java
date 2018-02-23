package com.example.lalo.sendmessages.Fragments;


import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.lalo.sendmessages.Adapters.RecyclerViewAdapter;
import com.example.lalo.sendmessages.Adapters.RecyclerViewAdapterForProductsTab;
import com.example.lalo.sendmessages.Models.Tienda;
import com.example.lalo.sendmessages.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

/**
 * A simple {@link Fragment} subclass.
 */
public class StoreProductListTab extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mDatabase;
    FirebaseRecyclerAdapter adapter2;

    public StoreProductListTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_store_product_list_tab, container, false);
        showProductsInStore(rootView);
        return rootView;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void showProductsInStore(View rootView) {
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerViewProductsFragment);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mDatabase = FirebaseDatabase.getInstance().getReference();

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Tiendas")
                .limitToLast(50);

        final FirebaseRecyclerOptions<Tienda> options =
                new FirebaseRecyclerOptions.Builder<Tienda>()
                        .setQuery(query, Tienda.class)
                        .build();

        adapter2 = new FirebaseRecyclerAdapter<Tienda, RecyclerViewAdapterForProductsTab.ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RecyclerViewAdapterForProductsTab.ViewHolder holder, int position, @NonNull Tienda model) {
                holder.bind(options.getSnapshots().get(position).getNombre());
            }

            @Override
            public RecyclerViewAdapterForProductsTab.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                // Create a new instance of the ViewHolder, in this case we are using a custom
                // layout called R.layout.message for each item
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.recycler_view_item_products, parent, false);
                RecyclerViewAdapterForProductsTab.ViewHolder vh = new RecyclerViewAdapterForProductsTab.ViewHolder(view);
                return vh;
            }
        };

        // Boost Perfomance
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(adapter2);
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter2.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter2.startListening();
    }

}