package com.pdf.reader.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "pdf")
data class Pdf(
    @PrimaryKey(autoGenerate = false)
    val id: Long?,
    val title: String,
    val path: String?,
    val addDate: Long?,
    val modifiedDate: Long?,
    val size: Long?,
    val time:Long?,
    var isBookmark: Boolean = false
): Serializable

