package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import com.example.convidados.model.GuestModel
import java.lang.Exception

class GuestsRepository private constructor(context: Context) {

    private val guestsDataBase = GuestsDataBase(context)

    private val guestId = DataBaseConstants.GUEST.COLUMNS.ID
    private val guestName = DataBaseConstants.GUEST.COLUMNS.NAME
    private val guestPresence = DataBaseConstants.GUEST.COLUMNS.PRESENCE
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
            values.put(guestName, guest.name)
            values.put(guestPresence, presenceInt)
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
            values.put(guestPresence, presenceInt)
            values.put(guestName, guest.name)

            val selection = "$guestId = ?"
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


    @SuppressLint("Range")
    fun getAll(): List<GuestModel> {
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestsDataBase.readableDatabase
            val projection = arrayOf(guestId, guestName, guestPresence)
            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(guestId))
                    val name = cursor.getString(cursor.getColumnIndex(guestName))
                    val presence = cursor.getInt(cursor.getColumnIndex(guestPresence))

                    guestList.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return guestList
        }
        return guestList
    }


    @SuppressLint("Range")
    fun getPresent(): List<GuestModel> {
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestsDataBase.readableDatabase

            val projection = arrayOf(guestId, guestName, guestPresence)

            val selection = "$guestPresence = ?"
            val args = arrayOf("1")

            val cursor = db.query(
                DataBaseConstants.GUEST.TABLE_NAME,
                projection,
                selection,
                args,
                null,
                null,
                null
            )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(guestId))
                    val name = cursor.getString(cursor.getColumnIndex(guestName))
                    val presence = cursor.getInt(cursor.getColumnIndex(guestPresence))

                    guestList.add(GuestModel(id, name, presence == 1))
                }
            }

            cursor.close()

        } catch (e: Exception) {
            return guestList
        }
        return guestList
    }


    @SuppressLint("Range")
    fun getAbsent(): List<GuestModel> {
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestsDataBase.readableDatabase

            val cursor =
                db.rawQuery(
                    "SELECT id, name, presence FROM Guest WHERE presence = 0",
                    null, null
                )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(guestId))
                    val name = cursor.getString(cursor.getColumnIndex(guestName))
                    val presence = cursor.getInt(cursor.getColumnIndex(guestPresence))

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