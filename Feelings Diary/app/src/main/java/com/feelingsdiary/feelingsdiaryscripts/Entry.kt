package com.feelingsdiary.feelingsdiaryscripts

class Entry {
    private val title : String;
    private val entry : String;

    constructor(title: Any, entry: Any) {
        this.title = title as String
        this.entry = entry as String
    }
}