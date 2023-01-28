package com.example.greedygameassignment.common.base

interface Mapper<F,T> {

    fun fromMap(from:F):T

}