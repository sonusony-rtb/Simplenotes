package com.rtb.simplenotes.baseclasses

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.rtb.simplenotes.utils.animateSlideLeft
import com.rtb.simplenotes.utils.animateSlideRight
import org.koin.androidx.viewmodel.ext.android.getViewModel
import kotlin.reflect.KClass

open class BaseActivity<V : BaseViewModel>(private val viewModelClass: KClass<V>) : AppCompatActivity() {

    protected val TAG = "Sonu"
    protected lateinit var viewModel: V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseInit()
    }

    private fun baseInit() {
        viewModel = getViewModel(clazz = viewModelClass)
    }

    protected fun startNewActivity(activity: Class<*>, shouldFinish: Boolean = false) {
        startNewActivity(Intent(this, activity), shouldFinish)
    }

    protected fun startNewActivity(intent: Intent, shouldFinish: Boolean = false) {
        startActivity(intent)
        animateSlideLeft()
        if (shouldFinish) finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        animateSlideRight()
    }
}