package com.example.weather_task.feature.main_screen.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.weather_task.R
import com.example.weather_task.base.utils.setAdapterAndCleanupOnDetachFromWindow
import com.example.weather_task.base.utils.setThrottledClickListener
import com.example.weather_task.databinding.FragmentMainscreenBinding
import com.example.weather_task.feature.detailed_forecast.ui.DetailedForecastFragment
import com.example.weather_task.feature.main_screen.ui.adapter.ForecastAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainScreenFragment : Fragment(R.layout.fragment_mainscreen) {
    private val viewModel by viewModel<MainScreenViewModel>()

    private var _binding: FragmentMainscreenBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Cannot access main screen binding")

    private var citiesAdapter: ForecastAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding =
            FragmentMainscreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        viewModel.viewState.observe(viewLifecycleOwner, ::render)
        viewModel.singleLiveEvent.observe(viewLifecycleOwner, ::singleEvent)

        binding.btnAddCity.setThrottledClickListener {
            viewModel.processUiEvent(UiEvent.OnAddCityClicked)
        }
    }

    private fun render(viewState: ViewState) {
        citiesAdapter?.items = viewState.forecastList
        binding.progressBar.isVisible = viewState.isLoading
        binding.btnAddCity.isGone = viewState.isLoading
    }

    private fun singleEvent(singleEvent: SingleEvent) {
        when (singleEvent) {
            is SingleEvent.ShowAddCityDialogue -> {

                val input = EditText(requireContext())

                MaterialAlertDialogBuilder(requireContext())
                    .setView(input)
                    .setMessage(getString(singleEvent.title))
                    .setPositiveButton(R.string.text_add) { _, _ ->
                        viewModel.processUiEvent(UiEvent.OnAddCityConfirmed(input.text.toString()))
                    }
                    .setNegativeButton(R.string.text_cancel) { _, _ -> }
                    .show()
            }

            is SingleEvent.ShowToast -> {
                Toast.makeText(
                    requireActivity(), singleEvent.errorMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initAdapter() {
        citiesAdapter = ForecastAdapter(onClicked = {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container, DetailedForecastFragment.newInstance(it.city))
                .addToBackStack(null).commit()
        })

        binding.rvCities.apply {
            citiesAdapter?.let { setAdapterAndCleanupOnDetachFromWindow(it) }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
