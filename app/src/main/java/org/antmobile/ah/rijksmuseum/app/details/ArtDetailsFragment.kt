package org.antmobile.ah.rijksmuseum.app.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.google.android.material.snackbar.Snackbar
import org.antmobile.ah.rijksmuseum.R
import org.antmobile.ah.rijksmuseum.databinding.ArtDetailsFragmentBinding
import org.antmobile.ah.rijksmuseum.domain.models.ArtDetails
import org.antmobile.ah.rijksmuseum.utils.GlideImageLoadedListener
import org.koin.android.viewmodel.ext.android.viewModel

class ArtDetailsFragment : Fragment() {

    private var _binding: ArtDetailsFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModel<ArtDetailViewModel>()

    private var errorView: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ArtDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        viewModel.run {
            showProgress.observe(viewLifecycleOwner) { showProgress ->
                if (showProgress == null) {
                    return@observe
                }
                if (showProgress) {
                    binding.content.visibility = View.GONE
                    binding.progress.visibility = View.VISIBLE
                } else {
                    binding.progress.visibility = View.GONE
                    binding.content.visibility = View.VISIBLE
                }
            }
            uiState.observe(viewLifecycleOwner) {
                renderUiState(it)
            }
            if (savedInstanceState == null) {
                loadDetails(requireArguments().artId)
            }
        }
    }

    private fun renderUiState(uiState: ArtDetailUiModel?) {
        if (uiState == null) {
            return
        }

        when (uiState) {
            is ArtDetailUiModel.ShowContent -> {
                renderContent(uiState.artDetails)
                errorView?.dismiss()
            }
            is ArtDetailUiModel.ShowError -> {
                errorView?.dismiss()
                errorView =
                    Snackbar.make(requireView(), uiState.errorMessage, Snackbar.LENGTH_INDEFINITE)
                        .apply {
                            setAction(R.string.retry) {
                                uiState.retryCallback.invoke()
                            }
                            show()
                        }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        errorView = null
        _binding = null
    }

    private fun renderContent(artDetails: ArtDetails) {
        binding.title.text = artDetails.title
        binding.description.text = artDetails.description
        binding.imageLoading.visibility = View.VISIBLE
        binding.image.contentDescription = artDetails.title
        Glide.with(this)
            .load(artDetails.imageUrl)
            .transform(CenterCrop())
            .addListener(GlideImageLoadedListener {
                _binding?.imageLoading?.visibility = View.GONE
            })
            .into(binding.image)
    }

    companion object {
        val TAG: String = ArtDetailsFragment::class.java.simpleName

        fun newInstance(artId: String) = ArtDetailsFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_ART_ID, artId)
            }
        }

        private const val KEY_ART_ID = "ArtDetailsFragment.artsId"
        private val Bundle.artId: String
            get() = getString(KEY_ART_ID)!!
    }

}
