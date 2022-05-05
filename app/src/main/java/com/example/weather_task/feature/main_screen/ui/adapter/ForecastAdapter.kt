package com.example.weather_task.feature.main_screen.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weather_task.R
import com.example.weather_task.base.utils.setThrottledClickListener
import com.example.weather_task.databinding.ItemCityForecastBinding
import com.example.weather_task.feature.main_screen.domain.model.CityForecastModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class ForecastAdapter(val onClicked: (CityForecastModel) -> Unit) : AsyncListDifferDelegationAdapter<CityForecastModel>(ForecastDiffUtilCallback()) {
    init {
        delegatesManager.addDelegate(forecastAdapterDelegate())
    }

    private fun forecastAdapterDelegate() =
        adapterDelegateViewBinding<CityForecastModel, CityForecastModel, ItemCityForecastBinding>(
            { layoutInflater, parent -> ItemCityForecastBinding.inflate(layoutInflater, parent, false) }
        ) {
            bind {
                binding.apply {
                    tvCity.text = item.city
                    tvTemperature.text =  context.getString(R.string.text_template_temperature, item.temp)

                    clCityForecast.setThrottledClickListener {
                        this@ForecastAdapter.onClicked(item)
                    }
                }
            }
        }

    class ForecastDiffUtilCallback : DiffUtil.ItemCallback<CityForecastModel>() {
        override fun areItemsTheSame(oldItem: CityForecastModel, newItem: CityForecastModel): Boolean {
            return oldItem.city == newItem.city
        }

        override fun areContentsTheSame(oldItem: CityForecastModel, newItem: CityForecastModel): Boolean {
            return oldItem == newItem
        }
    }
}
