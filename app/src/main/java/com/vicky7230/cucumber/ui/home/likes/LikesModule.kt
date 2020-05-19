package com.vicky7230.cucumber.ui.home.likes

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.vicky7230.cucumber.di.ApplicationContext
import com.vicky7230.cucumber.ui.home.ItemOffsetDecoration
import dagger.Module
import dagger.Provides


/**
 * Created by vicky on 13/2/18.
 */
@Module
class LikesModule {

    @Provides
    fun provideLikesMvpPresenter(presenter: LikesPresenter<LikesMvpView>): LikesMvpPresenter<LikesMvpView> {
        return presenter
    }

    @Provides
    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
        return LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    @Provides
    fun provideItemOffsetDecoration(): ItemOffsetDecoration {
        return ItemOffsetDecoration(40)
    }

    @Provides
    fun provideLikesAdapter(): LikesAdapter {
        return LikesAdapter(ArrayList())
    }
}