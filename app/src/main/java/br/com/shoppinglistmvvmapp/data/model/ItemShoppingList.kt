package br.com.shoppinglistmvvmapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
data class ItemShoppingList(
    @SerializedName("id") @PrimaryKey var id: String,
    @SerializedName("description") @ColumnInfo var description: String,
    @SerializedName("selected") @ColumnInfo var selected: Boolean,
    @SerializedName("shoppingListId") @ColumnInfo var shoppingListId: String,
    @SerializedName("deleted") @ColumnInfo var deleted: Boolean,
    @SerializedName("createAt") @ColumnInfo var createAt: String,
    @SerializedName("updateAt") @ColumnInfo var updateAt: String,
    @Expose(deserialize = false) @ColumnInfo var sent: Boolean
) : BaseModel()