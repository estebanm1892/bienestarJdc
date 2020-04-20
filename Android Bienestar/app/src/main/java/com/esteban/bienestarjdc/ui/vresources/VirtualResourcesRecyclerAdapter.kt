package com.esteban.bienestarjdc.ui.vresources

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.VirtualResource
import kotlinx.android.synthetic.main.virtual_resources_list_item.view.*

class VirtualResourcesRecyclerAdapter(private val context: Context, private val virtualResources: List<VirtualResource>): RecyclerView.Adapter<VirtualResourcesRecyclerAdapter.VirtualResourceViewHolder>(){
    class VirtualResourceViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)  {

        fun bind(virtualResource: VirtualResource?, context: Context){

            itemView.tittle.text = virtualResource?.tittle
            itemView.description.text = virtualResource?.description

            itemView.setOnClickListener {
                /*
                val intent = Intent(context, AreaActivity::class.java)
                intent.putExtra("id", area?.id)
                context.startActivity(intent)
                 */
                Toast.makeText(context, "recurso virtual:  " +virtualResource?.id, Toast.LENGTH_LONG).show()


            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VirtualResourcesRecyclerAdapter.VirtualResourceViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.virtual_resources_list_item, parent, false)
        return VirtualResourceViewHolder(v)
    }

    override fun getItemCount(): Int {
        return virtualResources.size
    }

    override fun onBindViewHolder(
        holder: VirtualResourcesRecyclerAdapter.VirtualResourceViewHolder,
        position: Int
    ) {
        return holder.bind(virtualResources[position], context)
    }
}