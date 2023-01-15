package com.example.convidados.repository

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import androidx.core.content.contentValuesOf
import com.example.convidados.model.GuestModel
import java.lang.Exception

class GuestsRepository private constructor(context: Context) {

    private val guestsDataBase = GuestsDataBase(context)

//    private val id = DataBaseConstants.GUEST.COLUMNS.ID
//    private val name = DataBaseConstants.GUEST.COLUMNS.NAME
//    private val presence = DataBaseConstants.GUEST.COLUMNS.PRESENCE
//    private val tableName = DataBaseConstants.GUEST.TABLE_NAME

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
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presenceInt)
            db.insert(DataBaseConstants.GUEST.TABLE_NAME, null, values)
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
            values.put(DataBaseConstants.GUEST.COLUMNS.PRESENCE, presenceInt)
            values.put(DataBaseConstants.GUEST.COLUMNS.NAME, guest.name)

            val selection = "${DataBaseConstants.GUEST.COLUMNS.ID} = ?"
            val args = arrayOf(guest.id.toString())

            db.update(DataBaseConstants.GUEST.TABLE_NAME, values, selection, args)
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

            db.delete(DataBaseConstants.GUEST.TABLE_NAME, selection, args)
            true
        } catch (e: Exception) {
            false
        }
    }


    fun getAll(): List<GuestModel> {
        val guestList = mutableListOf<GuestModel>()

        try {
            val db = guestsDataBase.readableDatabase
            val projection = arrayOf(DataBaseConstants.GUEST.COLUMNS.ID, DataBaseConstants.GUEST.COLUMNS.NAME, DataBaseConstants.GUEST.COLUMNS.PRESENCE)
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
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

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

            val projection = arrayOf(DataBaseConstants.GUEST.COLUMNS.ID, DataBaseConstants.GUEST.COLUMNS.NAME, DataBaseConstants.GUEST.COLUMNS.PRESENCE)

            val selection = "${DataBaseConstants.GUEST.COLUMNS.PRESENCE}= ?"
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
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

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

            val projection = arrayOf(DataBaseConstants.GUEST.COLUMNS.ID, DataBaseConstants.GUEST.COLUMNS.NAME, DataBaseConstants.GUEST.COLUMNS.PRESENCE)

            val selection = "${DataBaseConstants.GUEST.COLUMNS.PRESENCE} = ?"
            val args = arrayOf("1")

            val cursor =
                db.rawQuery(
                    "SELECT id, name, presence FROM Guest WHERE presence = 0",
                    null, null
                )

            if (cursor != null && cursor.count > 0) {
                while (cursor.moveToNext()) {
                    val id = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.ID))
                    val name = cursor.getString(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.NAME))
                    val presence = cursor.getInt(cursor.getColumnIndex(DataBaseConstants.GUEST.COLUMNS.PRESENCE))

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