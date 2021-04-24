package com.goutam.routefinder.ui.routelist

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.goutam.routefinder.R
import com.goutam.routefinder.customview.CustomProgressBar
import com.goutam.routefinder.customview.ThemeTextView
import com.goutam.routefinder.customview.model.ProgressItem
import com.goutam.routefinder.databinding.ItemRouteListBinding
import com.goutam.routefinder.di.daggercomponent.DaggerAppComponent
import com.goutam.routefinder.model.Route
import com.goutam.routefinder.roomhelper.tables.TabRouteDetails
import com.goutam.routefinder.utils.Utils
import javax.inject.Inject

class RouteListAdapter(
        private val routeList: MutableList<TabRouteDetails> = mutableListOf(),
        private val onItemClicked: (routeDetails: TabRouteDetails) -> Unit
): RecyclerView.Adapter<RouteListAdapter.RouteHolder>() {
    @Inject
    lateinit var utils: Utils
    init {
        DaggerAppComponent.create().inject(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        val itemRouteListBinding = ItemRouteListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RouteHolder(itemRouteListBinding)
    }

    override fun getItemCount(): Int {
        return routeList.size
    }

    override fun onBindViewHolder(holder: RouteHolder, position: Int) {
        holder.bind(routeList[position])
    }

    fun bindRouteList(listOfRoute: List<TabRouteDetails>){
        routeList.clear()
        routeList.addAll(listOfRoute)
        notifyDataSetChanged()
    }

    inner class RouteHolder(private val itemRouteListBinding: ItemRouteListBinding): RecyclerView.ViewHolder(itemRouteListBinding.root) {
        fun bind(routeDetails: TabRouteDetails) {
            itemRouteListBinding.apply {
                val res: Resources? = itemRouteListBinding.root.resources
                tripDetails.travelTime.text = res?.getString(R.string.minute_duration, utils.getTimeInMin(routeDetails.totalDuration).toInt().toString())
                tripDetails.distance.text = res?.getString(R.string.kms_distance, String.format("%.2f", routeDetails.totalDistance))
                tripDetails.estPrice.text = res?.getString(R.string.total_fare, routeDetails.totalFare.toString())
                cardContainer.setOnClickListener { onItemClicked(routeDetails) }
                initProgressData(progress,legIconHolder,legDetailsHolder, routeDetails.routes, routeDetails.totalDuration)
            }
        }

        private fun initProgressData(progress: CustomProgressBar, legIconHolder: LinearLayout, legDetailsHolder:LinearLayout, routes: List<Route>?, totalDuration: String?) {
            val progressItemList = mutableListOf<ProgressItem>()
            val totalDurationInSec = utils.getTimeInMin(totalDuration)
            legIconHolder.removeAllViews()
            legDetailsHolder.removeAllViews()
            legIconHolder.weightSum = 100.0f
            routes?.forEachIndexed {index, route ->
                val routeDuration = utils.getTimeInMin(route.duration)
                val percentage = routeDuration / totalDurationInSec * 100

                progressItemList.add(ProgressItem(getProgressColor(route.medium), percentage))

                //Add Leg icons
                val inflater = LayoutInflater.from(progress.context)
                val rowView: View = inflater.inflate(R.layout.item_leg_icon, legIconHolder, false)
                val image = rowView.findViewById<ImageView>(R.id.img_leg_icon)
                image.setImageResource(getLegIcon(route.medium))
                val param = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, percentage.toFloat())
                legIconHolder.addView(rowView, param)

                //Add Leg Details
                val legDetails: View = inflater.inflate(R.layout.item_leg_details, legDetailsHolder, false)
                val legIcon = legDetails.findViewById<ImageView>(R.id.img_leg_icon)
                val txtLeg = legDetails.findViewById<ThemeTextView>(R.id.txt_leg_details)
                processLegDetailsDisplayData(legIcon,txtLeg,route)
                val paramForDetails = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT)
                legDetailsHolder.addView(legDetails, paramForDetails)
                //Add Arrow
                if (index != routes.size -1){
                    val imageView = ImageView(progress.context)
                    progress.resources.getDimension(R.dimen.dim_15_dp).toInt().let {
                        val imageParams = LinearLayout.LayoutParams(it, it)
                        imageParams.setMargins(15, 0 , 15, 0 )
                        imageView.layoutParams = imageParams
                    }
                    imageView.setImageResource(R.drawable.ic_right_arrow)
                    legDetailsHolder.addView(imageView)
                }
            }
            //Add Multicolored Progressbar
            progress.apply {
                thumb.mutate().alpha = 0
                isEnabled = false
                initData(progressItemList)
                invalidate()
            }
        }

        private fun processLegDetailsDisplayData(legIcon: ImageView?, txtLeg: ThemeTextView?, route: Route) {
            legIcon?.setImageResource(getLegIcon(route.medium))
            txtLeg?.apply {
                text = getLegDetailsLabel(txtLeg,route).orEmpty()
                setTextColor(ContextCompat.getColor(this.context, getLegDetailsLabelColor(route.medium)))
            }
        }

        private fun getLegDetailsLabelColor(medium: String?): Int {
            return when(medium){
                BUS -> R.color.text_yellow
                WALK -> R.color.black
                else -> R.color.text_yellow
            }
        }

        private fun getLegDetailsLabel(txtLeg: ThemeTextView?, route: Route): String? {
            return when(route.medium){
                BUS -> route.busRouteDetails?.routeNumber
                WALK -> {
                    val time = String.format("%.2f", utils.getTimeInMin(route.duration))
                    txtLeg?.resources?.getString(R.string.minute_duration, time)
                }
                else -> txtLeg?.resources?.getString(R.string.purple_line)
            }
        }

        private fun getLegIcon(medium: String?): Int {
            return when(medium){
                BUS -> R.drawable.ic_bus
                WALK -> R.drawable.ic_walk
                else -> R.drawable.ic_train
            }
        }

        private fun getProgressColor(medium: String?): Int {
            return when(medium){
                BUS -> R.color.bus
                WALK -> R.color.walk
                else -> R.color.train
            }
        }
    }

    companion object{
        const val BUS = "Bus"
        const val WALK = "Walk"
    }

}
