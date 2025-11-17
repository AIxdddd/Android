package com.example.lab1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.Cursor

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "app_database.db"
        private const val DATABASE_VERSION = 1

        const val TABLE_USERS = "users"
        const val COLUMN_ID = "id"
        const val COLUMN_LOGIN = "login"
        const val COLUMN_PASSWORD = "password"
        const val COLUMN_NAME = "name"
        const val COLUMN_BIRTH_DATE = "birth_date"
        const val COLUMN_GENDER = "gender"
        const val COLUMN_IS_ADMIN = "is_admin"
        const val COLUMN_AVATAR_URI = "avatar_uri"
        const val COLUMN_THEME = "theme"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USERS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_LOGIN TEXT UNIQUE NOT NULL,
                $COLUMN_PASSWORD TEXT NOT NULL,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_BIRTH_DATE TEXT,
                $COLUMN_GENDER TEXT,
                $COLUMN_IS_ADMIN INTEGER DEFAULT 0,
                $COLUMN_AVATAR_URI TEXT,
                $COLUMN_THEME TEXT DEFAULT 'light'
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USERS")
        onCreate(db)
    }

    fun addUser(user: User): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_LOGIN, user.login)
            put(COLUMN_PASSWORD, user.password)
            put(COLUMN_NAME, user.name)
            put(COLUMN_BIRTH_DATE, user.birthDate)
            put(COLUMN_GENDER, user.gender)
            put(COLUMN_IS_ADMIN, if (user.isAdmin) 1 else 0)
            put(COLUMN_AVATAR_URI, user.avatarUri)
            put(COLUMN_THEME, user.theme)
        }

        val id = db.insert(TABLE_USERS, null, values)
        db.close()
        return id
    }

    fun getUser(login: String, password: String): User? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_USERS,
            null,
            "$COLUMN_LOGIN = ? AND $COLUMN_PASSWORD = ?",
            arrayOf(login, password),
            null, null, null
        )

        return if (cursor.moveToFirst()) {
            val user = User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                login = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOGIN)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                birthDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ADMIN)) == 1,
                avatarUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR_URI)),
                theme = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THEME))
            )
            cursor.close()
            db.close()
            user
        } else {
            cursor.close()
            db.close()
            null
        }
    }

    fun getAllUsers(): List<User> {
        val users = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.query(TABLE_USERS, null, null, null, null, null, null)

        while (cursor.moveToNext()) {
            val user = User(
                id = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                login = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LOGIN)),
                password = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PASSWORD)),
                name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                birthDate = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_BIRTH_DATE)),
                gender = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GENDER)),
                isAdmin = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_ADMIN)) == 1,
                avatarUri = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AVATAR_URI)),
                theme = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THEME))
            )
            users.add(user)
        }
        cursor.close()
        db.close()
        return users
    }

    fun updateUserAdminStatus(userId: Long, isAdmin: Boolean): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_IS_ADMIN, if (isAdmin) 1 else 0)
        }

        val rowsAffected = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(userId.toString())
        )
        db.close()
        return rowsAffected > 0
    }

    fun updateUserTheme(userId: Long, theme: String): Boolean {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_THEME, theme)
        }

        val rowsAffected = db.update(
            TABLE_USERS,
            values,
            "$COLUMN_ID = ?",
            arrayOf(userId.toString())
        )
        db.close()
        return rowsAffected > 0
    }
    fun getUserTheme(userId: Long): String {
        val db = readableDatabase
        var cursor: Cursor? = null
        return try {
            cursor = db.query(
                TABLE_USERS,
                arrayOf(COLUMN_THEME),
                "$COLUMN_ID = ?",
                arrayOf(userId.toString()),
                null, null, null
            )

            if (cursor.moveToFirst()) {
                cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_THEME)) ?: "light"
            } else {
                "light" // значение по умолчанию
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "light" // значение по умолчанию в случае ошибки
        } finally {
            cursor?.close()
            db.close()
        }
    }
    fun getUserCount(): Int {
        val db = readableDatabase
        var cursor: Cursor? = null
        return try {
            cursor = db.rawQuery("SELECT COUNT(*) FROM $TABLE_USERS", null)
            if (cursor.moveToFirst()) {
                cursor.getInt(0)
            } else {
                0
            }
        } catch (e: Exception) {
            e.printStackTrace()
            0
        } finally {
            cursor?.close()
            db.close()
        }
    }

    // Дополнительный метод для проверки существования пользователей
    fun hasUsers(): Boolean {
        return getUserCount() > 0
    }
}