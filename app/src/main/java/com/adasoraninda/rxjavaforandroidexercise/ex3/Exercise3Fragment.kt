package com.adasoraninda.rxjavaforandroidexercise.ex3

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.adasoraninda.rxjavaforandroidexercise.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject

class Exercise3Fragment : Fragment(R.layout.fragment_ex_3) {

    private val cardNumberObservable = PublishSubject.create<String>()
    private val expirationDateObservable = PublishSubject.create<String>()
    private val cvcObservable = PublishSubject.create<String>()

    private val compositeDisposable = CompositeDisposable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<EditText>(R.id.input_card_number).addTextChangedListener {
            cardNumberObservable.onNext(it.toString())
        }

        view.findViewById<EditText>(R.id.input_expire_date).addTextChangedListener {
            expirationDateObservable.onNext(it.toString())
        }

        view.findViewById<EditText>(R.id.input_cvc).addTextChangedListener {
            cvcObservable.onNext(it.toString())
        }

        view.findViewById<ListView>(R.id.list_error)

        val submitButton = view.findViewById<Button>(R.id.button_submit)
        submitButton.isEnabled = false

        // Expiration Date
        val isExpirationDateValid: Observable<Boolean> = expirationDateObservable
            .map(ValidationUtils::checkExpirationDate)

        // Card Number
        val cardTypeObservable = cardNumberObservable
            .map(CardType::fromString)

        val isCardTypeValid = cardTypeObservable
            .map(ValidationUtils::isValidCardType)

        val isCheckSumValid = cardNumberObservable
            .map(ValidationUtils::checkCardChecksum)

        val isCreditCardValid: Observable<Boolean> =
            ValidationUtils.and(isCardTypeValid, isCheckSumValid)

        // CVC
        val requiredCvcLength = cardTypeObservable
            .map(CardType::cvcLength)

        val cvcInputLength = cvcObservable
            .map(String::length)

        val isCvcValid: Observable<Boolean> =
            ValidationUtils.equals(requiredCvcLength, cvcInputLength)

        // Check input valid
        val isFormValidObservable = ValidationUtils.and(
            isCreditCardValid,
            isExpirationDateValid,
            isCvcValid
        )

        compositeDisposable.add(
            isFormValidObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(submitButton::setEnabled)
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        compositeDisposable.clear()
    }

}