package com.renat.phrasecollection.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.renat.phrasecollection.data.repository.PhraseRepository

/**
 * Factory that provides PhraseViewModel with its repository dependency.
 */
class PhraseViewModelFactory(private val repository: PhraseRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PhraseViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PhraseViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
