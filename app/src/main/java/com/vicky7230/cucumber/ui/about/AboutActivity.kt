package com.vicky7230.cucumber.ui.about

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.ContextThemeWrapper
import android.view.MenuItem
import com.franmontiel.attributionpresenter.AttributionPresenter
import com.franmontiel.attributionpresenter.entities.Attribution
import com.franmontiel.attributionpresenter.entities.Library
import com.franmontiel.attributionpresenter.entities.License
import com.vicky7230.cucumber.BuildConfig
import com.vicky7230.cucumber.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, AboutActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        setSupportActionBar(toolbar as Toolbar?)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        logo_attribution.setHtml("<div>App Icon made by <a href=\"https://www.flaticon.com/authors/prosymbols\" title=\"Prosymbols\">Prosymbols</a> from <a href=\"https://www.flaticon.com/\" title=\"Flaticon\">www.flaticon.com</a> is licensed by <a href=\"http://creativecommons.org/licenses/by/3.0/\" title=\"Creative Commons BY 3.0\" target=\"_blank\">CC 3.0 BY</a></div>")

        api_attribution.setHtml("Powered By <a href=\"https://food2fork.com/\" title=\"Food2Fork\">Food2Fork.com</a>")

        val attributionPresenter =
            AttributionPresenter.Builder(ContextThemeWrapper(this, R.style.AttributionsTheme))
                .addAttributions(
                    Attribution.Builder("AttributionPresenter")
                        .addCopyrightNotice("Copyright 2017 Francisco José Montiel Navarro")
                        .addLicense(License.APACHE)
                        .setWebsite("https://github.com/franmontiel/AttributionPresenter")
                        .build()
                )
                .addAttributions(
                    Library.RX_JAVA,
                    Library.RX_ANDROID,
                    Library.GLIDE,
                    Library.RETROFIT,
                    Library.OK_HTTP,
                    Library.DAGGER_2,
                    Library.GSON
                )
                .build()

        version.text = "Version ${BuildConfig.VERSION_NAME}"

        licenses.setOnClickListener({
            attributionPresenter.showDialog("Open Source Libraries")
        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else ->
                super.onOptionsItemSelected(item)
        }
    }
}
