package com.esteban.bienestarjdc.ui.normative

import android.content.Context
import android.util.Log
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.esteban.bienestarjdc.R
import com.esteban.bienestarjdc.data.Normative
import com.esteban.bienestarjdc.network.IMAGE_URL
import kotlinx.android.synthetic.main.publications_list_item.view.*


class NormativesRecyclerAdapter(private val context: Context, private val normatives: List<Normative>): RecyclerView.Adapter<NormativesRecyclerAdapter.NormativeViewHolder>() {

    class NormativeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(normative: Normative, context: Context){



            itemView.tittle.text = normative?.tittle

            itemView.setOnClickListener {

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
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.normatives_list_item, parent, false)
        return NormativeViewHolder(v)
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