package com.yognevoy.wordtopdfconverter.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _selectedFile = MutableLiveData<FileData?>()
    val selectedFile: LiveData<FileData?> = _selectedFile

    private val _isConverting = MutableLiveData(false)
    val isConverting: LiveData<Boolean> = _isConverting

    private val _conversionCompleted = MutableLiveData(false)
    val conversionCompleted: LiveData<Boolean> = _conversionCompleted

    fun updateSelectedFile(file: FileData) {
        _selectedFile.value = file
    }

    fun startConversion() {
        _isConverting.value = true
        // TODO - convert file
        viewModelScope.launch {
            delay(2000)
            _isConverting.value = false
            _conversionCompleted.value = true
        }
    }

    fun resetConversionStatus() {
        _conversionCompleted.value = false
    }
}

data class FileData(val name: String, val size: Long)
