package com.esteban.bienestarjdc.ui.normative

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.databinding.NormativesListItemBinding
import com.esteban.bienestarjdc.network.IMAGE_URL


class NormativesRecyclerAdapter(private val context: Context, private val normatives: List<Normative>): RecyclerView.Adapter<NormativesRecyclerAdapter.NormativeViewHolder>() {

    class NormativeViewHolder(private val binding: NormativesListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(normative: Normative, context: Context){



            binding.tittle.text = normative?.tittle

            binding.root.setOnClickListener {

                val browserIntent =
                    Intent(Intent.ACTION_VIEW, Uri.parse(IMAGE_URL + normative?.document ))
                browserIntent.putExtra("id", normative?.document)
                context.startActivity(browserIntent)
            }

                /*
                Toast.makeText(context, "normativa con titulo: " +normative?.document, Toast.LENGTH_LONG).show()
                 */



        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NormativesRecyclerAdapter.NormativeViewHolder {
        val binding = NormativesListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NormativeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return normatives.size
    }

    override fun onBindViewHolder(
        holder: NormativesRecyclerAdapter.NormativeViewHolder,
        position: Int
    ) {
        return holder.bind(normatives[position], context)
    }
}
