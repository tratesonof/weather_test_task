package com.example.weather_task.feature.detailed_forecast.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.weather_task.R
import com.example.weather_task.databinding.ItemHourForecastBinding
import com.example.weather_task.feature.detailed_forecast.domain.model.DetailedForecastModel
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class DetailedForecastAdapter : AsyncListDifferDelegationAdapter<DetailedForecastModel>(DetailedForecastDiffUtilCallback()) {
    init{
        delegatesManager.addDelegate(detailedForecastAdapterDelegate())
    }

    private fun detailedForecastAdapterDelegate() =
        adapterDelegateViewBinding<DetailedForecastModel, DetailedForecastModel, ItemHourForecastBinding>(
            { layoutInflater, parent ->  ItemHourForecastBinding.inflate(layoutInflater, parent, false) }
        ) {
            bind {
                binding.apply {
                    tvDatetime.text = item.datetime
                    tvTemperature.text = context.getString(R.string.text_template_temperature, item.temp)
                }
            }
        }

    class DetailedForecastDiffUtilCallback : DiffUtil.ItemCallback<DetailedForecastModel>(){
        override fun areItemsTheSame(oldItem: DetailedForecastModel, newItem: DetailedForecastModel): Boolean {
            return oldItem.datetime == newItem.datetime
        }

        override fun areContentsTheSame(oldItem: DetailedForecastModel, newItem: DetailedForecastModel): Boolean {
            return oldItem == newItem
        }
    }
}
