package com.kotlin.allapprxjava.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kotlin.allapprxjava.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_demo__defer.*

class Demo_Map : AppCompatActivity() {
    val TAG = "Demo_FlatMap"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo__defer)

        getUsersObservable().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map(object : Function<User, User> {
                override fun apply(user: User): User {
                    user.Address = String.format("%s@rxjava.wtf", user.Name)
                    user.Name = user.Name.toUpperCase()
                    return user
                }
            })
            .subscribe(object :Observer<User>{
                override fun onComplete() {
                }

                override fun onSubscribe(d: Disposable) {
                }

                override fun onNext(user: User) {
                    Log.d(TAG, "onNext: " + user.Name + ", " + user.Gender + ", " + user.Address);
                    text_show.text =  user.Name + ", " + user.Gender + ", " + user.Address
                }

                override fun onError(e: Throwable) {

                }
            })
    }

    private fun getUsersObservable(): Observable<User> {
        var stringArr: Array<String> = arrayOf("Phu", "Tan", "Thai", "Chuong")
        var liUser: MutableList<User> = arrayListOf()
        for (s in stringArr) {
            var user = User()
            user.Name = s
            user.Gender = "male"
            liUser.add(user)
        }

        return Observable.create(ObservableOnSubscribe { emitter: ObservableEmitter<User> ->
            for (u: User in liUser) {
                if (!emitter.isDisposed) {
                    emitter.onNext(u)
                }
            }
            if (!emitter.isDisposed) {
                emitter.onComplete()
            }
        }).subscribeOn(Schedulers.io())
    }

    class User {
        private var name: String = ""
        private var address: String = ""
        private var gender: String = ""

        public var Name: String
            get() {
                return name
            }
            set(value) {
                this.name = value
            }
        public var Address: String
            get() {
                return address
            }
            set(value) {
                this.address = value
            }
        public var Gender: String
            get() {
                return gender
            }
            set(value) {
                this.gender = value
            }

    }
}
