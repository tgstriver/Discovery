package com.nepxion.discovery.plugin.strategy.wrapper;

import java.util.concurrent.Callable;

public interface StrategyCallableWrapper {

    <T> Callable<T> wrapCallable(Callable<T> callable);
}