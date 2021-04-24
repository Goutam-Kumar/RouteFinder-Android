package com.goutam.routefinder.ui.routelist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import com.goutam.routefinder.R
import com.goutam.routefinder.databinding.FragmentRouteListBinding
import com.goutam.routefinder.model.ModelRouteData
import com.goutam.routefinder.roomhelper.RouteFinderDatabase
import com.goutam.routefinder.utils.Utils

private const val AUTO_COMPLETE_REQ_CODE: Int = 2000

class RouteListFragment : Fragment() {
    private lateinit var binding: FragmentRouteListBinding
    private lateinit var routeJsonResponse: List<ModelRouteData>
    private lateinit var viewModel: RouteListViewModel
    private lateinit var recyclerAdapter: RouteListAdapter
    private lateinit var placesClient: PlacesClient
    private lateinit var currentClickedView: View

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
        if(!Places.isInitialized())
            Places.initialize(requireContext(), resources.getString(R.string.google_maps_key))
        placesClient = Places.createClient(requireContext())
        viewModel = ViewModelProvider(this, Utils.getViewModelFactory { RouteListViewModel(db.routeDao()) }).get(RouteListViewModel::class.java)
        viewModel.apply {
            //TODO remove this
            processRouteResponse(routeJsonResponse)
            getAllRoutes()
        }

        binding.apply {
            listLayout.visibility = View.GONE
            rcvRouteList.apply {
                layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
                addItemDecoration(ListItemDecoration(resources.getDimension(R.dimen.dim_10_dp).toInt()))
                recyclerAdapter = RouteListAdapter{
                    findNavController().navigate(RouteListFragmentDirections.actionRouteListFragmentToRouteDetailsFragment(it))
                }
                adapter = recyclerAdapter
            }
            sourceName.setOnClickListener {
                currentClickedView = sourceName
                onSearchCalled()
            }

            destName.setOnClickListener {
                currentClickedView = destName
                onSearchCalled()
            }
        }
        observeViewModelObservers(viewModel)
    }

    private fun onSearchCalled() {
        view.let {
            val fields: List<Place.Field> = listOf(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS, Place.Field.LAT_LNG)
            val intent: Intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fields).setCountry("IN").build(requireContext())
            startActivityForResult(intent,AUTO_COMPLETE_REQ_CODE)
        }
    }

    private fun observeViewModelObservers(viewModel: RouteListViewModel) {
        viewModel.apply {
            allRouteList.observe(viewLifecycleOwner, Observer {routeDetailsList ->
                if (routeDetailsList.isNotEmpty()){
                    binding.listLayout.visibility = View.VISIBLE
                    recyclerAdapter.bindRouteList(routeDetailsList)
                }
            })
            sourceAddress.observe(viewLifecycleOwner, Observer { binding.sourceName.text = it })
            destinationAddress.observe(viewLifecycleOwner, Observer { binding.destName.text = it })
            tableCleared.observe(viewLifecycleOwner, Observer { if(it == true) processRouteResponse(routeJsonResponse) })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        binding.apply {
            data?.let {
                if (requestCode == AUTO_COMPLETE_REQ_CODE){
                    when (resultCode) {
                        AutocompleteActivity.RESULT_OK -> {
                            val place = Autocomplete.getPlaceFromIntent(it)
                            Log.i("Place ", "Place: " + place.name + ", " + place.id + ", " + place.address)
                            val address = place.name.plus(",").plus(place.address)
                            if (sourceName  == currentClickedView){
                                sourceName.text = address
                                viewModel.setAddress(true, address)
                            }
                            else if (destName == currentClickedView){
                                destName.text =  address
                                viewModel.setAddress(false, address)
                            }
                            viewModel.refreshAllRoute()
                        }
                        AutocompleteActivity.RESULT_ERROR -> {
                            val status = Autocomplete.getStatusFromIntent(it)
                            Toast.makeText(requireContext(), "Error: " + status.statusMessage, Toast.LENGTH_LONG).show()
                            Log.i("TAG", status.statusMessage.orEmpty())
                        }
                        AutocompleteActivity.RESULT_CANCELED -> {
                            // The user canceled the operation.
                        }
                    }
                }
            }
        }
    }
}
