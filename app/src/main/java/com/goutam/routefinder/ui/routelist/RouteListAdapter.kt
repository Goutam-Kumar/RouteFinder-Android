package com.goutam.routefinder.ui.routelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.goutam.routefinder.R

class RouteListAdapter: RecyclerView.Adapter<RouteListAdapter.RouteHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        return RouteHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_route_list, parent,false))
    }

    override fun getItemCount(): Int {
        return 2
    }

    override fun onBindViewHolder(holder: RouteHolder, position: Int) {

    }

    class RouteHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }
}
