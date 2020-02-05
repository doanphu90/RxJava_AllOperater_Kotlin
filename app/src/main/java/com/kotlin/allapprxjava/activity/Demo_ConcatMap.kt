package com.kotlin.allapprxjava.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kotlin.allapprxjava.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.*

class Demo_ConcatMap : AppCompatActivity() {
    val TAG:String="Demo_ConcatMap"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo__concat_map)

        getUserObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .concatMap(object : Function<Demo_Map.User, Observable<Demo_Map.User>> {
                override fun apply(t: Demo_Map.User): Observable<Demo_Map.User> {
                    return getAddressObservable(t)
                }
            }).subscribe(object : Observer<Demo_Map.User> {
                override fun onComplete() {
                    println("${TAG} - onComplete")
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onNext(t: Demo_Map.User) {
                    println("${TAG} - onNext: ${t.Name} - ${t.Gender} - ${t.Address}")
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    private fun getAddressObservable(item: Demo_Map.User): Observable<Demo_Map.User> {
        var arrAdress: Array<String> = arrayOf(
            "Le thanh ton, Q1", "Pham van dong, Q.Thu duc", "Dinh bo linh, Q.binh thanh",
            "Phan van tri, Gò vap"
        )
        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<Demo_Map.User> ->
            var add = Address(arrAdress[Random().nextInt(3) + 0])
            if (!emitter.isDisposed) {
                item.Address = add.toString()

                var sleepItem: Int = Random().nextInt(1000) + 500
                Thread.sleep(sleepItem.toLong())
                emitter.onNext(item)
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io())
    }

    private fun getUserObservable(): Observable<Demo_Map.User> {
        var arrAdress: Array<String> = arrayOf("Putin", "Tap Can Binh", "Trump", "Obama")
        var liUser: MutableList<Demo_Map.User> = arrayListOf()
        for (str in arrAdress) {
            var user = Demo_Map.User()
            user.Name = str
            user.Gender = "Male"
            liUser.add(user)
        }

        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<Demo_Map.User> ->
            for (item in liUser) {
                if (!emitter.isDisposed) {
                    emitter.onNext(item)
                }
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io())
    }
}
