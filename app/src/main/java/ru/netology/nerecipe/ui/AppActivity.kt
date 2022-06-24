package ru.netology.nerecipe.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.appbar.MaterialToolbar
import ru.netology.nerecipe.R
import ru.netology.nerecipe.databinding.AppActivityBinding

class AppActivity : AppCompatActivity(R.layout.app_activity) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
    }



//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        val binding = AppActivityBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//       if( supportFragmentManager.findFragmentByTag(FeedFragment.TAG) ==null) {
//
//
//           supportFragmentManager.commit {
//               add(R.id.fragment_container, FeedFragment(), FeedFragment.TAG)
//           }
//       }
//
//    }
}