package org.antmobile.ah.rijksmuseum.app

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.antmobile.ah.rijksmuseum.R
import org.antmobile.ah.rijksmuseum.app.list.ArtListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ArtListFragment.newInstance(), ArtListFragment.TAG)
                .commitNow()
        }
    }
}
