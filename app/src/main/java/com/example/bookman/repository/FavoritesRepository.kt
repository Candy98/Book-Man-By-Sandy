package com.example.bookman.repository

import android.app.Application
import com.example.bookman.data.Work
import com.example.bookman.db.FavoritesDao
import com.example.bookman.db.FavoritesDatabase
import org.jetbrains.anko.doAsync

class FavoritesRepository(app: Application) {
    private val favoritesDao: FavoritesDao = FavoritesDatabase.create(app).favoritesDao()

    fun getFavorites() = favoritesDao.getFavorites()

    fun getFavorite(id: String) = favoritesDao.getFavorite(id)

    fun getFavoriteCount() = favoritesDao.getFavoriteCount()

    fun addFavorite(work: Work) {
        doAsync {
            favoritesDao.addFavorite(work)
        }
    }

    fun removeFavorite(work: Work) {
        doAsync {
            favoritesDao.removeFavorite(work)
        }
    }

}
