package com.esteban.bienestarjdc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.AreaActivity
import com.esteban.bienestarjdc.data.Day
import com.esteban.bienestarjdc.ui.activity.Prepregister.PreregisterActivity
import com.esteban.bienestarjdc.ui.vresources.VirtualResourcesActivity
import kotlinx.android.synthetic.main.activities_list_item.view.*
import kotlinx.android.synthetic.main.activities_list_item.view.name

class AreaActivitiesRecyclerAdapter(private val context: Context,
                                    private val areaActivities: List<AreaActivity>) : RecyclerView.Adapter<AreaActivitiesRecyclerAdapter.AreaActivityViewHolder>() {
    class AreaActivityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(areaActivity: AreaActivity?, context: Context){

            val btn_presential : ImageButton
            val btn_vresource : ImageButton

            itemView.name.text = areaActivity?.name
            itemView.description.text = areaActivity?.description
            itemView.initial_hour.text = "Inicia: " + areaActivity?.initial_hour
            itemView.final_hour.text = "Termina: " + areaActivity?.final_hour
            itemView.days.text = areaActivity?.days?.joinToString { day: Day -> day.name }

            btn_presential = itemView.findViewById(R.id.activity_presential) as ImageButton
            btn_presential.setOnClickListener {
                val intent = Intent(context, PreregisterActivity::class.java)
                intent.putExtra("id", areaActivity?.id)
                context.startActivity(intent)
            }

            btn_vresource = itemView.findViewById(R.id.activity_vresource) as ImageButton
            btn_vresource.setOnClickListener {
                val intent = Intent(context, VirtualResourcesActivity::class.java)
                intent.putExtra("id", areaActivity?.id)
                context.startActivity(intent)
            }

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