package br.com.shoppinglistmvvmapp.data.datasource.cache

//@Database(entities = [(ShoppingList::class)], version = 1, exportSchema = false)
//abstract class AppDatabase: RoomDatabase() {
//
////    abstract fun getItemShoppingListDao(): ItemShoppingListDao
////    abstract fun getShoppingListDao(): ShoppingListDao
//
//    companion object {
//        private var sInstance: AppDatabase? = null
//
//        @Synchronized
//        fun getInstance(context: Context): AppDatabase {
//            if (sInstance == null) {
//                sInstance = Room.databaseBuilder(
//                    context.applicationContext,
//                    AppDatabase::class.java,
//                    "shoppinglist.db"
//                ).addCallback(AppDatabaseCallback()).fallbackToDestructiveMigration().build()
//            }
//            return sInstance!!
//        }
//
//        private class AppDatabaseCallback : RoomDatabase.Callback() {
//
//            override fun onOpen(db: SupportSQLiteDatabase) {
//                super.onOpen(db)
//
//                sInstance?.let {
//                    println("AppDatabase: OpenDB!")
//                }
//            }
//        }
//    }
//
//
//}