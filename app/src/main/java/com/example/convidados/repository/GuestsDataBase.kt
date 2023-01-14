package com.example.convidados.repository

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class GuestsDataBase(context: Context) : SQLiteOpenHelper(context, NAME, null, 1) {
    companion object {
        private const val NAME = "guestsdb"
        private const val VERSION = 1

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
            "CREATE TABLE " + DataBaseConstants.GUEST.TABLE_NAME + "("
                    + DataBaseConstants.GUEST.COLUMNS.ID + "integer PRIMARY KEY AUTOINCREMENT, "
                    + DataBaseConstants.GUEST.COLUMNS.NAME + "text, "
                    + DataBaseConstants.GUEST.COLUMNS.PRESENCE + "integer);"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }


}