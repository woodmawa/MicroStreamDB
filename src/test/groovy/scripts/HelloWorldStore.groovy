package scripts

import one.microstream.storage.embedded.types.EmbeddedStorage
import one.microstream.storage.embedded.types.EmbeddedStorageManager

// Initialize a storage manager ("the database") with purely defaults.
final EmbeddedStorageManager storageManager = EmbeddedStorage.start()

// print the last loaded root instance,
// replace it with a current version and store it

def root = storageManager.root()

System.out.println(storageManager.root())
storageManager.setRoot("Hello World! @ " + new Date())

def storeRef = storageManager.storeRoot()

// shutdown storage
def closed = storageManager.shutdown()
println " all shutdown [$closed]"

System.exit(0)