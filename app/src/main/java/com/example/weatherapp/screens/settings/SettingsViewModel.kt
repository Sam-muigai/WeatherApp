package com.example.weatherapp.screens.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.model.units.Units
import com.example.weatherapp.repository.WeatherDBRepository
import com.example.weatherapp.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repository: WeatherDBRepository):ViewModel() {
    private val _unitList = MutableStateFlow<List<Units>>(emptyList())
    val unitList = _unitList.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .getUnits()
                .distinctUntilChanged()
                .collect { listOfUnits ->
                if (listOfUnits.isEmpty()){
                    Log.d("Empty" , "Empty List")
                }else{
                    _unitList.value = listOfUnits
                }
            }
        }
    }

    fun insertUnit(units: Units) = viewModelScope.launch {
        repository.insertUnit(units)
    }
    fun updateUnit(units: Units) = viewModelScope.launch {
        repository.updateUnits(units)
    }
    fun deleteUnit(units: Units) = viewModelScope.launch {
        repository.deleteUnits(units)
    }
    fun deleteAllUnits() = viewModelScope.launch {
        repository.deleteAllUnits()
    }
}