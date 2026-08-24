package com.esteban.bienestarjdc.ui.activity

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esteban.bienestarjdc.data.AreaActivity
import com.esteban.bienestarjdc.data.Day
import com.esteban.bienestarjdc.databinding.ActivitiesListItemBinding
import com.esteban.bienestarjdc.ui.activity.Prepregister.PreregisterActivity
import com.esteban.bienestarjdc.ui.vresources.VirtualResourcesActivity

class AreaActivitiesRecyclerAdapter(private val context: Context,
                                     private val areaActivities: List<AreaActivity>) : RecyclerView.Adapter<AreaActivitiesRecyclerAdapter.AreaActivityViewHolder>() {
    class AreaActivityViewHolder(private val binding: ActivitiesListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(areaActivity: AreaActivity?, context: Context){

            binding.name.text = areaActivity?.name
            binding.description.text = areaActivity?.description
            binding.initialHour.text = "Inicia: " + areaActivity?.initial_hour
            binding.finalHour.text = "Termina: " + areaActivity?.final_hour
            binding.days.text = areaActivity?.days?.joinToString { day: Day -> day.name }

            binding.activityPresential.setOnClickListener {
                val intent = Intent(context, PreregisterActivity::class.java)
                intent.putExtra("id", areaActivity?.id)
                context.startActivity(intent)
            }

            binding.activityVresource.setOnClickListener {
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
        val binding = ActivitiesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AreaActivityViewHolder(binding)
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
