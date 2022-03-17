package scripts

import com.softwood.db.DataRoot
import com.softwood.model.Child
import com.softwood.model.Something

def db = new DataRoot()

Something s = new Something(name: "will")

Child child = new Child(name:"child#1")
s.children << child
child.parent = s

db.save(s)

Optional<Something> slookup = db.getById(1)
Optional<Something> slookup2 = db.getById(2)

println "slkup: ${slookup.orElse("unknown")} hash : ${slookup.hashCode()} "
println "slkup2: ${slookup2.orElse("unknown")} hash : ${slookup2.hashCode()} "
println "db: ${db} "



db.shutdown()
System.exit(0)