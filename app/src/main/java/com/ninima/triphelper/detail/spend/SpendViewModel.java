package com.ninima.triphelper.detail.spend;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.ninima.triphelper.detail.SpendDao;
import com.ninima.triphelper.global.MyDatabase;
import com.ninima.triphelper.main.TripDao;
import com.ninima.triphelper.model.Spend;

import java.util.List;

public class SpendViewModel extends ViewModel {
    private MyDatabase database = MyDatabase.instance();
    private TripDao tripDao =  database.tripDao();
    private SpendDao spendDao = database.spendDao();
    long tid;
    int sid;
    LiveData<String> categories;
    LiveData<List<Spend>> spendList;
    LiveData<Spend> spend;

    public SpendViewModel(long tid){
        this.tid = tid;
        spendList = spendDao.getAllSpend(tid);
    }

    public SpendViewModel(int sid, boolean b){
        this.sid = sid;
        this.spend = spendDao.getOneSpend(sid);
    }

    void insertNewSpend(final Spend spend){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                spendDao.insert(spend);
            }
        });
    }

    void deleteSpend(final Spend spend){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                spendDao.delete(spend);
            }
        });
    }

    void updateSpend(final Spend spend){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                spendDao.update(spend);
            }
        });
    }

//    void getCategories(final long tid){
//        AsyncTask.execute(new Runnable() {
//            @Override
//            public void run() {
//                categories = tripDao.getCategories(tid);
//                //옵저버에서 컨버터 사용해 맵으로 고치자
//            }
//        });
//    }


    static class SpendViewModelFactory extends ViewModelProvider.NewInstanceFactory {
        private long tid;
        public SpendViewModelFactory(long tid){
            this.tid = tid;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return ((T)(new SpendViewModel(tid)));
        }
    }

    static class SpendViewModelFactory2 extends ViewModelProvider.NewInstanceFactory {
        private int sid;
        public SpendViewModelFactory2(int sid){
            this.sid = sid;
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return ((T)(new SpendViewModel(sid, true)));
        }
    }
}
