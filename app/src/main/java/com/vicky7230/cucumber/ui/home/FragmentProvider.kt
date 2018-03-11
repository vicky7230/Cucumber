package com.vicky7230.cucumber.ui.home

import com.vicky7230.cucumber.ui.home.likes.LikesFragment
import com.vicky7230.cucumber.ui.home.likes.LikesModule
import com.vicky7230.cucumber.ui.home.recipes.RecipesFragment
import com.vicky7230.cucumber.ui.home.recipes.RecipesModule
import com.vicky7230.cucumber.ui.home.search.SearchFragment
import com.vicky7230.cucumber.ui.home.search.SearchModule
import dagger.Module
import dagger.android.ContributesAndroidInjector


/**
 * Created by vicky on 11/2/18.
 */
@Module
abstract class FragmentProvider {
    @ContributesAndroidInjector(modules = [(RecipesModule::class)])
    internal abstract fun provideRecipesFragmentFactory(): RecipesFragment

    @ContributesAndroidInjector(modules = [(LikesModule::class)])
    internal abstract fun provideLikesFragmentFactory(): LikesFragment

    @ContributesAndroidInjector(modules = [(SearchModule::class)])
    internal abstract fun provideSearchFragmentFactory(): SearchFragment
}