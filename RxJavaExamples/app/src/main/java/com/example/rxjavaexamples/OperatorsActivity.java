package com.example.rxjavaexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.TextView;

import com.example.rxjavaexamples.models.Task;
import com.example.rxjavaexamples.util.DataSource;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OperatorsActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    // ui
    private TextView text;

    private CompositeDisposable disposables = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operators);
        text = findViewById(R.id.text);
        /**overView();*/
        /**singleObjectCreate();*/
        listObjectCreate();

    }

    private void fromCallable() {
        // create observable (method will not execute yet)
        Observable<List<Task>> callable = Observable
                .fromCallable(new Callable<List<Task>>() {
                    @Override
                    public List<Task> call() throws Exception {
                        // long operations. Like room 
                        return DataSource.createTasksList();
                    }
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        callable.subscribe(new Observer<List<Task>>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull List<Task> tasks) {
                Log.d(TAG, "onNext: " + tasks.size());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void fromArray() {
        Observable<Task> taskObservable = Observable
                .fromArray(DataSource.createTaskArray())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void timer() {
        Observable<Long> timerObservable = Observable
                .timer(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Throwable {
                        Log.d(TAG, "test: " + aLong);
                        return aLong <= 5;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

        timerObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "onNext: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void interval() {
        Observable<Long> intervalObservable = Observable
                .interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .takeWhile(new Predicate<Long>() {
                    @Override
                    public boolean test(Long aLong) throws Throwable {
                        Log.d(TAG,
                                "test: " + aLong + ", thread: " + Thread.currentThread().getName());
                        return aLong <= 5;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

        intervalObservable.subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Long aLong) {
                Log.d(TAG, "onNext: " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void repeat() {
        Observable<Integer> observable = Observable
                .range(0, 9)
                .subscribeOn(Schedulers.io())
                .repeat(3)
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Integer integer) {

            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void map() {
        Observable<Task> observable = Observable
                .range(0, 9)
                .subscribeOn(Schedulers.io())
                .map(new Function<Integer, Task>() {
                    @Override
                    public Task apply(Integer integer) throws Throwable {
                        Log.d(TAG, "apply: " + Thread.currentThread().getName());
                        return new Task("this is task with priority: "
                                + String.valueOf(integer), false, integer);
                    }
                }).takeWhile(new Predicate<Task>() {
                    @Override
                    public boolean test(Task task) throws Throwable {
                        return task.getPriority() < 9;
                    }
                }).observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + task.getPriority());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

    }

    private void createRange() {
        Observable<Integer> observable = Observable
                .range(0, 9)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        observable.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
                Log.d(TAG, "onNext: " + integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });

    }

    /**
     * two different ways to create an Observable using the Create() operator
     * Creating an observable from a single object
     * Creating an observable from a list object
     */

    private void createSingleObjectWithJust() {
        final Task task = new Task("Walk", false, 3);
        Observable<Task> taskObservable = Observable
                .just(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {

            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void singleObjectCreate() {
        final Task task = new Task("Walk", false, 3);
        Observable<Task> taskObservable = Observable.create(new ObservableOnSubscribe<Task>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Task> emitter) throws Throwable {
                Log.d(TAG, "subscribe: worked");
                if (!emitter.isDisposed()) {
                    emitter.onNext(task);
                    emitter.onComplete();
                }

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });
    }

    private void listObjectCreate() {

        Observable<Task> taskObservable = Observable.create(new ObservableOnSubscribe<Task>() {
            @Override
            public void subscribe(@NonNull ObservableEmitter<Task> emitter) throws Throwable {
                Log.d(TAG, "subscribe: worked");
                for (Task task : DataSource.createTasksList()) {
                    if (!emitter.isDisposed()) {
                        emitter.onNext(task);
                    }
                }
                emitter.onComplete();

            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: ");
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + task.getDescription());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: ");
            }
        });

    }


    private void overView() {
        Observable<Task> taskObservable = Observable
                .fromIterable(DataSource.createTasksList()) // observe list
                .subscribeOn(Schedulers.io()) // work in worker thread
                .filter(task -> { // filter them in worker
                    Log.d(TAG, "test: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return task.isComplete();
                })
                .observeOn(AndroidSchedulers.mainThread());

        taskObservable.subscribe(new Observer<Task>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                Log.d(TAG, "onSubscribe: " + Thread.currentThread().getName());
                Log.d(TAG, "onSubscribe: called");
                disposables.add(d);
            }

            @Override
            public void onNext(@NonNull Task task) {
                Log.d(TAG, "onNext: " + Thread.currentThread().getName());
                Log.d(TAG, "onNext: " + task.getDescription());
                /**try { blocks main thread
                 Thread.sleep(1000);
                 } catch (Exception e) {

                 }*/

            }

            @Override
            public void onError(@NonNull Throwable e) {
                Log.d(TAG, "onError: " + Thread.currentThread().getName());
                Log.d(TAG, "onError: " + e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: " + Thread.currentThread().getName());
                Log.d(TAG, "onComplete: called");
            }
        });


        disposables.add(taskObservable.subscribe(task -> Log.d(TAG, "accept: ")));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
