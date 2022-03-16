package com.softwood.db.generators

import com.fasterxml.uuid.Generators
import com.softwood.persistence.api.SequenceGenerator
import com.sun.media.sound.WaveExtensibleFileReader

import java.util.concurrent.atomic.AtomicLong

class GuidSequenceGenerator implements SequenceGenerator {

    private UUID value = Generators.timeBasedGenerator().generate()

    GuidSequenceGenerator() {
        value = Generators.timeBasedGenerator().generate()
    }

    @Override
    public def getNext() {
        return value = Generators.timeBasedGenerator().generate()
    }

}
