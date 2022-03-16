package com.softwood.db.generators

import com.softwood.persistence.api.SequenceGenerator

import java.util.concurrent.atomic.AtomicLong

class AutoSequenceGenerator implements SequenceGenerator {

    @Delegate
    private AtomicLong value= new AtomicLong(1)

    AutoSequenceGenerator(long initialValue = 1) {
        value = new AtomicLong(initialValue)
    }

    @Override
    public def getNext() {
        return value.getAndIncrement()
    }

}
