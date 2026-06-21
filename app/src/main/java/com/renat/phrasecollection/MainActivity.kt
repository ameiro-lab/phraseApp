package com.renat.phrasecollection

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.renat.phrasecollection.ui.navigation.PhraseCollectionApp
import com.renat.phrasecollection.ui.theme.PhraseCollectionTheme
import com.renat.phrasecollection.viewmodel.PhraseViewModel
import com.renat.phrasecollection.viewmodel.PhraseViewModelFactory

/**
 * Main entry point that wires the Compose UI to the application repository.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val application = application as PhraseCollectionApplication
        setContent {
            val viewModel: PhraseViewModel = viewModel(
                factory = PhraseViewModelFactory(application.repository)
            )
            PhraseCollectionTheme {
                PhraseCollectionApp(viewModel = viewModel)
            }
        }
    }
}
