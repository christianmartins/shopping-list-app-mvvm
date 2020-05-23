package br.com.shoppinglistmvvmapp.domain.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

//@Entity(primaryKeys = ["id", "userId"], indices = [Index(value = ["id", "userId"])])
data class ShoppingList (
    @SerializedName("id") /*@ColumnInfo*/ var id: String,
    @SerializedName("title") /*@ColumnInfo*/ var title: String,
    @SerializedName("description") /*@ColumnInfo*/ var description: String,
    @SerializedName("userId") /*@ColumnInfo*/ var userId: Int,
    @SerializedName("authorName") /*@ColumnInfo*/ var authorName: String,
    @SerializedName("createAt") /*@ColumnInfo*/ var createAt: String,
    @SerializedName("updateAt") /*@ColumnInfo*/ var updateAt: String,
    @SerializedName("deleted") /*@ColumnInfo*/ var deleted: Boolean,
    @SerializedName("totalItemsToComplete") /*@ColumnInfo*/ var totalItemsToComplete: Int,
    @SerializedName("currentItemsToComplete") /*@ColumnInfo*/ var currentItemsToComplete: Int,
    @Expose(deserialize = false) /*@ColumnInfo*/ var sent: Boolean
): BaseModel()