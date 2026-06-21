package com.renat.phrasecollection

import android.app.Application
import com.renat.phrasecollection.data.database.AppDatabase
import com.renat.phrasecollection.data.repository.PhraseRepository

/**
 * Holds application-level singletons for the local database and repository.
 */
class PhraseCollectionApplication : Application() {
    /** Lazily created Room database used by the whole app process. */
    val database: AppDatabase by lazy { AppDatabase.getDatabase(this) }

    /** Repository that coordinates phrase and category persistence. */
    val repository: PhraseRepository by lazy { PhraseRepository(database.phraseDao()) }
}
