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
import kotlin.random.Random

class Demo_FlatMap : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo__flat_map)

        getUserObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .flatMap(object : Function<UserT, Observable<UserT>> {
                override fun apply(t: UserT): Observable<UserT> {
                    return getAddressObservable(t)
                }
            }).subscribe(object : Observer<UserT> {
                override fun onComplete() {
                    println("onComplete")
                }

                override fun onSubscribe(d: Disposable) {
                    println("Disposable")
                }

                override fun onNext(t: UserT) {
                    println("onNext: ${t.NamT}, ${t.GenderT}, ${t.AddressT}");
                }

                override fun onError(e: Throwable) {
                    println("Throwable")
                }

            })

    }

    private fun getAddressObservable(use: UserT): Observable<UserT> {
        var arrString: Array<String> = arrayOf(
            "hiep binh chanh, thu duc", "phuong1, quan 9",
            "hiep binh phuoc, thu duc", "phuong 5, quan 9"
        )
        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<UserT> ->
            var ass = Address(arrString[Random.nextInt(4) + 1])
            if (!emitter.isDisposed) {
                use.AddressT = ass.toString()
                var sleepTime: Int = Random.nextInt(1000) + 500

                Thread.sleep(sleepTime.toLong())
                emitter.onNext(use)
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io())
    }
}

private fun getUserObservable(): Observable<UserT> {
    var arrString: Array<String> = arrayOf("Phu", "Tan", "Thai", "Chuong")
    var liUser: MutableList<UserT> = arrayListOf()
    for (ch in arrString) {
        var uT = UserT()
        uT.NamT = ch
        uT.GenderT = "Male"
        liUser.add(uT)
    }

    return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<UserT> ->
        for (user in liUser) {
            if (!emitter.isDisposed) {
                emitter.onNext(user)
            }
        }
        if (!emitter.isDisposed) {
            emitter.onComplete()
        }
    }).subscribeOn(Schedulers.io())
}

data class Address(var address: String)

class UserT {
    private var nameT: String = ""
    private var addressT: String = ""
    private var genderT: String = ""

    public var NamT: String
        get() {
            return nameT
        }
        set(value) {
            this.nameT = value
        }
    public var AddressT: String
        get() {
            return addressT
        }
        set(value) {
            this.addressT = value
        }
    public var GenderT: String
        get() {
            return genderT
        }
        set(value) {
            this.genderT = value
        }
}
