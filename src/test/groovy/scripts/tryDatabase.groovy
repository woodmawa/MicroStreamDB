package scripts

import com.softwood.db.DataRoot

def db = new DataRoot()

def saveRes = db.save ( "hello world")

def result = db.getById (1)
def id = db.getRecordId ("hello world")
println "found {$result} as record 1"

db.dbShutdown ()

System.exit (0)