package org.antmobile.ah.rijksmuseum.app.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import org.antmobile.ah.rijksmuseum.R
import org.antmobile.ah.rijksmuseum.databinding.ArtListFragmentBinding
import org.antmobile.ah.rijksmuseum.domain.models.Art
import org.antmobile.ah.rijksmuseum.utils.InfiniteScrollListener
import org.koin.android.viewmodel.ext.android.viewModel

class ArtListFragment : Fragment() {

    private var _binding: ArtListFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ArtListViewModel>()
    private val artsListAdapter: ArtsListAdapter =
        ArtsListAdapter { art -> onArtClicked(art) }

    private var errorView: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUi()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.content.adapter = null
        _binding = null
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
            adapter = artsListAdapter
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
            artsListAdapter.submitList(it)
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

    }

    companion object {
        val TAG: String = ArtListFragment::class.java.simpleName
        fun newInstance() = ArtListFragment()
    }

}
