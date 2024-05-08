package Ventana_1_Funcion

import IStudentsVM
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog


@Preview
@Composable
fun Ventanas(
    StudentsViewModel: IStudentsVM,
){
    val estudiante = StudentsViewModel.newStudent.value
    estudiantes(StudentsViewModel, estudiante)
    if (StudentsViewModel.showInfoMessage.value) {
        InfoMessage(
            message = StudentsViewModel.infoMessage.value,
            onCloseInfoMessage = {
                StudentsViewModel.showInfoMesssage(false)
            }
        )
    }
    }

@Composable
fun Boton(texto:String, onAction:() -> Unit){
    Button(
        onClick = {
            onAction()
        },
        modifier = Modifier
            .size(100.dp, 50.dp)
    ){
        Text(texto)
    }
}

@Composable
fun InfoMessage(message: String, onCloseInfoMessage: () -> Unit) {
    Dialog(
        title = "Atención",
        resizable = false,
        onCloseRequest = onCloseInfoMessage
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            Text(message)
        }
    }
}


@Composable
fun estudiantes(studentsViewModel: IStudentsVM, estudiante:String){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray)
    ){
        Row {
            Column (
                modifier = Modifier
                    .fillMaxWidth(0.5F)
                    .fillMaxHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                OutlinedTextField(
                    value = estudiante,
                    onValueChange = {
                        studentsViewModel.newStudentChange(it)
                    },
                    label = { Text("Estudiante")},
                    modifier = Modifier
                )
                Spacer(modifier = Modifier.size(50.dp))
                Boton("Añadir") { studentsViewModel.addEstudiante() }
            }
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Column(
                    modifier = Modifier
                        .border(2.dp, Color.Black)
                        .wrapContentWidth()
                        .wrapContentSize()
                ) {
                    for (estudiante in studentsViewModel.students){
                        OutlinedTextField(
                            modifier = Modifier
                                .wrapContentSize()
                                .background(Color.White),
                            value = estudiante.second,
                            enabled = false,
                            onValueChange = {},
                            trailingIcon = {
                                IconButton(
                                    enabled = true,
                                    onClick = {
                                        studentsViewModel.borrarEstudiante(studentsViewModel.students.indexOf(estudiante))
                                    }
                                ){
                                    Icon(imageVector = Icons.Default.Delete, "Eliminar Estudiante")
                                }
                            }
                        )
                    }
                }
                Spacer(modifier = Modifier.size(50.dp))
                Boton("VACIAR") { studentsViewModel.vaciarEstudiantes() }
            }
        }
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter),
        ) {
            Button(
                modifier = Modifier
                    .align(Alignment.CenterVertically),
                onClick = {
                    //tudentsViewModel.guardarEstudiante()
                }
            ){
                Text("GUARDAR CAMBIOS\n          Y SALIR")
            }
            Spacer(Modifier.size(10.dp))
        }
    }
}