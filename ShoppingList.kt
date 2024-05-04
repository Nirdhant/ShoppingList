package com.example.shopping_list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


data class Items(val id:Int, var name:String, var qty:Int, var isEditing:Boolean=false)
{
//isEditing:Maintain the details Whether we are currently editing shopping item or not
}



@Composable
fun Shopping() {
    var sItem by remember { mutableStateOf(listOf<Items>()) }
    /*sItems will update the list as soon as the items are added or deleted
    listof items contains the list of shopping items object,that are of data class type*/
    var showDialog by remember { mutableStateOf(false) }
    //maintain the state whether to display the box or not
    var iName by remember { mutableStateOf("") }
    var iQty by remember { mutableStateOf("") }  //Qty is int but to int it into text field we have use it as string

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center)
    {
        Button(onClick={showDialog=true} ,
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(10.dp),
            shape=CutCornerShape(10.dp),
            colors=ButtonDefaults.buttonColors(Color.Black,Color.White))
        {
            Text(text="Add Item!" ,style= TextStyle(fontWeight =FontWeight.W900 ,fontSize =20.sp ,fontFamily=FontFamily.Monospace))
        }
        /*Lazy column is a column composable allows the developers to display the long list without overwhelming the system,it only
              renders items visible on the screen as user scrolls new items are composed and off screen items are
              discarded,ensuring optimal performance,it only display the amount that need to be displayed*/

        LazyColumn(modifier= Modifier
            .fillMaxSize()
            .padding(10.dp))
        {
            items(sItem)
            {
                item ->      //we can use a keyword instead of it    ,this is it
                if(item.isEditing)
                {
                    ShoppingEditor(obj=item ,onEditComplete ={editName,editQty->sItem=sItem.map{it.copy(isEditing =false)}
                     val editedItem=sItem.find {it.id==item.id}
                      editedItem?.let { it.name=editName ;it.qty=editQty}  } )
                }
                else{
                    ShoppingItems(obj=item ,clickEdit={sItem=sItem.map{it.copy(isEditing =it.id==item.id)}} ,clickDel={sItem=sItem-item})
                }

            }
        }
    }

    if (showDialog)
    {
        AlertDialog(onDismissRequest={showDialog=false},
            confirmButton = {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(1.dp) ,horizontalArrangement=Arrangement.SpaceBetween)
                {
                    //Space between push the two buttons away from each other to ensure that there should be proper space in between
                    Button(onClick = {
                        if (iName.isNotBlank())
                        {
                            val x = Items(id = sItem.size + 1, name = iName, qty = iQty.toInt())
                            sItem = sItem + x
                            showDialog = false
                            iName = " "
                            iQty = ""
                        }
                    },shape= CutCornerShape(10.dp), colors = ButtonDefaults.buttonColors(Color.White, Color.Black))

                    {
                        Text("Add Item", style =TextStyle(fontSize =17.sp, fontWeight=FontWeight.Bold))
                    }

                    Button(onClick={showDialog=false}, shape= CutCornerShape(10.dp), colors = ButtonDefaults.buttonColors(Color.White, Color.Black))
                    {
                        Text("Cancel",style =TextStyle(fontSize =17.sp, fontWeight=FontWeight.Bold))
                    }
                }
            },

            title = {Text("Add Shopping Items", style=TextStyle(fontSize =23.sp ,fontWeight=FontWeight.Bold ,fontFamily=FontFamily.Monospace))},

            text = {
                Column()
                {
                    OutlinedTextField(value=iName ,onValueChange={iName=it} ,singleLine=true ,modifier= Modifier
                        .fillMaxWidth()
                        .padding(8.dp)  )

                    OutlinedTextField(value=iQty ,onValueChange={iQty=it} ,singleLine=true ,modifier= Modifier
                        .fillMaxWidth()
                        .padding(8.dp))
                }
            }
            , shape=CutCornerShape(20.dp)
            , containerColor=Color.Black
        )

    }
}




@Composable
fun ShoppingItems(obj:Items ,clickEdit:()->Unit ,clickDel:()->Unit)
{
    Row(modifier= Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .border(border = BorderStroke(2.dp, Color.Black), shape = CutCornerShape(15))
        ,horizontalArrangement =Arrangement.SpaceBetween)
    {
        Text(text =obj.name,modifier=Modifier.padding(20.dp),style =TextStyle(fontSize =20.sp, fontWeight=FontWeight.ExtraBold, fontFamily= FontFamily.Monospace ))
        Text(text = "Qty: ${obj.qty}",modifier=Modifier.padding(20.dp),style =TextStyle(fontSize =20.sp, fontWeight=FontWeight.ExtraBold,fontFamily= FontFamily.Monospace))

        Row (modifier=Modifier.padding(5.dp))      //edit
        {
            IconButton(onClick =clickEdit)
            {
                   Icon(imageVector =Icons.Default.Edit ,contentDescription =null)
            }
            IconButton(onClick=clickDel)
            {
                Icon(imageVector = Icons.Default.Delete ,contentDescription =null)
            }
        }


    }
}




@Composable
fun ShoppingEditor(obj:Items,onEditComplete:(String,Int)->Unit)
{
    //item of class Items which is to be edited ,OnEdit:Lambda function to edit item
    var eItem by remember { mutableStateOf(obj.name) }
    var eQty by remember { mutableStateOf(obj.qty.toString()) }
    var isEditing by remember { mutableStateOf(obj.isEditing) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(border = BorderStroke(2.dp,Color.Black))
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    )
    {
        //space evenly :placed items with same distance evenly next to each other
        Column()
        {
            BasicTextField(
                value = eItem,
                onValueChange = { eItem = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp),textStyle=TextStyle(fontSize=30.sp ,fontWeight=FontWeight.Bold ,fontFamily=FontFamily.Monospace)
            )
            //wrap content size will take up only space that it really needs+8.dp

            BasicTextField(
                value = eQty,
                onValueChange = { eQty = it },
                singleLine = true,
                modifier = Modifier
                    .wrapContentSize()
                    .padding(10.dp) ,textStyle=TextStyle(fontSize=30.sp ,fontWeight=FontWeight.Bold ,fontFamily=FontFamily.Monospace)
            )
        }

        Button(
            onClick = { isEditing = false;onEditComplete(eItem, eQty.toIntOrNull() ?: 1) },
            modifier = Modifier.padding(25.dp),shape= CutCornerShape(20), colors=ButtonDefaults.buttonColors(
                Color.Black, Color.White)
        )
        {
            Text(text = "Save", style=TextStyle(fontFamily = FontFamily.Monospace ,fontWeight= FontWeight.ExtraBold ,fontSize =17.sp))
        }
    }
}

//map is used to iterate each item in a list
//copy method allows us to copy the item and make changes to it without modifying the original one
//let function allows us to use and transform an object without confined scope,without affecting the original one