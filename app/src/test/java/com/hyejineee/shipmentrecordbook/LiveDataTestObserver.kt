package com.hyejineee.shipmentrecordbook

import androidx.lifecycle.Observer
import java.lang.AssertionError

class LiveDataTestObserver<T> : Observer<T> {
    private val values: MutableList<T> = mutableListOf()

    override fun onChanged(t: T) {
        values.add(t)
    }

    fun assertValueSequence(expected: List<T>): LiveDataTestObserver<T> {
        var i = 0
        var actualIterator = values.iterator()
        var expectIterator = expected.iterator()

        var actualNext: Boolean
        var expectedNext: Boolean

        System.out.println(
            "actual : $values\n" +
                    "expected : $expected"
        )

        while (true) {
            actualNext = actualIterator.hasNext()
            expectedNext = expectIterator.hasNext()

            if (actualNext.not() or expectedNext.not()) break

            val actualData: T = actualIterator.next()
            val expectedData: T = expectIterator.next()

            if (expectedData != actualData) {
                throw AssertionError("Error at : $i \n Actual: ${actualData} \n Expected : ${expectedData}")
            }
            i++
        }

        if (actualNext) {
            throw AssertionError("More valued received than expected ($i)")
        }

        if (expectedNext) {
            throw AssertionError("Fewer values received than expected ($i)")
        }

        return this

    }

}