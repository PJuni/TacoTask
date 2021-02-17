package com.candybytes.taco.ui.util

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer

open class TwoSourceMediatorData<T : Any> : MediatorLiveData<T>() {


	private var firstTypeSource: LiveData<*>? = null

	private var secondTypeSource: LiveData<*>? = null

	fun <S : Any?> addSource(index: SourceIndex, source: LiveData<S>, onChanged: Observer<S>) {
		updateSource(index, source)
		addSource(source, onChanged)
	}

	fun <S : Any?> addUniqueSource(index: SourceIndex, source: LiveData<S>, onChanged: Observer<S>) {
		if (hasSourceAtPosition(index)) {
			return
		}
		addSource(source, onChanged)
	}

	private fun hasSourceAtPosition(index: SourceIndex): Boolean {
		return when (index) {
			SourceIndex.First -> firstTypeSource != null
			SourceIndex.Second -> secondTypeSource != null
		}
	}

	fun clearSources() {
		firstTypeSource?.let { removeSource(it) }
		secondTypeSource?.let { removeSource(it) }
	}

	private fun <S : Any?> updateSource(index: SourceIndex, source: LiveData<S>) {
		when (index) {
			SourceIndex.First -> {
				firstTypeSource?.let { removeSource(it) }
				firstTypeSource = source
			}

			SourceIndex.Second -> {
				secondTypeSource?.let { removeSource(it) }
				secondTypeSource = source
			}
		}
	}
}

enum class SourceIndex {
	First,
	Second
}