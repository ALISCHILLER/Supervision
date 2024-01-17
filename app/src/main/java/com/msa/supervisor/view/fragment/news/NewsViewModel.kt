package com.msa.supervisor.view.fragment.news

import androidx.lifecycle.viewModelScope
import com.msa.supervisor.model.data.response.news.NewsModel
import com.msa.supervisor.model.data.response.repository.TourRepository
import com.msa.supervisor.view.fragment.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * create by Ali Soleymani.
 */
@HiltViewModel
class NewsViewModel  @Inject constructor(
    private var tourRepository: TourRepository
):BaseViewModel(){

    val newsModel: com.msa.supervisor.tool.SingleLiveEvent<List<NewsModel>> by lazy { com.msa.supervisor.tool.SingleLiveEvent<List<NewsModel>>() }
    fun requestNews(){
        viewModelScope.launch(Dispatchers.IO + exceptionHandler()){
            val response=callApi2(tourRepository.requestNews())
            response.let {
                newsModel.postValue(it)
            }
        }
    }
}