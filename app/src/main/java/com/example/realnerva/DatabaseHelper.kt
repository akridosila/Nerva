package com.example.realnerva

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_USERS = "Users"
        private const val COLUMN_ID = "id"
        private const val COLUMN_USERNAME = "username"
        private const val COLUMN_PASSWORD = "password"
        private const val COLUMN_AMKA = "amka"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_SURNAME = "surname"
    }
    // Na kserete den exw valei akoma limitation gia to ti prepei na einai to mail kai poso megalos o kodikos dioti testarw me apla A A A kai 1 1 1 gia pio grhgora gia thn wra

    override fun onCreate(db: SQLiteDatabase) {
        val createTableQuery = """
            CREATE TABLE $TABLE_USERS (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_NAME TEXT NOT NULL,
            $COLUMN_SURNAME TEXT NOT NULL,
            $COLUMN_AMKA TEXT UNIQUE,
            $COLUMN_USERNAME TEXT NOT NULL UNIQUE,
            $COLUMN_PASSWORD TEXT NOT NULL
        )
    """
        db.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun registerUser(username: String, password: String, amka: String, name: String, surname: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_USERNAME, username)
            put(COLUMN_PASSWORD, password)
            put(COLUMN_AMKA,amka)
            put(COLUMN_NAME,name)
            put(COLUMN_SURNAME, surname)
        }
        val result = db.insert(TABLE_USERS, null, values)
        db.close()
        if (result == -1L) {
            Log.e("DatabaseHelper", "Failed to insert user: $username, $password, $amka")//mou evgaze kati perierga errors opote eipa na kanw ena loga
        }
        return result != -1L
    }

    fun validateUser(username: String, password: String, amka: String): Boolean {
        val db = readableDatabase
        val query = """
        SELECT * FROM $TABLE_USERS 
        WHERE ($COLUMN_USERNAME = ?
        OR $COLUMN_AMKA = ?) 
        AND $COLUMN_PASSWORD = ?
    """
        val cursor = db.rawQuery(query, arrayOf(username, amka, password))
        val isValid = cursor.count > 0
        cursor.close()
        db.close()
        return isValid
    }
}