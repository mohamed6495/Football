package com.easycash.footballleague.ui.teem


import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.easycash.footballleague.R
import com.easycash.footballleague.databinding.CompetitionsFragmentBinding
import com.easycash.footballleague.ui.competitions.CompetitionsAdapter
import com.easycash.footballleague.ui.competitions.viewModel.CompetitionsViewModel
import com.easycash.footballleague.ui.competitionsInfo.CompetitionsInfoFragmentArgs
import com.easycash.footballleague.ui.teem.viewModel.TeamViewModel
import com.easycash.footballleague.utils.Resource
import com.easycash.footballleague.utils.checkNetwork.checkConnection
import com.easycash.footballleague.utils.progressLoading
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.competitions_fragment.*
import javax.inject.Inject


@AndroidEntryPoint
class TeamFragment : Fragment(R.layout.competitions_fragment) {
    private var binding: CompetitionsFragmentBinding? = null

    //  lateinit var db: DbDatabase
    private val viewModel: TeamViewModel by viewModels()
    val payArgs: TeamFragmentArgs by navArgs()
    @set:Inject
    var adapter: TeamAdapter? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = CompetitionsFragmentBinding.inflate(inflater, container, false);

        return binding!!.root;
    }

    @SuppressLint("UseRequireInsteadOfGet")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()


        if (checkConnection.getInstance().checkNetWork(activity)) {
            viewModel.getTeam(payArgs.id)
        } else {
            if (viewModel.getTeamsCash(payArgs.id).value == null) {
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

    }

    private fun subscribeToUI() {
        viewModel.teamResponseLiveData.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { newsResponse ->
                        try_again.visibility = View.GONE
                        binding?.recyclerView?.visibility= View.VISIBLE
                        adapter?.differ?.submitList(newsResponse.teams)
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


        viewModel.getTeamsCash(payArgs.id).observe(viewLifecycleOwner) { response ->
            response.let {
                if (it!=null){
                    try_again.visibility = View.GONE
                    binding?.recyclerView?.visibility= View.VISIBLE
                    adapter?.differ?.submitList(response.teams)
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