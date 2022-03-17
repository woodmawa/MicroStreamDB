package com.softwood.db.generators

import com.fasterxml.uuid.Generators
import com.softwood.persistence.api.SequenceGenerator
import com.sun.media.sound.WaveExtensibleFileReader

import java.util.concurrent.atomic.AtomicLong

class GuidSequenceGeneratorImpl implements SequenceGenerator {

    private ThreadLocal<UUID> value

    GuidSequenceGeneratorImpl() {
        value.set ( Generators.timeBasedGenerator().generate() )
    }

    @Override
    public def getNext() {
        value.set ( Generators.timeBasedGenerator().generate() )
        return value.get()
    }

    @Override
    public def getCurrentId() {
        return value.get()
    }

    @Override
    void setCurrentId( id) {
        value.set (id)
    }

}
