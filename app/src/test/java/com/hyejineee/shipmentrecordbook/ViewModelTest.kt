package com.hyejineee.shipmentrecordbook

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

abstract class ViewModelTest{

    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset the main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    protected fun <T> LiveData<T>.test():LiveDataTestObserver<T>{
        val testObserver = LiveDataTestObserver<T>()
        this.observeForever(testObserver)
        return testObserver
    }
}