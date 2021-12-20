package com.adasoraninda.rxjavaforandroidexercise.ex2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.adasoraninda.rxjavaforandroidexercise.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class Exercise2Fragment : Fragment(R.layout.fragment_ex_2) {

    private val compositeDisposable = CompositeDisposable()

    private val subject1 = PublishSubject.create<String>()
    private val subject2 = PublishSubject.create<String>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EditText>(R.id.input_text_1).addTextChangedListener {
            subject1.onNext(it.toString())
        }

        view.findViewById<EditText>(R.id.input_text_2).addTextChangedListener {
            subject2.onNext(it.toString())
        }

        compositeDisposable.add(Observable.combineLatest(subject1, subject2, { s1, s2 ->
            "$s1 $s2"
        }).subscribe {
            view.findViewById<TextView>(R.id.text_result)?.text = it
        })

    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

}