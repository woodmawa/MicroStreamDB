package com.softwood.db

import com.softwood.db.generators.GeneratorType
import com.softwood.persistence.api.Generators
import com.softwood.persistence.api.SequenceGenerator
import one.microstream.concurrency.XThreads
import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.storage.embedded.types.EmbeddedStorageManager

import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class DataRoot {

    private ConcurrentHashMap root = new ConcurrentHashMap()
    private AtomicLong recordId
    private SequenceGenerator generator


    final EmbeddedStorageManager dbManager

    DataRoot () {
        super()

        generator = Generators.newInstance(GeneratorType.AUTO)

        dbManager = EmbeddedStorage.start(
                root,                  // root object
                Paths.get("data") // storage directory
        )

        XThreads.executeSynchronized {
            if (root.isEmpty()) {
                 root.put ("latestIdValue", generator.getCurrentId() )
            }
            else {
                Optional idGen = getById("latestIdValue")
                Long latest = idGen.orElse(0)
                generator.setCurrentId(latest)
            }
            root.putIfAbsent("dbVersion", "database v1.0")
            dbManager.store(root)
        }

    }

    //empty db
    def clearDb () {
        root.clear()
        dbManager.store(root)
    }

    //save record in root map using incrementing id
    def save (record) {
        Long id

        def existing = getRecordId(record)
        if (!existing) {
            id = generator.getNext()
        } else {
            id = Long.valueOf(existing)  //essentially this is an update, use existing key
        }

        if (record.hasProperty ('id')) {
            if (record.id == 0) {
                record.id = id
            }
        }

        def result
        XThreads.executeSynchronized(() -> {
            if (!existing) {
                root.putIfAbsent(id.toString(), record)
                root.put ("latestIdValue", id ) //update with last id
            } else {
                //just an update to existing record
                root.put (id.toString(), record)
            }
            result = dbManager.store(root)
        })

        result
    }

    def getRecordId (record){
        def entry = root?.find {key, value -> value == record}
        entry?.key ?: null
    }

    def remove(record) {
        def id, result
        if (root.containsValue (record) ) {
            id = getRecordId (record)
            if (id) {
                result = root.remove(id)
            }
            save (root)
            result
        } else {
            return null
        }
    }

    //get record from root map
    Optional getById (id) {
        def match = root.get(id.toString())
        Optional.ofNullable(match)
    }

    boolean dbShutdown () {
        dbManager.shutdown()
    }

    String toString() {
        return "Root: $root"
    }
}
