package com.goutam.routefinder.ui.routelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.goutam.routefinder.databinding.FragmentRouteListBinding
import com.goutam.routefinder.model.ModelRouteData
import com.goutam.routefinder.roomhelper.RouteFinderDatabase
import com.goutam.routefinder.utils.Utils


class RouteListFragment : Fragment() {
    private lateinit var binding: FragmentRouteListBinding
    private lateinit var routeJsonResponse: List<ModelRouteData>
    private lateinit var viewModel: RouteListViewModel

    private val db by lazy {
        RouteFinderDatabase.invoke(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRouteListBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        routeJsonResponse = Utils.getRouteData(requireContext())
        viewModel = ViewModelProvider(this, Utils.getViewModelFactory { RouteListViewModel(db.routeDao(), db.legDao(), db.trailDao(), db.busRouteDao()) }).get(RouteListViewModel::class.java)
        viewModel.apply {
            processRouteResponse(routeJsonResponse)
        }

        binding.rcvRouteList.apply {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(this.context, DividerItemDecoration.VERTICAL))
            adapter = RouteListAdapter()
        }
    }
}
