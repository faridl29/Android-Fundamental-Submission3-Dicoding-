package com.example.submission3.views.base;

public interface Presenter<T extends View> {
    void onAttach(T view);

    void onDetach();
}
