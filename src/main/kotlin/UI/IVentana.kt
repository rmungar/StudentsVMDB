package UI

import ViewModel.IStudentsVM
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable

interface IVentana {

    @Preview
    @Composable
    fun Ventana(StudentsViewModel: IStudentsVM)
    @Composable
    fun Boton(texto: String, onAction: () -> Unit)
    @Composable
    fun InfoMessage(message: String, onCloseInfoMessage: () -> Unit)
    @Composable
    fun estudiantes(studentsViewModel: IStudentsVM, estudiante: String)
}