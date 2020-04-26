package com.js.nowakelock.base

import java.util.regex.Pattern.matches

val test = "dsadsa\n" +
        "ddasdsd大厦\n" +
        "大厦的杀\n" +
        "ipoj789())())\n" +
        "      \n"

fun main() {
    var tmp = test
        .split("\n")
        .filter {it.matches(Regex("[^\n ]+"))}
    print("${tmp.size} \n $tmp")
}