package com.softwood.persistence.api

import com.softwood.db.generators.AutoSequenceGeneratorImpl
import com.softwood.db.generators.GeneratorType
import com.softwood.db.generators.GuidSequenceGeneratorImpl
import com.softwood.db.generators.SequenceGeneratorImpl

/*
 * factor class to get an id generator
 */
class Generators {

    static SequenceGenerator newInstance (GeneratorType type) {
        switch (type) {
            case GeneratorType.AUTO -> new AutoSequenceGeneratorImpl(1)
            case GeneratorType.UUID -> new GuidSequenceGeneratorImpl()
            case GeneratorType.SEQUENCE -> new SequenceGeneratorImpl (1, 9)
        }
    }
}
