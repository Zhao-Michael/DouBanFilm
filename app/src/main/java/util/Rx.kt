package util

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers

class Rx<T> private constructor() : Observer<T> {

    companion object {
        fun <T> get(action: () -> T): Rx<T> {
            val obs = Observable.create<T> {
                val t = action()
                it.onNext(t)
                it.onComplete()
            }.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            return Rx<T>(obs)
        }
    }

    private constructor(_observer: Observable<T>) : this() {
        mObserver = _observer
        mObserver.subscribe(this)
    }

    private lateinit var mObserver: Observable<T>
    private var onNext = Consumer<T> {}
    private var onError = Consumer<Throwable> {}
    private var onComplete = {}
    private var onSubscribe = Consumer<Disposable> {}

    fun err(action: (Throwable) -> Unit): Rx<T> {
        onError = Consumer(action)
        return this
    }

    fun set(action: (T) -> Unit): Rx<T> {
        onNext = Consumer(action)
        return this
    }

    fun end(action: () -> Unit): Rx<T> {
        onComplete = { action.invoke() }
        return this
    }

    override fun onComplete() {
        onComplete.invoke()
    }

    override fun onSubscribe(d: Disposable) {
        onSubscribe.accept(d)
    }

    override fun onNext(t: T) {
        try {
            onNext.accept(t)
        } catch (ex: Exception) {
            ex.printStackTrace()
            onError.accept(ex)
        }
    }

    override fun onError(ex: Throwable) {
        onError.accept(ex)
        onComplete()
    }


}