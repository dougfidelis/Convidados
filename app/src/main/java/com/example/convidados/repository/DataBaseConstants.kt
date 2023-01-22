package com.example.convidados.repository

class DataBaseConstants private constructor() {

    object GUEST {
        const val TABLE_NAME = "Guest"
        const val ID = "guestId"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val PRESENCE = "presence"
        }
    }
}