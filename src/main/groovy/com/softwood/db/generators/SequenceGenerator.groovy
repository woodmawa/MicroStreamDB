package com.softwood.db.generators


import java.util.concurrent.atomic.AtomicLong

class SequenceGenerator implements com.softwood.persistence.api.SequenceGenerator {

    @Delegate
    private AtomicLong value= new AtomicLong(1)

    SequenceGenerator(long initialValue = 1) {
        value = new AtomicLong(initialValue)
    }

    @Override
    public def getNext() {
        return value.getAndIncrement()
    }

}
