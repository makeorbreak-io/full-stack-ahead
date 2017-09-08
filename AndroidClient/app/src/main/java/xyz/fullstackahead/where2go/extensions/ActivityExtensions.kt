package xyz.fullstackahead.where2go.extensions

import android.support.v7.app.AppCompatActivity
import xyz.fullstackahead.where2go.Where2GoApp

val AppCompatActivity.app: Where2GoApp
    get() = application as Where2GoApp
