package com.application.developerslife.ui

import android.os.Bundle
import com.application.developerslife.R
import com.application.developerslife.mvp.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, PagerFragment())
            .commit()
    }
}
