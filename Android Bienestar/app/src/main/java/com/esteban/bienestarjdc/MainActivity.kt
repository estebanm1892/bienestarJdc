package com.esteban.bienestarjdc

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.FragmentTransaction
import com.esteban.bienestarjdc.ui.area.AreasFragment
import com.esteban.bienestarjdc.ui.normative.NormativesFragment
import com.esteban.bienestarjdc.ui.publication.PublicationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var areasFragment: AreasFragment
    lateinit var publicationsFragment: PublicationsFragment
    lateinit var normativesFragment: NormativesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigation : BottomNavigationView = findViewById(R.id.btm_nav)

        areasFragment = AreasFragment()
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.frame_layout, areasFragment)
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .commit()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId){
                R.id.areas -> {
                    areasFragment =
                        AreasFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, areasFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.publications -> {
                    publicationsFragment =
                        PublicationsFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, publicationsFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }
                R.id.normatives -> {
                    normativesFragment =
                        NormativesFragment()
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.frame_layout, normativesFragment)
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit()
                }

            }

            true
        }
    }

    override fun onCreatePanelMenu(featureId: Int, menu: Menu): Boolean {
        super.onCreatePanelMenu(featureId, menu)
        menuInflater.inflate(R.menu.top_nav, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var selectedOption = ""

        val browserIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/BienestarUniversitarioJDC/" ))
        startActivity(browserIntent)

        when(item?.itemId) {
            R.id.fanpage -> selectedOption = browserIntent.toString()
        }

        return super.onOptionsItemSelected(item)
    }
}
