package com.goutam.routefinder.ui.routedetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.gson.Gson
import com.goutam.routefinder.R
import com.goutam.routefinder.databinding.FragmentRouteDetailsBinding
import com.goutam.routefinder.roomhelper.tables.TabRouteDetails

class RouteDetailsFragment : Fragment(), OnMapReadyCallback {
    private lateinit var binding: FragmentRouteDetailsBinding
    private lateinit var routeDetails: TabRouteDetails
    private lateinit var viewModel: RouteDetailsViewModel

    private var googleMap: GoogleMap? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentRouteDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = RouteDetailsFragmentArgs.fromBundle(requireArguments())
        routeDetails = args.routeDetails
        viewModel = ViewModelProvider(this).get(RouteDetailsViewModel::class.java)
        val mapFragment = childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.googleMap = googleMap
        showPath()
    }

    private fun showPath() {
        googleMap?.apply {
            val builder = LatLngBounds.Builder()
            routeDetails.routes?.let {
                val allLatLng = viewModel.getAllLatLng(it)
                Log.d("LAts", Gson().toJson(allLatLng))
                allLatLng.forEach {routeLatLng -> builder.include(routeLatLng) }
                val bounds = builder.build()
                animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10))

                viewModel.getPolyLineData(it).forEach {pair ->
                    when(pair.first){
                        "Bus" -> drawBusPolyLine(pair.second)
                        "Walk" -> drawPolyLine(pair.second, "Walk")
                        "Train" -> drawPolyLine(pair.second, "Train")
                        else -> drawPolyLine(pair.second, "Default")
                    }
                }
                // Draw Source Marker
                drawSourceMarker(it.first().sourceLat, it.first().sourceLong)
                drawDestMarker(it.last().destinationLat, it.first().destinationLong)

            }
        }
    }

    private fun drawDestMarker(sourceLat: Double, sourceLong: Double) {
        googleMap?.apply {
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(MapUtils.getDestBitmap(requireContext()))
            addMarker(MarkerOptions().position(LatLng(sourceLat, sourceLong)).flat(true).icon(bitmapDescriptor))
        }
    }

    private fun drawSourceMarker(sourceLat: Double, sourceLong: Double) {
        googleMap?.apply {
            val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(MapUtils.getSourceBitmap(requireContext()))
            addMarker(MarkerOptions().position(LatLng(sourceLat, sourceLong)).flat(true).icon(bitmapDescriptor))
        }
    }

    private fun drawPolyLine(lines: List<LatLng>, medium: String) {
        googleMap?.apply {
            val polylineOptions = PolylineOptions().apply {
                when (medium) {
                    RouteDetailsViewModel.WALK -> color(ContextCompat.getColor(requireContext(), R.color.walk_line))
                    RouteDetailsViewModel.TRAIN -> color(ContextCompat.getColor(requireContext(), R.color.train_line))
                    else -> color(ContextCompat.getColor(requireContext(), R.color.purple_700))
                }
                width(15f)
                addAll(lines)
            }
            addPolyline(polylineOptions)
        }
    }

    private fun drawBusPolyLine(lines: List<LatLng>) {
        googleMap?.apply {
            val polylineOptions = PolylineOptions().apply {
                color(ContextCompat.getColor(requireContext(), R.color.bus_line))
                width(15f)
                addAll(lines)
            }
            addPolyline(polylineOptions)
            addBusMarkerAndGet(lines.first())
            addBusMarkerAndGet(lines.last())
            lines.forEach{ latLng ->
                addTrailsMarkerAndGet(latLng)
            }
        }
    }

    private fun addBusMarkerAndGet(latLng: LatLng): Marker? {
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(MapUtils.getBusBitmap(requireContext()))
        return googleMap?.addMarker(MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor))
    }

    private fun addTrailsMarkerAndGet(latLng: LatLng): Marker? {
        val bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(MapUtils.getTrailsBitmap(requireContext()))
        return googleMap?.addMarker(MarkerOptions().position(latLng).flat(true).icon(bitmapDescriptor))
    }


}
