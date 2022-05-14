package com.easycash.footballleague.ui.competitions

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easycash.footballleague.R
import com.easycash.footballleague.databinding.CompetitionsFragmentBinding
import com.easycash.footballleague.databinding.ItemCompetitionsBinding
import com.easycash.footballleague.ui.competitions.model.Competition
import kotlinx.android.synthetic.main.item_competitions.view.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This is the adapter used for the search result recycler view it takes :
 * @property searchItemActionsListener SearchItemActionsListener which is used to control the actions
 * performed on the item inside the adapter
 *
 */
@Singleton
class CompetitionsAdapter @Inject constructor() : RecyclerView.Adapter<CompetitionsAdapter.DefaultDataViewHolder>() {

  inner class DefaultDataViewHolder(private val itemBinding: ItemCompetitionsBinding): RecyclerView.ViewHolder(itemBinding.root)

  private val differCallback = object : DiffUtil.ItemCallback<Competition>() {
    override fun areItemsTheSame(oldItem: Competition, newItem: Competition): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Competition, newItem: Competition): Boolean {
      return oldItem == newItem
    }
  }

  val differ = AsyncListDiffer(this, differCallback)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultDataViewHolder {

    val itemBinding = ItemCompetitionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return DefaultDataViewHolder(itemBinding)

  }

  override fun getItemCount(): Int {
    return differ.currentList.size
  }

  private var onItemClickListener: ((Competition) -> Unit)? = null

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: DefaultDataViewHolder, position: Int) {
    val data = differ.currentList[position]
    holder.itemView.apply {
        Glide.with(this)
            .load(data.emblemUrl)
            .apply(RequestOptions().placeholder(R.drawable.logo).error(R.drawable.logo))
            .into(ivItemImage)
      txt_name.text = context.getString(R.string.name)+" "+ data.name
      if (data.code!=null){
        txt_short_name.text = context.getString(R.string.short_name)+" "+ data.code
      }

      txt_number.text =  context.getString(R.string.number)+" "+data.numberOfAvailableSeasons.toString()

      setOnClickListener {
        onItemClickListener?.let { it(data) }
      }
    }
  }

  fun setOnItemClickListener(listener: (Competition) -> Unit) {
    onItemClickListener = listener
  }

}

