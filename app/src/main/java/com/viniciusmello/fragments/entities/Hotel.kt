package com.viniciusmello.fragments.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.viniciusmello.fragments.COLUMN_ID
import com.viniciusmello.fragments.COLUMN_NAME
import com.viniciusmello.fragments.TABLE_HOTEL

@Entity(tableName = TABLE_HOTEL)
data class Hotel(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id:Long = 0L ,
    @ColumnInfo(name = COLUMN_NAME)
    var nome:String = "",
    var adress:String = "",
    var rating:Float = 0.0F
) {
    override fun toString(): String = this.nome
}
