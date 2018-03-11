package com.vicky7230.cucumber.di.component

import com.vicky7230.cucumber.CucumberApplication
import com.vicky7230.cucumber.di.module.ActivityBindingModule
import dagger.BindsInstance
import com.vicky7230.cucumber.di.module.ApplicationModule
import com.vicky7230.cucumber.di.module.NetworkModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


/**
 * Created by vicky on 12/2/18.
 */
@Singleton
@Component(
    modules =
    [
        AndroidSupportInjectionModule::class,
        NetworkModule::class,
        ApplicationModule::class,
        ActivityBindingModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(cayenneApplication: CucumberApplication): Builder

        fun build(): ApplicationComponent
    }

    fun inject(cayenneApplication: CucumberApplication)

}