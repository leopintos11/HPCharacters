package com.lrp.hpcharacters.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lrp.hpcharacters.model.HpCharacter
import com.lrp.hpcharacters.service.HpCharactersService
import kotlinx.coroutines.*

class MainFragmentViewModel(
    private val hpCharactersService : HpCharactersService
    ) : BaseViewModel() {

    private val _results = MutableLiveData<List<HpCharacter>>(emptyList())
    val results : LiveData<List<HpCharacter>> = _results

    fun getMarsRealEstateProperties() {
        coroutineScope.launch {
            _results.value = hpCharactersService.getAllCharacters()
        }
    }

}

class MainFragmentViewModelFactory(val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainFragmentViewModel::class.java)) {
            return MainFragmentViewModel(HpCharactersService(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}