package com.easycash.footballleague.ui.competitionsInfo


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.easycash.footballleague.R
import com.easycash.footballleague.databinding.CompetitionsFragmentBinding
import com.easycash.footballleague.databinding.InfoFragmentBinding
import com.easycash.footballleague.ui.competitions.CompetitionsAdapter
import com.easycash.footballleague.ui.competitions.viewModel.CompetitionsViewModel
import com.easycash.footballleague.ui.competitionsInfo.model.CompetitionsInfoResponse
import com.easycash.footballleague.ui.competitionsInfo.viewModel.CompetitionsInfoViewModel
import com.easycash.footballleague.utils.Resource
import com.easycash.footballleague.utils.checkNetwork.checkConnection
import com.easycash.footballleague.utils.progressLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.competitions_fragment.*
import kotlinx.android.synthetic.main.item_competitions.view.*
import javax.inject.Inject


@AndroidEntryPoint
class CompetitionsInfoFragment : Fragment(R.layout.info_fragment) {
    private var binding: InfoFragmentBinding? = null
    private val viewModel: CompetitionsInfoViewModel by viewModels()
    val payArgs: CompetitionsInfoFragmentArgs by navArgs()
    @set:Inject
    var adapter: CompetitionsInfoAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = InfoFragmentBinding.inflate(inflater, container, false);
        return binding!!.root;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        if (checkConnection.getInstance()!!.checkNetWork(activity)) {
            viewModel.getCompetitionInfo(payArgs.id)
        } else {
            if (viewModel.getCompetitionsCash(payArgs.id).value == null) {
                try_again.visibility = View.VISIBLE
                binding?.scrollView?.visibility = View.GONE
            }
        }

        subscribeToUI()

        binding?.showMore?.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("id",payArgs.id)
            }
            findNavController().navigate(
                R.id.action_competitionsInfoFragment_to_teamFragment, bundle
            )
        }
    }


    /**
     * this method is used to setup the basics for the recycler views
     */
    private fun setupRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = adapter
    }

    private fun subscribeToUI() {
        viewModel.competitionsResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        setData(newsResponse)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        try_again.visibility = View.VISIBLE
                        binding?.scrollView?.visibility = View.GONE
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }

        viewModel.getCompetitionsCash(payArgs.id).observe(viewLifecycleOwner) { response ->
            response.let {
                if (response != null) {
                    setData(it)
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setData(response: CompetitionsInfoResponse) {
            try_again.visibility = View.GONE
            binding?.scrollView?.visibility = View.VISIBLE
            Glide.with(this)
                .load(response.emblemUrl)
                .apply(RequestOptions().placeholder(R.drawable.logo).error(R.drawable.logo))
                .into(binding?.img!!)
            binding?.txtName?.text = response.name
            binding?.txtArea?.text =
                response.area!!.name + getString(R.string.code) + "  " + response.code + getString(R.string.plan) + " " + response.plan
            binding?.txtDate?.text = getString(R.string.date) + " : " + response.currentSeason!!.startDate + " - " + response.currentSeason.endDate
            adapter?.differ?.submitList(response.seasons)
    }

    private fun hideProgressBar() {
        progressLoading.HideProgress()
    }

    private fun showProgressBar() {
        progressLoading.CreateProgress(activity)

    }


}