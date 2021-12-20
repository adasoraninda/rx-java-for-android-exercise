package com.adasoraninda.rxjavaforandroidexercise.ex1

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.adasoraninda.rxjavaforandroidexercise.R
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class Exercise1Fragment : Fragment(R.layout.fragment_ex_1) {

    private val compositeDisposable = CompositeDisposable()
    private val subject = PublishSubject.create<String>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EditText>(R.id.input_text).addTextChangedListener {
            subject.onNext(it.toString())
        }

        compositeDisposable.add(subject
            .map { if (it.length > 7) "Text to long" else it }
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe(this::setText))

        if (savedInstanceState != null) {
            val value = savedInstanceState.getString("value").orEmpty()
            setText(value)
        }

    }

    private fun setText(value: String) {
        val colorRes = if (value.length > 7) R.color.purple_700
        else R.color.black

        view?.findViewById<TextView>(R.id.text_result)?.apply {
            text = value
            setTextColor(ContextCompat.getColor(requireContext(), colorRes))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val value = view?.findViewById<TextView>(R.id.text_result)?.text.toString()
        outState.putString("value", value)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

}