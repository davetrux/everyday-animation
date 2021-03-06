package com.truxall.everydayanimation

import android.content.Context
import android.os.Build
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class Utils {

    companion object {
        @Throws(IOException::class)
        fun copyDataBase(context: Context, dbName: String) {

            val db = context.assets.open(dbName)
            val dbPath = context.getDatabasePath(dbName)
            val file = File(dbPath.path)
            if (file.exists())
                return
            val stream = FileOutputStream(dbPath)
            val buffer = ByteArray(1024)
            var length = db.read(buffer)
            while (length > 0) {
                stream.write(buffer, 0, length)
                length = db.read(buffer)
            }
            stream.flush()
            stream.close()
            db.close()
        }

        val dbName = "ArtistDatabase.db"

        fun getColorWrapper(context: Context, id: Int) : Int {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return context.getColor(id)
            } else {
                return context.getResources().getColor(id)
            }
        }
    }
}