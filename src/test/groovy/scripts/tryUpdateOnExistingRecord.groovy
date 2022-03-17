package scripts

import com.softwood.db.DataRoot
import com.softwood.model.Child
import com.softwood.model.Something


def db = new DataRoot()

db
Something s1 = db.getById(1).get()

s1.name = "new name changed  "
Child nc = new Child()
nc.name = "new child#3"
nc.parent = s1
s1.children << nc

db.save (nc)
db.save (s1)

Something lkup = db.getById(1).get()

println "new child : $nc"

println "latest lkup $lkup"

db.shutdown()
System.exit(0)
