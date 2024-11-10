package com.fazalulabid.axel_machinetextcompose.domain.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "accounts")
data class Account(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val fullName: String,
    val password: String,
    val dob: String,
    @ColumnInfo(name = "profile_picture_uri") val profilePictureUri: String?
)