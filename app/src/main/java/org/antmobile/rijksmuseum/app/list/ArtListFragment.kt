package org.antmobile.rijksmuseum.app.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.antmobile.rijksmuseum.R
import org.antmobile.rijksmuseum.app.details.ArtDetailsFragment
import org.antmobile.rijksmuseum.databinding.ArtListFragmentBinding
import org.antmobile.rijksmuseum.domain.models.Art
import org.antmobile.rijksmuseum.utils.InfiniteScrollListener
import org.antmobile.rijksmuseum.utils.autoCleaned
import org.koin.android.viewmodel.ext.android.viewModel

class ArtListFragment : Fragment() {

    private var binding: ArtListFragmentBinding by autoCleaned()

    private val viewModel by viewModel<ArtListViewModel>()
    private val artListAdapter: ArtListAdapter by autoCleaned {
        ArtListAdapter { art -> onArtClicked(art) }
    }

    private var errorView: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArtListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservers()
    }

    private fun initUi() {
        binding.toolbar.apply {
            inflateMenu(R.menu.art_list_menu)
            setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.action_refresh -> {
                        viewModel.refresh()
                        true
                    }
                    else -> false
                }
            }
        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        val infiniteScrollListener = object : InfiniteScrollListener(linearLayoutManager) {
            override fun onLoadMore() {
                viewModel.loadMore()
            }

            override fun isDataLoading(): Boolean = viewModel.isContentLoading()
        }
        binding.content.apply {
            adapter = artListAdapter
            layoutManager = linearLayoutManager
            addOnScrollListener(infiniteScrollListener)
            setHasFixedSize(true)
        }
    }

    private fun initObservers() {
        viewModel.showLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.content.visibility = View.GONE
                binding.progress.visibility = View.VISIBLE
            } else {
                binding.progress.visibility = View.GONE
                binding.content.visibility = View.VISIBLE
            }
        }
        viewModel.artListUiItems.observe(viewLifecycleOwner) {
            artListAdapter.submitList(it)
        }
        viewModel.artListLoadingError.observe(viewLifecycleOwner) { error ->
            if (error == null) {
                errorView?.dismiss()
                return@observe
            }
            errorView = Snackbar.make(requireView(), error.errorMessage, Snackbar.LENGTH_INDEFINITE)
                .apply {
                    setAction(R.string.retry) {
                        error.retryCallback.invoke()
                    }
                    show()
                }
        }
    }

    private fun onArtClicked(item: Art) {
        parentFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.container, ArtDetailsFragment.newInstance(item.id), ArtDetailsFragment.TAG)
            addToBackStack(null)
        }
    }

    companion object {
        val TAG: String = ArtListFragment::class.java.simpleName
        fun newInstance() = ArtListFragment()
    }

}
