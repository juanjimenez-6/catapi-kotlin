package com.juanjimenez.catapi.ui.FavoriteCats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.juanjimenez.catapi.models.CatFavorite
import com.juanjimenez.catapi.services.CatApiService
import kotlinx.coroutines.launch

class FavoriteCatsViewModel(private val catApiService: CatApiService) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _favorites = MutableLiveData<List<CatFavorite>>()
    val favorites: LiveData<List<CatFavorite>> = _favorites

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<Boolean>()
    val error: LiveData<Boolean> = _error

    private var _favoriteToDelete = MutableLiveData<Int?>()
    val favoriteToDelete: LiveData<Int?> = _favoriteToDelete

    private val _showConfirmDialog = MutableLiveData<Boolean>()
    val showConfirmDialog: LiveData<Boolean> = _showConfirmDialog

    init {
        fetchFavoriteCats()
    }

    fun fetchFavoriteCats() {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val images = catApiService.getFavoriteCats()
                _favorites.value = images.body()
                _success.value = true
                _error.value = false
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage
                _success.value = false
                _error.value = true
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteFavorite(favoriteId: Int, completion: (Result<Void>) -> Unit) {
        viewModelScope.launch {
            try {
                val response = catApiService.deleteFavoriteCat(favoriteId)
                if (response.isSuccessful) {
                    _success.value = true
                    _error.value = false
                    deleteFavoriteCancel()
                    completion(Result.success(response.body()) as Result<Void>)
                } else {
                    _success.value = false
                    _error.value = true
                    completion(Result.failure(Throwable(response.errorBody()?.string())))
                }
            } catch (e: Exception) {
                _success.value = false
                _error.value = true
                completion(Result.failure(e))
            }
        }
    }

    fun deleteFavoriteConfirmation(favoriteId: Int) {
        _favoriteToDelete.value = favoriteId
        _showConfirmDialog.value = true
    }

    fun deleteFavoriteCancel() {
        _favoriteToDelete.value = null
        _showConfirmDialog.value = false
    }
}
