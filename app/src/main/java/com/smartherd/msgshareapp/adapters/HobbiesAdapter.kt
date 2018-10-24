package com.smartherd.msgshareapp.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import com.smartherd.msgshareapp.models.Hobby
import com.smartherd.msgshareapp.R
import com.smartherd.msgshareapp.activities.MainActivity
import com.smartherd.msgshareapp.showToast
import kotlinx.android.synthetic.main.list_item.view.*

class HobbiesAdapter(
    val context: Context,
    private var hobbies: MutableList<Hobby>,
    private val hobbies_full: List<Hobby> = hobbies.toList() )
    : RecyclerView.Adapter<HobbiesAdapter.MyViewHolder>(), Filterable {

    override fun getFilter(): Filter {
        return hobbiesFilter;
    }

    private var hobbiesFilter = object: Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            var filteredList = mutableListOf<Hobby>()

            if (constraint == null || constraint.isEmpty())
                filteredList = hobbies_full.toMutableList()
            else {
                var filterPattern = constraint.toString().toLowerCase().trim()

                for (item in hobbies_full) {
                    if (item.title.toLowerCase().contains((filterPattern)))
                        filteredList.add(item)
                }
            }

            var results = FilterResults()
            results.values = filteredList

            return results
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            hobbies.clear()
            hobbies.addAll( results!!.values as List<Hobby>)
            notifyDataSetChanged()
        }
    }


    companion object {
        val TAG: String = HobbiesAdapter::class.java.simpleName
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item, p0,false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return hobbies.size
    }

    override fun onBindViewHolder(p0: MyViewHolder, p1: Int) {
        val hobby = hobbies[p1]
        p0.setData(hobby, p1)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var currentHobby: Hobby? = null
        var currentPosition: Int = 0

        init {
            itemView.setOnClickListener {
                currentHobby?.let {
                    context.showToast(currentHobby!!.title + " Clicked !")
                }
            }

            itemView.imgShare.setOnClickListener {

                currentHobby?.let {
                    val message: String = "My hobby is: " + currentHobby!!.title

                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(Intent.EXTRA_TEXT, message)
                    intent.type = "text/plain"

                    context.startActivity(Intent.createChooser(intent, "Please select app: "))
                }
            }
        }

        fun setData(hobby: Hobby?, pos: Int) {

            hobby?.let {
                itemView.txvTitle.text = hobby.title
            }

            this.currentHobby = hobby
            this.currentPosition = pos
        }

    }

}