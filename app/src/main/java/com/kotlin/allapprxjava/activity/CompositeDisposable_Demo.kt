package com.kotlin.allapprxjava.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.kotlin.allapprxjava.R
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers

class CompositeDisposable_Demo : AppCompatActivity() {

    private var disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_composite_disposable)

        disposable.add(
            getNoteObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(object : Function<Notes, Notes> {
                    override fun apply(t: Notes): Notes {
                        return t
                    }
                })
                .subscribeWith(getNotesObserver())
        )
    }

    private fun getNotesObserver(): DisposableObserver<Notes> {
        return object : DisposableObserver<Notes>() {
            override fun onComplete() {
                Log.d("CompositeDisposable", "onComplete")
            }

            override fun onNext(note: Notes) {
                Log.d("CompositeDisposable", "Note: " + note.note)
            }

            override fun onError(e: Throwable) {
                Log.d("CompositeDisposable", "onError: " + e.message)
            }
        }
    }

    private fun getNoteObservable(): Observable<Notes> {
        var liNotes: ArrayList<Notes> = getNotes()
        return Observable.create(object : ObservableOnSubscribe<Notes> {
            override fun subscribe(emitter: ObservableEmitter<Notes>) {
                for (note in liNotes) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(note);
                    }
                }
                if (!emitter.isDisposed()) {
                    emitter.onComplete();
                }
            }
        })
    }

    private fun getNotes(): ArrayList<Notes> {
        var liNotes: ArrayList<Notes> = arrayListOf()
        liNotes.add(Notes(1, "buy tooth paste!"))
        liNotes.add(Notes(2, "call brother!"));
        liNotes.add(Notes(3, "watch narcos tonight!"));
        liNotes.add(Notes(4, "pay power bill!"));

        return liNotes
    }

    data class Notes(var id: Int, var note: String)
}
