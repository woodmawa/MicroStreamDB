package com.softwood.db.generators


import java.util.concurrent.atomic.AtomicLong

class SequenceGeneratorImpl implements com.softwood.persistence.api.SequenceGenerator {

    @Delegate
    private AtomicLong value= new AtomicLong(1)
    private long delta

    SequenceGeneratorImpl (long initialValue = 1, long skipValue) {
        value = new AtomicLong(initialValue)
        delta = skipValue
    }

    @Override
    public def getNext() {
        return value.getAndAdd (delta)
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
