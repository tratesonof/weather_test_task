package com.example.weather_task.feature.detailed_forecast.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_task.R
import com.example.weather_task.base.utils.setAdapterAndCleanupOnDetachFromWindow
import com.example.weather_task.databinding.FragmentDetailedForecastBinding
import com.example.weather_task.feature.detailed_forecast.ui.adapter.DetailedForecastAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailedForecastFragment : Fragment(R.layout.fragment_detailed_forecast) {

    companion object {
        private const val ARG_CITY = "city"

        fun newInstance(city: String) = DetailedForecastFragment().apply {
            arguments = bundleOf(
                ARG_CITY to city
            )
        }
    }

    private val city: String
        get() = requireArguments().getString(ARG_CITY)!!

    private val viewModel: DetailedForecastViewModel by viewModel {
        parametersOf(city)
    }

    private var _binding: FragmentDetailedForecastBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Cannot access detailed forecast binding")

    private var detailedForecastAdapter: DetailedForecastAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentDetailedForecastBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        viewModel.singleLiveEvent.observe(viewLifecycleOwner, ::singleEvent)
    }

    private fun render(viewState: ViewState) {
        detailedForecastAdapter?.items = viewState.detailedForecastList

        binding.apply {
            tvCity.text = viewState.city

            progressBar.isVisible = viewState.isLoading
            tvCity.isVisible = !viewState.isLoading
            divider.isVisible = !viewState.isLoading
        }
    }

    private fun singleEvent(singleEvent: SingleEvent) {
        when (singleEvent) {
            is SingleEvent.ShowToast -> {
                Toast.makeText(
                    requireActivity(), singleEvent.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initAdapter() {
        detailedForecastAdapter = DetailedForecastAdapter()
        binding.rvDetailedForecast.apply {
            detailedForecastAdapter?.let { setAdapterAndCleanupOnDetachFromWindow(it) }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
