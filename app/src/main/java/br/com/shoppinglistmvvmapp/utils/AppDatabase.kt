package br.com.shoppinglistmvvmapp.utils

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import br.com.shoppinglistmvvmapp.data.dao.ItemShoppingListDao
import br.com.shoppinglistmvvmapp.data.dao.ShoppingListDao
import br.com.shoppinglistmvvmapp.data.model.ShoppingList

@Database(entities = [(ShoppingList::class)], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun getItemShoppingListDao(): ItemShoppingListDao
    abstract fun getShoppingListDao(): ShoppingListDao

    companion object {
        private var sInstance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase {
            if (sInstance == null) {
                sInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shoppinglist.db"
                ).addCallback(AppDatabaseCallback()).fallbackToDestructiveMigration().build()
            }
            return sInstance!!
        }

        private class AppDatabaseCallback : RoomDatabase.Callback() {

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)

                sInstance?.let {
                    println("AppDatabase: OpenDB!")
                }
            }
        }
    }


}