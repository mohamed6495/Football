package com.easycash.footballleague.ui.competitions


import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider.NewInstanceFactory.Companion.instance
import androidx.recyclerview.widget.LinearLayoutManager
import com.easycash.footballleague.R
import com.easycash.footballleague.databinding.CompetitionsFragmentBinding
import com.easycash.footballleague.ui.competitions.viewModel.CompetitionsViewModel
import com.easycash.footballleague.utils.Resource
import com.easycash.footballleague.utils.checkNetwork.checkConnection
import com.easycash.footballleague.utils.progressLoading
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.android.synthetic.main.competitions_fragment.*

@AndroidEntryPoint
class CompetitionsFragment : Fragment(R.layout.competitions_fragment) {
    private var binding: CompetitionsFragmentBinding? = null
    private val viewModel: CompetitionsViewModel by viewModels()
    @set:Inject
    var adapter: CompetitionsAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CompetitionsFragmentBinding.inflate(inflater, container, false);
        return binding!!.root;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        if (checkConnection.getInstance().checkNetWork(activity)) {
            viewModel.getCompetitions()
        } else {
            if (viewModel.getCompetitionsCash().value == null) {
                try_again.visibility = View.VISIBLE
                binding?.recyclerView?.visibility= View.GONE
            }
        }

        subscribeToUI()
    }


    /**
     * this method is used to setup the basics for the recycler views
     */
    private fun setupRecyclerView() {
        binding?.recyclerView?.layoutManager = LinearLayoutManager(context)
        binding?.recyclerView?.adapter = adapter

        adapter?.setOnItemClickListener {
            val bundle = Bundle().apply {
                putSerializable("id", it.id)
            }
            findNavController().navigate(
                R.id.action_competitionsFragment_to_competitionsInfoFragment, bundle
            )
        }
    }

    private fun subscribeToUI() {
        viewModel.competitionsResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        try_again.visibility = View.GONE
                        binding?.recyclerView?.visibility= View.VISIBLE
                        adapter?.differ?.submitList(newsResponse.competitions)
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        try_again.visibility = View.VISIBLE
                        binding?.recyclerView?.visibility= View.GONE
                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        }
        viewModel.getCompetitionsCash().observe(viewLifecycleOwner) { response ->
            response.let {
                if (it != null) {
                    try_again.visibility = View.GONE
                    binding?.recyclerView?.visibility= View.VISIBLE
                    adapter?.differ?.submitList(response.competitions)
                }else{
                    try_again.visibility = View.VISIBLE
                    binding?.recyclerView?.visibility= View.GONE
                }
            }
        }


    }

    private fun hideProgressBar() {
        progressLoading.HideProgress()
    }

    private fun showProgressBar() {
        progressLoading.CreateProgress(activity)

    }


}