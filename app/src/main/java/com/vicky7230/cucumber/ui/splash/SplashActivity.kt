package com.vicky7230.cucumber.ui.splash

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.vicky7230.cucumber.R
import com.vicky7230.cucumber.ui.home.HomeActivity
import io.reactivex.Observable.timer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init() {
        compositeDisposable.add(
            timer(1500, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    startActivity(HomeActivity.getStartIntent(this))
                    finish()
                }, {
                    Timber.e("Error.")
                })
        )
    }


    override fun onDestroy() {
        compositeDisposable.dispose()
        super.onDestroy()
    }
}
