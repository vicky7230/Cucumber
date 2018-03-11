package com.vicky7230.cucumber.ui.home

import dagger.Module
import dagger.Provides


/**
 * Created by vicky on 12/2/18.
 */
@Module
class HomeActivityModule {

    @Provides
    fun provideViewPagerAdapter(homeActivity: HomeActivity): ViewPagerAdapter {
        return ViewPagerAdapter(homeActivity.supportFragmentManager)
    }

    @Provides
    fun provideHomeMvpPresenter(presenter: HomePresenter<HomeMvpView>): HomeMvpPresenter<HomeMvpView> {
        return presenter
    }
}