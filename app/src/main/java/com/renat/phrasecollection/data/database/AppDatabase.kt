package com.renat.phrasecollection.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.renat.phrasecollection.data.dao.PhraseDao
import com.renat.phrasecollection.data.entity.CategoryEntity
import com.renat.phrasecollection.data.entity.PhraseCategoryCrossRef
import com.renat.phrasecollection.data.entity.PhraseEntity
import com.renat.phrasecollection.model.FixedCategory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Room database for the local-only phrase collection.
 */
@Database(
    entities = [
        PhraseEntity::class,
        CategoryEntity::class,
        PhraseCategoryCrossRef::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    /** Provides DAO methods for phrase collection data. */
    abstract fun phraseDao(): PhraseDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        /** Returns the singleton app database and seeds fixed categories on first open. */
        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "phrase_collection.db"
                )
                    .addCallback(CategorySeedCallback())
                    .build()
                    .also { instance = it }
            }
    }

    /**
     * Seeds category master rows when the database is created.
     */
    private class CategorySeedCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            instance?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    database.phraseDao().insertCategories(FixedCategory.entities())
                }
            }
        }
    }
}
