package com.paum.bangbang;

public interface ISubject {
    void attach(IObserver observer);
    void detach(IObserver observer);
    void inform();
}
