package com.application.developerslife.mvp.base

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.application.developerslife.model.network.NetworkState

/**
 * @autor d.snytko
 */
abstract class BaseActivity : AppCompatActivity(), BaseView<NetworkState> {

    override fun showError(errorMessage: String) {
        Toast.makeText(this, "Error: $errorMessage", Toast.LENGTH_LONG).show()
    }
}
