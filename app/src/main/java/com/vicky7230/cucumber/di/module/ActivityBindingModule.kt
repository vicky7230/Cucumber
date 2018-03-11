package com.vicky7230.cucumber.di.module

import dagger.Module
import com.vicky7230.cucumber.ui.home.HomeActivity
import com.vicky7230.cucumber.ui.home.FragmentProvider
import com.vicky7230.cucumber.ui.home.HomeActivityModule
import dagger.android.ContributesAndroidInjector


/**
 * Created by vicky on 1/1/18.
 */
@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [(HomeActivityModule::class), (FragmentProvider::class)])
    abstract fun bindHomeActivity(): HomeActivity
}