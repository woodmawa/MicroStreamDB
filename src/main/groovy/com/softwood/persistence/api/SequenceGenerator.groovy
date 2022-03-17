package com.softwood.persistence.api

interface SequenceGenerator {
    def getNext()
    def getCurrentId ()
    void setCurrentId (id )
}
