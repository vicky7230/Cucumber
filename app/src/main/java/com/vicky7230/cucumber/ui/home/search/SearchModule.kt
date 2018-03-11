package com.vicky7230.cucumber.ui.home.search

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import com.vicky7230.cucumber.di.ApplicationContext
import dagger.Module
import dagger.Provides

/**
 * Created by vicky on 17/2/18.
 */
@Module
class SearchModule {
    @Provides
    fun provideSearchMvpPresenter(presenter: SearchPresenter<SearchMvpView>): SearchMvpPresenter<SearchMvpView> {
        return presenter
    }

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    @Provides
    fun provideSearchAdapter(): SearchAdapter {
        return SearchAdapter(ArrayList())
    }
}