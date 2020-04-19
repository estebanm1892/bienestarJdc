package com.esteban.bienestarjdc.ui.activity

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.AreaActivity
import com.esteban.bienestarjdc.data.Day
import kotlinx.android.synthetic.main.activities_list_item.view.*

class AreaActivitiesRecyclerAdapter(private val context: Context,
                                    private val areaActivities: List<AreaActivity>) : RecyclerView.Adapter<AreaActivitiesRecyclerAdapter.AreaActivityViewHolder>() {
    class AreaActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(areaActivity: AreaActivity?, context: Context){

            itemView.name.text = areaActivity?.name
            itemView.initial_hour.text = "Inicia: " + areaActivity?.initial_hour
            itemView.final_hour.text = "Termina: " + areaActivity?.final_hour
            itemView.days.text = areaActivity?.days?.joinToString { day: Day -> day.name }


        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AreaActivitiesRecyclerAdapter.AreaActivityViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.activities_list_item, parent, false)
        return AreaActivityViewHolder(v)
    }

    override fun getItemCount(): Int {
        return areaActivities.size
    }

    override fun onBindViewHolder(
        holder: AreaActivitiesRecyclerAdapter.AreaActivityViewHolder,
        position: Int
    ) {
        return holder.bind(areaActivities[position], context)
    }
}