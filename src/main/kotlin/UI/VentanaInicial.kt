package UI

import Files.GestorFichero
import Ventana_1_Funcion.VentanaDB
import Ventana_1_Funcion.VentanaF
import ViewModel.StudentsViewModelDB
import ViewModel.StudentsViewModelIF
import androidx.compose.foundation.layout.*
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun VentanaPrincipal(){
    var verVentanaPrincipal by remember { mutableStateOf(true) }
    var verVentanaDB by remember { mutableStateOf(false) }
    var verVentanaF by remember { mutableStateOf(false) }
    Seleccion(
        verVentanaPrincipal,
        verVentanaDB,
        verVentanaF,
        {
            verVentanaPrincipal = false
            verVentanaF = true
        },
        {
            verVentanaPrincipal = false
            verVentanaDB = true
        }
    )
}


@Composable
fun Seleccion(verVentanaPrincipal:Boolean, verVentanaDB:Boolean, verVentanaF:Boolean, onFile: ()-> Unit, onDataBase: ()->Unit){
    if (verVentanaPrincipal && !verVentanaF && !verVentanaDB){
        Box(modifier = Modifier
            .fillMaxSize(),
            contentAlignment = Alignment.Center
        ){
            Column {
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    RadioButton(

                        onClick = { onFile() },
                        selected = false,
                        modifier = Modifier,
                        enabled = true
                    )
                    Text(
                        text = "Fichero de texto",

                        )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = false,
                        onClick = { onDataBase() },
                        modifier = Modifier,
                        enabled = true
                    )
                    Text(
                        text = "Base de datos"
                    )
                }
            }
        }
    }
    else if (!verVentanaPrincipal && verVentanaF && !verVentanaDB){
        val gestorFichero = GestorFichero()
        val fichero = gestorFichero.crearFichero("Students.txt")
        val studentsViewModelIF = StudentsViewModelIF(gestorFichero,fichero!!)
        VentanaF().Ventana(studentsViewModelIF)
    }
    else{
        val studentsViewModelDB = StudentsViewModelDB()
        VentanaDB().Ventana(studentsViewModelDB)
    }
}