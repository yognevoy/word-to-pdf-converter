package com.yognevoy.wordtopdfconverter.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yognevoy.wordtopdfconverter.R

class HomeViewModel : ViewModel() {
    private val _selectedFile = MutableLiveData<FileData?>()
    val selectedFile: LiveData<FileData?> = _selectedFile

    fun updateSelectedFile(file: FileData) {
        _selectedFile.value = file
    }

    fun convertFileToPDF(context: Context) {
        selectedFile.value?.let { file ->
            // TODO - convert file
            Toast.makeText(
                context,
                context.getString(R.string.convert_process, file.name),
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

data class FileData(val name: String, val size: Long)
