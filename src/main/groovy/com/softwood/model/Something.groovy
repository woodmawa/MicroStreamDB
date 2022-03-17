package com.softwood.model

class Something {
    long id
    String name

    List<Child> children = []

    String toString() {
        "Something [id:$id, name:$name with ${children.size()} children]"
    }
}
