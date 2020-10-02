package com.example.booker.arch;

public interface BasePresenter <V extends BaseView>{
    void onViewAttach(V v);

    void onViewDetach();

    void onDestroy();
}