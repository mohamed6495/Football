package com.easycash.footballleague.ui.competitionsInfo

import android.annotation.SuppressLint
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

import com.easycash.footballleague.R
import com.easycash.footballleague.databinding.ItemCompetitionsBinding
import com.easycash.footballleague.ui.competitionsInfo.model.Season
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.item_competitions.view.*
import java.io.InputStream
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This is the adapter used for the search result recycler view it takes :
 * @property searchItemActionsListener SearchItemActionsListener which is used to control the actions
 * performed on the item inside the adapter
 *
 */
@Singleton
class CompetitionsInfoAdapter @Inject constructor() : RecyclerView.Adapter<CompetitionsInfoAdapter.DefaultDataViewHolder>() {

  inner class DefaultDataViewHolder(private val itemBinding: ItemCompetitionsBinding): RecyclerView.ViewHolder(itemBinding.root)

  private val differCallback = object : DiffUtil.ItemCallback<Season>() {
    override fun areItemsTheSame(oldItem: Season, newItem: Season): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Season, newItem: Season): Boolean {
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

  private var onItemClickListener: ((Season) -> Unit)? = null

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: DefaultDataViewHolder, position: Int) {
    val data = differ.currentList[position]
    holder.itemView.apply {
      if (data.winner!=null){
        GlideToVectorYou
          .init()
          .with(context)
          .load(data.winner.crestUrl.toUri(), ivItemImage)

        txt_name.text = data.winner.name
        txt_short_name.text = data.winner.tla
      }else{
        txt_name.text = context.getString(R.string.not_started)
      }
      txt_number.text = data.startDate +" - " + data.endDate
      setOnClickListener {
        onItemClickListener?.let { it(data) }
      }
    }
  }

  fun setOnItemClickListener(listener: (Season) -> Unit) {
    onItemClickListener = listener
  }

}

