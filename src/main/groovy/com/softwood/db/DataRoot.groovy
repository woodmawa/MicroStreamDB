package com.softwood.db

import one.microstream.concurrency.XThreads
import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.storage.embedded.types.EmbeddedStorageManager

import java.nio.file.Paths
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

class DataRoot {

    private ConcurrentHashMap root = new ConcurrentHashMap()
    private AtomicLong recordId


    final EmbeddedStorageManager dbManager

    DataRoot () {
        super()

        dbManager = EmbeddedStorage.start(
                root,                  // root object
                Paths.get("data") // storage directory
        )

        XThreads.executeSynchronized {
            if (root.isEmpty()) {
                recordId = new AtomicLong(0)
                root.put ("idGenerator", recordId.get() )
            }
            else {
                Optional idGen = getById("idGenerator")
                recordId  = idGen.orElse(new AtomicLong(0) )
                root.put ("idGenerator", recordId )
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
        Long id = recordId.incrementAndGet()

        if (record.hasProperty ('id')) {
            if (record.id == 0) {
                record.id = id
            }
        }

        def result
        XThreads.executeSynchronized(() -> {
            root.putIfAbsent(id.toString(), record)
            root.put ("idGenerator", recordId ) //update with last id
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
