package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import androidx.core.content.contentValuesOf
import com.example.convidados.model.GuestModel
import java.lang.Exception

class GuestsRepository private constructor(context: Context) {

    private val guestsDataBase = GuestsDataBase(context)

    private val id = DataBaseConstants.GUEST.COLUMNS.ID
    private val name = DataBaseConstants.GUEST.COLUMNS.NAME
    private val presence = DataBaseConstants.GUEST.COLUMNS.PRESENCE
    private val tableName = DataBaseConstants.GUEST.TABLE_NAME

    companion object {
        private lateinit var repository: GuestsRepository

        fun getInstance(context: Context): GuestsRepository {
            if (!::repository.isInitialized) {
                repository = GuestsRepository(context)
            }
            return repository
        }
    }

    fun insert(guest: GuestModel): Boolean {
        return try {
            val db = guestsDataBase.writableDatabase
            val presenceInt = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(presence, presenceInt)
            values.put(name, guest.name)

            db.insert(tableName, null, values)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun update(guest: GuestModel): Boolean {
        return try {
            val db = guestsDataBase.writableDatabase
            val presenceInt = if (guest.presence) 1 else 0

            val values = ContentValues()
            values.put(presence, presenceInt)
            values.put(name, guest.name)

            val selection = "$id = ?"
            val args = arrayOf(guest.id.toString())

            db.update(tableName, values, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun delete(id: Int): Boolean {
        return try {
            val db = guestsDataBase.writableDatabase

            val selection = "$id = ?"
            val args = arrayOf(id.toString())

            db.delete(tableName, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAll(): List<GuestModel> {
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestsDataBase.readableDatabase
            val projection = arrayOf(id, name, presence)
            val cursor = db.query(
                tableName,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(id))
                    val name = cursor.getString(cursor.getColumnIndex(name))
                    val presence = cursor.getInt(cursor.getColumnIndex(presence))

                    guestList.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return guestList
        }
        return guestList
    }


    fun getPresent(): List<GuestModel> {
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestsDataBase.readableDatabase

            val projection = arrayOf(id, name, presence)

            val selection = "$presence= ?"
            val args = arrayOf("1")

            val cursor = db.query(
                tableName,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(id))
                    val name = cursor.getString(cursor.getColumnIndex(name))
                    val presence = cursor.getInt(cursor.getColumnIndex(presence))

                    guestList.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return guestList
        }
        return guestList
    }


    fun getAbsent(): List<GuestModel> {
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestsDataBase.readableDatabase

            val projection = arrayOf(id, name, presence)

            val selection = "$presence= ?"
            val args = arrayOf("1")

            val cursor =
                db.rawQuery(
                    "SELECT id, name, presence FROM Guest WHERE presence = 0",
                    null, null
                )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(id))
                    val name = cursor.getString(cursor.getColumnIndex(name))
                    val presence = cursor.getInt(cursor.getColumnIndex(presence))

                    guestList.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return guestList
        }
        return guestList
    }

}