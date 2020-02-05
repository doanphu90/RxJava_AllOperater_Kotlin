package com.kotlin.allapprxjava.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.kotlin.allapprxjava.R
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    val TAG: String? = "Observable: "
    private var animalsObservable: Observable<String> = getAnimalsObservable()
    private var animalsObserver: Observer<String> = getAnimalsObserver()
    var dd: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var arrInt: IntArray = intArrayOf(1, 2, 3, 4)

        Observable.fromArray(arrInt)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<IntArray> {
                override fun onComplete() {

                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(t: IntArray) {
                    println(TAG + t)
                }

                override fun onError(e: Throwable) {
                }
            })

        btn_from_just.setOnClickListener(onClick)
        btn_filter.setOnClickListener(onClick)
        btn_map.setOnClickListener(onClick)
        btn_flatmap.setOnClickListener(onClick)
        btn_ConcatMap.setOnClickListener(onClick)
        btn_Disposable.setOnClickListener(onClick)
        //
    }

    private fun getAnimalsObserver(): Observer<String> {
        return object : Observer<String> {
            override fun onComplete() {
                println(TAG + "All items are emitted!")
            }

            override fun onSubscribe(d: Disposable) {
                println(TAG + "onSubscribe")
                dd = d;
                /*Disposable is used to dispose the subscription when an Observer no longer wants to listen to Observable.
                In android disposable are very useful in avoiding memory leaks.
                call it in method destroy*/
            }

            override fun onNext(s: String) {
                println(TAG + "Name: $s")
            }

            override fun onError(e: Throwable) {
                println(TAG + "onError: " + e.message)
            }
        }
    }

    private fun getAnimalsObservable(): Observable<String> {
        return Observable.just("Ant", "Bee", "Cat", "Dog", "Fox")
    }

    private val onClick = View.OnClickListener { v ->
        when (v.id) {
            R.id.btn_from_just -> {
                animalsObservable
                    .observeOn(Schedulers.io())
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .subscribe(animalsObserver)
            }
            R.id.btn_filter -> {
                val intent = Intent(this, DemoObFilter::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_map -> {
                val intent = Intent(this, Demo_Map::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_flatmap -> {
                val intent = Intent(this, Demo_FlatMap::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_ConcatMap -> {
                val intent = Intent(this, Demo_ConcatMap::class.java)
                startActivity(intent)
                finish()
            }
            R.id.btn_Disposable -> {
                val intent = Intent(this, CompositeDisposable_Demo::class.java)
                startActivity(intent)
                finish()
            }
            else -> {

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dd?.dispose()
    }
}
