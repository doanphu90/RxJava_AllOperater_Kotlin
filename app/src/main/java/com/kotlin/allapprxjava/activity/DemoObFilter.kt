package com.kotlin.allapprxjava.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.allapprxjava.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DemoObFilter : AppCompatActivity() {
    val TAG:String = "DemoObFilter"
    private var animalsObservable: Observable<String>? = getAnimalsObservable()
    private var animalsObserver:Observer<String>? = getAnimalsObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo_ob_filter)

        animalsObservable?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.filter { t ->  t.contains("T")}
            ?.subscribeWith(animalsObserver)
    }
    private fun getAnimalsObserver(): Observer<String>? {
        return object : Observer<String> {
            override fun onComplete() {
                println(TAG + "All items are emitted!")
            }

            override fun onSubscribe(d: Disposable) {
                println(TAG + "onSubscribe")
            }

            override fun onNext(t: String) {
                println(TAG + "Name: $t")
            }

            override fun onError(e: Throwable) {
                println(TAG + "onError: " + e.message)
            }
        }
    }

    private fun getAnimalsObservable(): Observable<String>? {
        return Observable.fromArray("Phu", "Tan", "Thai", "Chuong")
    }
}
