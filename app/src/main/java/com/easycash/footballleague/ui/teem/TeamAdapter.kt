package com.easycash.footballleague.ui.teem

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.easycash.footballleague.R
import com.easycash.footballleague.databinding.ItemTeamBinding
import com.easycash.footballleague.ui.teem.model.Team
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.item_team.view.*
import javax.inject.Inject
import javax.inject.Singleton


/**
 * This is the adapter used for the search result recycler view it takes :
 * @property searchItemActionsListener SearchItemActionsListener which is used to control the actions
 * performed on the item inside the adapter
 *
 */
@Singleton
class TeamAdapter @Inject constructor() : RecyclerView.Adapter<TeamAdapter.DefaultDataViewHolder>() {

  inner class DefaultDataViewHolder(private val itemBinding: ItemTeamBinding): RecyclerView.ViewHolder(itemBinding.root)

  private val differCallback = object : DiffUtil.ItemCallback<Team>() {
    override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
      return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
      return oldItem == newItem
    }
  }

  val differ = AsyncListDiffer(this, differCallback)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultDataViewHolder {

    val itemBinding = ItemTeamBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return DefaultDataViewHolder(itemBinding)

  }

  override fun getItemCount(): Int {
    return differ.currentList.size
  }

  private var onItemClickListener: ((Team) -> Unit)? = null

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: DefaultDataViewHolder, position: Int) {
    val data = differ.currentList[position]
    holder.itemView.apply {
        GlideToVectorYou
          .init()
          .with(context)
          .load(data.crestUrl.toUri(), ivItemImage);
      txt_name.text=context.getString(R.string.name)+" "+data.name
      txt_team.text=context.getString(R.string.team_color)+" "+data.clubColors
      txt_address.text=context.getString(R.string.address)+" "+data.address
      txt_phone.text=context.getString(R.string.phone)+" "+data.phone
      txt_website.text=context.getString(R.string.website)+" "+data.website
      txt_email.text=context.getString(R.string.email)+" "+data.email

      setOnClickListener {
        onItemClickListener?.let { it(data) }
      }
    }


  }

  fun setOnItemClickListener(listener: (Team) -> Unit) {
    onItemClickListener = listener
  }

}

