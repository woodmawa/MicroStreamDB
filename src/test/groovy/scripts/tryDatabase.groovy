package scripts

import com.softwood.db.DataRoot

def db = new DataRoot()

def saveRes = db.save ( "hello world")

def result = db.getById (1)
def id = db.getRecordId ("hello world")
println "found [${result.orElse("unknown")}] as record 1"

db.shutdown ()

System.exit (0)