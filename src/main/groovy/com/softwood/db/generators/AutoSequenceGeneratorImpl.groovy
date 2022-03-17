package com.softwood.db.generators

import com.softwood.persistence.api.SequenceGenerator

import java.util.concurrent.atomic.AtomicLong

class AutoSequenceGeneratorImpl implements SequenceGenerator {

    @Delegate
    private AtomicLong value= new AtomicLong(1)

    AutoSequenceGeneratorImpl(long initialValue = 1) {
        value = new AtomicLong(initialValue)
    }

    @Override
    public def getNext() {
        return value.getAndIncrement()
    }

    @Override
    public def getCurrentId() {
        return value.get()
    }

    @Override
    void setCurrentId( id) {
        value = new AtomicLong (id)
    }
}
