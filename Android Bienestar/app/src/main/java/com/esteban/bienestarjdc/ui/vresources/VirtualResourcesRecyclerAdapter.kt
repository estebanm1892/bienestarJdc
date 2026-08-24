package com.esteban.bienestarjdc.ui.vresources

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esteban.bienestarjdc.data.VirtualResource
import com.esteban.bienestarjdc.databinding.VirtualResourcesListItemBinding
import com.esteban.bienestarjdc.ui.vresources.details.VirtualResourceActivity

class VirtualResourcesRecyclerAdapter(private val context: Context, private val virtualResources: List<VirtualResource>): RecyclerView.Adapter<VirtualResourcesRecyclerAdapter.VirtualResourceViewHolder>(){
    class VirtualResourceViewHolder(private val binding: VirtualResourcesListItemBinding): RecyclerView.ViewHolder(binding.root)  {

        fun bind(virtualResource: VirtualResource?, context: Context){

            binding.tittle.text = virtualResource?.tittle
            binding.description.text = virtualResource?.description

            binding.root.setOnClickListener {

                val intent = Intent(context, VirtualResourceActivity::class.java)
                intent.putExtra("id", virtualResource?.id)
                context.startActivity(intent)

                /*
                Toast.makeText(context, "recurso virtual:  " +virtualResource?.id, Toast.LENGTH_LONG).show()
                 */


            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VirtualResourcesRecyclerAdapter.VirtualResourceViewHolder {
        val binding = VirtualResourcesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VirtualResourceViewHolder(binding)
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
