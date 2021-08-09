package com.example.bookman.ui

import androidx.appcompat.widget.Toolbar

interface MainActivityDelegate {
    fun setupNavDrawer(toolbar: Toolbar)

    fun enableNavDrawer(enable: Boolean)
}
